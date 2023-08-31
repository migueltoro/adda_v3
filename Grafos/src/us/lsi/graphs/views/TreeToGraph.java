package us.lsi.graphs.views;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;

public class TreeToGraph {
	
	public static <E> void addEdges(BinaryTree<E> tree, SimpleDirectedGraph<BinaryTree<E>, DefaultEdge> graph) {
		switch (tree) {
		case BEmpty<E> t -> {}
		case BLeaf<E> t -> {}
		case BTree<E> t -> {
			graph.addEdge(t, t.left());
			graph.addEdge(t, t.right()); 
			addEdges(t.left(), graph);
			addEdges(t.right(), graph);
			}
		};
	}
	
	public static <E> SimpleDirectedGraph<BinaryTree<E>, DefaultEdge> toGraph(BinaryTree<E> tree) {
		SimpleDirectedGraph<BinaryTree<E>, DefaultEdge> graph = new SimpleDirectedGraph<>(null,()->new DefaultEdge(), false);
		tree.byDepth().distinct().forEach(t -> graph.addVertex(t));
		addEdges(tree, graph);
		return graph;
	}
	
	public static <E> void addEdges(Tree<E> tree, SimpleDirectedGraph<Tree<E>, DefaultEdge> graph) {
		switch (tree) {
		case TEmpty<E> t -> {}
		case TLeaf<E> t -> {}
		case Tree<E> t -> t.children().stream().forEach(tt->{graph.addEdge(t, tt);addEdges(tt,graph);});
		};
	}
	
	public static <E> SimpleDirectedGraph<Tree<E>, DefaultEdge> toGraph(Tree<E> tree) {
		SimpleDirectedGraph<Tree<E>, DefaultEdge> graph = new SimpleDirectedGraph<>(null,()->new DefaultEdge(), false);
		tree.byDepth().distinct().forEach(t -> graph.addVertex(t));
		addEdges(tree, graph);
		return graph;
	}

}
