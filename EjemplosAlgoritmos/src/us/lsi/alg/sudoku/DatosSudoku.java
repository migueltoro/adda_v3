package us.lsi.alg.sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;
import us.lsi.common.Files2;
import us.lsi.common.String2;


public class DatosSudoku {
	
	/**
	 * Tamaño de un subcuadro
	 */
	public static Integer tamSubCuadro = 3;
	/**
	 * Número de filas
	 */
	public static Integer numeroDeFilas = tamSubCuadro*tamSubCuadro;
	/**
	 * Numero de casillas
	 */
	public static Integer numeroDeCasillas = numeroDeFilas*numeroDeFilas;
	
	public static Sudoku sudoku;
	
	/**
	 * @param nf Fichero de datos
	 * @post Inicializa las variables del tipo
	 */
	public static void iniDatos(String nf) {
		List<Casilla> casillas = new ArrayList<>();
		IntStream.range(0, DatosSudoku.numeroDeCasillas).forEach(p->casillas.add(Casilla.of(p)));
		DatosSudoku.sudoku = Sudoku.of(casillas);
		Files2.streamFromFile(nf)
				.map(ln->Casilla.parse(ln))
				.forEach(c ->{Casilla co = Sudoku.casillas().get(c.getP()); 
							   DatosSudoku.sudoku.setValueInCasilla(co,c.getValue());
				 			   co.setInitialValue(true);});
		DatosSudoku.sudoku.check();
		DatosSudoku.sudoku.completarValor();
		DatosSudoku.sudoku.sortIndices(0,DatosSudoku.numeroDeCasillas);		
	}
	
	public record SolucionSudoku(Sudoku sudoku) implements Comparable<SolucionSudoku>{		
		
		@Override
		public String toString() {
			return String.format("Errores = %d\n%s",sudoku.errores(),sudoku);
		}

		@Override
		public int compareTo(SolucionSudoku other) {
			return this.sudoku().errores().compareTo(other.sudoku().errores());
		}
		
	}
	
	
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosSudoku.tamSubCuadro = 3;
		DatosSudoku.iniDatos("ficheros/sudoku2.txt");
		System.out.println(DatosSudoku.sudoku);
		System.out.println(DatosSudoku.sudoku.indices());
		IntStream.range(0,DatosSudoku.numeroDeCasillas).boxed()
			.map(i->DatosSudoku.sudoku.casillaIndex(i))
			.forEach(c->String2.toConsole(String.format("%s, %d",c,DatosSudoku.sudoku.numValoresLibresEnCasilla(c))));
		
//		System.out.println(SudokuProblem.first(DatosSudoku.sudoku));
	}
}
