package us.lsi.alg.colorgraphs;

import java.util.HashMap;
import java.util.Map;

import us.lsi.common.IntegerSet;
import us.lsi.graphs.virtual.VirtualVertex;

public interface ColorVertex extends VirtualVertex<ColorVertex,ColorEdge,Integer> {

	/**
	 * Devuelve el índice del vértice actual.
	 */	
	Integer index();	
	/**
	 * Devuelve los colores asignados a los vértices anteriores.
	 */	
	Map<Integer,Integer> cav();
	
	/**
	 * Devuelve el conjunto de colores asignados hasta ahora.
	 */	
	IntegerSet ca();
	/**
	 * Devuelve el número de colores asignados hasta ahora.
	 */
	Integer nc();
	
	/**
	 * Devuelve los colores asignados a los vértices vecinos.
	 */
	IntegerSet cv();
	
	public static ColorVertex first() {
		return ColorVertexI.of(0,new HashMap<>());
	}

}