package us.lsi.tiposrecursivos;

import java.io.Writer;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;
import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.MutableType;
import us.lsi.streams.Stream2;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;



public class BinaryTrees {


	public static <E> Stream<BinaryTree<E>> byDeph(BinaryTree<E> tree) {
		return Stream2.ofIterator(DepthPathBinaryTree.of(tree));
	}
	
	public static <E> Stream<BinaryTreeLevel<E>> byLevel(BinaryTree<E> tree) {
		return Stream2.ofIterator(BreadthPathBinaryTree.of(tree));
	}
	
	public static <E> void toDot(BinaryTree<E> tree, String file) {
		Graph<Nv<E>,DefaultEdge> graph = 
				new SimpleDirectedGraph<Nv<E>,DefaultEdge>(null,()->new DefaultEdge(),true);
		BinaryTree<Nv<E>> tt = BinaryTrees.map(tree);
		toDot1(tt,graph);
		toDot2(tt,graph);
		toDot3(graph,file,v->v.v()!=null?v.v().toString().toString():"_",e->"");
	}
	
	private static Integer nv = 0;
	public static Integer nextInteger() {
		Integer n = nv;
		nv++;
		return n;
	}
	
	public static record Nv<E>(Integer n, E v) {
		public static <E> Nv<E> of(Integer n, E v) {return new Nv<E>(n,v);}
		public String toString() {return String.format("(%d,%s)",this.n(),this.v());}
	}
	
	public static <E> BinaryTree<Nv<E>> map(BinaryTree<E> tree) {
		return switch(tree) {
		case BEmpty<E> t -> BinaryTree.leaf(Nv.of(BinaryTrees.nextInteger(),null));
		case BLeaf<E> t -> BinaryTree.leaf(Nv.of(BinaryTrees.nextInteger(),t.label()));
		case BTree<E> t -> BinaryTree.binary(Nv.of(BinaryTrees.nextInteger(),t.label()),
				BinaryTrees.map(t.left()),BinaryTrees.map(t.right()));
		};
	}
	
	private static <E> void toDot1(BinaryTree<Nv<E>> tt, Graph<Nv<E>,DefaultEdge> graph) {
		switch(tt) {
		case BEmpty<Nv<E>> t: break;
		case BLeaf<Nv<E>> t : graph.addVertex(t.label()); break;
		case BTree<Nv<E>> t : 
			graph.addVertex(t.label()); 
			toDot1(t.left(), graph);
			toDot1(t.right(), graph);
			break;
		}
	}
	
	private static <E> void toDot2(BinaryTree<Nv<E>> tt, Graph<Nv<E>,DefaultEdge> graph) {
		switch(tt) {
		case BEmpty<Nv<E>> t: break;
		case BLeaf<Nv<E>> t : break;
		case BTree<Nv<E>> t : 
			if(t.left() instanceof BLeaf<Nv<E>> tl) graph.addEdge(t.label(),tl.label());
			if(t.left() instanceof BTree<Nv<E>> tl) graph.addEdge(t.label(),tl.label());
			if(t.right() instanceof BLeaf<Nv<E>> tr) graph.addEdge(t.label(),tr.label());
			if(t.right() instanceof BTree<Nv<E>> tr) graph.addEdge(t.label(),tr.label());
			toDot2(t.left(),graph);
			toDot2(t.right(),graph);
			break;
		}
	}

	private static Map<String, Attribute> label(String label) {
		if(label.equals("")) return new HashMap<>();
		return Map.of("label", DefaultAttribute.createAttribute(label));
	}
	
	private static <V,E> void toDot3(Graph<V,E> graph, String file, 
			Function<V,String> vertexLabel,
			Function<E,String> edgeLabel) {		
		DOTExporter<V,E> de = new DOTExporter<V,E>();
		de.setVertexAttributeProvider(v->BinaryTrees.label(vertexLabel.apply(v)));
		de.setEdgeAttributeProvider(e->BinaryTrees.label(edgeLabel.apply(e)));		
		Writer f1 = Files2.getWriter(file);
		de.exportGraph(graph, f1);
	}
	
