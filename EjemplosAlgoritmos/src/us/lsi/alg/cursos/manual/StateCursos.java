package us.lsi.alg.cursos.manual;

import java.util.ArrayList;
import java.util.List;

import us.lsi.alg.cursos.CursosVertex;
import us.lsi.alg.cursos.DatosCursos;

public class StateCursos {
	
	private CursosVertex vertice;
	private Double valorAcumulado;
	private List<Integer> acciones;
	private List<CursosVertex> vertices;

	private StateCursos(CursosVertex vertice, Double valorAcumulado, List<Integer> acciones,
			List<CursosVertex> vertices) {
		super();
		this.vertice = vertice;
		this.valorAcumulado = valorAcumulado;
		this.acciones = acciones;
		this.vertices = vertices;
	}

	public static StateCursos of(CursosVertex vertex) {
		List<CursosVertex> vt = new ArrayList<>();
		vt.add(vertex);
		return new StateCursos(vertex, 0., new ArrayList<>(), vt);
	}

	void forward(Integer a) {
		this.acciones.add(a);
		CursosVertex vcn = this.vertice().neighbor(a);
		this.vertices.add(vcn);
		this.valorAcumulado = this.valorAcumulado() + a*DatosCursos.getCoste(this.vertice.index());
		this.vertice = vcn;
	}

	void back(Integer a) {
		this.acciones.remove(this.acciones.size() - 1);
		this.vertices.remove(this.vertices.size() - 1);
		this.vertice = this.vertices.get(this.vertices.size() - 1);
		this.valorAcumulado = this.valorAcumulado() - a*DatosCursos.getCoste(this.vertice.index());
	}

	public CursosVertex vertice() {
		return vertice;
	}

	public Double valorAcumulado() {
		return valorAcumulado;
	}

	public List<Integer> acciones() {
		return acciones;
	}
}
