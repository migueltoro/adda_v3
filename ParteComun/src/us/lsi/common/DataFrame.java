package us.lsi.common;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataFrame extends DataText {
	
	
	public static DataFrame of(String file){
		Matrix<String> m = Matrix.of(file, ";");
		return new DataFrame(m);
	}
	
	public static DataFrame of(DataText d){
		return new DataFrame(d.matrix);
	}
		
	private Map<String,Integer> rowIds;
	private Map<String,Integer> columnIds;
	private List<String> rowNames;
	private List<String> columNames;
	
	private DataFrame(Matrix<String> matrix) {
		super(matrix);
		this.rowIds = IntStream.range(1,this.matrix.nf()).boxed()
				.collect(Collectors.toMap(i->this.matrix.get(i,0),i->i-1));
		this.columnIds = IntStream.range(1,this.matrix.nc()).boxed()
				.collect(Collectors.toMap(c->this.matrix.get(0,c),c->c-1));
		this.rowNames = IntStream.range(1,this.matrix.nf()).boxed()
				.map(f->this.matrix.get(f,0))
				.toList();
		this.columNames = IntStream.range(1,this.matrix.nc()).boxed()
				.map(c->this.matrix.get(0,c))
				.toList();
	} 
		
	public Map<String, Integer> rowIds() {
		return rowIds;
	}

	public Map<String, Integer> columnIds() {
		return columnIds;
	}

	public List<String> rowNames() {
		return rowNames;
	}

	public List<String> columNames() {
		return columNames;
	}
	
	public String value(Integer f, Integer c) {
		return matrix.get(f+1,c+1);
	}

	public Integer rn() {
		return matrix.nf()-1;
	}

	public Integer cn() {
		return matrix.nc()-1;
	}
	
	public String title() {
		return this.get(0,0);
	}
	
	public Integer rowId(String name) {
		return this.rowIds.getOrDefault(name,1);
	}
	
	public Integer columnId(String name) {
		return this.columnIds.getOrDefault(name,-1);
	}
	
	public String get(String row, String columna) {
		return this.get(this.rowId(row)+1,this.columnId(columna)+1);
	}
	
	public String rowName(Integer f) {
		return rowNames.get(f);
	}

	public List<String> columnNames() {
		return columNames;
	}
	
	public String columnName(Integer c) {
		return columNames.get(c);
	}
	
	public <E> List<E> columnValues(Integer c, Function<String,E> t) {
		return IntStream.range(0, this.rn()).boxed()
				.map(f->t.apply(this.value(f,c)))
				.toList();
	}
	
	public <E> List<E> columnValues(String columnName, Function<String,E> t) {
		Integer c = this.columnId(columnName);
		return IntStream.range(0, this.rn()).boxed()
				.map(f->t.apply(this.value(f,c)))
				.toList();
	}
	
	public <E> E rowValue(String rowName, Function<List<String>,E> t) {
		Integer f = this.rowId(rowName);
		List<String> lf = IntStream.range(0,this.cn()).boxed()
				.map(c->this.get(f,c)).toList();
		return t.apply(lf);
	}
	
	public <E> E rowValue(Integer rowId, Function<List<String>,E> t) {
		return rowValue(this.rowName(rowId),t);
	}
	
	public <E> List<E> allRowValues(Function<List<String>,E> t) {
		return IntStream.range(0,this.rn()).boxed()
				.map(r->this.rowValue(r,t))
				.toList();
	}

	public static void main(String[] args) {
		DataText m = DataText.of("ficheros/prueba.csv");    
		System.out.println(m);
		System.out.println(m.get(1, 3));
		System.out.println(m.subDataText(1,1,5,2));
		DataFrame m2 = m.asDataFrame();
		System.out.println(m2.rowId("c3"));
		System.out.println(m2.columnId("p1"));
		System.out.println(m2.get("c3","p1"));
		System.out.println(m2.get(3,1));
		System.out.println(m2.value(2,0));
		System.out.println(m2.columnValues(0,e->Integer.parseInt(e)));
		System.out.println(m2.columnValues(1,e->Double.parseDouble(e)));
		System.out.println(m2.columnValues("p1",e->Double.parseDouble(e)));
		System.out.println(m2.columnNames());
		System.out.println(m2.rowNames());
		System.out.println(m2.columnValues("p2",e->e.equals("T")?true:false));
    }    
}
