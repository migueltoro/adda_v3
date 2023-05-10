package us.lsi.alg.monedas;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.graphs.virtual.VirtualVertex;

public record MonedaVertex(Integer index,Integer valorRestante) implements VirtualVertex<MonedaVertex, MonedaEdge, Integer>{

	public static MonedaVertex of(Integer index, Integer valorRestante) {
		return new MonedaVertex(index, valorRestante);
	}

	public static MonedaVertex first(Integer valorInicial) {
		return new MonedaVertex(0,valorInicial);
	}
	
	public static MonedaVertex last() {
		return new MonedaVertex(DatosMonedas.n,0);
	}
	
	public static Predicate<MonedaVertex> goal() {
		return v->v.index() == DatosMonedas.n;
	}
	
	public static Predicate<MonedaVertex> goalHasSolution() {
		return v->v.valorRestante() == 0;
	}
	
	public MonedaVertex copy() {
		return MonedaVertex.of(this.index(), this.valorRestante());
	}
	
	public Integer size() {
		return DatosMonedas.n - this.index();
	}
	
	@Override
	public Boolean isValid() {
		return this.index() >=0 && this.index() <= DatosMonedas.n && this.valorRestante() >=0;
	}

	public MonedaEdge aristaVoraz() {
		Integer a = this.valorRestante()/DatosMonedas.valor(this.index());
		return MonedaEdge.of(this,this.neighbor(a),a);
	}
	
	@Override
	public List<Integer> actions() {
		List<Integer> r;
		if(this.index() == DatosMonedas.n) r = new ArrayList<>();
		else if(this.index() == DatosMonedas.n-1 && this.valorRestante%DatosMonedas.valor(this.index) == 0) {
			r = List.of(this.aristaVoraz().action());
		} else if(this.index() == DatosMonedas.n-1 && this.valorRestante%DatosMonedas.valor(this.index) != 0) {
		    r = new ArrayList<>();
		} else {
			Integer nue = this.valorRestante()/DatosMonedas.valor(this.index);
			r = IntStream.range(0,nue+1).boxed().collect(Collectors.toList());
			Collections.reverse(r);
		}
		return r;
	}

	@Override
	public MonedaVertex neighbor(Integer a) {
		MonedaVertex r;
		if(this.valorRestante() == 0) r = MonedaVertex.last();
		else r = MonedaVertex.of(this.index()+1,this.valorRestante()-a*DatosMonedas.valor(this.index()));
		return r;
	}

	@Override
	public MonedaEdge edge(Integer a) {
		return MonedaEdge.of(this,this.neighbor(a),a);
	}
	
}
