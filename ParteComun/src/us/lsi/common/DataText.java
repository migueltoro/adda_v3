package us.lsi.common;

import java.util.List;

public class DataText {
	
	public static DataText of(String file, Integer nf, Integer nc){
		Matrix<String> m = Matrix.of(file, ",", nf, nc);
		return new DataText(m);
	}
	
	protected Matrix<String> matrix;
		
	protected DataText(Matrix<String> matrix) {
		super();
		this.matrix = matrix;
	}
	
	public DataFrame asDataFrame() {
		return DataFrame.of(this);
	}
	
	public DataRel asDataRel(List<String> rowNames1, List<String> rowNames2) {
		return DataRel.of(this,rowNames1, rowNames2);
	}

	public int hashCode() {
		return matrix.hashCode();
	}

	public boolean equals(Object obj) {
		return matrix.equals(obj);
	}

	public String toString() {
		return matrix.toString();
	}
	
	public String get(Integer f, Integer c) {
		return matrix.get(f, c);
	}

	public String get(IntPair p) {
		return matrix.get(p);
	}
	
	public void set(Integer f, Integer c, String value) {
		matrix.set(f, c, value);
	}

	public void set(IntPair p, String value) {
		matrix.set(p, value);
	}

	public Integer rn() {
		return matrix.nf();
	}

	public Integer cn() {
		return matrix.nc();
	}

	public DataText copy() {
		return new DataText(matrix.copy());
	}
	
	public  DataText subDataText(Integer f0, Integer c0, Integer nf, Integer nc) {
		return new DataText(this.matrix.view(f0,c0,nf,nc).copy());
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
