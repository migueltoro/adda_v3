package us.lsi.alg.sudoku.manual;


import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import us.lsi.alg.sudoku.DatosSudoku;
import us.lsi.alg.sudoku.DatosSudoku.SolucionSudoku;
import us.lsi.common.List2;
import us.lsi.common.String2;

public class SudokuBT {

	public static SudokuProblem start;
	public static StateSudoku estado;
	public static Set<SolucionSudoku> soluciones;
	public static Integer iteraciones = 0;

	public static void btm() {
		SudokuBT.start = SudokuProblem.first(DatosSudoku.sudoku);
		SudokuBT.estado = StateSudoku.of(start);
		SudokuBT.soluciones = new HashSet<>();
		do {
			SudokuBT.btm_p();
			iteraciones++;
		} while (SudokuBT.soluciones.size() == 0);
	}

	public static void btm_p() {
		if (SudokuBT.estado.vertice().index() == DatosSudoku.numeroDeCasillas) {
			SolucionSudoku s = SudokuBT.estado.solucion();
			if (s.sudoku().errores() == 0)
				SudokuBT.soluciones.add(s);
		} else {
			List<Integer> alternativas = SudokuBT.estado.vertice().acciones();
			if (SudokuBT.estado.vertice().index() < 80)
				alternativas = List2.randomUnitary(alternativas);
			for (Integer a : alternativas) {
				SudokuBT.estado.forward(a);
				SudokuBT.btm_p();
				SudokuBT.estado.back(a);
			}
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		DatosSudoku.iniDatos("ficheros/sudoku2.txt");
		long startTime = System.nanoTime();
		SudokuBT.btm();
		long endTime = System.nanoTime() - startTime;
		System.out.println("Tiempo = " + endTime);
		System.out.println("Iteraciones = " + SudokuBT.iteraciones);
		String2.toConsole(SudokuBT.soluciones, "Soluciones");
	}

}
