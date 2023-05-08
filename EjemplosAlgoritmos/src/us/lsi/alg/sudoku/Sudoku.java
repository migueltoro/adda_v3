package us.lsi.alg.sudoku;

import java.util.List;

import us.lsi.common.IntegerSet;

public interface Sudoku {
	
	List<Casilla> casillas();
	
	List<Integer> indices();
	
	IntegerSet valoresLibresEnCasilla(Casilla c);

	Integer numValoresLibresEnCasilla(Casilla c);
	
	Casilla casilla(Integer p);

	Casilla casillaIndex(Integer index);

	void sortIndices(Integer from, Integer to);

	void completarValores();

	void setValueInCasilla(Casilla c, Integer v);

	Integer errores();

	Sudoku copy();

	void check();

	String toString();

}