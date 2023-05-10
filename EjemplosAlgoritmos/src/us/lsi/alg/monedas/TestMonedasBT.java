package us.lsi.alg.monedas;

import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;


import org.jgrapht.GraphPath;
import org.jgrapht.graph.SimpleDirectedGraph;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestMonedasBT {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		DatosMonedas.datosIniciales("ficheros/monedas2.txt");
		Integer valorInicial = 401;

		MonedaVertex e1 = MonedaVertex.first(valorInicial);
		
		EGraph<MonedaVertex, MonedaEdge> graph = EGraph.virtual(e1,MonedaVertex.goal(),PathType.Sum,Type.Max)
				.goalHasSolution(MonedaVertex.goalHasSolution())
				.greedyEdge(MonedaVertex::aristaVoraz)
				.heuristic(MonedasHeuristica::heuristic)
				.build();

		GreedyOnGraph<MonedaVertex, MonedaEdge> rr = GreedyOnGraph.of(graph);

		GraphPath<MonedaVertex, MonedaEdge> path1 = rr.path();

		BT<MonedaVertex, MonedaEdge, SolucionMonedas> ms1;

		if (rr.isSolution(path1)) {
			System.out.println("Hay solucion voraz 1"+path1.getWeight());
			ms1 = BT.of(graph,SolucionMonedas::of,path1.getWeight(),path1,true);
		} else {
			ms1 = BT.of(graph,SolucionMonedas::of,null,null,true);
		}
		
		ms1.search();

		Optional<GraphPath<MonedaVertex, MonedaEdge>> ps = ms1.optimalPath();

		if (ps.isPresent()) {
			SolucionMonedas s = SolucionMonedas.of(ps.get());
			System.out.println("2 = " + s.toString());
			SimpleDirectedGraph<MonedaVertex, MonedaEdge> g = ms1.outGraph();
			GraphColors.toDot(g, "ficheros/MonedasBTGraph1.gv",
					v -> String.format("(%d,%d)", v.index(), v.valorRestante()), e -> e.action().toString(),
					v -> GraphColors.colorIf(Color.red, MonedaVertex.goal().test(v)),
					e -> GraphColors.color(Color.black));
		} else
			System.out.println("2 = No hay solucion");

		Collections.sort(DatosMonedas.monedas, Comparator.comparing(m -> m.pesoUnitario()));

		MonedaVertex e3 = MonedaVertex.first(valorInicial);

		graph = EGraph.virtual(e3,MonedaVertex.goal(),PathType.Sum,Type.Min)
				.goalHasSolution(MonedaVertex.goalHasSolution())
				.greedyEdge(MonedaVertex::aristaVoraz)
				.heuristic(MonedasHeuristica::heuristic)
				.build();

		rr = GreedyOnGraph.of(graph, MonedaVertex::aristaVoraz);

		GraphPath<MonedaVertex, MonedaEdge> path2 = rr.path();

		if (rr.isSolution(path1)) {
			System.out.println("Hay solucion voraz 1"+path1.getWeight());
			ms1 = BT.of(graph,SolucionMonedas::of,path2.getWeight(),path2,true);
		} else {
			ms1 = BT.of(graph,SolucionMonedas::of,null,null,true);
		}
		
		Optional<GraphPath<MonedaVertex, MonedaEdge>> gp = ms1.search();

		if (gp.isPresent()) {
			System.out.println("4 = " + SolucionMonedas.of(gp.get()).toString());
		} else
			System.out.println("4 = No hay solucion");

	}
}
