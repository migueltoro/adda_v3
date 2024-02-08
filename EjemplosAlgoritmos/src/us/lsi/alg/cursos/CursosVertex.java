package us.lsi.alg.cursos;


import java.util.List;

import us.lsi.common.IntegerSet;
import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

public record CursosVertex(Integer index, IntegerSet remaining, IntegerSet centers) 
    implements VirtualVertex<CursosVertex, CursosEdge, Integer>{
	
	public static CursosVertex first() {
		return of(0, DatosCursos.tematicas, IntegerSet.empty());
	}

	public static CursosVertex of(Integer i, IntegerSet rest, IntegerSet cs) {
		return new CursosVertex(i, rest, cs);
	}
	
	@Override
	public Boolean goal() {
		return this.index() == DatosCursos.n;
	}
	
	@Override
	public Boolean goalHasSolution() {
		return this.remaining().isEmpty();
	}
	
	@Override
	public List<Integer> actions() {
		if (this.index() == DatosCursos.n)
			return List2.empty();
		List<Integer> alternatives = List2.of();
		Integer c = DatosCursos.cursos.get(this.index()).centro();
		Boolean r1 = this.centers().addF(c).size() <= DatosCursos.maxCentros;
		if (r1)
			r1 = this.remaining().intersection(DatosCursos.getTematicasDelCurso(this.index())).size() > 0;
		if (r1)
			alternatives.add(1);
		alternatives.add(0);
		return alternatives;
	}
	
	@Override
	public CursosVertex neighbor(Integer a) {
		IntegerSet rest = a == 0 ? this.remaining()
				: this.remaining().difference(DatosCursos.getTematicasDelCurso(index));
		IntegerSet cs = a == 0 ? this.centers() : this.centers().addF(DatosCursos.getCentro(index));
		return of(index + 1, rest, cs);
	}
	
	@Override
	public CursosEdge edge(Integer a) {
		return CursosEdge.of(this, neighbor(a), a);
	}
	
	public CursosEdge greedyEdge() {
		Integer ac = this.actions().stream().mapToInt(a->a).max().getAsInt();
		return this.edge(ac);
	}
}
