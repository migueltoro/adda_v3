package us.lsi.alg.typ.manual;

import java.util.ArrayList;
import java.util.List;

import us.lsi.alg.typ.SolucionTyP;
import us.lsi.alg.typ.TyPVertexI;
import us.lsi.common.List2;

public class StateTyP {

	TyPVertexI vertice;
	Integer valorAcumulado;
	List<Integer> acciones;
	List<TyPVertexI> vertices;

	public StateTyP(TyPVertexI vertice, Integer valorAcumulado, List<Integer> acciones, List<TyPVertexI> vertices) {
		super();
		this.vertice = vertice;
		this.valorAcumulado = valorAcumulado;
		this.acciones = acciones;
		this.vertices = vertices;
	}

	public static StateTyP of(TyPVertexI vertex) {
		List<TyPVertexI> vt = List2.of(vertex);
		return new StateTyP(vertex, 0, new ArrayList<>(), vt);
	}

	public void forward(Integer a) {
		this.acciones.add(a);
		TyPVertexI vcn = this.vertice.neighbor(a);
		this.vertices.add(vcn);
		this.valorAcumulado = vcn.maxCarga().intValue();
		this.vertice = vcn;
	}

	public void back(Integer a) {
		this.acciones.remove(this.acciones.size() - 1);
		this.vertices.remove(this.vertices.size() - 1);
		this.vertice = this.vertices.get(this.vertices.size() - 1);
		this.valorAcumulado = this.vertice.maxCarga().intValue();
	}
	

	public SolucionTyP solucion() {
		return SolucionTyP.of(TyPVertexI.first(), this.acciones);
	}

	public TyPVertexI vertice() {
		return vertice;
	}
	
}
