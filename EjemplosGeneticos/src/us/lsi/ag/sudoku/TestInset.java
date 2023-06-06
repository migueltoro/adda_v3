package us.lsi.ag.sudoku;

import us.lsi.alg.sudoku.DatosSudoku;
import us.lsi.alg.sudoku.SudokuVertex;

public class TestInset {

	public static void main(String[] args) {
		DatosSudoku.tamSubCuadro = 3;
		DatosSudoku.leeFichero("ficheros/sudoku.txt");
		
		SudokuVertex sv = SudokuVertex.first();
		
		InSetDatosSudokuAG p = new InSetDatosSudokuAG(sv);
		
		System.out.println(sv);
		System.out.println("-------------------");
		System.out.println(p.values());
	}

}
