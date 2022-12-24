package us.lsi.alg.colorgraphs;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.SimpleWeightedGraph;

import us.lsi.common.List2;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.views.IntegerVertexGraphView;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestColorAstar {

	
	public static SimpleWeightedGraph<Ciudad, Carretera> leeGrafo(String fichero) {
		SimpleWeightedGraph<Ciudad, Carretera> graph = GraphsReader.newGraph(fichero, 
				Ciudad::ofFormat, 
				Carretera::ofFormat,
				Graphs2::simpleWeightedGraph, 
				Carretera::km);
		return graph;
	}

	public static void main(String[] args) {

		SimpleWeightedGraph<Ciudad, Carretera> g0 = leeGrafo("./ficheros/andalucia.txt");		
//		System.out.println(g0);		
		Graph<Integer,SimpleEdge<Integer>> g2 = IntegerVertexGraphView.of(g0);	
//		Integer n = g2.vertexSet().size();
		ColorVertex.data(9, g2);	
		ColorVertex e1 = ColorVertex.first();
					
		
		EGraph<ColorVertex, ColorEdge> graph = 
				EGraph.virtual(e1,ColorVertex.goal(),PathType.Last,Type.Min)
				.vertexWeight(v->v.nc().doubleValue())
				.heuristic((v1,p,v2)->(double) v1.nc())
				.build();
				
		GraphPath<ColorVertex, ColorEdge> p = GreedyOnGraph.of(graph,v->v.greedyEdge()).path();
		Integer m = p.getEndVertex().nc();
		System.out.println("Voraz = "+m);
		
		ColorVertex.data(m, g2);
		
		AStar<ColorVertex, ColorEdge,?> ms = AStar.ofGreedy(graph);
		
		GraphPath<ColorVertex, ColorEdge> path = ms.search().get();
		ColorVertex lv = List2.last(path.getVertexList());
		System.out.println("Numero de Colores = "+lv.nc());
		System.out.println(lv.cav());

	}

}
