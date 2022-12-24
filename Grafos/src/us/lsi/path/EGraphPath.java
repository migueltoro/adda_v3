package us.lsi.path;

import java.util.Map;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.common.TriFunction;
import us.lsi.graphs.alg.DPR.Sp;
import us.lsi.graphs.virtual.EGraph;

public interface EGraphPath<V, E> extends GraphPath<V, E> {	
	E lastEdge();
	EGraphPath<V, E> add(E edge);
	EGraphPath<V, E> remove(E edge);
	Double add(V vertexActual, Double acumulateValue, E edge, E lastEdge);
	EGraphPath<V, E> copy();
	Double boundedValue(V vertexActual,Double acumulateValue,E edge,Predicate<V> goal,V end,
			TriFunction<V,Predicate<V>,V,Double> heuristic);
	Double estimatedWeightToEnd(V vertexActual,Double acumulateValue,Predicate<V> goal,V end,
			TriFunction<V,Predicate<V>,V,Double> heuristic);
	EGraphPath<V, E> concat(GraphPath<V,E> path);
	GraphPath<V, E> reverse();
	PathType type();
	
	public static enum PathType{Sum,Last}	
	
	public static <V,E> Double weight(GraphPath<V,E> path) {
		return path.getEdgeList().stream().mapToDouble(e->path.getGraph().getEdgeWeight(e)).sum();
	}
	
	public static <V, E> GraphPathSum<V, E> ofMap(EGraph<V,E> graph, V vertex, Map<V,Sp<E>> solutions) {
		return GraphPathSum.ofMap(graph, vertex, solutions);
	}
	
	public static <V, E> EGraphPath<V, E> ofVertex(EGraph<V, E> graph, V vertex, PathType type){
		EGraphPath<V, E> r = null;
		switch(type) {
		case Sum: r =  GraphPathSum.ofVertex(graph, vertex); break;
		case Last: r = GraphPathLast.ofVertex(graph, vertex); break;
		}
		return r;
	}

}
