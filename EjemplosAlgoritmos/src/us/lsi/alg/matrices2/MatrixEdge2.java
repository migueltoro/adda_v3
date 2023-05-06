package us.lsi.alg.matrices2;

import java.util.List;

import us.lsi.hypergraphsD.HyperEdgeD;

public record MatrixEdge2(MatrixVertex2 source,List<MatrixVertex2> targets,Integer action) 
           implements HyperEdgeD<MatrixVertex2,MatrixEdge2,Integer,String> {
	
	public static MatrixEdge2 of(MatrixVertex2 source, List<MatrixVertex2> targets, Integer action) {
		return new MatrixEdge2(source, targets, action);
	}
	
	public MatrixEdge2 me() {
		return this;
	}

	@Override
	public Double weight(List<Double> solutions) {		
		Double weight = solutions.get(0)+solutions.get(1);
		Integer i = source.i();
		Integer a = action();
		Integer j = source.j();
		weight += MatrixVertex2.matrices.get(i).nf*MatrixVertex2.matrices.get(a).nf*MatrixVertex2.matrices.get(j-1).nc;
		return weight;
	}

	@Override
	public String solution(List<String> solutions) {
		return String.format("(%s) * (%s)",solutions.get(0),solutions.get(1));
	}

	@Override
	public String toString() {
		return String.format("%s",this.action());
	}
}
