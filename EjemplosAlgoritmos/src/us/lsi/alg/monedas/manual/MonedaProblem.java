package us.lsi.alg.monedas.manual;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record MonedaProblem(Integer index,Integer valorRestante) {
	
	
	public static void datosIniciales(String fichero, Integer valorInicial) {
		Moneda.datos(fichero);
		MonedaProblem.valorInicial = valorInicial;
		MonedaProblem.n = Moneda.monedas.size();
	}

	public static MonedaProblem of(Integer index, Integer valorRestante) {
		return new MonedaProblem(index, valorRestante);
	}


	public static MonedaProblem first() {
		return new MonedaProblem(0,MonedaProblem.valorInicial);
	}
	
	public static MonedaProblem last() {
		return new MonedaProblem(MonedaProblem.n,0);
	}
	
	public static Predicate<MonedaProblem> goal() {
		return v->v.index() == MonedaProblem.n;
	}
	
	public static Predicate<MonedaProblem> goalHasSolution() {
		return v->v.valorRestante() == 0;
	}

	public static Integer n;
	public static Integer valorInicial;


	public Integer accionVoraz() {
		Integer a = this.valorRestante()/Moneda.valor(this.index());
		return a;
	}
	
	public Double accionHeuristica() {
		Double a =((double)this.valorRestante())/Moneda.valor(this.index());
		return a;
	}
	
	public List<Integer> acciones() {
		List<Integer> r;
		if(this.index() == MonedaProblem.n) r = new ArrayList<>();
		else if(this.index() == MonedaProblem.n-1 && this.valorRestante%Moneda.valor(this.index) == 0) {
			r = List.of(this.accionVoraz());
		} else if(this.index() == MonedaProblem.n-1 && this.valorRestante%Moneda.valor(this.index) != 0) {
		    r = new ArrayList<>();
		} else {
			Integer nue = this.valorRestante()/Moneda.valor(this.index);
			r = IntStream.range(0,nue+1).boxed().collect(Collectors.toList());
			Collections.reverse(r);
		}
		return r;
	}

	public MonedaProblem vecino(Integer a) {
		MonedaProblem r;
		if(this.valorRestante() == 0) r = MonedaProblem.last();
		else r = MonedaProblem.of(this.index()+1,this.valorRestante()-a*Moneda.valor(this.index()));
		return r;
	}
	
}
