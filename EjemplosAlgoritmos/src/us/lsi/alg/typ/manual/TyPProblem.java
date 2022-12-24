package us.lsi.alg.typ.manual;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.List2;

public record TyPProblem(Integer index,List<Integer> cargas, Integer maxCarga,Integer npMax,Integer npMin) {

	public static TyPProblem of(Integer index,List<Integer> cargas) {
		List<Integer> cargasC = List.copyOf(cargas);
		Integer npMax = IntStream.range(0,DatosTyP.m)
				.boxed()
				.max(Comparator.comparing(i->cargasC.get(i)))
				.get();
		Integer npMin = IntStream.range(0,DatosTyP.m)
				.boxed()
				.min(Comparator.comparing(i->cargasC.get(i)))
				.get();	
		Integer maxCarga = cargasC.get(npMax);
		return new TyPProblem(index,cargasC,maxCarga,npMax,npMin);
	}
	
	public static TyPProblem first() {
		return TyPProblem.of(0,List2.ofTam(0,DatosTyP.m));
	}
	
	public List<Integer> acciones() {
		if(this.index == DatosTyP.n) return List2.of();
		
		Map<Integer, List<Integer>> s = IntStream.range(0, DatosTyP.m).boxed()
				.collect(Collectors.groupingBy(p -> this.cargas().get(p)));

		return  s.values().stream().map(ls -> ls.get(0)).collect(Collectors.toList());
	}
	
	public Integer greadyAction() {
		return this.npMin();
	}
	
	public TyPProblem vecino(Integer a) {
		Integer nd = this.cargas().get(a)+DatosTyP.tareas.get(this.index).duracion(); 
		List<Integer> nc = List2.setElement(this.cargas(),a,nd);
		TyPProblem v = TyPProblem.of(index+1, nc);
		return v;
	}
	
}
