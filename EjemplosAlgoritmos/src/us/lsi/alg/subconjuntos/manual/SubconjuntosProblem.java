package us.lsi.alg.subconjuntos.manual;


import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import us.lsi.common.Set2;

public record SubconjuntosProblem(Integer indice, Set<Integer> elementosCubiertos) {
	
	public static SubconjuntosProblem of(Integer indice, Set<Integer> elementosCubiertos) {
		return new SubconjuntosProblem(indice,elementosCubiertos);
	}
	
	public static SubconjuntosProblem initial() {
		return SubconjuntosProblem.of(0,new HashSet<>());
	}

	public static Predicate<SubconjuntosProblem> goal() {
		return v-> v.indice == DatosSubconjuntos.NUM_SC;
	}
	
	public static SubconjuntosProblem copy(SubconjuntosProblem sv) {
		return SubconjuntosProblem.of(sv.indice(),new HashSet<>(sv.elementosCubiertos()));
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
	
	public SubconjuntosProblem neighbor(Integer a) {
		if(a==0) return SubconjuntosProblem.of(this.cubreUniverso()?DatosSubconjuntos.NUM_SC:this.indice()+1,
				this.elementosCubiertos());
		else return SubconjuntosProblem.of(this.indice()+1,
				Set2.union(DatosSubconjuntos.conjunto(this.indice()),this.elementosCubiertos()));
	}
	
	
}
