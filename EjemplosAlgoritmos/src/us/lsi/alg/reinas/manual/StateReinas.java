package us.lsi.alg.reinas.manual;

import java.util.List;

public class StateReinas {
	ReinasProblem vertice;
	List<ReinasProblem> vertices;
	
	public StateReinas(ReinasProblem vertice, List<ReinasProblem> vertices) {
		super();
		this.vertice = vertice;
		this.vertices = vertices;
	}

	void forward(Integer a) {
		ReinasProblem nv = vertice.vecino(a);
		this.vertices.add(nv);
		this.vertice = nv;
	}
	
	void back(Integer a) {
		this.vertices.remove(this.vertices.size()-1);
		this.vertice = this.vertices.get(this.vertices.size()-1);		
	}
	
	SolucionReinas solucion() {
		return SolucionReinas.of(this.vertice);
	}
	
}
