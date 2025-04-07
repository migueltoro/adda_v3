package us.lsi.alg.festival;

import java.util.List;

import us.lsi.graphs.virtual.VirtualVertex;

public interface FestivalVertex extends VirtualVertex<FestivalVertex, FestivalEdge, Integer> {
	Integer k();
	Integer i();  // tipo de entrada
	Integer j();  // tipo de área
	Integer c();  // coste de la entrada i en el área j
	List<Integer> af(); // af(j) es el aforo máximo del área j
	List<Integer> q(); // q(i) cantidad mínima de las entradas de tipo i
	Integer n(); // numero de tipos de entradas
	Integer m(); // número de areas
	FestivalVertex neighbor2(Integer a);

	public static FestivalVertex initial() {
		return new FestivalVertexI(0,DatosFestival2.aforoMaximoAreas,DatosFestival2.cuotasMinimas);
	}
}
