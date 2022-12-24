package us.lsi.alg.matrices;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import us.lsi.common.Files2;
import us.lsi.hypergraphs.GraphTree;

public class Auxiliar {
	
	public static void leeFichero(String fichero){
		List<Integer> ls = Files2.streamFromFile(fichero)
				.map(ln->Integer.parseInt(ln))
				.collect(Collectors.toList());
		Integer n = ls.size();
		List<MatrixInf> r = new ArrayList<>();
		for(int i = 0; i<n-1;i++) {
			MatrixInf m = MatrixInf.of(ls.get(i),ls.get(i+1));
			r.add(m);
		}
		MatrixVertex.matrices = r;
		MatrixVertex.n = r.size();
		System.out.println(r);
	}
	
	public static String solucion(GraphTree<MatrixVertex, MatrixEdge, Integer> tree) {
		String r;
		if(tree.isBaseCase()) {
			Integer i = tree.vertex().i();
			Integer j = tree.vertex().j();
			if(j-i ==1) r = MatrixVertex.matrices.get(tree.vertex().i()).toString();
			else r = String.format("(%s * %s)",MatrixVertex.matrices.get(i).toString(),MatrixVertex.matrices.get(i+1).toString());
		} else {
			r = String.format("(%s * %s)",solucion(tree.neighbords().get(0)),solucion(tree.neighbords().get(1)));
		}
		return r;
	}

}
