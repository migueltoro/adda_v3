package us.lsi.graphs.manual.recorridos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import us.lsi.common.TriFunction;
import us.lsi.graphs.manual.GraphPath;
import us.lsi.graphs.manual.VirtualGraph;

public class DynamicProgramming<V, E> {
	

	public static <V,E> GraphPath<V,E> path(Map<V,Sp<E>> memory,V start,VirtualGraph<V, E> graph) {
		List<V> vertices = new ArrayList<>();
		List<E> edges = new ArrayList<>();
		V actual = start;
		Double weight = memory.get(actual).weight();
		while (memory.containsKey(actual)) {
			Sp<E> sp = memory.get(actual);
			vertices.add(actual);
			if (sp.edge() == null) break;
			edges.add(sp.edge());
			actual = graph.otherVertex(actual, sp.edge());
		}		
		return GraphPath.of(vertices,edges,weight);
	}
	
	public static record Sp<E>(E edge, Double weight) implements Comparable<Sp<E>>{
		
		public static Comparator<Double> comp = null;
		
		public static <E> Sp<E> of(E edge, Double weight) {
			return new Sp<E>(edge, weight);
		}

		@Override
		public int compareTo(Sp<E> other) {
			return comp.compare(this.weight(), other.weight());
		}	
	}
	
	public static <V, E> GraphPath<V, E> dynamicProgrammingR(V start, Predicate<V> end, 
			VirtualGraph<V, E> graph,
			TriFunction<V,Predicate<V>,V, Double> heuristica) {
		return dynamicProgrammingR(start, end, graph, heuristica, (x,y)->x.compareTo(y));
	}
	
	public static <V, E> GraphPath<V, E> dynamicProgrammingR(V start, Predicate<V> goal, 
			VirtualGraph<V, E> graph,TriFunction<V,Predicate<V>,V, Double> heuristica, 
			Comparator<Double> cmp) {
		Sp.comp = cmp;
		Map<V,Sp<E>> memory = new HashMap<>();
		Set<V> path = new HashSet<>();
		path.add(start);
		dynamicProgrammingR(start,goal,graph,heuristica,cmp,memory,path);
		return path(memory,start,graph);
	}

	private static <V, E> Sp<E> dynamicProgrammingR(V actualVertex, Predicate<V> goal, 
			VirtualGraph<V, E> graph,
			TriFunction<V,Predicate<V>,V, Double> heuristica, Comparator<Double> cmp,
			Map<V, Sp<E>> memory,Set<V> path) {
		Sp<E> r = null;
		if (goal.test(actualVertex)) {
			r = Sp.of(null, 0.);
			memory.put(actualVertex, r);
		} else if (memory.containsKey(actualVertex)) {
			r = memory.get(actualVertex);
		} else {
			List<Sp<E>> spChildren = new ArrayList<>();
			for (V v : graph.neighbors(actualVertex)) {
				if (path.contains(v)) continue;
				Double edgeWeight = graph.edgeWeight(actualVertex, v);
				path.add(v);
				Sp<E> spc = dynamicProgrammingR(v,goal,graph,heuristica,cmp, memory,path);
				path.remove(v);
				E edge = graph.edge(actualVertex, v);
				if (spc != null) {
					Sp<E> sp = Sp.of(edge, spc.weight() + edgeWeight);
					spChildren.add(sp);
				}
			}
			r = spChildren.stream().min(Comparator.naturalOrder()).orElse(null);
			memory.put(actualVertex, r);
		}
		return r;
	}

}
