package us.lsi.alg.sudoku.manual;


import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import us.lsi.alg.sudoku.DatosSudoku;
import us.lsi.alg.sudoku.DatosSudoku.SolucionSudoku;
import us.lsi.alg.sudoku.Sudoku;
import us.lsi.alg.sudoku.SudokuVertex;
import us.lsi.common.List2;
import us.lsi.common.String2;

public class SudokuBT {
	
	public static SudokuBT of() {
		return new SudokuBT();
	}

	private StateSudoku estado;
	private Set<SolucionSudoku> soluciones;
	private Integer iteraciones = 0;
	private Long time;

	private SudokuBT() {
		super();
	}
	
	public Long time() {
		return time;
	}
	
	public Set<SolucionSudoku> soluciones() {
		return soluciones;
	}

	public Integer iteraciones() {
		return iteraciones;
	}

	public void bt(SudokuVertex start) {
		this.time = System.nanoTime();
		this.estado = StateSudoku.of(start);
		this.soluciones = new HashSet<>();
		do {
			bt();
			iteraciones++;
		} while (this.soluciones.size() == 0);
		this.time = System.nanoTime() - this.time;
	}

	public void bt() {
		if (this.estado.vertice().index() == DatosSudoku.numeroDeCasillas) {
			SolucionSudoku s = this.estado.solucion();
			if (s.sudoku().errores() == 0)
				this.soluciones.add(s);
		} else {
			List<Integer> alternativas = this.estado.vertice().actions();
			if (this.estado.vertice().index() < 80)
				alternativas = List2.randomUnitary(alternativas);
			for (Integer a : alternativas) {
				this.estado.forward(a);
				this.bt();
				this.estado.back(a);
			}
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		DatosSudoku.tamSubCuadro = 3;
		Sudoku sd = DatosSudoku.leeFichero("ficheros/sudoku2.txt");
		SudokuBT a = SudokuBT.of();
		a.bt(SudokuVertex.first(sd));
		System.out.println("Tiempo = " + a.time());
		System.out.println("Iteraciones = " + a.iteraciones());
		String2.toConsole(a.soluciones(), "Soluciones");
	}

	

}
