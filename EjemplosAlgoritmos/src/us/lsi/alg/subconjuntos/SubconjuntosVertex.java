package us.lsi.alg.subconjuntos;


import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import us.lsi.common.Set2;
import us.lsi.graphs.virtual.VirtualVertex;

public record SubconjuntosVertex(Integer indice, Set<Integer> elementosCubiertos) 
    implements VirtualVertex<SubconjuntosVertex, SubconjuntosEdge, Integer>{
	
	public static SubconjuntosVertex of(Integer indice, Set<Integer> elementosCubiertos) {
		return new SubconjuntosVertex(indice,elementosCubiertos);
	}
	
	public static SubconjuntosVertex initial() {
		return SubconjuntosVertex.of(0,new HashSet<>());
	}

	public static Predicate<SubconjuntosVertex> goal() {
		return v-> v.indice == DatosSubconjuntos.NUM_SC;
	}
	
	public static SubconjuntosVertex copy(SubconjuntosVertex sv) {
		return SubconjuntosVertex.of(sv.indice(),new HashSet<>(sv.elementosCubiertos()));
	}
	
	public String toGraph() {
//		Double hu = SubconjuntosHeuristic.heuristic1(this,DatosSubconjuntos.NUM_SC);
//		Double hu = 0.;
		return String.format("%s,%s)",DatosSubconjuntos.nombre(this.indice()),this.cubreUniverso()?"Y":"N");
	}
	
    public Boolean cubreUniverso() {
    	return this.elementosCubiertos().equals(DatosSubconjuntos.universo());
    }
    
    public Boolean cubreUniversoDespues() {
    	Set<Integer>  s = Set2.union(this.elementosCubiertos(),DatosSubconjuntos.conjunto(this.indice()));
    	return s.equals(DatosSubconjuntos.universo());
    }
    
    public Integer greedyAction() {
    	return this.actions().stream().max(Comparator.naturalOrder()).get();
    }
    
    public SubconjuntosEdge greedyEdge() {
    	return this.edge(this.greedyAction());
    }

	@Override
	public Boolean isValid() {
		return true;
	}
	
	@Override
	public List<Integer> actions() {
		List<Integer> r;
		if(this.indice() == DatosSubconjuntos.NUM_SC) r = List.of();
		else if(this.indice() == DatosSubconjuntos.NUM_SC-1) {
			if(this.cubreUniverso()) r = List.of(0);
			else if(this.cubreUniversoDespues()) r = List.of(1);
			else r = List.of();
		} else {
			Set<Integer> s = Set2.difference(DatosSubconjuntos.conjunto(this.indice()),this.elementosCubiertos());
			if(s.isEmpty()) r = List.of(0);
			else r = List.of(1,0);
		}
		return r;
	}
	
	@Override
	public SubconjuntosVertex neighbor(Integer a) {
		if(a==0) return SubconjuntosVertex.of(this.cubreUniverso()?DatosSubconjuntos.NUM_SC:this.indice()+1,this.elementosCubiertos());
		else return SubconjuntosVertex.of(this.indice()+1,
				Set2.union(DatosSubconjuntos.conjunto(this.indice()),this.elementosCubiertos()));
	}
	@Override
	public SubconjuntosEdge edge(Integer a) {
		return SubconjuntosEdge.of(this,this.neighbor(a),a);
	}
	
}
