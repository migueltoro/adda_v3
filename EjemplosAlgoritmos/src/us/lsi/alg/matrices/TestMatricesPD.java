package us.lsi.alg.matrices;

import java.util.Locale;

import us.lsi.graphs.alg.DP;
import us.lsi.graphs.alg.DP.PDType;
import us.lsi.hypergraphs.GraphTree;
import us.lsi.hypergraphs.SimpleVirtualHyperGraph;

public class TestMatricesPD {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		Auxiliar.leeFichero("./ficheros/matrices.txt");
		
		MatrixVertex initial = MatrixVertex.initial();
		
		SimpleVirtualHyperGraph<MatrixVertex,MatrixEdge,Integer> graph = 
				SimpleVirtualHyperGraph.simpleVirtualHyperGraph(initial);
		
		DP<MatrixVertex, MatrixEdge, Integer> a = DP.dynamicProgrammingSearch(graph,PDType.Min);
		
		a.withGraph = true;
		a.search();
		
		GraphTree<MatrixVertex, MatrixEdge, Integer> tree = a.searchTree(initial);
		
		System.out.println(tree);
		
		System.out.println(Auxiliar.solucion(tree));
		
		a.toDot("ficheros/matricesPDGraph.gv",
				v->String.format("(%d,%d)",v.i(),v.j()),
				e->e.action().toString(),
				tree.vertices());
	}

}
