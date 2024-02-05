package us.lsi.p5.ej_1;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestBT {

	public static void main(String[] args) {

		// Set up
		Locale.setDefault(Locale.of("en", "US"));

		for (Integer id_fichero = 0; id_fichero < 7; id_fichero++) {

			DatosMulticonjunto.iniDatos("ficheros/p5/multiconjuntos.txt", id_fichero);
			System.out.println("=============");
			System.out.println("\tResultados para el test " + id_fichero + "\n");
			
			DatosMulticonjunto.toConsole();

			// V�rtices clave

			MulticonjuntoVertex start = MulticonjuntoVertex.initial();
			Predicate<MulticonjuntoVertex> goal = MulticonjuntoVertex.goal();

			// Grafo

			

			System.out.println("\n#### Algoritmo BT ####");
			
			// Algoritmo BT
			
			EGraph<MulticonjuntoVertex, MulticonjuntoEdge> graph =
					EGraph.virtual(start,goal,PathType.Sum, Type.Min)
					.edgeWeight(x -> x.weight())
					.greedyEdge(MulticonjuntoVertex::greedyEdge)
					.goalHasSolution(MulticonjuntoVertex.goalHasSolution())
					.heuristic(MulticonjuntoHeuristic::heuristic)
					.build();
			
			
			GreedyOnGraph<MulticonjuntoVertex, MulticonjuntoEdge> rr = GreedyOnGraph.of(graph);
			
			GraphPath<MulticonjuntoVertex, MulticonjuntoEdge> r = rr.path();
			
			System.out.println("Voraz = "+r.getWeight()+"  == "+MulticonjuntoVertex.getSolucion(r));
			
			BT<MulticonjuntoVertex, MulticonjuntoEdge, SolucionMulticonjunto> bta = BT.of(graph,
					MulticonjuntoVertex::getSolucion, null, null, true);

			if (rr.isSolution(r)) {
				bta = BT.of(graph, MulticonjuntoVertex::getSolucion, r.getWeight(), r, true);
			}
			Optional<GraphPath<MulticonjuntoVertex, MulticonjuntoEdge>> gp = bta.search();
			System.out.println(MulticonjuntoVertex.getSolucion(gp.get()));
			
//			System.out.println(bta.path.getEdgeList().stream().map(x -> x.action())
//					.collect(Collectors.toList()));
			
			
//			GraphColors.toDot(bta.graph(), "ficheros/multiconjuntosBTGraph.gv", 
//					v -> v.toGraph(),
//					e -> e.action().toString(), 
//					v -> GraphColors.colorIf(Color.red, MulticonjuntoVertex.goal().test(v)),
//					e -> GraphColors.colorIf(Color.red, bta.optimalPath.getEdgeList().contains(e)));

		}
	}

}