	public static <E> Optional<E> minLabel(BinaryTree<E> tree, Comparator<Optional<E>> cmp) {
		return switch (tree) {
		case BEmpty<E>  t -> Optional.empty();
		case BLeaf<E>  t -> Optional.of(t.label());
		case BTree<E>  t -> {
			Optional<E> e1 = minLabel(t.left(), cmp);
			Optional<E> e2 = minLabel(t.right(), cmp);
			yield Stream.of(Optional.of(t.label()),e1, e2).filter(e->!e.isEmpty()).min(cmp).get();
		}
		};
	}
	
	public static <E> Optional<E> minLabelOrdered(BinaryTree<E> tree, Comparator<E> cmp) {
		return switch (tree) {
		case BEmpty<E>  t -> Optional.empty();
		case BLeaf<E>  t ->  Optional.of(t.label());
		case BTree<E>  t when t.left() instanceof BEmpty<E>  tl -> Optional.of(t.label());
		case BTree<E>  t when t.left() instanceof BLeaf<E>  tl -> Optional.of(tl.label());
		case BTree<E>  t when t.left() instanceof BTree<E>  tl -> minLabelOrdered(tl, cmp);
		};
	}
	
	public static <E> Optional<E> maxLabelOrdered(BinaryTree<E> tree, Comparator<E> cmp) {
		return switch (tree) {
		case BEmpty<E>  t -> Optional.empty();
		case BLeaf<E>  t ->  Optional.of(t.label());
		case BTree<E>  t when t.right() instanceof BEmpty<E>  tr -> Optional.of(t.label());
		case BTree<E>  t when t.right() instanceof BLeaf<E>  tr -> Optional.of(tr.label());
		case BTree<E>  t when t.right() instanceof BTree<E>  tr -> maxLabelOrdered(tr, cmp);
		};
	}
	
	public static <E> Boolean containsLabel(BinaryTree<E> tree, E label) {
		return tree.byDeph()
			.map(t -> t.optionalLabel())
			.filter(e -> e.isPresent())
			.map(e->e.get())
			.anyMatch(e -> e.equals(label));
	}
	
	public static <E> Boolean containsLabelr(BinaryTree<E> tree, E label) {
		return switch (tree) {
		case BEmpty<E>  t -> false;
		case BLeaf<E>  t -> t.label().equals(label);
		case BTree<E>  t -> t.label().equals(label) || containsLabel(t.left(), label)
				|| containsLabel(t.right(), label);
		};
	}
	
	public static <E> BinaryTree<E> copy(BinaryTree<E> tree) {
		return switch (tree) {
		case BEmpty<E>  t -> BinaryTree.empty();
		case BLeaf<E>  t -> BinaryTree.leaf(t.label());
		case BTree<E>  t -> BinaryTree.binary(t.label(),t.left(), t.right());
		};
	}
	
	public static <E> Boolean containsLabelOrdered(BinaryTree<E> tree, E label, Comparator<E> cmp) {
		return switch (tree) {
		case BEmpty<E> t -> false;
		case BLeaf<E> t -> t.label().equals(label);
		case BTree<E> t when cmp.compare(label, t.label()) == 0 -> true;
		case BTree<E> t when cmp.compare(label, t.label()) < 0 -> containsLabelOrdered(t.left(), label, cmp);
		case BTree<E> t when cmp.compare(label, t.label()) > 0  -> containsLabelOrdered(t.right(), label, cmp);
		};
	}
	
	public static <E> BinaryTree<E> addOrdered(BinaryTree<E> tree, List<E> elements, Comparator<E> cmp) {
		BinaryTree<E> t = BinaryTree.empty();
		for(E e:elements) {
			t = BinaryTrees.addOrdered(t, e, cmp);
		}
		return t;
	}
	
