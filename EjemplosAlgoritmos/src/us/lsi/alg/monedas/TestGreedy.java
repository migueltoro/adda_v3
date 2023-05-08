package us.lsi.alg.monedas;

import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestGreedy {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		DatosMonedas.datosIniciales("ficheros/monedas2.txt", 402);
		System.out.println(Moneda.monedas);
		System.out.println(Moneda.monedas.size());

//		System.out.println("1 = "+e1);
//		System.out.println("2 = "+e2);
		
//		System.out.println("3 = "+e1);
//		System.out.println("4 = "+e2);
		
		for (int i = 0; i < 5; i++) {
			
			DatosMonedas.valorInicial = 400+i;
			Collections.sort(Moneda.monedas, Comparator.comparing(m -> -m.pesoUnitario()));
			System.out.println(DatosMonedas.valorInicial + "--------");
			MonedaVertex e1 = MonedaVertex.first();
			//		MonedaVertex e2 = MonedaVertex.last();
			EGraph<MonedaVertex, MonedaEdge> graph = EGraph.virtual(e1,MonedaVertex.goal(),PathType.Sum,Type.Max)
					.goalHasSolution(MonedaVertex.goalHasSolution())
					.greedyEdge(MonedaVertex::aristaVoraz)
					.heuristic(MonedasHeuristica::heuristic)
					.build();	
			GreedyOnGraph<MonedaVertex, MonedaEdge> rr = GreedyOnGraph.of(graph);
			GraphPath<MonedaVertex, MonedaEdge> path = rr.path();
			System.out.println(String.format("%d ,%.2f,",i,rr.isSolution(path),
					path.getWeight(),
					MonedasHeuristica.heuristic(e1, MonedaVertex.goal(), null)));
			Collections.sort(Moneda.monedas, Comparator.comparing(m -> m.pesoUnitario()));
			//		System.out.println(Moneda.monedas);
			e1 = MonedaVertex.first();
			graph = EGraph.virtual(e1,MonedaVertex.goal(),PathType.Sum,Type.Min)
					.goalHasSolution(MonedaVertex.goalHasSolution())
					.greedyEdge(MonedaVertex::aristaVoraz)
					.heuristic(MonedasHeuristica::heuristic)
					.build();	
			rr = GreedyOnGraph.of(graph, MonedaVertex::aristaVoraz);
			path = rr.path();
			System.out.println(String.format("%d ,%s,%.2f,",i,rr.isSolution(path),path.getWeight(),
					MonedasHeuristica.heuristic(e1, MonedaVertex.goal(), null)));
		}
	}

}
