package us.lsi.alg.sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.IntegerSet;
import us.lsi.common.Preconditions;
import us.lsi.streams.Stream2;

public class Sudoku {
	
	public static Sudoku of(List<Casilla> casillas) {
		return new Sudoku(casillas);
	}
	
	public static IntegerSet all = IntStream.rangeClosed(1,DatosSudoku.numeroDeFilas).boxed()
			.map(i->IntegerSet.of(i))
			.reduce(IntegerSet.empty(),(s1,s2)->s1.union(s2));
	
	private static List<Casilla> casillas;
	private List<IntegerSet> valoresOcupadosEnFilas;
	private List<IntegerSet> valoresOcupadosEnColumnas;
	private List<IntegerSet> valoresOcupadosEnSubcuadros;
	private List<Integer> indices;

	private Sudoku(List<Casilla> casillas) {
		super();
		Sudoku.casillas = casillas;
		this.valoresOcupadosEnColumnas = IntStream.range(0,DatosSudoku.numeroDeFilas).boxed()
				.map(i->IntegerSet.empty())
				.collect(Collectors.toList());
		this.valoresOcupadosEnFilas = IntStream.range(0,DatosSudoku.numeroDeFilas).boxed()
				.map(i->IntegerSet.empty())
				.collect(Collectors.toList());
		this.valoresOcupadosEnSubcuadros = IntStream.range(0,DatosSudoku.numeroDeFilas).boxed()
				.map(i->IntegerSet.empty())
				.collect(Collectors.toList());
		this.indices = IntStream.range(0,DatosSudoku.numeroDeCasillas).boxed()
				.collect(Collectors.toList());
	}
	
	public Sudoku(List<IntegerSet> valoresOcupadosEnFilas,
			List<IntegerSet> valoresOcupadosEnColumnas, List<IntegerSet> valoresOcupadosEnSubcuadros,
			List<Integer> indices) {
		this.valoresOcupadosEnFilas = valoresOcupadosEnFilas.stream().map(s->s.copy()).toList();
		this.valoresOcupadosEnColumnas = valoresOcupadosEnColumnas.stream().map(s->s.copy()).toList();
		this.valoresOcupadosEnSubcuadros = valoresOcupadosEnSubcuadros.stream().map(s->s.copy()).toList();
		this.indices = new ArrayList<>(indices);
	}

	public Sudoku copy() {
		return new Sudoku(this.valoresOcupadosEnFilas,this.valoresOcupadosEnColumnas, 
				this.valoresOcupadosEnSubcuadros,this.indices);
	}
	
	public void check() {		
		Sudoku.casillas()
		.stream()
		.filter(c->!c.isWithInitialValue())
		.forEach(c->
			Preconditions.checkArgument(
				this.numValoresLibresEnCasilla(c) > 0 ,
				"La casilla "+c+" est� mal"));
	}
	
	public void sortIndices(Integer from, Integer to) {
		Collections.sort(this.indices.subList(from,to),
				Comparator.comparing(i->this.numValoresLibresEnCasilla(Sudoku.casillas.get(i))));
	}
	
	public void completarValor() {
		Boolean cnd = true;
		while (cnd) {
			cnd = false;
			for (Casilla c : Sudoku.casillas) {
				if (this.numValoresLibresEnCasilla(c) == 1) {
					this.setValueInCasilla(c, this.valoresLibresEnCasilla(c).stream().toList().get(0));
					cnd = true;
				}
			}
		}
	}
	
	public void setValueInCasilla(Casilla c, Integer v) {
		c.setValue(v);
		Integer x = c.getX();
		Integer y = c.getY();
		Integer sc = c.getSubCuadro();
		this.valoresOcupadosEnColumnas.get(x).add(v);
		this.valoresOcupadosEnFilas.get(y).add(v);
		this.valoresOcupadosEnSubcuadros.get(sc).add(v);
	}
	
	public Integer errores() {
		Integer r = 0;
		Integer n = DatosSudoku.numeroDeFilas;
		r = r+IntStream.range(0, n)
			.map(y->n-valoresOcupadosEnFila(y).size())
			.sum();
		r = r+IntStream.range(0, n)
			.map(x->n-valoresOcupadosEnColumna(x).size())
			.sum();
		r = r+IntStream.range(0, n)
			.map(sc->n-valoresOcupadosEnSubcuadro(sc).size())
			.sum();
		return r;
	}
	
	private IntegerSet valoresOcupadosEnFila(Integer y) {
		return this.valoresOcupadosEnFilas.get(y);
	}

	private IntegerSet valoresOcupadosEnColumna(Integer x) {
		return this.valoresOcupadosEnColumnas.get(x);
	}

	private IntegerSet valoresOcupadosEnSubcuadro(Integer sc) {
		return this.valoresOcupadosEnSubcuadros.get(sc);
	}

	private IntegerSet valoresOcupadosEnCasilla(Casilla c) {
		Integer x = c.getX();
		Integer y = c.getY();
		Integer sc = c.getSubCuadro();
		IntegerSet r = valoresOcupadosEnFila(y).union(valoresOcupadosEnColumna(x));
		r = r.union(valoresOcupadosEnSubcuadro(sc));
		return r;
	}
	
	public IntegerSet valoresLibresEnCasilla(Casilla c){
		if(c.isWithInitialValue()) return IntegerSet.empty();
		IntegerSet r = valoresOcupadosEnCasilla(c);
		return Sudoku.all.difference(r);
	}
	
	public Integer numValoresLibresEnCasilla(Casilla c) {
		if(c.isWithInitialValue()) return 0;
		return valoresLibresEnCasilla(c).size();
	}
	
	public List<Integer> indices() {
		return this.indices;
	}

	public static List<Casilla> casillas() {
		return Sudoku.casillas;
	}
	
	public Casilla casilla(Integer p) {
		return Sudoku.casillas.get(p);
	}
	
	public Casilla casillaIndex(Integer index) {
		Integer p = this.indices.get(index);
		return Sudoku.casillas.get(p);
	}
	
	private String fila(Integer y) {
		return IntStream.range(0,DatosSudoku.numeroDeFilas)
					.boxed()
					.map(x->casillas.get(Casilla.of(x, y).getP()).getStringValue())
					.collect(Collectors.joining(" "));
	}
	
	@Override
	public String toString() {
		Integer errores = errores();
		return Stream2.range(DatosSudoku.numeroDeFilas-1, -1, -1)
			    .boxed()
			    .map(y->fila(y))
			    .collect(Collectors.joining("\n",
			    		"Errores = "+errores+"\n\n","\n__________\n"));
	}
}
