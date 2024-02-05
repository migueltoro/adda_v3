package us.lsi.tiposrecursivos;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import us.lsi.common.Preconditions;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;
import us.lsi.tiposrecursivos.BinaryTrees.BinaryTreeLevel;
import us.lsi.tiposrecursivos.parsers.BinaryTreeLexer;
import us.lsi.tiposrecursivos.parsers.BinaryTreeParser;

public sealed interface BinaryTree<E> permits BEmpty,BLeaf,BTree {
	
	public enum BinaryType{Empty,Leaf,Binary}
	
	BinaryType type();
	int size();
	default int sizeDifferent() {
		return (int) this.byDepth().distinct().count();
	}
	int height();
	BinaryTree<E> copy();
	BinaryTree<E> reverse();
	<R> BinaryTree<R> map(Function<E, R> f);
	boolean isEmpty();
	Optional<E> optionalLabel();
	boolean equals(Object t);
	int hashCode();
	
	public default Stream<BinaryTree<E>> byDepth() {
		return BinaryTrees.byDeph(this);
	}
	
	public default Stream<BinaryTreeLevel<E>> byLevel() {
		return BinaryTrees.byLevel(this);
	}
	
	public static <E> BinaryTree<E> empty() {
		return new BEmpty<E>();
	}
	
	public static <E> BinaryTree<E> leaf(E label) {
		Preconditions.checkNotNull(label,"La etiqueta no puede ser null");
		return new BLeaf<E>(label);
	}
	
	public static <E> BinaryTree<E> binary(E label, BinaryTree<E> left, BinaryTree<E> right) {
		Preconditions.checkNotNull(label,"La etiqueta no puede ser null");
		Preconditions.checkNotNull(left,"El árbol izquierdo no puede ser null");
		Preconditions.checkNotNull(right,"El árbol derecho no puede ser null");
		if(left.isEmpty() && right.isEmpty()) return new BLeaf<E>(label);
		return new BTree<E>(label,left,right);
	}
	
	@SuppressWarnings("unchecked")
	public static BinaryTree<String> parse(String s) {
		BinaryTreeLexer lexer = new BinaryTreeLexer(CharStreams.fromString(s));
		BinaryTreeParser parser = new BinaryTreeParser(new CommonTokenStream(lexer));
		ParseTree parseTree = parser.binary_tree();
		BinaryTree<String> tree = (BinaryTree<String>) parseTree.accept(new BinaryTreeVisitorC());
		return tree;
	}
	
	public static <E> BinaryTree<E> parse(String s, Function<String,E> f) {
		BinaryTree<String> tree = BinaryTree.parse(s);
		return tree.map(f);
	}
	
	public static <E,R> BinaryTree<R> map(BinaryTree<E> tree, Function<E,R> f) {
		return switch(tree) {
		case BEmpty<E> t -> BinaryTree.empty();
		case BLeaf<E> t -> BinaryTree.leaf(f.apply(t.label()));
		case BTree<E> t -> BinaryTree.binary(f.apply(t.label()),
				BinaryTree.map(t.left(),f),BinaryTree.map(t.right(),f));
		};
	}
	
	public default Boolean containsLabel(E label) {
		return BinaryTrees.containsLabel(this,label);
	}
	
	public default Boolean isOrdered(Comparator<E> cmp) {
		return BinaryTrees.isOrdered(this,cmp);
	}
	
	public default BinaryTree<E> equilibrate() {
		return BinaryTrees.equilibrate(this);
	}
	
	public static record BEmpty<E>() implements BinaryTree<E> {
		public BinaryType type() { return BinaryType.Empty;}
		public boolean isEmpty() {return true;}
		public Optional<E> optionalLabel() { return Optional.empty(); }
		public int size() { return 0; }
		public int height() { return -1; }
		public BinaryTree<E> copy() { return BinaryTree.empty(); }
		public BinaryTree<E> reverse() { return BinaryTree.empty(); }
		public <R> BinaryTree<R> map(Function<E, R> f) { return BinaryTree.empty();}
		public String toString() { return "_"; }
	}
	
	public static record BLeaf<E>(E label) implements BinaryTree<E> {
		public BinaryType type() { return BinaryType.Leaf;}
		public boolean isEmpty() {return false;}
		public Optional<E> optionalLabel() { return Optional.of(this.label()); }
		public int size() { return 1; }
		public int height() { return 0; }
		public BinaryTree<E> copy() { return BinaryTree.leaf(this.label()); }
		public BinaryTree<E> reverse() { return BinaryTree.leaf(this.label()); }
		public <R> BinaryTree<R> map(Function<E, R> f) { return BinaryTree.leaf(f.apply(this.label()));}
		public String toString() { return this.label().toString(); }
	}
	
	public static record BTree<E>(E label, BinaryTree<E> left, BinaryTree<E> right) 
			implements BinaryTree<E> {
		public BinaryType type() { return BinaryType.Binary;}
		public boolean isEmpty() {return false;}
		public Optional<E> optionalLabel() { return Optional.of(this.label()); }
		public int size() { return 1+this.left().size()+this.right().size();}
		public int height() {return 1 + Math.max(this.left().height(), this.right().height());}
		public BinaryTree<E> copy() { return BinaryTree.binary(this.label(),this.left().copy(),this.right().copy()); }
		public BinaryTree<E> reverse() { return BinaryTree.binary(this.label(),this.right().copy(),this.left().copy()); }
		public <R> BinaryTree<R> map(Function<E, R> f) { return BinaryTree.binary(f.apply(this.label()),
				this.left().map(f),this.right().map(f));}
		public String toString() { return String.format("%s(%s,%s)",
				this.label().toString(),this.left().toString(),this.right().toString());}
	}
	
}