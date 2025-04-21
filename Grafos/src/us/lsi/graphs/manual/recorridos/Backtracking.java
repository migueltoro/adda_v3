package us.lsi.graphs.manual.recorridos;


import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;
import us.lsi.graphs.manual.GraphPath;
import us.lsi.graphs.manual.VirtualGraph;

public class Backtracking {
	
	public static <V, E> GraphPath<V, E> backtracking(V start, Predicate<V> goal, 
			VirtualGraph<V, E> graph,
			Function<V, Double> heuristica) {
		return backtracking(start,goal,graph,heuristica,(x,y)->x.compareTo(y));
	}
	
	public static <V, E> GraphPath<V, E> backtracking(V start, 
			Predicate<V> goal, VirtualGraph<V, E> graph,
			Function<V, Double> heuristica,Comparator<Double> cmp) {
		State<V, E> state = State.of(start, 0.);
		state.goal = goal;
		state.graph = graph;
		state.heuristica = heuristica;
		state.cmp = cmp;
		backtracking(state);
		return state.bestPath;
	}

	private static <V, E> void backtracking(State<V, E> state) {
		V actualVertex = state.vertex();		
		if (state.goal.test(actualVertex)) {	
			state.update();
		} 
		else for (V v : state.graph.neighbors(state.vertex())) {
			E edge = state.graph.edge(actualVertex, v);
			Double value = state.graph.edgeWeight(actualVertex, v);
			if (state.contains(v) || state.filter(v, value)) continue;
			state.add(v, edge, value);
			backtracking(state);
			state.remove();
		}
	}
	
	

}