	public static <E> BinaryTree<E> addOrdered(BinaryTree<E> tree, E element, Comparator<E> cmp) {
		BinaryTree<E> s = switch (tree) {
		case BEmpty<E> t -> BinaryTree.leaf(element);
		case BLeaf<E> t when cmp.compare(element, t.label()) == 0 -> tree;
		case BLeaf<E> t when cmp.compare(element, t.label()) < 0 ->
			BinaryTree.binary(t.label(), BinaryTree.leaf(element), BinaryTree.empty());
		case BLeaf<E> t when cmp.compare(element, t.label()) > 0 ->
			BinaryTree.binary(t.label(), BinaryTree.empty(), BinaryTree.leaf(element));
		case BTree<E> t when cmp.compare(element, t.label()) == 0 -> tree;
		case BTree<E> t when cmp.compare(element, t.label()) < 0 -> {
			BinaryTree<E> new_left = addOrdered(t.left(), element, cmp);
			BinaryTree<E> r = tree;
			if (t.left() != new_left) {
				r = BinaryTree.binary(t.label(), new_left, t.right());
				r = r.equilibrate();
			}
			yield r;
		}
		case BTree<E> t when cmp.compare(element, t.label()) > 0 -> {
			BinaryTree<E> new_right = addOrdered(t.right(), element, cmp);
			BinaryTree<E> r = tree;
			if (t.right() != new_right) {
				r = BinaryTree.binary(t.label(), t.left(), new_right);
				r = r.equilibrate();
			}
			yield r;
		}
		};
		return s;
	}
	
	public static <E> BinaryTree<E> removeOrdered(BinaryTree<E> tree, E element, Comparator<E> cmp) {
		BinaryTree<E> r = tree;
		return switch(tree) {
		case BEmpty<E> t  -> t;
		case BLeaf<E> t when cmp.compare(element, t.label()) == 0 -> BinaryTree.empty();
		case BLeaf<E> t when cmp.compare(element, t.label()) != 0 -> t;
		case BTree<E> t when cmp.compare(element, t.label()) == 0 ->  
				switch(t.left()) {
				case BEmpty<E> tl  -> {
					E label = BinaryTrees.minLabelOrdered(t.right(),cmp).get();
					BinaryTree<E> rl = removeOrdered(t.right(),label,cmp);
					r = BinaryTree.binary(label,tl,rl);
					r = r.equilibrate();
					yield r;
				}
				case BLeaf<E> tl  -> {
					E label = BinaryTrees.maxLabelOrdered(t.left(),cmp).get();
					BinaryTree<E> rr = removeOrdered(t.left(),label,cmp);
					r = BinaryTree.binary(label,rr,t.right());
					r = r.equilibrate();
					yield r;
				}
				case BTree<E> tl  -> {
					E label = BinaryTrees.maxLabelOrdered(t.left(),cmp).get();
					BinaryTree<E> rr = removeOrdered(t.left(),label,cmp);
					r = BinaryTree.binary(label,rr,t.right());
					r = r.equilibrate();
					yield r;
				}
				};
		case BTree<E> t when cmp.compare(element, t.label()) < 0 -> {
				BinaryTree<E> rl = removeOrdered(t.left(),element,cmp);
				if(rl != t.left()) {
					r = BinaryTree.binary(t.label(), rl, t.right()); 
					r.equilibrate();
				}
				yield r;
		}
		case BTree<E> t when cmp.compare(element, t.label()) > 0 -> {
				BinaryTree<E> rr = removeOrdered(t.right(),element,cmp);
				if(rr != t.right()) {
					r = BinaryTree.binary(t.label(), t.left(), rr); 
					r.equilibrate();
				}
				yield r;
		}
		};
	}
	
	public static <E> Boolean isOrdered(BinaryTree<E> tree, Comparator<E> cmp) {
		return switch (tree) {
		case BEmpty<E> t -> true;
		case BLeaf<E> t -> true;
		case BTree<E> t -> {
			Optional<E> lfLabel = maxLabelOrdered(t.left(), cmp);
			Optional<E> rgLabel = minLabelOrdered(t.right(), cmp);
			Boolean r = (lfLabel.isEmpty() || cmp.compare(lfLabel.get(), t.label()) < 0)
					&& (rgLabel.isEmpty() || cmp.compare(rgLabel.get(), t.label()) > 0);
			yield r && isOrdered(t.left(), cmp) && isOrdered(t.right(), cmp);
		}
		};
	}


