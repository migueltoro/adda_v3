package us.lsi.alg.cursos;

import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.alg.PDR;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestPDR {

	public static void main(String[] args) {
		
Locale.setDefault(Locale.of("en", "US"));
		
		DatosCursos.iniDatos("ficheros/cursos/cursos3.txt");
		
		EGraph<CursosVertex, CursosEdge> graph = 
				EGraph.virtual(CursosVertex.first(),CursosVertex.goal(), PathType.Sum, Type.Min)
					.goalHasSolution(CursosVertex.goalHasSolution())
					.greedyEdge(CursosVertex::greedyEdge)
					.heuristic(CursosHeuristic::heuristic)
					.build();
		
		GreedyOnGraph<CursosVertex, CursosEdge> ms = GreedyOnGraph.of(graph);
		GraphPath<CursosVertex, CursosEdge> path = ms.path();
		
		System.out.println(SolucionCursos.of(path));
		
		System.out.println("_________________________________");
		
		PDR<CursosVertex, CursosEdge, SolucionCursos> pdr = PDR.of(graph,path.getWeight(),path);
		
		GraphPath<CursosVertex,CursosEdge> gp = pdr.search().get();
		SolucionCursos s = SolucionCursos.of(gp);
		System.out.println(s);
	}

}
