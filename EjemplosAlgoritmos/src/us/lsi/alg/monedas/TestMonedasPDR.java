package us.lsi.alg.monedas;



import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.DPR;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;


public class TestMonedasPDR {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		MonedaVertex.datosIniciales("ficheros/monedas2.txt", 400);

		MonedaVertex e1 = MonedaVertex.first();
		
		EGraph<MonedaVertex, MonedaEdge> graph = EGraph.virtual(e1,MonedaVertex.goal(),PathType.Sum,Type.Max)
				.goalHasSolution(MonedaVertex.goalHasSolution())
				.greedyEdge(MonedaVertex::aristaVoraz)
				.heuristic(MonedasHeuristica::heuristic)
				.build();

		GreedyOnGraph<MonedaVertex, MonedaEdge> rr = GreedyOnGraph.of(graph);
		
		GraphPath<MonedaVertex, MonedaEdge> path1 = rr.path();
		
		DPR<MonedaVertex, MonedaEdge, SolucionMonedas> ms1;

		if (rr.isSolution(path1)) {
			System.out.println("1 = " + SolucionMonedas.of(path1));
			ms1 = DPR.of(graph, null,path1.getWeight(), path1, false);
		} else {
			ms1 = DPR.of(graph, null,MonedaVoraz.voraz(), null, false);
		}
		
		Optional<GraphPath<MonedaVertex, MonedaEdge>> s1 = ms1.search();
		
		if (s1.isPresent()) System.out.println("2 = " + SolucionMonedas.of(s1.get()));
		else System.out.println("2 = No hay solucion");

		Collections.sort(Moneda.monedas, Comparator.comparing(m -> m.pesoUnitario()));

		MonedaVertex e3 = MonedaVertex.first();
		
		graph = EGraph.virtual(e3,MonedaVertex.goal(),PathType.Sum,Type.Min)
				.goalHasSolution(MonedaVertex.goalHasSolution())
				.greedyEdge(MonedaVertex::aristaVoraz)
				.heuristic(MonedasHeuristica::heuristic)
				.build();

		rr = GreedyOnGraph.of(graph);
		
		GraphPath<MonedaVertex, MonedaEdge> path2 = rr.path();

		DPR<MonedaVertex, MonedaEdge, SolucionMonedas> ms2;

		if (rr.isSolution(path2)) {
			System.out.println("3 = " + SolucionMonedas.of(path2));
			ms2 = DPR.of(graph, null,path2.getWeight(), path2, false);
		}else {
			ms2 = DPR.of(graph, null,MonedaVoraz.voraz(), null, false);
		}
		Optional<GraphPath<MonedaVertex, MonedaEdge>> s2 = ms2.search();
		if (s2.isPresent()) System.out.println("4 = " + SolucionMonedas.of(s2.get()));
		else System.out.println("4 = No hay solucion");

	}
}