	public static <E> BinaryTree<E> equilibrate(BinaryTree<E> tree) {
		Patterns<E> pt = Patterns.of();
		TypeEquilibrate te = BinaryTrees.equilibrateType(tree); 
		MutableType<Boolean> r = MutableType.of(true);
		return switch(te) {
		case Equilibrate -> tree;
		case LeftLeft -> BinaryPattern.transform(tree, pt.leftLeft, pt.result, r);
		case LeftRight -> BinaryPattern.transform(tree, pt.leftRight, pt.result, r);
		case RightLeft -> BinaryPattern.transform(tree, pt.rightLeft, pt.result, r);
		case RightRight -> BinaryPattern.transform(tree, pt.rightRight, pt.result, r);	
		};
	} 
	
	public static <E> BinaryTree<Integer> heights(BinaryTree<E> tree,int n){
		return switch(tree) {
		case BEmpty<E> t -> BinaryTree.leaf(0);
		case BLeaf<E> t -> BinaryTree.leaf(0);
		case BTree<E> t -> 
			n==0? BinaryTree.binary(t.height(),BinaryTree.leaf(t.left().height()),BinaryTree.leaf(t.right().height())) :
				BinaryTree.binary(t.height(),BinaryTrees.heights(t.left(),n-1),BinaryTrees.heights(t.right(),n-1));
		};
	}
	
	public static <E> TypeEquilibrate equilibrateType(BinaryTree<E> tree) {
		BinaryTree<Integer> h = heights(tree, 2);
		TypeEquilibrate r = null;
		int left = 0, right = 0, leftleft = 0, leftright = 0, rightleft = 0, rightright = 0;
		if (h instanceof BTree<Integer> t)
			left = t.left().optionalLabel().orElse(0);
		if (h instanceof BTree<Integer> t)
			right = t.right().optionalLabel().orElse(0);
		if (h instanceof BTree<Integer> t && t.left() instanceof BTree<Integer> tl)
			leftleft = tl.left().optionalLabel().orElse(0);
		if (h instanceof BTree<Integer> t && t.left() instanceof BTree<Integer> tl)
			leftright = tl.right().optionalLabel().orElse(0);
		if (h instanceof BTree<Integer> t && t.right() instanceof BTree<Integer> tr)
			rightleft = tr.left().optionalLabel().orElse(0);
		if (h instanceof BTree<Integer> t && t.right() instanceof BTree<Integer> tr)
			rightright = tr.right().optionalLabel().orElse(0);
		if (Math.abs(left - right) < 2)
			r = TypeEquilibrate.Equilibrate;
		else if (left - right >= 2) {
			if (leftleft >= leftright)
				r = TypeEquilibrate.LeftLeft;
			else
				r = TypeEquilibrate.LeftRight;
		} else {
			if (rightleft >= rightright)
				r = TypeEquilibrate.RightLeft;
			else
				r = TypeEquilibrate.RightRight;
		}
		return r;
	}

	public enum TypeEquilibrate{LeftRight, LeftLeft, RightLeft, RightRight, Equilibrate} 
	
	static class Patterns<R> {
		BinaryPattern<R> leftRight; 
	    BinaryPattern<R> rightLeft;
	    BinaryPattern<R> leftLeft;
	    BinaryPattern<R> rightRight;
	    BinaryPattern<R> result;
	    private static Patterns<?> patterns = null;
	    @SuppressWarnings("unchecked")
		public static <R> Patterns<R> of(){
	    	if(patterns==null) patterns = new Patterns<R>();
	    	return (Patterns<R>)patterns;
	    }
	    public Patterns() {
			super();
			this.leftRight = BinaryPattern.parse("_e5(_e3(_A,_e4(_B,_C)),_D)");
			this.rightLeft = BinaryPattern.parse("_e3(_A,_e5(_e4(_B,_C),_D))");
			this.leftLeft = BinaryPattern.parse("_e5(_e4(_e3(_A,_B),_C),_D)");
			this.rightRight = BinaryPattern.parse("_e3(_A,_e4(_B,_e5(_C,_D)))");
			this.result = BinaryPattern.parse("_e4(_e3(_A,_B),_e5(_C,_D))");
		} 
	}

