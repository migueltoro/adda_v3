package us.lsi.alg.contenedores;

import java.util.Locale;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestVoraz {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));

		for (Integer id_fichero = 1; id_fichero < 2; id_fichero++) {

			DatosContenedores.iniDatos("ficheros/contenedores/contenedores"+ id_fichero +".txt");
			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");	

			// V�rtices clave

			VertexContenedores start = VertexContenedores.initial();
			Predicate<VertexContenedores> goal = VertexContenedores.goal();

			// Grafo
			
			EGraph<VertexContenedores, EdgeContenedores> graph = 
					EGraph.virtual(start,goal,PathType.Last,Type.Max)
					.vertexWeight(x -> (double)x.contenedoresCompletos().size())
					.heuristic(ContenedoresHeuristic::heuristic)
					.build();

			System.out.println("\n\n#### PI-7 Ej3  ####");
			
			GraphPath<VertexContenedores, EdgeContenedores> max = ContenedoresHeuristic.caminoVoraz(graph,50);
			GraphPath<VertexContenedores, EdgeContenedores> max2 = ContenedoresHeuristic.caminoVoraz2(graph);
			Double hu = ContenedoresHeuristic.heuristic(start,goal, null);
			System.out.println(String.format("%.2f,%.2f,%.2f", max.getWeight(),max2.getWeight(),hu));
			
			graph = EGraph.virtual(start.neighbor(start.greadyAction()),goal,PathType.Last,Type.Max)
					.vertexWeight(x -> (double)x.contenedoresCompletos().size())
					.heuristic(ContenedoresHeuristic::heuristic)
					.build();
			
			max2 = ContenedoresHeuristic.caminoVoraz2(graph);
			hu = ContenedoresHeuristic.heuristic(start.neighbor(start.greadyAction()),goal, null);
			System.out.println(String.format("%.2f,%.2f,%.2f", max.getWeight(),max2.getWeight(),hu));
		}

	}

}
