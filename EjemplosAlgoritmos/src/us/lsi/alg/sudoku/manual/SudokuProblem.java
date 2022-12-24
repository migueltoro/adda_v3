package us.lsi.alg.sudoku.manual;


import java.util.List;
import java.util.stream.IntStream;

import us.lsi.alg.sudoku.Casilla;
import us.lsi.alg.sudoku.DatosSudoku;
import us.lsi.alg.sudoku.Sudoku;

public record SudokuProblem(Integer index, Sudoku sudoku) {
	
	public static SudokuProblem of(Integer index, Sudoku sudoku) {
		SudokuProblem sp = new SudokuProblem(index, sudoku);
		sp.sudoku.sortIndices(index,DatosSudoku.numeroDeCasillas);
		return sp;
	}
	
	public static SudokuProblem first(Sudoku sudoku) {
		Integer index = IntStream.range(0,DatosSudoku.numeroDeCasillas).boxed()
				.filter(i->sudoku.numValoresLibresEnCasilla(sudoku.casillaIndex(i)) > 0)
				.findFirst()
				.get();
		return new SudokuProblem(index, sudoku);
	}
	
	public SudokuProblem vecino(Integer a) {
		Sudoku sp = this.sudoku().copy();
		Casilla c = sp.casillaIndex(this.index);
		sp.setValueInCasilla(c,a);
		return SudokuProblem.of(this.index+1,sp);
	}
	
	public List<Integer> acciones() {
		Casilla c = this.sudoku().casillaIndex(this.index);
		return this.sudoku().valoresLibresEnCasilla(c).stream().toList();
	}

}
