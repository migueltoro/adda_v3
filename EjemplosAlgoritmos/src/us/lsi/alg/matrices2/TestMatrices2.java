package us.lsi.alg.matrices2;

import java.util.Locale;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import us.lsi.common.String2;
import us.lsi.common.Union;
import us.lsi.hypergraphs2.Data;
import us.lsi.hypergraphs2.GraphTree2;


public class TestMatrices2 {

		public static void main(String[] args) {
			Locale.setDefault(Locale.of("en", "US"));
			Auxiliar2.leeFichero("./ficheros/matrices.txt");
			Data.type = Data.DpType.Min;
			MatrixVertex2 p = MatrixVertex2.initial();
			String s = p.solution();
			String2.toConsole(s);
			GraphTree2<MatrixVertex2, MatrixEdge2, Integer> t = p.graphTree();
			String2.toConsole(t.toString());
			String2.toConsole(t.children().toString());
			String2.toConsole(t.allVertices().toString());
			String2.toConsole(t.allEdges().toString());
			
			SimpleDirectedGraph<Union<MatrixVertex2, MatrixEdge2>, DefaultEdge> g = 
					Data.graph(Data.<MatrixVertex2, MatrixEdge2>of().memoryAll);
			
			Data.toDotHypergraph(g, "ficheros/hiperMatrix.gv", p);
			Data.toDotAndOr(g, "ficheros/andOrMatrix.gv", p);
		}

}
