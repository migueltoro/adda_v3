package us.lsi.graphs.manual.recorridos;

import java.util.function.Function;
import java.util.function.Predicate;
import us.lsi.graphs.manual.GraphPath;
import us.lsi.graphs.manual.VirtualGraph;

public class Greedy {
	
	public static <V, E> GraphPath<V, E> greedy(V start, Predicate<V> end, 
			VirtualGraph<V, E> graph,Function<V,V> greedyVertex) {
		State<V, E> state = State.of(start, 0.);
		state.goal = end;
		state.graph = graph;
		greedy(state,greedyVertex);
		System.out.println(state.path().vertices());
		return state.bestPath;
	}

	private static <V, E> void greedy(State<V, E> state,Function<V,V> greedyVertex) {
		V actualVertex = state.vertex();		
		while(!state.graph.neighbors(actualVertex).isEmpty()) {
			if(state.goal.test(actualVertex)) {
				state.update();
				break;
			}
			V v = greedyVertex.apply(actualVertex); 
			if (state.contains(v)) break;
			E edge = state.graph.edge(actualVertex, v);
			Double value = state.graph.edgeWeight(actualVertex, v);
			state.add(v, edge, value);
			actualVertex  = v;
		} 
		
	}
}
