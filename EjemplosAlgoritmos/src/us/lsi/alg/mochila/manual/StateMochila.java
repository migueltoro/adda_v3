package us.lsi.alg.mochila.manual;

import java.util.ArrayList;
import java.util.List;

import us.lsi.alg.mochila.MochilaVertex;
import us.lsi.mochila.datos.DatosMochila;

	
public class StateMochila {
	private MochilaVertex vertice;
	private Integer valorAcumulado;
	private List<Integer> acciones;
	private List<MochilaVertex> vertices;

	private StateMochila(MochilaVertex vertice, Integer valorAcumulado, List<Integer> acciones,
			List<MochilaVertex> vertices) {
		super();
		this.vertice = vertice;
		this.valorAcumulado = valorAcumulado;
		this.acciones = acciones;
		this.vertices = vertices;
	}

	public static StateMochila of(MochilaVertex vertex) {
		List<MochilaVertex> vt = new ArrayList<>();
		vt.add(vertex);
		return new StateMochila(vertex, 0, new ArrayList<>(), vt);
	}

	void forward(Integer a) {
		this.acciones.add(a);
		MochilaVertex vcn = this.vertice().neighbor(a);
		this.vertices.add(vcn);
		this.valorAcumulado = this.valorAcumulado() + a * DatosMochila.getValor(this.vertice().index());
		this.vertice = vcn;
	}

	void back(Integer a) {
		this.acciones.remove(this.acciones.size() - 1);
		this.vertices.remove(this.vertices.size() - 1);
		this.vertice = this.vertices.get(this.vertices.size() - 1);
		this.valorAcumulado = this.valorAcumulado() - a * DatosMochila.getValor(this.vertice.index());
	}

	public MochilaVertex vertice() {
		return vertice;
	}

	public Integer valorAcumulado() {
		return valorAcumulado;
	}

	public List<Integer> acciones() {
		return acciones;
	}

}
