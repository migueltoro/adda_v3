package us.lsi.alg.monedas;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.graphs.virtual.VirtualVertex;

public record MonedaVertex(Integer index,Integer valorRestante) implements VirtualVertex<MonedaVertex, MonedaEdge, Integer>{
	
	
	public static void datosIniciales(String fichero, Integer valorInicial) {
		Moneda.datos(fichero);
		MonedaVertex.valorInicial = valorInicial;
		MonedaVertex.n = Moneda.monedas.size();
	}

	public static MonedaVertex of(Integer index, Integer valorRestante) {
		return new MonedaVertex(index, valorRestante);
	}


	public static MonedaVertex first() {
		return new MonedaVertex(0,MonedaVertex.valorInicial);
	}
	
	public static MonedaVertex last() {
		return new MonedaVertex(MonedaVertex.n,0);
	}
	
	public static Predicate<MonedaVertex> goal() {
		return v->v.index() == MonedaVertex.n;
	}
	
	public static Predicate<MonedaVertex> goalHasSolution() {
		return v->v.valorRestante() == 0;
	}

	public static Integer n;
	public static Integer valorInicial;
	

	public MonedaVertex copy() {
		return MonedaVertex.of(this.index(), this.valorRestante());
	}
	
	public Integer size() {
		return MonedaVertex.n - this.index();
	}
	
	@Override
	public Boolean isValid() {
		return this.index() >=0 && this.index() <= MonedaVertex.n && this.valorRestante() >=0;
	}

	public MonedaEdge aristaVoraz() {
		Integer a = this.valorRestante()/Moneda.valor(this.index());
		return MonedaEdge.of(this,this.neighbor(a),a);
	}
	
	@Override
	public List<Integer> actions() {
		List<Integer> r;
		if(this.index() == MonedaVertex.n) r = new ArrayList<>();
		else if(this.index() == MonedaVertex.n-1 && this.valorRestante%Moneda.valor(this.index) == 0) {
			r = List.of(this.aristaVoraz().action());
		} else if(this.index() == MonedaVertex.n-1 && this.valorRestante%Moneda.valor(this.index) != 0) {
		    r = new ArrayList<>();
		} else {
			Integer nue = this.valorRestante()/Moneda.valor(this.index);
			r = IntStream.range(0,nue+1).boxed().collect(Collectors.toList());
			Collections.reverse(r);
		}
		return r;
	}

	@Override
	public MonedaVertex neighbor(Integer a) {
		MonedaVertex r;
		if(this.valorRestante() == 0) r = MonedaVertex.last();
		else r = MonedaVertex.of(this.index()+1,this.valorRestante()-a*Moneda.valor(this.index()));
		return r;
	}

	@Override
	public MonedaEdge edge(Integer a) {
		return MonedaEdge.of(this,this.neighbor(a),a);
	}
	
}
