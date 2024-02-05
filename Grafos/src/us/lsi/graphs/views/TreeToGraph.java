package us.lsi.graphs.views;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import us.lsi.common.MutableType.MutableInteger;
import us.lsi.common.Pair;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;
import us.lsi.tiposrecursivos.Tree.TNary;

public class TreeToGraph {
	
	private static MutableInteger mi = MutableInteger.of();
	
	public static <E> BinaryTree<Pair<E,Integer>> vertexNumber(BinaryTree<E> tree) {
		return switch (tree) {
		case BEmpty<E> t -> BinaryTree.leaf(Pair.of(null,TreeToGraph.mi.valueInc()));
		case BLeaf<E> t -> BinaryTree.leaf(Pair.of(t.label(),TreeToGraph.mi.valueInc()));
		case BTree<E> t -> 
			BinaryTree.binary(Pair.of(t.label(),TreeToGraph.mi.valueInc()),vertexNumber(t.left()),vertexNumber(t.right()));		
		};
	}
	
	public static <E> Tree<Pair<E,Integer>> vertexNumber(Tree<E> tree) {
		return switch (tree) {
		case TEmpty<E> t -> Tree.leaf(Pair.of(null,TreeToGraph.mi.valueInc()));
		case TLeaf<E> t -> Tree.leaf(Pair.of(t.label(),TreeToGraph.mi.valueInc()));
		case TNary<E> t -> 
			Tree.nary(Pair.of(t.label(),TreeToGraph.mi.valueInc()),t.children().stream().map(tt->vertexNumber(tt)).toList());		
		};
	}
	
	
	public static <E> void addEdgesBinaryTree(BinaryTree<E> tree, SimpleDirectedGraph<E, DefaultEdge> graph) {
		switch (tree) {
		case BEmpty<E> t -> {}
		case BLeaf<E> t -> {}
		case BTree<E> t -> {
			graph.addEdge(t.optionalLabel().get(), t.left().optionalLabel().get());
			graph.addEdge(t.optionalLabel().get(), t.right().optionalLabel().get()); 
			addEdgesBinaryTree(t.left(), graph);
			addEdgesBinaryTree(t.right(), graph);
			}
		};
	}
	
	public static <E> SimpleDirectedGraph<Pair<E,Integer>, DefaultEdge> toGraph(BinaryTree<E> tree) {
		SimpleDirectedGraph<Pair<E,Integer>, DefaultEdge> graph = new SimpleDirectedGraph<>(null,()->new DefaultEdge(), false);
		TreeToGraph.mi = MutableInteger.of();
		BinaryTree<Pair<E,Integer>> treeNumber = vertexNumber(tree);
		treeNumber.byDepth().forEach(t -> graph.addVertex(t.optionalLabel().get()));
		addEdgesBinaryTree(treeNumber, graph);
		return graph;
	}
	
	public static <E> void addEdgesTree(Tree<E> tree, SimpleDirectedGraph<E, DefaultEdge> graph) {
		switch (tree) {
		case TEmpty<E> t -> {}
		case TLeaf<E> t -> {}
		case Tree<E> t -> t.children().stream()
			.forEach(tt->{graph.addEdge(t.optionalLabel().get(), tt.optionalLabel().get());addEdgesTree(tt,graph);});
		};
	}
	
	public static <E> SimpleDirectedGraph<Pair<E,Integer>, DefaultEdge> toGraph(Tree<E> tree) {
		SimpleDirectedGraph<Pair<E,Integer>, DefaultEdge> graph = new SimpleDirectedGraph<>(null,()->new DefaultEdge(), false);
		TreeToGraph.mi = MutableInteger.of();
		Tree<Pair<E,Integer>> treeNumber = vertexNumber(tree);
		treeNumber.byDepth().forEach(t -> graph.addVertex(t.optionalLabel().get()));
		addEdgesTree(treeNumber, graph);
		return graph;
	}
	
	

}
