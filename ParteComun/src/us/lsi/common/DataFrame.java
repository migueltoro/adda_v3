package us.lsi.common;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataFrame {
	
	private Matrix<String> matrix;
	private Map<String,Integer> idFilas;
	private Map<String,Integer> idColumnas;
	private List<String> nombresFilas;
	private List<String> nombresColumnas;
	
	
	private DataFrame(Matrix<String> matrix) {
		super();
		this.matrix = matrix;
		this.idFilas = IntStream.range(1,this.matrix.nf()).boxed()
				.collect(Collectors.toMap(i->this.matrix.get(i,0),i->i-1));
		this.idColumnas = IntStream.range(1,this.matrix.nc()).boxed()
				.collect(Collectors.toMap(c->this.matrix.get(0,c),c->c-1));
		this.nombresFilas = IntStream.range(1,this.matrix.nf()).boxed()
				.map(f->this.matrix.get(f,0))
				.toList();
		this.nombresColumnas = IntStream.range(1,this.matrix.nc()).boxed()
				.map(c->this.matrix.get(0,c))
				.toList();
	}
	
	public static DataFrame of(String file){
		Matrix<String> m = Matrix.of(file, ";");
		return new DataFrame(m);
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
	
	public String value(Integer f, Integer c) {
		return matrix.get(f+1,c+1);
	}

	public void set(Integer f, Integer c, String value) {
		matrix.set(f, c, value);
	}

	public void set(IntPair p, String value) {
		matrix.set(p, value);
	}

	public Integer nf() {
		return matrix.nf()-1;
	}

	public Integer nc() {
		return matrix.nc()-1;
	}

	public <R> Matrix<R> map(Function<String, R> ft) {
		return matrix.map(ft);
	}

	public Matrix<String> copy() {
		return matrix.copy();
	}
	
	public  DataFrame subDataFrame(Integer f0, Integer c0, Integer nf, Integer nc) {
		return new DataFrame(this.matrix.view(f0,c0,nf,nc).copy());
	}
	
	public String titulo() {
		return this.get(0,0);
	}
	
	public Integer idFila(String name) {
		return this.idFilas.getOrDefault(name,1);
	}
	
	public Integer idColumna(String name) {
		return this.idColumnas.getOrDefault(name,-1);
	}
	
	public String get(String fila, String columna) {
		return this.get(this.idFila(fila)+1,this.idColumna(columna)+1);
	}
	
	public List<String> nombresFilas() {
		return nombresFilas;
	}
	
	public String nombreFila(Integer f) {
		return nombresFilas.get(f);
	}

	public List<String> nombresColumnas() {
		return nombresColumnas;
	}
	
	public String nombreColumna(Integer c) {
		return nombresColumnas.get(c);
	}
	
	public <E> List<E> values(Integer c, Function<String,E> t) {
		return IntStream.range(0, this.nf()).boxed()
				.map(f->t.apply(this.value(f,c)))
				.toList();
	}
	
	public <E> List<E> values(String columnName, Function<String,E> t) {
		Integer c = this.idColumna(columnName);
		return IntStream.range(0, this.nf()).boxed()
				.map(f->t.apply(this.value(f,c)))
				.toList();
	}

	public static void main(String[] args) {
		DataFrame m = DataFrame.of("ficheros/prueba.csv");    
		System.out.println(m);
		System.out.println(m.get(1, 3));
		System.out.println(m.subDataFrame(1, 1,5,2));
		System.out.println(m.idFila("c3"));
		System.out.println(m.idColumna("p1"));
		System.out.println(m.get("c3","p1"));
		System.out.println(m.get(3,1));
		System.out.println(m.value(2,0));
		System.out.println(m.values(0,e->Integer.parseInt(e)));
		System.out.println(m.values(1,e->Double.parseDouble(e)));
		System.out.println(m.values("p1",e->Double.parseDouble(e)));
		System.out.println(m.nombresColumnas());
		System.out.println(m.nombresFilas());
		System.out.println(m.values("p2",e->e.equals("T")?true:false));
    }    
}
