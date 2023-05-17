package us.lsi.alg.cursos;

import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestGreedy {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		
		DatosCursos.iniDatos("ficheros/cursos/cursos3.txt");
		
		EGraph<CursosVertex, CursosEdge> graph = 
				EGraph.virtual(CursosVertex.first(),CursosVertex.goal(), PathType.Sum, Type.Min)
					.goalHasSolution(CursosVertex.goalHasSolution())
					.greedyEdge(CursosVertex::greedyEdge)
					.heuristic(CursosHeuristic::heuristic)
					.build();
		
		System.out.println(CursosVertex.first());
		System.out.println("==================");
		GreedyOnGraph<CursosVertex, CursosEdge> ms = GreedyOnGraph.of(graph);
		GraphPath<CursosVertex, CursosEdge> path = ms.path();
		
		System.out.println(SolucionCursos.of(path));

	}

}
