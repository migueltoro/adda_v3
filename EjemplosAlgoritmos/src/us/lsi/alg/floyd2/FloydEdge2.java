package us.lsi.alg.floyd2;

import java.util.List;

import org.jgrapht.graph.GraphWalk;

import us.lsi.graphs.SimpleEdge;
import us.lsi.hypergraphs2.HyperEdge2;
import us.lsi.path.EGraphPath;

public record FloydEdge2(FloydVertex2 source, List<FloydVertex2> targets, Boolean action)
		implements HyperEdge2<FloydVertex2, FloydEdge2, Boolean, GraphWalk<Integer,SimpleEdge<Integer>>> {

	public static FloydEdge2 of(FloydVertex2 source, List<FloydVertex2> targets, Boolean action) {
		return new FloydEdge2(source, targets, action);
	}

	@Override
	public Double edgeWeight(List<Double> solutions) {
		Double weight = null;
		if (!action()) weight = solutions.get(0);
		else weight = solutions.get(0) + solutions.get(1);
		return weight;
	}

	@Override
	public FloydEdge2 me() {
		return this;
	}

	@Override
	public GraphWalk<Integer, SimpleEdge<Integer>> solution(List<GraphWalk<Integer, SimpleEdge<Integer>>> solutions) {
		GraphWalk<Integer, SimpleEdge<Integer>> gp;
		if (!action())
			gp = solutions.get(0);
		else
			gp = solutions.get(0).concat(solutions.get(1),g->EGraphPath.weight(g));
		return gp;
	}

	@Override
	public String toString() {
		return this.action()?"T":"F";
	}
	
	

}
