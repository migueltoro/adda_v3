package us.lsi.alg.multiconjuntos;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class Test_aS {

	public static void main(String[] args) {

		// Set up
		Locale.setDefault(Locale.of("en", "US"));

		for (Integer id_fichero = 0; id_fichero < 7; id_fichero++) {

			DatosMulticonjunto.iniDatos("ficheros/multiconjuntos.txt", id_fichero);
			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");

			// V�rtices clave

			MulticonjuntoVertex start = MulticonjuntoVertex.initial();
			Predicate<MulticonjuntoVertex> goal = MulticonjuntoVertex.goal();

			// Grafo

			System.out.println("#### Algoritmo A* ####");

			// Algoritmo A*
			EGraph<MulticonjuntoVertex, MulticonjuntoEdge> graph =
					EGraph.virtual(start,goal,PathType.Sum, Type.Min)
					.edgeWeight(x -> x.weight())
					.goalHasSolution(MulticonjuntoVertex.goalHasSolution())
					.heuristic(MulticonjuntoHeuristic::heuristic)
					.build();
					
			AStar<MulticonjuntoVertex, MulticonjuntoEdge,?> aStar = AStar.ofGreedy(graph);
			
			GraphPath<MulticonjuntoVertex, MulticonjuntoEdge> gp = aStar.search().get();
			
			List<Integer> gp_as = gp.getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList()); // getEdgeList();
			
			
			SolucionMulticonjunto s_as = SolucionMulticonjunto.create(gp_as);
			
			
			System.out.println(s_as);
			System.out.println(gp_as);

//			GraphColors.toDot(aStar.outGraph, "ficheros/multiconjuntosAStarGraph.gv", 
//					v -> v.toGraph(),
//					e -> e.action().toString(), 
//					v -> GraphColors.colorIf(Color.red, MulticonjuntoVertex.goal().test(v)),
//					e -> GraphColors.colorIf(Color.red, gp.getEdgeList().contains(e)));
		}
	}

}
