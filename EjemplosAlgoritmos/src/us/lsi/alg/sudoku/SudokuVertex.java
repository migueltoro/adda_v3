package us.lsi.alg.sudoku;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import us.lsi.common.IntegerSet;
import us.lsi.common.List2;
import us.lsi.common.Preconditions;
import us.lsi.graphs.virtual.SimpleEdgeAction;
import us.lsi.graphs.virtual.VirtualVertex;

public class SudokuVertex
		implements VirtualVertex<SudokuVertex,SimpleEdgeAction<SudokuVertex,Integer>,Integer>{
	
	
	public static SudokuVertex of(Integer index, List<Casilla> casillas) {
		return new SudokuVertex(index,casillas);
	}
	
	private Integer index;
	private List<Casilla> casillas;
	private List<IntegerSet> valoresOcupadosEnFilas;
	private List<IntegerSet> valoresOcupadosEnColumnas;
	private List<IntegerSet> valoresOcupadosEnSubtablas;
	
	private SudokuVertex(Integer index, List<Casilla> casillas) {
		super();
		this.index = index;
		this.casillas = DatosSudoku.copyCasillas(casillas);
		this.valoresOcupados();
		this.fillOneValues();
		this.sortCasillas(this.index);
		this.index = IntStream.range(index,DatosSudoku.n).boxed()
				.filter(i->this.valoresLibresEnCasilla(this.casilla(i)).size()>0)
				.findFirst()
				.orElse(DatosSudoku.n);
	}
	
	public static Predicate<SudokuVertex> goal() {
		return sv->sv.index == DatosSudoku.n;
	}
	
	public static Predicate<SudokuVertex> goalHasSolution() {
		return v->v.errores() == 0;
	}
	
	public static Double heuristic(SudokuVertex v1, Predicate<SudokuVertex> goal, SudokuVertex v2) {
		return (double) -v1.errores();
	}
	
	public static SudokuVertex first() {
		return SudokuVertex.of(0,DatosSudoku.casillas);
	}
	
	public Integer index() {
		return index;
	}
	
	public List<Casilla> casillas() {
		return casillas;
	}

	public List<IntegerSet> valoresOcupadosEnFilas() {
		return valoresOcupadosEnFilas;
	}

	public List<IntegerSet> valoresOcupadosEnColumnas() {
		return valoresOcupadosEnColumnas;
	}

	public List<IntegerSet> valoresOcupadosEnSubtablas() {
		return valoresOcupadosEnSubtablas;
	}

	public Integer errores() {
		Integer r = 0;
		Integer nf = DatosSudoku.nf;
		r = r+IntStream.range(0, nf).map(f->nf-this.valoresOcupadosEnFilas.get(f).size()).sum();
		r = r+IntStream.range(0, nf).map(c->nf-this.valoresOcupadosEnColumnas.get(c).size()).sum();
		r = r+IntStream.range(0, nf).map(st->nf-this.valoresOcupadosEnSubtablas.get(st).size()).sum();
		return r;
	}
	
	public void valoresOcupados() {
		this.valoresOcupadosEnFilas = List2.nCopies(IntegerSet.empty(), DatosSudoku.nf);
		this.valoresOcupadosEnColumnas = List2.nCopies(IntegerSet.empty(), DatosSudoku.nf);
		this.valoresOcupadosEnSubtablas = List2.nCopies(IntegerSet.empty(), DatosSudoku.nf);
		for(int p=0;p<DatosSudoku.n;p++) {
			Casilla cs = this.casilla(p);
			Integer f = cs.f();
			Integer c = cs.c();
			Integer st = cs.st();
			if (cs.definida()) {
				Integer v = cs.value();
				IntegerSet vf = this.valoresOcupadosEnFilas.get(f);
				IntegerSet vc = this.valoresOcupadosEnColumnas.get(c);
				IntegerSet vst = this.valoresOcupadosEnSubtablas.get(st);
				vf = vf.addF(v);
				vc = vc.addF(v);
				vst = vst.addF(v);
				this.valoresOcupadosEnFilas.set(f,vf);
				this.valoresOcupadosEnColumnas.set(c,vc);
				this.valoresOcupadosEnSubtablas.set(st,vst);
			}	
		}
	}
	
	private Comparator<Casilla> comparator() {
		return Comparator.comparing(c->this.valoresLibresEnCasilla(c).size());
	}
	
	public void sortCasillas(Integer from) {
		Collections.sort(this.casillas.subList(from,DatosSudoku.n),this.comparator());
	}

	private IntegerSet valoresOcupadosEnCasilla(Casilla cs) {
		Integer f = cs.f();
		Integer c = cs.c();
		Integer st = cs.st();
		IntegerSet r = this.valoresOcupadosEnFilas.get(f)
				.union(this.valoresOcupadosEnColumnas.get(c))
				.union(this.valoresOcupadosEnSubtablas.get(st));
		return r;
	}
	
	public IntegerSet valoresLibresEnCasilla(Casilla c){
		if(c.definida()) return IntegerSet.empty();
		IntegerSet r = valoresOcupadosEnCasilla(c);
		return DatosSudoku.allValues.difference(r);
	}
	
	private Integer element(Casilla c) {
		IntegerSet s = this.valoresLibresEnCasilla(c);
		Preconditions.checkArgument(!s.isEmpty(),String.format("El conjunto %s está vacío",s));
		return s.stream().toList().get(0);
	}
	
	public void fillOneValues() {
		while (this.numValoresLibresEnCasillas().stream().anyMatch(v->v==1)) {
			this.casillas().stream()
				.filter(c -> this.valoresLibresEnCasilla(c).size() == 1)
				.forEach(c -> c.setValue(element(c)));
		}	
	}
	
	public Casilla casilla() {
		Preconditions.checkArgument(this.index() < DatosSudoku.n);
		return this.casilla(this.index);
	}
	
	public Casilla casilla(Integer i) {
		
		return this.casillas.get(i);
	}
	
	public List<Integer> numValoresLibresEnCasillas() {
		return this.casillas.stream()
				.map(c -> this.valoresLibresEnCasilla(c).size())
				.toList();
	}
	
	@Override
	public Boolean isValid() {
		return true;
	}

	@Override
	public List<Integer> actions() {
		if(this.index == DatosSudoku.numeroDeCasillas) return List.of();
		Casilla c = this.casilla();
		return this.valoresLibresEnCasilla(c).stream().toList();
	}

	@Override
	public SudokuVertex neighbor(Integer a) {		
		List<Casilla> casillas = DatosSudoku.copyCasillas(this.casillas);
		casillas.get(this.index).setValue(a);
		SudokuVertex r = SudokuVertex.of(this.index+1,casillas);
		return r;
	}

	@Override
	public SimpleEdgeAction<SudokuVertex, Integer> edge(Integer a) {
		return SimpleEdgeAction.of(this,this.neighbor(a),a,1.);
	}

	@Override
	public int hashCode() {
		return Objects.hash(casillas, index, valoresOcupadosEnColumnas, valoresOcupadosEnFilas,
				valoresOcupadosEnSubtablas);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SudokuVertex other = (SudokuVertex) obj;
		return Objects.equals(casillas, other.casillas) && Objects.equals(index, other.index)
				&& Objects.equals(valoresOcupadosEnColumnas, other.valoresOcupadosEnColumnas)
				&& Objects.equals(valoresOcupadosEnFilas, other.valoresOcupadosEnFilas)
				&& Objects.equals(valoresOcupadosEnSubtablas, other.valoresOcupadosEnSubtablas);
	}

	@Override
	public String toString() {
		return "index=" + this.index 
				+ "\nvaloresOcupadosEnFilas=" + this.valoresOcupadosEnFilas 
				+ "\nvaloresOcupadosEnColumnas=" + this.valoresOcupadosEnColumnas
				+ "\nvaloresOcupadosEnSubtablas=" + this.valoresOcupadosEnSubtablas
				+ "\nnumeroDeValoresLibres="+this.numValoresLibresEnCasillas()
				+ "\nvaloresLibresEnCasilla="+
					(this.index<DatosSudoku.n?this.valoresLibresEnCasilla(this.casilla()):"___");
	}
}
