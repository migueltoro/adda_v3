module ejemplos_pl {
	
	exports us.lsi.pli.reparto;
	exports us.lsi.pli.sudoku;

	requires transitive datos_compartidos;
	requires transitive solve;
	requires transitive grafos;
	requires transitive partecomun;
	requires transitive org.jgrapht.io;
	requires transitive ejemplos_algoritmos;
}