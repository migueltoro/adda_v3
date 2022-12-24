package us.lsi.sudoku.datos;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.streams.Stream2;


public class SolucionSudoku {
	
	private Casilla[][] casillas;
	private Integer errores;
	
	public static SolucionSudoku of() {
		return new SolucionSudoku();
	}

	public static SolucionSudoku of(List<Integer> primerosValoresEnCasillasLibres) {
		return new SolucionSudoku(primerosValoresEnCasillasLibres);
	}

	private SolucionSudoku(SolucionSudoku s) {
		super();
		Integer n = DatosSudoku.numeroDeFilas;
		this.casillas = new Casilla[n][n];
		for(int x = 0;x<n;x++)
			for(int y=0;y<n;y++)
				this.casillas[x][y] = s.casillas[x][y].copy();
	}

	private SolucionSudoku() {
		super();
		Integer n = DatosSudoku.numeroDeFilas;
		this.casillas = new Casilla[n][n];
		DatosSudoku.getCasillas()
			.stream()
			.forEach(c->casillas[c.getX()][c.getY()] = c.copy());
	}
	
	private SolucionSudoku(List<Integer> primerosValoresEnCasillasLibres) {
		super();
		Integer n = DatosSudoku.numeroDeFilas;
		this.casillas = new Casilla[n][n];
		DatosSudoku.getCasillas()
			.stream()
			.forEach(c->casillas[c.getX()][c.getY()] = c.copy());		
		IntStream.range(0, primerosValoresEnCasillasLibres.size())
			.forEach(i->this.getCasillaLibre(i).setValue(primerosValoresEnCasillasLibres.get(i)));
		this.errores = calculaErrores();
	}
	
	public SolucionSudoku copy() {
		return new SolucionSudoku(this);
	}
	
	public List<Integer> getValoresLibresEnCasilla(Casilla d){
		Set<Integer> vo = DatosSudoku.getCasillasEnConflicto(d).stream()
				.filter(c->this.casillas[c.getX()][c.getY()].getValue()!=null)
				.map(c->this.casillas[c.getX()][c.getY()].getValue())
				.collect(Collectors.toSet());
		List<Integer> r = new ArrayList<>(DatosSudoku.getTodosLosValores());
		r.removeAll(vo);
		return r;
	}
	
	public List<Integer> getValoresLibresEnCasilla(Integer index){
		return getValoresLibresEnCasilla(getCasillaLibre(index));
	}
	
	public Set<Integer> getValoresOcupadosEnFila(Integer y){
		return DatosSudoku.getCasillasEnFila(y).stream()
				.filter(c->this.casillas[c.getX()][c.getY()].getValue()!=null)
				.map(c->this.casillas[c.getX()][c.getY()].getValue())
				.collect(Collectors.toSet());
	}
	
	public Set<Integer> getValoresOcupadosEnColumna(Integer x){
		return DatosSudoku.getCasillasEnColumna(x).stream()
				.filter(c->this.casillas[c.getX()][c.getY()].getValue()!=null)
				.map(c->this.casillas[c.getX()][c.getY()].getValue())
				.collect(Collectors.toSet());
	}
	
	public Set<Integer> getValoresOcupadosEnSubCuadro(Integer sc){
		return DatosSudoku.getCasillasEnSubCuadro(sc).stream()
				.filter(c->this.casillas[c.getX()][c.getY()].getValue()!=null)
				.map(c->this.casillas[c.getX()][c.getY()].getValue())
				.collect(Collectors.toSet());
	}

	private Integer calculaErrores() {
		Integer r = 0;
		Integer n = DatosSudoku.numeroDeFilas;
		r = r+IntStream.range(0, n)
			.map(y->n-getValoresOcupadosEnFila(y).size())
			.sum();
		r = r+IntStream.range(0, n)
			.map(x->n-getValoresOcupadosEnColumna(x).size())
			.sum();
		r = r+IntStream.range(0, n)
			.map(sc->n-getValoresOcupadosEnSubCuadro(sc).size())
			.sum();
		return r;
	}

	private String fila(Integer y) {
		return IntStream.range(0,DatosSudoku.numeroDeFilas)
					.boxed()
					.map(x->casillas[x][y].getStringValue())
					.collect(Collectors.joining(" "));
	}
	@Override
	public String toString() {
		errores = calculaErrores();
		return Stream2.range(DatosSudoku.numeroDeFilas-1, -1, -1)
			    .boxed()
			    .map(y->fila(y))
			    .collect(Collectors.joining("\n",
			    		"Errores = "+this.errores+"\n\n","\n__________\n"));
	}

	public int[] getValores() {
		int[] values = new int[81];
		int k = 0;
		for(int y = DatosSudoku.numeroDeFilas-1; y > -1;y = y -1) {
			for(int x = 0; x < DatosSudoku.numeroDeFilas;x = x+1) {
				values[k]= casillas[x][y].getValue();
				k++;
			}
		}
		return values;
	}
	
	public Integer getErrores() {
		return calculaErrores();
	}

	public Casilla getCasilla(Integer x, Integer y) {
		return casillas[x][y];
	}
	
	public Casilla getCasilla(Casilla c) {
		return casillas[c.getX()][c.getY()];
	}
	
	public Casilla getCasillaLibre(Integer index) {
		Casilla c = DatosSudoku.getCasillaLibre(index);
		return this.getCasilla(c);
	}
}
