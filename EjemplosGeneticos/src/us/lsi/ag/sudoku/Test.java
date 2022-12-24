package us.lsi.ag.sudoku;

import us.lsi.sudoku.datos.DatosSudoku;
import us.lsi.sudoku.datos.SolucionSudoku;

import java.util.stream.Collectors;

public class Test {
	
	public static void main(String[] args) {
		
		DatosSudoku.tamSubCuadro = 3;
		DatosSudoku.iniDatos("ficheros/sudoku.txt");
		Integer n = DatosSudoku.casillasLibres().size();
		System.out.println(n);
		System.out.println(DatosSudoku.casillasLibres());
		System.out.println(DatosSudoku.getValoresLibresEnCasilla(DatosSudoku.get(2, 2)));
		System.out.println(DatosSudoku.getValoresLibresEnCasilla(DatosSudoku.get(2, 8)));
		System.out.println(SolucionSudoku.of());
		
		BlocksDatosSudokuFilasAG p = new BlocksDatosSudokuFilasAG();
		
		System.out.println(DatosSudoku.casillasLibres().stream().map(c->c.getString()).collect(Collectors.toList()));
		System.out.println(DatosSudoku.casillasLibres().size());
		System.out.println(p.initialValues);
		System.out.println(p.initialValues.size());
		System.out.println(p.blocksLimits);
		System.out.println(p.blocksLimits.size());
		
	}

}
