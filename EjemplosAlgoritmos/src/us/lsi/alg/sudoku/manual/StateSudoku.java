package us.lsi.alg.sudoku.manual;

import java.util.ArrayList;
import java.util.List;

import us.lsi.alg.sudoku.DatosSudoku.SolucionSudoku;


public class StateSudoku {
	private SudokuProblem vertice;
	private List<SudokuProblem> vertices;

	private StateSudoku(SudokuProblem vertice, List<SudokuProblem> vertices) {
		super();
		this.vertice = vertice;
		this.vertices = vertices;
	}

	public static StateSudoku of(SudokuProblem vertex) {
		List<SudokuProblem> vt = new ArrayList<>();
		vt.add(vertex);
		return new StateSudoku(vertex, vt);
	}

	void forward(Integer a) {
		SudokuProblem vcn = this.vertice().vecino(a);
		this.vertices.add(vcn);
		this.vertice = vcn;
	}

	void back(Integer a) {
		this.vertices.remove(this.vertices.size() - 1);
		this.vertice = this.vertices.get(this.vertices.size() - 1);
	}

	SolucionSudoku solucion() {
		return new SolucionSudoku(this.vertice.sudoku());
	}

	public SudokuProblem vertice() {
		return vertice;
	}

}
