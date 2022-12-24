package us.lsi.alg.sudoku.manual;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import us.lsi.alg.sudoku.DatosSudoku;
import us.lsi.alg.sudoku.DatosSudoku.SolucionSudoku;
import us.lsi.common.List2;
import us.lsi.common.String2;

public class SudokuBT {
	
	public static class StateSudoku {
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
		return new StateSudoku(vertex,vt);
	}

	void forward(Integer a) {
		SudokuProblem vcn = this.vertice().vecino(a);		
		this.vertices.add(vcn);
		this.vertice = vcn;
	}

	void back(Integer a) {
		this.vertices.remove(this.vertices.size()-1);
		this.vertice = this.vertices.get(this.vertices.size()-1);		
	}
	
	SolucionSudoku solucion() {
		return new SolucionSudoku(this.vertice.sudoku());
	}

	public SudokuProblem vertice() {
		return vertice;
	}
	
}

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
	} while(SudokuBT.soluciones.size() == 0);
}

public static void btm_p() {
	if(SudokuBT.estado.vertice().index() == DatosSudoku.numeroDeCasillas) {
		SolucionSudoku s = SudokuBT.estado.solucion();
		if(s.sudoku().errores() == 0)	SudokuBT.soluciones.add(s);
	} else {
		List<Integer> alternativas = SudokuBT.estado.vertice().acciones();
		if(SudokuBT.estado.vertice().index() < 80) alternativas = List2.randomUnitary(alternativas);
		for(Integer a:alternativas) {
			SudokuBT.estado.forward(a);
			SudokuBT.btm_p();  
			SudokuBT.estado.back(a);
		}
	}
}

public static void main(String[] args) {
	Locale.setDefault(new Locale("en", "US"));
	DatosSudoku.iniDatos("ficheros/sudoku2.txt");
	long startTime = System.nanoTime();
	SudokuBT.btm();
	long endTime = System.nanoTime() - startTime;
	System.out.println("Tiempo = " + endTime);
	System.out.println("Iteraciones = " + SudokuBT.iteraciones);
	String2.toConsole(SudokuBT.soluciones,"Soluciones");
}

}
