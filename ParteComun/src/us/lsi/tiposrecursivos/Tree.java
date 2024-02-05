package us.lsi.tiposrecursivos;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import us.lsi.common.List2;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;
import us.lsi.tiposrecursivos.Tree.TNary;
import us.lsi.tiposrecursivos.Trees.TreeLevel;
import us.lsi.tiposrecursivos.parsers.TreeLexer;
import us.lsi.tiposrecursivos.parsers.TreeParser;

public sealed interface Tree<E> permits TEmpty, TLeaf, TNary{
	
	public enum TreeType{Empty,Leaf,Nary}
	
	public static <R> Tree<R> empty() {
		return new TEmpty<>();
	}
	
	public static <R> Tree<R> leaf(R label) {
		return new TLeaf<>(label);
	}

	public static <R> Tree<R> nary(R label, List<Tree<R>> elements) {
		if(elements.isEmpty()) return Tree.leaf(label);
		else return new TNary<R>(label,new ArrayList<>(elements));
	}

	@SafeVarargs
	public static <R> Tree<R> nary(R label, Tree<R>... elements) {
		List<Tree<R>> ls = Arrays.stream(elements).collect(Collectors.toList());
		if(ls.isEmpty()) return Tree.leaf(label);
		else return Tree.nary(label,ls);
	}

	@SuppressWarnings("unchecked")
	public static Tree<String> parse(String s){
		TreeLexer lexer = new TreeLexer(CharStreams.fromString(s));
		TreeParser parser = new TreeParser(new CommonTokenStream(lexer));
	    ParseTree parseTree = parser.nary_tree();
	    Tree<String> tree =  (Tree<String>) parseTree.accept(new TreeVisitorC());
	    return tree;
	}
	
	public static <R> Tree<R> parse(String s, Function<String,R> f){
		Tree<String> tree = Tree.parse(s);
		return tree.map(f);
	}
		
	TreeType type();
	boolean isEmpty();
	int size();
	default int sizeDifferent() {
		return (int) this.byDepth().distinct().count();
	}
	int height();
	Tree<E> copy();
	Tree<E> reverse();
	<R> Tree<R> map(Function<E, R> f);
	String toString();
	List<Tree<E>> children();
	int childrenNumber();
	Optional<E> optionalLabel();
	boolean equals(Object obj);
	int hashCode();
	
	public default Stream<Tree<E>> byDepth(){
		return Trees.byDeph(this);
	}
	
	public default Stream<TreeLevel<E>> byLevel(){
		return Trees.byLevel(this);
	}
	
	public default List<Tree<E>> level(Integer n){
		return this.byLevel().filter(e->e.level().equals(n)).map(e->e.tree()).toList();
	}
	
	public static record TEmpty<E>() implements Tree<E> {
		public TreeType type() { return TreeType.Empty;}
		public boolean isEmpty() {return true;};
		public Optional<E> optionalLabel() { return Optional.empty(); }
		public List<Tree<E>> children() {return new ArrayList<>();}
		public int size() { return 0; }
		public int height() { return -1; }
		public Tree<E> copy() { return Tree.empty(); }
		public Tree<E> reverse() { return Tree.empty(); }
		public <R> Tree<R> map(Function<E, R> f) { return Tree.empty();}
		public String toString() { return "_"; }
		public int childrenNumber() { return 0;}
	}
	
	public static record TLeaf<E>(E label) implements Tree<E> {
		public TreeType type() { return TreeType.Leaf;}
		public boolean isEmpty() {return false;};
		public Optional<E> optionalLabel() { return Optional.of(this.label()); }
		public List<Tree<E>> children() {return new ArrayList<>();}
		public int size() { return 1; }
		public int height() { return 0; }
		public Tree<E> copy() { return Tree.leaf(this.label()); }
		public Tree<E> reverse() { return Tree.leaf(this.label()); }
		public <R> Tree<R> map(Function<E, R> f) { return Tree.leaf(f.apply(this.label()));}
		public String toString() { return this.label().toString(); }
		public int childrenNumber() { return 0;}
	}
	
	public static record TNary<E>(E label, List<Tree<E>> children) implements Tree<E> {
		public TreeType type() { return TreeType.Nary;}
		public boolean isEmpty() {return false;};
		public Optional<E> optionalLabel() { return Optional.of(this.label()); }
		public int size() { return  1+(int)children().stream().mapToInt(x->x.size()).sum();}
		public int height() { return 1+ this.children().stream().mapToInt(x->x.height()).max().getAsInt();}
		public Tree<E> copy() { return Tree.nary(label(),children().stream().map(x->x.copy()).toList()); }
		public Tree<E> reverse() { 
			List<Tree<E>> nElements = List2.reverse(this.children()).stream().map(x -> x.reverse()).toList();
			return Tree.nary(this.label(), nElements);
		}
		public <R> Tree<R> map(Function<E, R> f) { 
			List<Tree<R>> nElements = this.children().stream().map(x->x.map(f)).collect(Collectors.toList());	
			return Tree.nary(f.apply(label), nElements);
		}
		public String toString() {
			return label().toString()
					+ children().stream().map(x -> x.toString()).collect(Collectors.joining(",", "(", ")"));
		}
		public Tree<E> element(int index) {return this.children().get(index); }
		public int childrenNumber() { return this.children().size();}
	}
	
}