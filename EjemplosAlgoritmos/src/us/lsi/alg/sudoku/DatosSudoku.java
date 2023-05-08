package us.lsi.alg.sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;
import us.lsi.common.Files2;
import us.lsi.common.String2;


public class DatosSudoku {
	
	/**
	 * Tama�o de un subcuadro
	 */
	public static Integer tamSubCuadro = 3;
	/**
	 * N�mero de filas
	 */
	public static Integer numeroDeFilas = tamSubCuadro*tamSubCuadro;
	/**
	 * Numero de casillas
	 */
	public static Integer numeroDeCasillas = numeroDeFilas*numeroDeFilas;
	
	
	/**
	 * @param nf Fichero de datos
	 * @post Inicializa las variables del tipo
	 */
	public static Sudoku leeFichero(String file) {
		List<Casilla> casillas = new ArrayList<>();
		IntStream.range(0, DatosSudoku.numeroDeCasillas).forEach(p->casillas.add(Casilla.of(p)));
		Sudoku sudoku = SudokuI.of(casillas);
		Files2.streamFromFile(file)
				.map(ln->Casilla.parse(ln))
				.forEach(c ->{Casilla co = sudoku.casillas().get(c.getP()); 
							   sudoku.setValueInCasilla(co,c.getValue());
				 			   co.setInitialValue(true);});
		sudoku.check();
		sudoku.completarValores();
		sudoku.sortIndices(0,DatosSudoku.numeroDeCasillas);	
		return sudoku;
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
		Locale.setDefault(Locale.of("en", "US"));
		DatosSudoku.tamSubCuadro = 3;
		Sudoku sd = DatosSudoku.leeFichero("ficheros/sudoku2.txt");
		System.out.println(sd);
		System.out.println(sd.indices());
		IntStream.range(0,DatosSudoku.numeroDeCasillas).boxed()
			.map(i->sd.casillaIndex(i))
			.forEach(c->String2.toConsole(String.format("%s, %d",c,sd.numValoresLibresEnCasilla(c))));
		
//		System.out.println(SudokuProblem.first(DatosSudoku.sudoku));
	}
}
