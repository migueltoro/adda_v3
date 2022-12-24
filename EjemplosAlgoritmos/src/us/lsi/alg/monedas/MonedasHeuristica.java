package us.lsi.alg.monedas;


import java.util.function.Predicate;
import us.lsi.graphs.alg.Greedy;


public class MonedasHeuristica {
	
	public static Double heuristic_negate(MonedaVertex v1, Predicate<MonedaVertex> goal, MonedaVertex v2) {
		return -hu(Mnd.of(v1.index(), v1.valorRestante().doubleValue()),v->v.index() == MonedaVertex.n);
	}
	
	public static Double heuristic(MonedaVertex v1, Predicate<MonedaVertex> goal, MonedaVertex v2) {
		return hu(Mnd.of(v1.index(), v1.valorRestante().doubleValue()),v->v.index() == MonedaVertex.n);
	}
	
	public static record Mnd(Integer index, Double vr) {
		public static Mnd of(Integer index, Double cr) {
			return new Mnd(index,cr);
		}
		public Double heuristicAction() {
			return vr()/Moneda.valor(index());
		}
		public Mnd next() {
			Double a = heuristicAction();
			return new Mnd(index()+1, vr() - a * Moneda.valor(index()));
		}
		public Double weight() {
			if(this.index >= Moneda.n) return 0.;
			return heuristicAction()*Moneda.peso(index());
		}
	}

	public static Double hu(Mnd v1, Predicate<Mnd> goal) {	
		Greedy<Mnd> r = Greedy.of(v1,v->v.next(),goal);
		return r.stream().mapToDouble(v->v.weight()).sum();
	}
	

}
