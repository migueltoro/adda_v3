/**
 * 
 */
/**
 * @author migueltoro
 *
 */

module ejemplos_parte_comun {

	exports us.lsi.iterativorecursivos;
	exports us.lsi.recursivos.problemasdelistas;
	exports us.lsi.trees;
	exports us.lsi.java8ejemplos;
	exports us.lsi.ejemplos_stream;
	exports us.lsi.recursivos.puntos;

	requires transitive datos_compartidos;
	requires junit;
	requires transitive partecomun;
	requires transitive commons.math3;
	requires transitive org.jgrapht.core;
	requires grafos;
}