package us.lsi.alg.bufete;
import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;


import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;


public record BufeteVertex(Integer index,List<Integer> cargas) 
      implements VirtualVertex<BufeteVertex, BufeteEdge, Integer> {
	
	public static BufeteVertex of(Integer i, List<Integer> cargas) {
		return new BufeteVertex(i,cargas);
	}

	public static BufeteVertex initialVertex() {
		return new BufeteVertex(0,List2.nCopies(0,DatosBufete.NUM_ABOGADOS));
	}
	
	public static Predicate<BufeteVertex> goal() {
		return v->v.index() == DatosBufete.NUM_CASOS;
	}

	public static BufeteVertex copy(BufeteVertex v) {
		return BufeteVertex.of(v.index(), v.cargas());
	}

	@Override
	public Boolean isValid() {
		return true;
	}
	
	public Integer npMax() {
		return IntStream.range(0,DatosBufete.NUM_ABOGADOS)
		.boxed()
		.max(Comparator.comparing(i->this.cargas.get(i)))
		.get();
	}
	
	public Integer npMin() {
		return IntStream.range(0,DatosBufete.NUM_ABOGADOS)
				.boxed()
				.min(Comparator.comparing(i->this.cargas.get(i)))
				.get();
	}
	
	public Integer maxCarga() {
		return this.cargas().get(this.npMax());
	}
	
	public List<Integer> cargasDespues(Integer a){
		Integer d = DatosBufete.horas(a,this.index) + this.cargas().get(a);
        return List2.setElement(this.cargas(), a, d);
	}
	
	public Integer greadyAction() {
		return IntStream.range(0,DatosBufete.NUM_ABOGADOS)
				.boxed()
				.min(Comparator.comparing(a->cargasDespues(a).get(a)))
				.get();
	}
	
	public  BufeteEdge greadyEdge() {
		return this.edge(this.greadyAction());
	}

	@Override
	public List<Integer> actions() {
		if(BufeteVertex.goal().test(this)) return List2.of();
		else if(this.index() == DatosBufete.NUM_CASOS-1) return List.of(this.greadyAction());
		return List2.rangeList(0,DatosBufete.NUM_ABOGADOS);
	}

	@Override
	public BufeteVertex neighbor(Integer a) {
		return BufeteVertex.of(index+1, cargasDespues(a));
	}

	@Override
	public BufeteEdge edge(Integer a) {
		BufeteVertex siguiente = this.neighbor(a);
		return BufeteEdge.of(this, siguiente, a);
	}

}

