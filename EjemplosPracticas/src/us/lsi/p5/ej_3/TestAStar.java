package us.lsi.p5.ej_3;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestAStar {

	public static void main(String[] args) {
		// Set up
		Locale.setDefault(Locale.of("en", "US"));

		String id_fichero = "alumnos_1.txt";
		DatosAlumnos.iniDatos("ficheros/p5/"+id_fichero);
		System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");

		// V�rtices clave

		AlumnosVertex start = AlumnosVertex.initial();
		Predicate<AlumnosVertex> goal = AlumnosVertex.goal();

		// Grafo

		System.out.println("#### Algoritmo A* ####");

		// Algoritmo A*
		EGraph<AlumnosVertex, AlumnosEdge> graph =
					EGraph.virtual(start,goal,PathType.Sum, Type.Max)
					.edgeWeight(x -> x.weight())
					.greedyEdge(AlumnosVertex::greedyEdge)
					.goalHasSolution(AlumnosVertex.goalHasSolution())
					.heuristic(AlumnosHeuristic::heuristic)
					.build();
					
		AStar<AlumnosVertex, AlumnosEdge,?> aStar = AStar.ofGreedy(graph);
			
		GraphPath<AlumnosVertex, AlumnosEdge> gp = aStar.search().get();
			
		List<Integer> gp_as = gp.getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList()); // getEdgeList();
	
		SolucionAlumnos s_as = SolucionAlumnos.of(gp);

		System.out.println(s_as);
		System.out.println(gp_as);

		GraphColors.toDot(aStar.outGraph(), "ficheros_generados/p5/ejemplo3/AlumnosAStarGraph1.gv", 
					v -> v.toGraph(),
					e -> e.action().toString(), 
					v -> GraphColors.colorIf(Color.red, AlumnosVertex.goal().test(v)),
					e -> GraphColors.colorIf(Color.red, gp.getEdgeList().contains(e)));
	}
	

}
