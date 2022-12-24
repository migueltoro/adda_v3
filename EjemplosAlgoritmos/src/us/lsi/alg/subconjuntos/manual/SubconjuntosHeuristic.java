package us.lsi.alg.subconjuntos.manual;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import us.lsi.common.String2;

public class SubconjuntosHeuristic {

	public static Double heuristic(SubconjuntosProblem v1, Predicate<SubconjuntosProblem> goal, SubconjuntosProblem v2) {
		return heuristic2(v1, DatosSubconjuntos.NUM_SC);
	}
	
	public static Double voraz(SubconjuntosProblem vertice, Integer lastIndex) {
		Double peso = 0.;
		while(!vertice.actions().isEmpty() && vertice.indice() < lastIndex && !vertice.cubreUniverso()) {
			Integer a = vertice.greedyAction();
			peso += a*DatosSubconjuntos.peso(vertice.indice());
			vertice = vertice.neighbor(a);
		}
		return peso;
	}
	
	public static SolucionSubconjuntos solucionVoraz(SubconjuntosProblem vertice, Integer lastIndex) {
		Double peso = 0.;
		Set<String> s = new HashSet<>();
		Set<Integer> ss = new HashSet<>();
		while(!vertice.actions().isEmpty() && vertice.indice() < lastIndex && !vertice.cubreUniverso()) {
			Integer a = vertice.greedyAction();
			peso += a*DatosSubconjuntos.peso(vertice.indice());
			if(a==1) {
				s.add(DatosSubconjuntos.nombre(vertice.indice()));
				ss.addAll(DatosSubconjuntos.conjunto(vertice.indice()));
			}
			vertice = vertice.neighbor(a);
		}
//		String2.toConsole(String.format("%s,\nSolucion = %s",vertice,s));
		Boolean c = ss.equals(DatosSubconjuntos.universo());
		return new SolucionSubconjuntos(peso,s,c);
	}
	
	public static Double heuristic0(SubconjuntosProblem vertice, Integer lastIndex) {
		return 0.;
	}

	public static Double heuristic1(SubconjuntosProblem vertice, Integer lastIndex) {
		if (vertice.cubreUniverso() || voraz(vertice,lastIndex) == 0.)  return 0.;
		else return IntStream.range(vertice.indice(),lastIndex)
				.mapToDouble(i->DatosSubconjuntos.peso(i))
				.min()
				.getAsDouble();
	}
	
	public static Double heuristic2(SubconjuntosProblem vertice, Integer lastIndex) {
		Double peso = 0.;
		while(!vertice.actions().isEmpty() && vertice.indice() < lastIndex && !vertice.cubreUniversoDespues()) {
			Integer a = vertice.greedyAction();
			peso += a*DatosSubconjuntos.peso(vertice.indice());
			vertice = vertice.neighbor(a);
		}
		return peso;
	}
	
	public static Double cota(SubconjuntosProblem v1, Integer a) {
		SubconjuntosProblem v2 = v1.neighbor(a);
		return DatosSubconjuntos.peso(v1.indice())*a+heuristic2(v2,DatosSubconjuntos.NUM_SC);
	}
	
	public static void main(String[] args) {

		
		Locale.setDefault(new Locale("en", "US"));

		for (Integer id_fichero = 1; id_fichero < 3; id_fichero++) {

			DatosSubconjuntos.iniDatos("ficheros/subconjuntos" + id_fichero + ".txt");
			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");
//			DatosSubconjuntos.toConsole();
			
			SubconjuntosProblem start = SubconjuntosProblem.initial();
			
			String2.toConsole("Solucion Voraz = "+solucionVoraz(start,DatosSubconjuntos.NUM_SC).toString());
			String2.toConsole("Voraz = "+voraz(start,DatosSubconjuntos.NUM_SC).toString());
			String2.toConsole("H1 = "+heuristic1(start,DatosSubconjuntos.NUM_SC).toString());
			String2.toConsole("H2 = "+heuristic2(start,DatosSubconjuntos.NUM_SC).toString());
		}
	}

}

