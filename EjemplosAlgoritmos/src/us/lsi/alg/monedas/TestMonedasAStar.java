package us.lsi.alg.monedas;

import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;

import org.jgrapht.GraphPath;
import org.jgrapht.graph.SimpleDirectedGraph;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;


public class TestMonedasAStar {

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
		
		AStar<MonedaVertex, MonedaEdge, SolucionMonedas> ms;
		
//		ms.stream().limit(100).forEach(v->System.out.printf("%s,actions = %s\n",v,v.actions()));
		
		if (rr.isSolution(path1)) {
			System.out.println("Hay solucion voraz 1 = " +path1.getWeight());
			ms = AStar.ofGreedy(graph);
		} else {
			ms = AStar.of(graph);
		}
		
		Optional<GraphPath<MonedaVertex, MonedaEdge>> path = ms.search();
		
		SolucionMonedas s;
		if (path.isPresent()) {
			s = SolucionMonedas.of(path.get());
			System.out.println(s);
			System.out.println(ms.tree.keySet().size());
			SimpleDirectedGraph<MonedaVertex, MonedaEdge> g = ms.outGraph();
			GraphColors.toDot(g,"ficheros/MonedasAstarGraph1.gv",
					v->String.format("(%d,%d)",v.index(),v.valorRestante()),
					e->e.action().toString(),
					v->GraphColors.colorIf(Color.red,MonedaVertex.goal().test(v)),
					e->GraphColors.color(Color.black)
					);
		} else {
			System.out.println("No hay solucion");
		}
		
		
	
		System.out.println("_________________________________");
		
		Collections.sort(DatosMonedas.monedas, Comparator.comparing(m -> m.pesoUnitario()));
		
		MonedaVertex e3 = MonedaVertex.first(valorInicial);
//		MonedaVertex e4 = MonedaVertex.last();
		
		

		graph = EGraph.virtual(e3,MonedaVertex.goal(),PathType.Sum,Type.Min)
				.goalHasSolution(MonedaVertex.goalHasSolution())
				.greedyEdge(MonedaVertex::aristaVoraz)
				.heuristic(MonedasHeuristica::heuristic)
				.build();	
		
		rr = GreedyOnGraph.of(graph, MonedaVertex::aristaVoraz);

		GraphPath<MonedaVertex, MonedaEdge> path2 = rr.path();
	    
	    if (rr.isSolution(path2)) {
			System.out.println("Hay solucion voraz 2 = "+path2.getWeight());
			ms = AStar.ofGreedy(graph);
		} else {
			ms = AStar.of(graph);
		}
		
	    path = ms.search();
		
	    if (path.isPresent()) {
			s = SolucionMonedas.of(path.get());
			System.out.println(s);
			System.out.println(ms.tree.keySet().size());
			SimpleDirectedGraph<MonedaVertex, MonedaEdge> g = ms.outGraph();
			GraphColors.toDot(g,"ficheros/MonedasAstarGraph2.gv",
					v->String.format("(%d,%d)",v.index(),v.valorRestante()),
					e->e.action().toString(),
					v->GraphColors.colorIf(Color.red,MonedaVertex.goal().test(v)),
					e->GraphColors.color(Color.black)
					);
		} else {			
			System.out.println("No hay solucion");
		}
		
	}

}
