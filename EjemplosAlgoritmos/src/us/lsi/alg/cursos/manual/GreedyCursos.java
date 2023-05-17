package us.lsi.alg.cursos.manual;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import us.lsi.alg.cursos.CursosHeuristic;
import us.lsi.alg.cursos.CursosVertex;
import us.lsi.alg.cursos.DatosCursos;
import us.lsi.alg.cursos.SolucionCursos;


public class GreedyCursos {
	
	public static Double cota(CursosVertex vertex, Integer a) {
		return a * DatosCursos.getCoste(vertex.index()) + 
				CursosHeuristic.heuristic(vertex.neighbor(a), CursosVertex.goal(), null);
	}
	
	public static SolucionCursos solucionVoraz(CursosVertex v1) {
		List<Integer> acciones = new ArrayList<>();
		CursosVertex v = v1;
		Double accumulateValue = 0.;
		while (v.index() < DatosCursos.n) {
			Integer a = v.greedyEdge().action();
			acciones.add(a);
			accumulateValue = accumulateValue + a * DatosCursos.getCoste(v.index());	
			v = v.neighbor(a);
		}
		if (v.remaining().isEmpty() && v.centers().size() <= DatosCursos.maxCentros) {
			return SolucionCursos.of(acciones);
		}else
			return null;
	}
	
	public static void main(String[] args) {	
		Locale.setDefault(Locale.of("en", "US"));
		DatosCursos.iniDatos("ficheros/cursos/cursos3.txt");
		CursosVertex v1 = CursosVertex.first();
		SolucionCursos sv = GreedyCursos.solucionVoraz(v1);
		System.out.println(sv);
		System.out.println(GreedyCursos.cota(v1.neighbor(1),0));
	}

}
