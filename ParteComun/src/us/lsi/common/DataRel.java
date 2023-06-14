package us.lsi.common;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataRel extends DataText {
	
	public static DataRel of(DataText d, List<String> rowNames1, List<String> rowNames2){
		return new DataRel(d.matrix, rowNames1,rowNames2);
	}
	
	public Map<Pair<String,String>,List<String>> map;
	protected List<String> rowNames1;
	protected List<String> rowNames2;

	private DataRel(Matrix<String> matrix, List<String> rowNames1,List<String> rowNames2) {
		super(matrix);
		this.map = IntStream.range(0, this.matrix.nf()).boxed()
				.collect(Collectors.toMap(
						f->Pair.of(this.matrix.get(f,0),this.matrix.get(f,1)),
						f->IntStream.range(2,super.cn()).boxed().map(c->super.get(f,c)).toList()));
		this.rowNames1 = rowNames1;
		this.rowNames2 = rowNames2;
	}
	
	public Boolean contains(String n1, String n2) {
		return this.map.containsKey(Pair.of(n1, n2));		
	}
	
	public Boolean contains(Integer n1, Integer n2) {
		return this.map.containsKey(Pair.of(rowNames1.get(n1),rowNames2.get(n2)));
	}
	
	public List<String> values(String n1, String n2) {
		return this.map.getOrDefault(Pair.of(n1, n2),List.of(" "));
	}
	
	public <E> E value(String n1, String n2, Function<String,E> f) {
		return f.apply(values(n1,n2).get(0));
	}
	
	public <E> E value(String n1, String n2, Integer index, Function<String,E> f) {
		return f.apply(values(n1,n2).get(index));
	}
	
	public <E> E value(Integer id1, Integer id2, Function<String,E> f) {
		return f.apply(values(rowNames1.get(id1),rowNames2.get(id2)).get(0));
	}
	
	public <E> E value(Integer id1, Integer id2, Integer index, Function<String,E> f) {
		return f.apply(values(rowNames1.get(id1),rowNames2.get(id2)).get(index));
	}
	
	public Set<Pair<String,String>> pairs() {
		return this.map.keySet();
	}

}
