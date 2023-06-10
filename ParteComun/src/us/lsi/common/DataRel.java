package us.lsi.common;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataRel extends DataText {
	
	public static DataRel of(String file, List<String> rowNames){
		Matrix<String> m = Matrix.of(file, ";");
		return new DataRel(m, rowNames);
	}
	
	public static DataRel of(DataText d, List<String> rowNames){
		return new DataRel(d.matrix, rowNames);
	}
	
	protected Map<Pair<String,String>,String> map;
	protected List<String> rowNames;

	private DataRel(Matrix<String> matrix, List<String> rowNames) {
		super(matrix);
		this.map = IntStream.range(0, this.matrix.nf()).boxed()
				.collect(Collectors.toMap(
						f->Pair.of(this.matrix.get(f,0),this.matrix.get(f,1)),
						f->this.matrix.get(f,2)));
		this.rowNames = rowNames;
	}
	
	public Boolean contains(String n1, String n2) {
		return this.map.containsKey(Pair.of(n1, n1));
	}
	
	public <E> E value(String n1, String n2, Function<String,E> f) {
		return f.apply(this.map.get(Pair.of(n1, n1)));
	}
	
	public <E> E value(Integer id1, Integer id2, Function<String,E> f) {
		return f.apply(this.map.get(Pair.of(rowNames.get(id1),rowNames.get(id2))));
	}

}
