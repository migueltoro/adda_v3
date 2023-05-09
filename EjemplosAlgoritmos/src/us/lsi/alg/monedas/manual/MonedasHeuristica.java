package us.lsi.alg.monedas.manual;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import us.lsi.alg.monedas.DatosMonedas;
import us.lsi.alg.monedas.MonedaVertex;
import us.lsi.alg.monedas.SolucionMonedas;

public class MonedasHeuristica {
	
	public static Double heuristic(MonedaVertex v1, Predicate<MonedaVertex> goal, MonedaVertex v2) {
		return heuristic(v1.index(), v1.valorRestante().doubleValue(), v2.index());
	}
	
	public static Double heuristic(Integer index, Double valorRestante, Integer lastIndex) {
		Double v = 0.;
		Double p = 0.;
		while (valorRestante> 0 && index < lastIndex) {
			Double a = valorRestante / DatosMonedas.valor(index);
			v = v + a * DatosMonedas.valor(index);
			p = p + a * DatosMonedas.peso(index);
			valorRestante = valorRestante - a * DatosMonedas.valor(index);
			index = index + 1;
		}
		if(valorRestante == 0) return p;
		else return null;
	}

	public static Double cota(MonedaVertex vertex, Integer a) {
		MonedaVertex v = vertex.neighbor(a);
		return a*DatosMonedas.peso(vertex.index())+heuristic(v,MonedaVertex.goal(),MonedaVertex.last());
	}
	
	public static SolucionMonedas solucionVoraz(MonedaVertex v1) {	
		List<Integer> acciones = new ArrayList<>();
		Integer r = 0;
		Integer p = 0;
		Integer valorRestante = v1.valorRestante();
		Integer index = 0;
		while(valorRestante > 0 && index < DatosMonedas.n) {
			Integer a = valorRestante / DatosMonedas.valor(index);
			r = r + a * DatosMonedas.valor(index);
			p = p + a * DatosMonedas.peso(index);
			valorRestante = valorRestante - a * DatosMonedas.valor(v1.index());
			index++;
		}
		System.out.println(valorRestante);
		if(valorRestante == 0) return SolucionMonedas.of(acciones);
		else return null;
	}
	
	public static SolucionMonedas solucionVoraz2(MonedaVertex v1) {
		SolucionMonedas s = null;
		Integer i=0;
		Integer valorInicial = DatosMonedas.valorInicial;
		while (s==null) {
			DatosMonedas.valorInicial = valorInicial + i;
			s = solucionVoraz(v1);
		}
		DatosMonedas.valorInicial = valorInicial;
		return s;
	}
}
