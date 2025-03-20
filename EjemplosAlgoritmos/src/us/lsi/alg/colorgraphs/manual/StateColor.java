package us.lsi.alg.colorgraphs.manual;

import java.util.ArrayList;
import java.util.List;

import us.lsi.alg.colorgraphs.ColorVertex;
import us.lsi.alg.colorgraphs.SolucionColor;

public class StateColor {
	
	private ColorVertex vertice;
	private Integer valorAcumulado;
	private List<Integer> acciones;
	private List<ColorVertex> vertices;
	

	private StateColor(ColorVertex vertice, Integer valorAcumulado, List<Integer> acciones,
			List<ColorVertex> vertices) {
		super();
		this.vertice = vertice;
		this.valorAcumulado = valorAcumulado;
		this.acciones = acciones;
		this.vertices = vertices;
	}

	public static StateColor of(ColorVertex vertex) {
		List<ColorVertex> vt = new ArrayList<>();
		vt.add(vertex);
		return new StateColor(vertex, 0, new ArrayList<>(), vt);
	}

	void forward(Integer a) {
		this.acciones.add(a);
		ColorVertex vcn = this.vertice().neighbor(a);
		this.vertices.add(vcn);
		this.vertice = vcn;
		this.valorAcumulado = this.vertice.nc();
	}

	void back(Integer a) {
		this.acciones.remove(this.acciones.size() - 1);
		this.vertices.remove(this.vertices.size() - 1);
		this.vertice = this.vertices.get(this.vertices.size() - 1);
		this.valorAcumulado = this.vertice.nc();
	}

	SolucionColor solucion() {
		return SolucionColor.of(this.acciones);
	}

	public ColorVertex vertice() {
		return vertice;
	}

	public Integer valorAcumulado() {
		return valorAcumulado;
	}


}