	public static class DepthPathBinaryTree<E> implements Iterator<BinaryTree<E>>, Iterable<BinaryTree<E>> {

		public static <E> DepthPathBinaryTree<E> of(BinaryTree<E> tree) {
			return new DepthPathBinaryTree<E>(tree);
		}

		private Stack<BinaryTree<E>> stack;

		public DepthPathBinaryTree(BinaryTree<E> tree) {
			super();
			this.stack = new Stack<>();
			this.stack.add(tree);
		}

		@Override
		public Iterator<BinaryTree<E>> iterator() {
			return this;
		}

		@Override
		public boolean hasNext() {
			return !this.stack.isEmpty();
		}

		@Override
		public BinaryTree<E> next() {
			BinaryTree<E> actual = stack.pop();
			switch (actual) {
			case BTree<E> t -> {
				for (BinaryTree<E> v : List.of(t.left(), t.right())) {
					stack.add(v);
				}
			}
			case BEmpty<E> t -> {}
			case BLeaf<E> t -> {}
			}
			return actual;
		}	
	}
	
	public static record BinaryTreeLevel<E>(Integer level, BinaryTree<E> tree){
		public static <R> BinaryTreeLevel<R> of(Integer level, BinaryTree<R> tree){
			return new BinaryTreeLevel<R>(level,tree);
		}
		@Override
		public String toString() {
			return String.format("(%d,%s)",this.level,this.tree);
		}
	}
	
	public static class BreadthPathBinaryTree<E> implements Iterator<BinaryTreeLevel<E>>, Iterable<BinaryTreeLevel<E>> {
		
		public static <E> BreadthPathBinaryTree<E> of(BinaryTree<E> tree){
			return new BreadthPathBinaryTree<E>(tree);
		}

		private Queue<BinaryTreeLevel<E>> queue;
		
		public BreadthPathBinaryTree(BinaryTree<E> tree) {
			super();
			this.queue = new LinkedList<>();
			this.queue.add(BinaryTreeLevel.of(0,tree));
		}

		@Override
		public Iterator<BinaryTreeLevel<E>> iterator() {
			return this;
		}

		@Override
		public boolean hasNext() {
			return !this.queue.isEmpty();
		}

		@Override
		public BinaryTreeLevel<E> next() {
			BinaryTreeLevel<E> actual = queue.remove();
			switch(actual.tree()) {
			case BTree<E> t -> { 
				for(BinaryTreeLevel<E> v:
					List.of(t.left(),t.right()).stream()
						.map(x->BinaryTreeLevel.of(actual.level()+1,x)).toList()) {
					queue.add(v);
				}
			}
			case BEmpty<E> t -> {}
			case BLeaf<E> t -> {}
			}
			return actual;
		}
		
	}
	
	public static void test1() {
		List<String> filas = Files2.streamFromFile("ficheros/test2.txt").collect(Collectors.toList());
		BinaryTree<String> tree = null;
		for (String fila : filas) {
			tree = BinaryTree.parse(fila);
			System.out.println(tree);
		}
	}
	
	public static void test2() {
		BinaryTree<Integer> t1 = BinaryTree.empty();
		BinaryTree<Integer> t2 = BinaryTree.leaf(2);
		BinaryTree<Integer> t3 = BinaryTree.leaf(3);
		BinaryTree<Integer> t4 = BinaryTree.leaf(4);
		BinaryTree<Integer> t5 = BinaryTree.binary(27, BinaryTree.binary(42, t1, t2), BinaryTree.binary(59, t3, t4));
		BinaryTree<Integer> t6 = BinaryTree.binary(39, t2, t5);
		System.out.println(t1);
		System.out.println(t2);
		System.out.println(t6);
		String ex = "-43.7(2.1,abc(-27.3(_,2),78.2(3,4)))";
		BinaryTree<String> t7 = BinaryTree.parse(ex);
		System.out.println(t7);
		System.out.println(List2.reverse(List2.of(1, 2, 3, 4, 5, 6, 7, 8, 9)));
		BinaryTree<String> t8 = t7.reverse();
		System.out.println(t8);
		
	}
	
