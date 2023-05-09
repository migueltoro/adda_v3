package us.lsi.alg.monedas.manual;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import us.lsi.alg.monedas.DatosMonedas;
import us.lsi.alg.monedas.Moneda;
import us.lsi.alg.monedas.MonedaVertex;
import us.lsi.alg.monedas.SolucionMonedas;

public class MonedasHeuristica {

	record Sh(Double valor, Double peso) {}
	
	public static Double heuristic(MonedaVertex v1, Predicate<MonedaVertex> goal, MonedaVertex v2) {
		return heuristic(v1.index(), v1.valorRestante().doubleValue(), v2.index());
	}
	
	public static Double heuristic(Integer index, Double valorRestante, Integer lastIndex) {
		Double r = 0.;
		Double p = 0.;
		while (valorRestante> 0 && index < lastIndex) {
			Double a = valorRestante / Moneda.valor(index);
			r = r + a * Moneda.valor(index);
			p = p + a * Moneda.peso(index);
			valorRestante = valorRestante - a * Moneda.valor(index);
			index = index + 1;
		}
		return p;
	}

	public static Double cota(MonedaVertex vertex, Integer a) {
		MonedaVertex v = vertex.neighbor(a);
		return a*Moneda.peso(vertex.index())+heuristic(v,MonedaVertex.goal(),MonedaVertex.last());
	}
	
	public static SolucionMonedas solucionVoraz(MonedaVertex v1) {	
		List<Integer> acciones = new ArrayList<>();
		Integer r = 0;
		Integer p = 0;
		Integer valorRestante = v1.valorRestante();
		for(int index = v1.index(); index < DatosMonedas.n; index++) {
			Integer a = v1.aristaVoraz().action();
			r = r + a * Moneda.valor(index);
			p = p + a * Moneda.peso(index);
			valorRestante = valorRestante - a * Moneda.valor(index);
		}
		return SolucionMonedas.of(acciones);	
	}
}
