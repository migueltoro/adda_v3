package us.lsi.alg.subconjuntos;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.DPR;
import us.lsi.graphs.virtual.EGraph;

public class TestPD {

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

			System.out.println("\n\n#### PI-7 Ej3 Algoritmo PD ####");

			// Algoritmo PD
			DPR<SubconjuntosVertex, SubconjuntosEdge,?> pdr = 
					DPR.of(graph, null,
							SubconjuntosHeuristic.voraz(start,DatosSubconjuntos.NUM_SC),null,true);
			
			SolucionSubconjuntos sv = SubconjuntosHeuristic.solucionVoraz(start,DatosSubconjuntos.NUM_SC);
			List<SubconjuntosEdge> le = SubconjuntosHeuristic.pathVoraz(start,DatosSubconjuntos.NUM_SC);
			System.out.println("Sv = "+sv);
			Optional<GraphPath<SubconjuntosVertex, SubconjuntosEdge>> gp = pdr.search();
			System.out.println(gp);
			SolucionSubconjuntos s_pdr;
			if (gp.isPresent()) {
				List<Integer> gp_pdr = gp.get().getEdgeList().stream().map(x -> x.action())
						.collect(Collectors.toList());
				s_pdr = SolucionSubconjuntos.of(gp_pdr);
			} else { 				
				s_pdr = sv;
			}

			System.out.println(s_pdr);
			
			GraphColors.toDot(pdr.outGraph,"ficheros/subconjuntosPDGraph.gv",
					v->v.toGraph(),
					e->e.action().toString(),
					v->GraphColors.colorIf(Color.red,SubconjuntosVertex.goal().test(v)),
					e->GraphColors.colorIf(Color.red,(gp.isPresent()?gp.get().getEdgeList():le).contains(e))
					);
		}
	}

}