	public static void test3() {
		String ex = "-43.7(2.1,abc(-27.3(_,2),78.2(3,4)))";
		BinaryTree<String> t7 = BinaryTree.parse(ex);		
		System.out.println(t7);
		BinaryTrees.byLevel(t7).forEach(t->System.out.println(t));
		System.out.println("______________");
		System.out.println(t7);
		BinaryTrees.byLevel(t7).forEach(t->System.out.println(t));
	}
	
	public static void test4() {
		String ex = "-43.7(2.1,56.7(-27.3(_,2),78.2(3,4)))";
		String ex2 = "-43.7(2.1,5(_,8.(3.,5.)))";
		BinaryTree<String> t7 = BinaryTree.parse(ex);	
		BinaryTree<String> t8 = BinaryTree.parse(ex2);	
		System.out.println(t7);
		System.out.println(t8);
		System.out.println(BinaryTrees.equilibrateType(t7));
		System.out.println(BinaryTrees.equilibrateType(t8));
		t7.toDot("ficheros/binary_tree.gv");
		BinaryTree<String> t9 = t7.equilibrate();
		BinaryTree<String> t10 = t8.equilibrate();
		t9.toDot("ficheros/binary_tree_equilibrate.gv");
		System.out.println(t9);
		System.out.println(t10);
		BinaryTree<Double> t11 =  t7.map(e->Double.parseDouble(e));
		System.out.println(t11);
	}
	
	public static void test5() {
		String ex = "4(2(1(0,_),3),7(5(_,6),10(9(8,_),11(_,12))))";
		BinaryTree<Integer> t0 = BinaryTree.parse(ex, e->Integer.parseInt(e));
		System.out.println(BinaryTrees.equilibrateType(t0));
		System.out.println(BinaryTrees.isOrdered(t0,Comparator.naturalOrder()));
		BinaryTree<Integer> t1 = BinaryTrees.addOrdered(t0,13,Comparator.naturalOrder());
		System.out.println("t1 = "+t1);
		System.out.println(BinaryTrees.equilibrateType(t1));
		System.out.println(BinaryTrees.isOrdered(t1,Comparator.naturalOrder()));
		t1.toDot("ficheros/t1.gv");
		BinaryTree<Integer> t2 = switch(t1) {
		case BEmpty<Integer> t -> t;
		case BLeaf<Integer> t  -> t;
		case BTree<Integer> t -> BinaryTree.binary(t.label(),t.left(),t.right().equilibrate());
		};
		System.out.println("t2 = "+t2);
		System.out.println(BinaryTrees.equilibrateType(t2));
		System.out.println(BinaryTrees.isOrdered(t2,Comparator.naturalOrder()));
		t2.toDot("ficheros/t2.gv");
		String t4 = "10(_,11(_,12(_,13)))";
		BinaryTree<Integer> t42 = BinaryTree.parse(t4, e->Integer.parseInt(e));
		BinaryTree<Integer> t43 = t42.equilibrate();
		System.out.println("t43 = "+t43);
		t43.toDot("ficheros/t43.gv");
	}
	
	public static void test6() {
		BinaryTree<Integer> tree = BinaryTree.empty();
		for (int i = 0; i < 50 ; i++) {
			tree = BinaryTrees.addOrdered(tree, i, Comparator.naturalOrder());
//			System.out.println(tree.tree().height()+" == "+tree.tree());
		}	
		tree = BinaryTrees.equilibrate(tree);
		tree.toDot("ficheros/avl_tree.gv");
	}
	
	public static void test7() {
		String ex = "[4;7](2(1(0,_),3),7(5(_,6),10(9(8,_),11(_,12))))";
		BinaryTree<String> t0 = BinaryTree.parse(ex);
		System.out.println(t0);
		System.out.println(BinaryTrees.containsLabel(t0,"3"));
	}
	
	
	public static void main(String[] args) {
		test7();
	}
	
	
}
