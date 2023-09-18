module partecomun {

	exports us.lsi.curvefitting;
	exports us.lsi.geometria;
	exports us.lsi.iterables;
	exports us.lsi.regularexpressions;
	exports us.lsi.basictypes;
	exports us.lsi.tiposrecursivos;
	exports us.lsi.tiposrecursivos.ast;
	exports us.lsi.math;
	exports us.lsi.tiposrecursivos.parsers;
	exports us.lsi.common;
	exports us.lsi.streams;
	exports us.lsi.filetools;
	exports us.lsi.graphics;
	
	requires transitive commons.math3;
	requires transitive juniversalchardet;
	requires transitive matplotlib4j;
	requires transitive log4j;
	requires transitive org.jgrapht.core;
	requires transitive org.jgrapht.io;
	requires transitive java.desktop;
	requires transitive org.antlr.antlr4.runtime;
}