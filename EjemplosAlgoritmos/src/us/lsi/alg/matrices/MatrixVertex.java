package us.lsi.alg.matrices;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.hypergraphs.VirtualHyperVertex;

public record MatrixVertex(Integer i,Integer j) implements VirtualHyperVertex<MatrixVertex,MatrixEdge,Integer>{

	
	public static MatrixVertex of(Integer i, Integer j) {
		return new MatrixVertex(i, j);
	}
	
	public static MatrixVertex initial() {
		return new MatrixVertex(0, n);
	}
	
	public static List<MatrixInf> matrices;
	public static Integer n;


	@Override
	public Boolean isValid() {
		return 0 <= i && i < n && i < j && j<=n;
	}

	@Override
	public List<Integer> actions() {
		return IntStream.range(i+1,j).boxed().collect(Collectors.toList());
	}

	@Override
	public Boolean isBaseCase() {
		return j-i < 3;
	}

	@Override
	public Double baseCaseSolution() {
		Double r;
		Integer d = j-i;
		switch(d) {
		case 0: r = 0.; break;
		case 1: r = 0.; break;
		case 2: r = (double) matrices.get(i).nf*matrices.get(i).nc*matrices.get(i+1).nc; break;
		default: r = null;
		
		}
		return r;
	}

	@Override
	public List<MatrixVertex> neighbors(Integer a) {
		return Arrays.asList(MatrixVertex.of(i, a),MatrixVertex.of(a, j));
	}

	@Override
	public MatrixEdge edge(Integer a) {
		return MatrixEdge.of(this, this.neighbors(a),a);
	}
	
}
