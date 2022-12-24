package us.lsi.alg.pack;


import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;


public class TestAStar {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		Data.data("ficheros/pack1.txt");
		PackVertex e1 = PackVertex.first();
		
		EGraph<PackVertex,PackEdge> graph = 
				EGraph.virtual(e1,PackVertex.goal(),PathType.Last,Type.Min)
				.vertexWeight(v->(double)v.nc())
				.edgeWeight(e->e.weight())
				.greedyEdge(PackVertex::greedyEdge)
				.heuristic(Heuristica::heuristic)
				.build();	
		
		GreedyOnGraph<PackVertex,PackEdge> rr = GreedyOnGraph.of(graph);
	
		GraphPath<PackVertex, PackEdge> p = rr.path();
		SolucionPack sp = SolucionPack.of(p);
		
		System.out.println("Solucion Voraz = "+sp);
		System.out.println("Heuristica = "+Heuristica.heuristic(e1, PackVertex.goal(), null));
		
		AStar<PackVertex, PackEdge, ?> ms = AStar.of(graph,null,(double)sp.nc(),p);
		
		GraphPath<PackVertex,PackEdge> path = ms.search().orElse(null);
		SolucionPack s = SolucionPack.of(path);
		System.out.println(String.format("Volumen contenedor = %d,Numero de Objetos = %d",
				Data.volumenContenedor,Data.n));
		System.out.println(s);
		
	}

}
