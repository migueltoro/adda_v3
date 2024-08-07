package us.lsi.ag.sudoku;

import us.lsi.alg.sudoku.DatosSudoku;
import us.lsi.alg.sudoku.SudokuVertexI;

public class TestSubTabla {

	public static void main(String[] args) {
		DatosSudoku.tamSubCuadro = 3;
		DatosSudoku.leeFichero("ficheros/sudoku.txt");
		
		SudokuVertexI sv = SudokuVertexI.first();
		
		BlocksDatosSudokuSubTablaAG p = new BlocksDatosSudokuSubTablaAG(sv);
		
		System.out.println(sv);
		System.out.println("-------------------");
		System.out.println(p.initialValues);
		System.out.println(p.initialValues.size());
		System.out.println(p.blocksLimits);
		System.out.println(p.blocksLimits.size());
	}

}
