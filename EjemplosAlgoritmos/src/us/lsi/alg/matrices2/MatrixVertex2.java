package us.lsi.alg.matrices2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import us.lsi.hypergraphs2.HyperVertex2;
	
public record MatrixVertex2(Integer i,Integer j) 
		implements HyperVertex2<MatrixVertex2,MatrixEdge2,Integer,String>{

		
		public static MatrixVertex2 of(Integer i, Integer j) {
			return new MatrixVertex2(i, j);
		}
		
		public static MatrixVertex2 initial() {
			return new MatrixVertex2(0, n);
		}
		
		public static List<MatrixInf> matrices;
		public static Integer n;

		@Override
		public MatrixVertex2 me() {
			return this;
		}

		public Boolean isValid() {
			return i>=0 && i<=n && j>=i && j<=n;
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
		public Double baseCaseWeight() {
			Integer d = j-i;
			return switch(d) {
			case 0 -> 0.; 
			case 1 -> 0.; 
			case 2 -> (double) matrices.get(i).nf*matrices.get(i).nc*matrices.get(i+1).nc; 
			default -> null;
			};
		}
		
		@Override
		public String baseCaseSolution() {
			Integer d = j-i;
			return switch(d) {
			case 0 -> ""; 
			case 1 -> MatrixVertex2.matrices.get(i()).toString();
			case 2 -> String.format("(%s * %s)",MatrixVertex2.matrices.get(i).toString(),
					MatrixVertex2.matrices.get(i+1).toString());
			default -> null;
			};
		}

		@Override
		public List<MatrixVertex2> neighbors(Integer a) {
			return Arrays.asList(MatrixVertex2.of(i, a),MatrixVertex2.of(a, j));
		}

		@Override
		public MatrixEdge2 edge(Integer a) {
			return MatrixEdge2.of(this, this.neighbors(a),a);
		}

		@Override
		public String toString() {
			return String.format("(%d,%d)",i,j);
		}
	
}
