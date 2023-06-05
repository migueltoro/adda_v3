package us.lsi.alg.sudoku;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import us.lsi.common.Files2;
import us.lsi.common.IntegerSet;
import us.lsi.streams.Collectors2;


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
	
	public static Integer n = numeroDeCasillas;
	
	public static Integer nf = numeroDeFilas;
	
	public static IntegerSet allValues; 
	
	public static List<Casilla> casillas;
	
	public static List<Integer> indices;
	
	public static Casilla casilla(Integer p) {
		return DatosSudoku.casillas.get(p);
	}
	
	public static Casilla casilla(Integer f, Integer c) {
		return DatosSudoku.casillas.get(f*DatosSudoku.nf+c);
	}
	
	public static List<Casilla> copyCasillas(List<Casilla> casillas) {
		return casillas.stream().map(c->c.copy()).collect(Collectors.toList());
	}

	/**
	 * @param nf Fichero de datos
	 * @post Inicializa las variables del tipo
	 */
	public static void leeFichero(String file) {
		DatosSudoku.casillas = IntStream.range(0, DatosSudoku.n).boxed()
				.map(p->Casilla.of(p))
				.collect(Collectors.toList());
		Files2.streamFromFile(file)
				.map(ln->Casilla.parse(ln))
				.forEach(c ->casillas.set(c.p(), c));
		DatosSudoku.allValues = IntStream.range(1, 10).boxed()
				.collect(Collectors2.toIntegerSet());
		DatosSudoku.indices = IntStream.range(0, DatosSudoku.n).boxed()
				.collect(Collectors.toList());
	}
	
	
	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		DatosSudoku.tamSubCuadro = 3;
		DatosSudoku.leeFichero("ficheros/sudoku2.txt");
		System.out.println(SolucionSudoku.of(SudokuVertex.first()));
	}
}
