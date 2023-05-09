package us.lsi.alg.monedas;


import java.util.function.Predicate;
import us.lsi.mochila.datos.DatosMochila;


public class MonedasHeuristica {
	
	public static Double heuristic(MonedaVertex v1, Predicate<MonedaVertex> goal, MonedaVertex v2) {
		return heuristic(v1);
	}
	
	public static Double heuristic(MonedaVertex v1) {
		Double r = 0.;
		Double p = 0.;
		Integer index = v1.index();
		Double valorRestante = v1.valorRestante().doubleValue();
		Integer lastIndex = DatosMochila.n;
		while (valorRestante> 0 && index < lastIndex) {
			Double a = valorRestante / Moneda.valor(index);
			r = r + a * Moneda.valor(index);
			p = p + a * Moneda.peso(index);
			valorRestante = valorRestante - a * Moneda.valor(index);
			index = index + 1;
		}
		return p;
	}
	

}
