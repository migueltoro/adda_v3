/**
 * 
 */
/**
 * @author migueltoro
 *
 */

module grafos {
	exports us.lsi.graphs;
	exports us.lsi.graphs.views;
	exports us.lsi.graphs.virtual;
	exports us.lsi.colors;
	exports us.lsi.flowgraph;
	exports us.lsi.hypergraphs;
	exports us.lsi.hypergraphsD;
	exports us.lsi.path;
	exports us.lsi.graphs.alg;
	exports us.lsi.graphs.tour;

	requires transitive org.jgrapht.core;
	requires transitive org.jheaps;
	requires transitive org.jgrapht.io;
	requires transitive partecomun;
	requires transitive solve;
	requires transitive datos_compartidos;
}