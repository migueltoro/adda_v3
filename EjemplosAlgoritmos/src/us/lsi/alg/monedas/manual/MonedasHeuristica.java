package us.lsi.alg.monedas.manual;


import java.util.function.Predicate;

public class MonedasHeuristica {

	record Sh(Double valor, Double peso) {}
	
	public static Double heuristic(MonedaProblem v1, Predicate<MonedaProblem> goal, MonedaProblem v2) {
		return heuristic_left(v1.index(), v1.valorRestante().doubleValue(), v2.index()).peso();
	}
	
	public static Sh heuristic_left(Integer index, Double valorRestante, Integer lastIndex) {
		Double r = 0.;
		Double p = 0.;
		while (valorRestante> 0 && index < lastIndex) {
			Double a = valorRestante / Moneda.valor(index);
			r = r + a * Moneda.valor(index);
			p = p + a * Moneda.peso(index);
			valorRestante = valorRestante - a * Moneda.valor(index);
			index = index + 1;
		}
		return new Sh(r,p);
	}

	public static Double cota(MonedaProblem vertex, Integer a) {
		MonedaProblem v = vertex.vecino(a);
		return a*Moneda.peso(vertex.index())+heuristic(v,MonedaProblem.goal(),MonedaProblem.last());
	}
	
	

}
