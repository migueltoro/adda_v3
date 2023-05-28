package us.lsi.alg.typ;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.List2;
import us.lsi.graphs.virtual.SimpleEdgeAction;
import us.lsi.graphs.virtual.VirtualVertex;


public record TyPVertex(Integer index, List<Double> cargas) 
      implements VirtualVertex<TyPVertex, SimpleEdgeAction<TyPVertex,Integer>, Integer>{

	
	public static TyPVertex first() {
		return new TyPVertex(0,List2.nCopies(0.,DatosTyP.m));
	}
	
	public static TyPVertex last() {
		return new TyPVertex(DatosTyP.n,List2.nCopies(0.,DatosTyP.m));
	}
	
	public static TyPVertex of(Integer index, List<Double> cargas) {
		List<Double> cargasC = List.copyOf(cargas);
		return new TyPVertex(index,cargasC);
	}
	
	public Boolean goal() {
		return this.index()==DatosTyP.n;
	}
	
	public Integer npMax() {
		return IntStream.range(0,DatosTyP.m)
		.boxed()
		.max(Comparator.comparing(i->this.cargas.get(i)))
		.get();
	}
	
	public Integer npMin() {
		return IntStream.range(0,DatosTyP.m)
				.boxed()
				.min(Comparator.comparing(i->this.cargas.get(i)))
				.get();
	}

	public Double maxCarga() {
		return this.cargas().get(this.npMax());
	}

	@Override
	public Boolean isValid() {
		return true;
	}

	@Override
	public List<Integer> actions() {
		if (this.goal()) return List2.of();

		Map<Double, List<Integer>> s = IntStream.range(0, DatosTyP.m).boxed()
				.collect(Collectors.groupingBy(p -> this.cargas().get(p)));

		return  s.values().stream().map(ls -> ls.get(0)).collect(Collectors.toList());
	}
	
	public List<Double> cargasDespues(Integer a){
		Double d = DatosTyP.duracion(this.index) + this.cargas().get(a);
        return List2.setElement(this.cargas(), a, d);
	}
	
	public Integer greadyAction() {
		return IntStream.range(0,DatosTyP.m)
				.boxed()
				.min(Comparator.comparing(a->cargasDespues(a).get(a)))
				.get();
	}
	
	public  SimpleEdgeAction<TyPVertex, Integer> greadyEdge() {
		return this.edge(this.greadyAction());
	}

	@Override
	public TyPVertex neighbor(Integer a) {
		return TyPVertex.of(index+1, cargasDespues(a));
	}
	
	@Override
	public SimpleEdgeAction<TyPVertex, Integer> edge(Integer a) {
		return SimpleEdgeAction.of(this,this.neighbor(a), a, 1.);
	}
	
	public static TyPVertex copy(TyPVertex vertex) {
		return new TyPVertex(vertex.index,vertex.cargas);
	}

}
