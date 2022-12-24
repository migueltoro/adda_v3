package us.lsi.alg.contenedores;


import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

public record VertexContenedores(Integer index, List<Integer> capRest)
    implements VirtualVertex<VertexContenedores,EdgeContenedores,Integer> {

	public static VertexContenedores of(Integer index,List<Integer> capRest) {
       return new VertexContenedores(index,capRest);	
	}
	
	public static VertexContenedores initial() {
		List<Integer> capRest = IntStream.range(0, DatosContenedores.getNumContenedores()+1).boxed()
			.map(j->DatosContenedores.getTamContenedor(j)).toList();
		return VertexContenedores.of(0,capRest);
	}

	public static Predicate<VertexContenedores> goal() {
		return v-> v.index() == DatosContenedores.getNumElementos();
	}	
	
	public Set<Integer>contenedoresCompletos(){
		return IntStream.range(0,DatosContenedores.getNumContenedores()).boxed()
			.filter(j->capRest.get(j).equals(0))
			.collect(Collectors.toSet());
	}
	
	public Integer greadyAction() {
		Function<Integer,Integer> f = a->this.capRest().get(a)-DatosContenedores.getTamElemento(this.index());
		List<Integer> ls  = this.actions();
		return ls.stream()
				.min(Comparator.comparing(a->f.apply(a)))
				.get();
	}
	
	@Override
	public List<Integer> actions() {
		if(this.index().equals(DatosContenedores.getNumElementos())) return List.of();
        List<Integer> r = IntStream.range(0,DatosContenedores.getNumContenedores()+1).boxed()
        		.filter(j->!contenedoresCompletos().contains(j))
        		.filter(j->DatosContenedores.getPuedeUbicarse(this.index(), j))
        		.filter(j->DatosContenedores.getTamElemento(this.index()) <= this.capRest().get(j))
        		.toList();
        return r;
	}

	@Override
	public VertexContenedores neighbor(Integer a) {
		List<Integer> capRest2 = List2.copy(capRest);
		capRest2.set(a,capRest2.get(a) - DatosContenedores.getTamElemento(index));
		VertexContenedores r = VertexContenedores.of(index+1,capRest2);
		return r;
	}

	@Override
	public EdgeContenedores edge(Integer a) {
		return EdgeContenedores.of(this,this.neighbor(a),a);
	}
	
	public String toGraph() {
		return String.format("%d,%s",this.index(),this.capRest());
	}

}
