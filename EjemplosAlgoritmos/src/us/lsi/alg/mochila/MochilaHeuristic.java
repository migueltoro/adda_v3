package us.lsi.alg.mochila;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.GraphWalk;

import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.mochila.datos.DatosMochila;

public class MochilaHeuristic {

	public static Double heuristic1(MochilaVertex v1, Predicate<MochilaVertex> goal, MochilaVertex v2) { 
		Double weight = 0.;
		Integer index = v1.index();
		Double cr = (double)v1.capacidadRestante();		
		while(!(index==MochilaVertexI.n || cr==0.)) {
			Double a = Math.min(cr/DatosMochila.getPeso(index),DatosMochila.getNumMaxDeUnidades(index));
			cr = cr - a * DatosMochila.getPeso(index);
			weight += a * DatosMochila.getValor(index);
			index = index + 1;
		} 
		return weight;
	}
	
	public static GraphPath<MochilaVertex,MochilaEdge> greedy(MochilaVertex start, 
			Graph<MochilaVertex,MochilaEdge> graph) {
		List<MochilaVertex> vertices = new ArrayList<>();
		Double weight = 0.;
		MochilaVertex v = start;		
		while(!v.goal()) {
			Integer a = v.greedyAction();
			MochilaEdge e = v.edge(a);
			v = v.neighbor(a);
			vertices.add(v);
			weight += e.weight();
		} 
		return new GraphWalk<MochilaVertex,MochilaEdge>(graph,vertices,weight);
	}
	
	public static SolucionMochila solucionVoraz(MochilaVertex v, EGraph<MochilaVertex, MochilaEdge> graph) {
		GraphPath<MochilaVertex, MochilaEdge> r = 
				GreedyOnGraph.of(graph,MochilaVertex::greedyEdge).path();
		return SolucionMochila.of(r);
	}
}
