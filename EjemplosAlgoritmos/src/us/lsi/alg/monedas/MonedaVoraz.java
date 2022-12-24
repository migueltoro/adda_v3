package us.lsi.alg.monedas;


import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class MonedaVoraz {
	
	
	public static Double voraz() {	
		Integer valorInicial = MonedaVertex.valorInicial;
		Double r = 0.;
		for (int i = 0; ; i++) {
			MonedaVertex.valorInicial = valorInicial + i;		
			MonedaVertex e1 = MonedaVertex.first();		
			EGraph<MonedaVertex, MonedaEdge> graph = EGraph.virtual(e1,MonedaVertex.goal(),PathType.Sum,Type.Max)
					.greedyEdge(MonedaVertex::aristaVoraz)
					.heuristic(MonedasHeuristica::heuristic)
					.build();	
			GreedyOnGraph<MonedaVertex, MonedaEdge> rr = GreedyOnGraph.of(graph, MonedaVertex::aristaVoraz);
			GraphPath<MonedaVertex, MonedaEdge> path = rr.path();
			if(rr.isSolution(path)) {
				r = path.getWeight();
				break; 
			}
		}
		MonedaVertex.valorInicial = valorInicial;
		return r;
	}

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		MonedaVertex.datosIniciales("ficheros/monedas2.txt", 401);
		System.out.println(Moneda.monedas);
		System.out.println(Moneda.monedas.size());
		System.out.println(voraz());
	}

}
