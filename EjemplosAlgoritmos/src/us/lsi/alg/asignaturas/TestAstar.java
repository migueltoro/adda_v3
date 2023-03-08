package us.lsi.alg.asignaturas;


import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestAstar {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "us"));
		String fichero = "ficheros/asignaturas.txt";

		DatosAsignaturas.iniciarDatos(fichero);
		System.out.println(DatosAsignaturas.mejoras);
		AsignaturasVertice v0 = AsignaturasVertice.inicial();
		Predicate<AsignaturasVertice> predicado = t ->AsignaturasVertice.goal(t);

		
		EGraph<AsignaturasVertice,AsignaturasEdge> grafo = 
				EGraph.virtual(v0,predicado,PathType.Last,Type.Max)
				.vertexWeight(v->(double)v.getPeso())
				.goalHasSolution(AsignaturasVertice.goalHasSolution())
				.heuristic(Heuristica::heuristic)
				.build();
	
		
		AStar<AsignaturasVertice, AsignaturasEdge, SolucionAsignaturas> as = AStar.ofGreedy(grafo);
		
		GraphPath<AsignaturasVertice, AsignaturasEdge> s1 = as.search().get();
		
		System.out.println(SolucionAsignaturas.of(s1));
		
		System.out.println("___________________");
		
		as = AStar.ofGreedy(grafo);
		
		Optional<GraphPath<AsignaturasVertice, AsignaturasEdge>> gp = as.search();
		
		System.out.println(SolucionAsignaturas.of(gp.get()));
	}

}
