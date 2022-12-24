package us.lsi.alg.subconjuntos;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.virtual.EGraph;

public class TestBT {

	public static void main(String[] args) {

		// Set up
		Locale.setDefault(new Locale("en", "US"));

		for (Integer id_fichero = 1; id_fichero < 3; id_fichero++) {

			DatosSubconjuntos.iniDatos("ficheros/subconjuntos" + id_fichero + ".txt");
			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");
//			DatosSubconjuntos.toConsole();
			

			// V�rtices clave

			SubconjuntosVertex start = SubconjuntosVertex.initial();

			// Grafo

			EGraph<SubconjuntosVertex, SubconjuntosEdge> graph = 
					EGraph.virtual(start,SubconjuntosVertex.goal())
					.edgeWeight(x-> x.weight())
					.heuristic(SubconjuntosHeuristic::heuristic)
					.build();

			System.out.println("\n\n#### PI-7 Ej3 Algoritmo BT ####");

			// Algoritmo BT
			BT<SubconjuntosVertex, SubconjuntosEdge, SolucionSubconjuntos> bta = 
				BT.of(graph, 
					SolucionSubconjuntos::of, 
					SubconjuntosHeuristic.voraz(start,DatosSubconjuntos.NUM_SC),null,true);

			SolucionSubconjuntos sv = SubconjuntosHeuristic.solucionVoraz(start,DatosSubconjuntos.NUM_SC);
			List<SubconjuntosEdge> le = SubconjuntosHeuristic.pathVoraz(start,DatosSubconjuntos.NUM_SC);
			System.out.println("Sv = "+sv);
			Optional<GraphPath<SubconjuntosVertex, SubconjuntosEdge>> gp = bta.search();
			
			System.out.println(gp.isPresent()?SolucionSubconjuntos.of(gp.get()):sv);
			List<SubconjuntosEdge> ls = bta.optimalPath != null?bta.optimalPath.getEdgeList():null;
			
			GraphColors.toDot(bta.outGraph(),"ficheros/subconjuntosBTGraph.gv",
					v->v.toGraph(),
					e->e.action().toString(),
					v->GraphColors.colorIf(Color.red,SubconjuntosVertex.goal().test(v)),
					e->GraphColors.colorIf(Color.red,(bta.optimalPath != null?ls:le).contains(e))
					);
			
//			System.out.println(ls.stream().map(e->e.action()).toList());
		}
	}

}

