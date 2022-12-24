package us.lsi.alg.matrices;


import java.util.List;
import us.lsi.hypergraphs.SimpleHyperEdge;

public record MatrixEdge(MatrixVertex source,List<MatrixVertex> targets,Integer action) 
           implements SimpleHyperEdge<MatrixVertex,MatrixEdge,Integer> {
	
	public static MatrixEdge of(MatrixVertex source, List<MatrixVertex> targets, Integer action) {
		return new MatrixEdge(source, targets, action);
	}


	@Override
	public Double weight(List<Double> solutions) {		
		Double weight = solutions.get(0)+solutions.get(1);
		Integer i = source.i();
		Integer a = action();
		Integer j = source.j();
		weight += MatrixVertex.matrices.get(i).nf*MatrixVertex.matrices.get(a).nf*MatrixVertex.matrices.get(j-1).nc;
		return weight;
	}
}
