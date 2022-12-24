package us.lsi.tiposrecursivos;

import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Stream;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import us.lsi.common.Files2;
import us.lsi.common.List2;

import us.lsi.streams.Stream2;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;
import us.lsi.tiposrecursivos.Tree.TNary;

public class Trees {
	
	public static <E> Stream<Tree<E>> byDeph(Tree<E> tree) {
		return Stream2.ofIterator(DepthPathTree.of(tree));
	}
	
	public static <E> Stream<TreeLevel<E>> byLevel(Tree<E> tree) {
		return Stream2.ofIterator(BreadthPathTree.of(tree));
	}
	
	public static <E> Tree<E> copy(Tree<E> tree) {
		return switch (tree) {
		case TEmpty<E>  t -> Tree.empty();
		case TLeaf<E>  t -> Tree.leaf(t.label());
		case TNary<E>  t -> Tree.nary(t.label(),t.elements().stream().map(x->Trees.copy(x)).toList());
		};
	}
	
	public static <E> void toDot(Tree<E> tree, String file) {
		Graph<Nv<E>,DefaultEdge> graph = 
				new SimpleDirectedGraph<Nv<E>,DefaultEdge>(null,()->new DefaultEdge(),true);
		Tree<Nv<E>> tt = Trees.map(tree);
		toDot1(tt,graph);
		System.out.println(graph);
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
	
	public static <E> Tree<Nv<E>> map(Tree<E> tree) {
		return switch(tree) {
		case TEmpty<E> t -> Tree.leaf(Nv.of(Trees.nextInteger(),null));
		case TLeaf<E> t -> Tree.leaf(Nv.of(Trees.nextInteger(),t.label()));
		case TNary<E> t -> Tree.nary(Nv.of(Trees.nextInteger(),t.label()),
				t.elements().stream().map(e->Trees.map(e)).toList());
		};
	}
	
	private static <E> void toDot1(Tree<Nv<E>> tt, Graph<Nv<E>,DefaultEdge> graph) {
		switch(tt) {
		case TEmpty<Nv<E>> t: break;
		case TLeaf<Nv<E>> t : graph.addVertex(t.label()); break;
		case TNary<Nv<E>> t : 
			graph.addVertex(t.label()); 
			t.elements().stream().forEach(e->toDot1(e,graph));
			break;
		}
	}
	
	private static <E> void toDot2(Tree<Nv<E>> tt, Graph<Nv<E>,DefaultEdge> graph) {
		switch(tt) {
		case TEmpty<Nv<E>> t: break;
		case TLeaf<Nv<E>> t : break;
		case TNary<Nv<E>> t : 
			t.elements().stream().forEach(e -> 
				{if(e instanceof TLeaf<Nv<E>> tl) graph.addEdge(t.label(),tl.label());
				if(e instanceof TNary<Nv<E>> tn) graph.addEdge(t.label(),tn.label());
				}
			);
			t.elements().stream().forEach(e->toDot2(e,graph));
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
		de.setVertexAttributeProvider(v->Trees.label(vertexLabel.apply(v)));
		de.setEdgeAttributeProvider(e->Trees.label(edgeLabel.apply(e)));		
		Writer f1 = Files2.getWriter(file);
		de.exportGraph(graph, f1);
	}

	public static record TreeLevel<E>(Integer level, Tree<E> tree){
		public static <R> TreeLevel<R> of(Integer level, Tree<R> tree){
			return new TreeLevel<R>(level,tree);
		}
		@Override
		public String toString() {
			return String.format("(%d,%s)",this.level,this.tree);
		}
	}
	
	public static class DepthPathTree<E> implements Iterator<Tree<E>>, Iterable<Tree<E>> {
		
		public static <E> DepthPathTree<E> of(Tree<E> tree){
			return new DepthPathTree<E>(tree);
		}

		private Stack<Tree<E>> stack;
		
		public DepthPathTree(Tree<E> tree) {
			super();
			this.stack = new Stack<>();
			this.stack.add(tree);
		}

		@Override
		public Iterator<Tree<E>> iterator() {
			return this;
		}

		@Override
		public boolean hasNext() {
			return !this.stack.isEmpty();
		}

		@Override
		public Tree<E> next() {
			Tree<E> actual = stack.pop();
			switch(actual) {
			case TNary<E> t: 
				for(Tree<E> v:List2.reverse(t.elements())) {
					stack.add(v);
				}
				break;
			case TEmpty<E> t:break;
			case TLeaf<E> t:break;
			}
			return actual;
		}
		
	}
	
	public static class BreadthPathTree<E> implements Iterator<TreeLevel<E>>, Iterable<TreeLevel<E>> {
		
		public static <E> BreadthPathTree<E> of(Tree<E> tree){
			return new BreadthPathTree<E>(tree);
		}

		private Queue<TreeLevel<E>> queue;
		
		public BreadthPathTree(Tree<E> tree) {
			super();
			this.queue = new LinkedList<>();
			this.queue.add(TreeLevel.of(0,tree));
		}

		@Override
		public Iterator<TreeLevel<E>> iterator() {
			return this;
		}

		@Override
		public boolean hasNext() {
			return !this.queue.isEmpty();
		}

		private static <E> List<TreeLevel<E>> children(TreeLevel<E> actual){
			return actual.tree().elements().stream().map(t->TreeLevel.of(actual.level()+1,t)).toList();
		}
		
		@Override
		public TreeLevel<E> next() {
			TreeLevel<E> actual = queue.remove();
			switch(actual.tree()) {
			case TNary<E> t: 
				for(TreeLevel<E> v:children(actual)) {
					queue.add(v);
				}
				break;
			case TEmpty<E> t:break;
			case TLeaf<E> t:break;
			}
			return actual;
		}
		
	}
	
	public static void test0() {
		Tree<Integer> t1 = Tree.empty();
		Tree<Integer> t2 = Tree.leaf(2);
		Tree<Integer> t3 = Tree.leaf(3);
		Tree<Integer> t4 = Tree.leaf(4);
		Tree<Integer> t5 = Tree.nary(27,t1,t2,t3,t4);
		Tree<Integer> t6 = Tree.nary(39,t2,t5);
		System.out.println(t1);
		System.out.println(t2);
		System.out.println(t6);
		String ex = "39(2,27(_,2,3,4))";
		Tree<String> t7 = Tree.parse(ex);
		System.out.println(t7);
		System.out.println(List2.reverse(List2.of(1,2,3,4,5,6,7,8,9)));
		Tree<String> t8 = t7.reverse();
		System.out.println(t8);
		System.out.println(Tree.parse("39(2.,27(_,2,3,4),9(8.,_))"));
	}
	
	public static void test1() {
		List<String> filas = Files2.linesFromFile("ficheros/test2.txt");
		Tree<String> nary = null;
		for (String fila : filas) {
			nary = Tree.parse(fila);
			System.out.println(nary);
		}
	}
	
	
	public static void test2() {
		List<String> filas = Files2.linesFromFile("ficheros/test2.txt");
		for (String fila : filas) {
			Tree<String> nary = Tree.parse(fila);
			List<Tree<String>> nary2 = nary.level(0);
			System.out.println(nary2);
		}
	}
	
	
	public static void test3() {
		Tree<String> t1 = Tree.parse("39(2.,27(_,2,3,4),9(8.,_))");
		Tree<Double> t2 = t1.map(s->Double.parseDouble(s));
		Tree<String> t3 = Tree.parse("9(8.,_)");
		Tree<Double> t4 = t3.map(s->Double.parseDouble(s));
		System.out.println(t2);
		System.out.println(t4);
	}
	
	public static void test4() {
		String ex = "39(2.,27(_,2,3,4),9(8.,_))";
		Tree<String> t7 = Tree.parse(ex);		
		System.out.println(t7);
		System.out.println("______________");
		System.out.println(t7);
		t7.byLevel().forEach(t->System.out.println(t));
	}
	
	public static void test5() {
		String ex = "4(2(1(0,_),3),7(5(_,6),10(9(8,_),11(_,12))))";
		Tree<String> t7 = Tree.parse(ex);		
		System.out.println(t7);
		t7.toDot("ficheros/tree.gv");
	}

	public static void main(String[] args) {
		test5();
	}

}
