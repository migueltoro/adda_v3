



module ejemplos_pl {
	
	exports us.lsi.pli.reparto;

	requires transitive datos_compartidos;
	requires transitive solve;
	requires transitive grafos;
	requires transitive partecomun;
	requires transitive org.jgrapht.io;
}