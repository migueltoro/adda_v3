package us.lsi.graphs.manual.recorridos;

import java.util.Collections;

import us.lsi.graphs.manual.Grafo;

public class RecorridoTopologico<V,E> extends RecorridoEnProfundidadPostorden<V,E> {
	
	public static <V, E> RecorridoTopologico<V, E> of(Grafo<V, E> grafo) {
		return new RecorridoTopologico<>(grafo);
	}

	protected RecorridoTopologico(Grafo<V, E> grafo) {
		super(grafo);
	}

	public void traverse(V source) {
		super.traverse(source);
		Collections.reverse(this.path);
	}

}
