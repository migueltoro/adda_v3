package us.lsi.p5.ej_2;


import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import us.lsi.common.IntegerSet;
import us.lsi.common.Set2;
import us.lsi.graphs.virtual.VirtualVertex;

public record SubconjuntosVertex(Integer index, IntegerSet remaining) 
	implements VirtualVertex<SubconjuntosVertex, SubconjuntosEdge, Integer>{

	public static SubconjuntosVertex initial() {
		return of(0, IntegerSet.of(DatosSubconjuntos.universo()));
	}

	public static SubconjuntosVertex of(Integer i, IntegerSet rest) {
		return new SubconjuntosVertex(i, rest);
	}

	public static Predicate<SubconjuntosVertex> goal() {
		return v-> v.index() == DatosSubconjuntos.NUM_SC;
	}

	public static Predicate<SubconjuntosVertex> goalHasSolution() {
		return v -> v.remaining().isEmpty();
	}


	public String toGraph() {
		return String.format("%s,%s)",DatosSubconjuntos.nombre(this.index),this.remaining().isEmpty()?"Y":"N");
	}

	
	public List<Integer> actions() {
		List<Integer> r;
		if(index == DatosSubconjuntos.NUM_SC) r = List.of();
		else if(this.index == DatosSubconjuntos.NUM_SC-1) {
			if(remaining.isEmpty()) r = List.of(0);
			else if(DatosSubconjuntos.conjunto(index).containsAll(this.remaining)) r = List.of(1);
			else r = List.of();
		} else {
			Set<Integer> rest = Set2.difference(remaining, DatosSubconjuntos.conjunto(index));
			if(rest.equals(remaining)) r = List.of(0);
			else r = List.of(1,0);
		}
		return r;
	}

	
	@Override
	public SubconjuntosVertex neighbor(Integer a) {
		IntegerSet rest = a==0? IntegerSet.copy(remaining):
			remaining.difference(DatosSubconjuntos.conjunto(index));
		return of(index+1, rest);
	}

	@Override
	public SubconjuntosEdge edge(Integer a) {
		return SubconjuntosEdge.of(this, neighbor(a), a);
	}

    public Integer greedyAction() {
    	return this.actions().stream().max(Comparator.naturalOrder()).get();
    }
    
    public SubconjuntosEdge greedyEdge() {
    	return this.edge(this.greedyAction());
    }

	@Override
	public String toString() {
		return String.format("%d; %d", index, remaining.size());
	}
}
