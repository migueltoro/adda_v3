package us.lsi.alg.mochila;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.*;

import org.jgrapht.GraphPath;


import us.lsi.common.Preconditions;
import us.lsi.graphs.virtual.VirtualVertex;
import us.lsi.mochila.datos.SolucionMochila;
import us.lsi.mochila.datos.DatosMochila;

public record MochilaVertex(Integer index, Integer capacidadRestante)
          implements VirtualVertex<MochilaVertex, MochilaEdge, Integer> {

	public static MochilaVertex initialVertex() {
		return of(0, capacidadInicial);
	}
	
	public static MochilaVertex copy(MochilaVertex m) {
		return of(m.index, m.capacidadRestante);
	}
	
	public static MochilaVertex of(int index, Integer capacidadRestante) {
		return new MochilaVertex(index, capacidadRestante);
	}
	
	public static MochilaVertex lastVertex() {
		return new MochilaVertex(n, 0);
	}
	
	public static Predicate<MochilaVertex> goal() {
		return v->v.index == MochilaVertex.n;
	}

	public static Integer n = DatosMochila.numeroDeObjetos;
	public static Integer capacidadInicial;

	
	public static SolucionMochila getSolucion(GraphPath<MochilaVertex, MochilaEdge> path){
		return MochilaVertex.getSolucion(path.getEdgeList());
	}

	public static SolucionMochila getSolucion(List<MochilaEdge> ls){
		SolucionMochila s = SolucionMochila.empty();
		ls.stream().forEach(e->s.add(DatosMochila.getObjeto(e.source().index),e.action().intValue()));
		return s;
	}

	@Override
	public Boolean isValid() {
		return index>=0 && index<=DatosMochila.getObjetos().size();
	}
	
	public MochilaEdge greedyEdge() {
		Preconditions.checkElementIndex(index, DatosMochila.numeroDeObjetos);
		Integer a = Math.min(this.capacidadRestante/DatosMochila.getPeso(index),DatosMochila.getNumMaxDeUnidades(index));
		return MochilaEdge.of(this,this.neighbor(a), a);
	}
	
	public Integer greedyAction() {
		Preconditions.checkElementIndex(index, DatosMochila.numeroDeObjetos);
		return Math.min(this.capacidadRestante/DatosMochila.getPeso(index),DatosMochila.getNumMaxDeUnidades(index));
	}
	
	public Double heuristicAction() {
		Preconditions.checkElementIndex(index, DatosMochila.numeroDeObjetos);
		return Math.min(this.capacidadRestante.doubleValue()/DatosMochila.getPeso(index),DatosMochila.getNumMaxDeUnidades(index));
	}

	@Override
	public List<Integer> actions() {
		if(this.index == n) return new ArrayList<>();
		Integer nu = greedyAction().intValue();
		if(this.index == n-1) return new ArrayList<>(nu);
		List<Integer> alternativas = IntStream.rangeClosed(0,nu)
				.boxed()
				.collect(Collectors.toList());
		Collections.reverse(alternativas);
		return alternativas;
	}
	
	@Override
	public MochilaVertex neighbor(Integer a) {
		MochilaVertex r;
		Integer cr = capacidadRestante - a * DatosMochila.getPeso(index);
		if (this.index == MochilaVertex.n - 1 || this.capacidadRestante == 0.) 
			r = MochilaVertex.of(MochilaVertex.n, cr);
		else r = MochilaVertex.of(index + 1, cr);
		return r;
	}

	@Override
	public MochilaEdge edge(Integer a) {
		MochilaVertex v = this.neighbor(a);
		return MochilaEdge.of(this,v,a);
	}
	
}

