package us.lsi.alg.subconjuntos;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import us.lsi.common.String2;

public class TestCamino {
	
	public static void camino(SubconjuntosVertex vertice, List<Integer> alt, Double bestValue) {
		Double peso = 0.;
		Set<String> s = new HashSet<>();
		Set<Integer> ss = new HashSet<>();
		for(Integer a:alt) {
//			Integer a = vertice.greedyAction();
			peso += a*DatosSubconjuntos.peso(vertice.indice());
			if(a==1) {
				s.add(DatosSubconjuntos.nombre(vertice.indice()));
				ss.addAll(DatosSubconjuntos.conjunto(vertice.indice()));
			}
			Double hu1 = SubconjuntosHeuristic.heuristic1(vertice,DatosSubconjuntos.NUM_SC);
			Double hu2 = SubconjuntosHeuristic.heuristic2(vertice,DatosSubconjuntos.NUM_SC);
			Boolean c = ss.equals(DatosSubconjuntos.universo());
			vertice = vertice.neighbor(a);
			String2.toConsole(String.format("a = %d, peso = %.1f, hu1 = %.1f, hu2 = %.1f, cubre = %s, vertice = %s",a,peso,hu1,hu2,c,vertice));
		}
	}
	
public static void main(String[] args) {

		
		Locale.setDefault(new Locale("en", "US"));

		for (Integer id_fichero = 2; id_fichero < 3; id_fichero++) {

			DatosSubconjuntos.iniDatos("ficheros/subconjuntos" + id_fichero + ".txt");
			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");
//			DatosSubconjuntos.toConsole();
			
			SubconjuntosVertex start = SubconjuntosVertex.initial();
			
			camino(start,List.of(1, 1, 0, 0, 1, 1, 1, 0), 14.);
			System.out.println("_____________________");
			camino(start,List.of(1, 1, 1, 0, 1, 1, 1, 0), 14.);
			System.out.println("_____________________");
			camino(start,List.of(1, 1, 0, 0, 1, 1, 1, 0), 14.);
			
		}
	}

}
