package us.lsi.alg.mochila.manual;

import java.util.ArrayList;
import java.util.List;

	
public class StateMochila {
	private MochilaProblem vertice;
	private Integer valorAcumulado;
	private List<Integer> acciones;
	private List<MochilaProblem> vertices;

	private StateMochila(MochilaProblem vertice, Integer valorAcumulado, List<Integer> acciones,
			List<MochilaProblem> vertices) {
		super();
		this.vertice = vertice;
		this.valorAcumulado = valorAcumulado;
		this.acciones = acciones;
		this.vertices = vertices;
	}

	public static StateMochila of(MochilaProblem vertex) {
		List<MochilaProblem> vt = new ArrayList<>();
		vt.add(vertex);
		return new StateMochila(vertex, 0, new ArrayList<>(), vt);
	}

	void forward(Integer a) {
		this.acciones.add(a);
		MochilaProblem vcn = this.vertice().vecino(a);
		this.vertices.add(vcn);
		this.valorAcumulado = this.valorAcumulado() + a * DatosMochila.valor(this.vertice().index());
		this.vertice = vcn;
	}

	void back(Integer a) {
		this.acciones.remove(this.acciones.size() - 1);
		this.vertices.remove(this.vertices.size() - 1);
		this.vertice = this.vertices.get(this.vertices.size() - 1);
		this.valorAcumulado = this.valorAcumulado() - a * DatosMochila.valor(this.vertice.index());
	}

	SolucionMochila solucion() {
		return SolucionMochila.of(MochilaBT.start, this.acciones);
	}

	public MochilaProblem vertice() {
		return vertice;
	}

	public Integer valorAcumulado() {
		return valorAcumulado;
	}

}
