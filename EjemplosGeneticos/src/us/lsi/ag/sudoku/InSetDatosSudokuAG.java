package us.lsi.ag.sudoku;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import us.lsi.ag.ValuesInSetData;
import us.lsi.alg.sudoku.Casilla;
import us.lsi.alg.sudoku.DatosSudoku;
import us.lsi.alg.sudoku.SolucionSudoku;
import us.lsi.alg.sudoku.SudokuVertex;


public class InSetDatosSudokuAG implements ValuesInSetData<SolucionSudoku> {


	SudokuVertex sv;
	Integer n; 
	Map<Integer,List<Integer>> values = new HashMap<>();
	
	public InSetDatosSudokuAG(SudokuVertex sv) {	
		super();
		this.sv = sv;	  
		this.n = DatosSudoku.n-sv.index();
		for(int i=0; i<n;i++) {
			Integer ic = i + sv.index();
			Casilla c = sv.casillas().get(ic);
			List<Integer> r = sv.valoresLibresEnCasilla(c).stream().toList();
			values.put(i, r);
		}
	}
	
	@Override
	public Integer size() {
		return n;
	}

	@Override
	public List<Integer> values(Integer index) {
		return  values.get(index);
	}
	
	public Map<Integer, List<Integer>> values() {
		return  values;
	}

	@Override
	public Double fitnessFunction(List<Integer> cr) {
		SolucionSudoku s = solucion(cr);
		return -(double)s.errores();
	}
	
	@Override
	public SolucionSudoku solucion(List<Integer> dc) {
		IntStream.range(0,n).forEach(i->sv.casilla(this.sv.index()+i).setValue(dc.get(i)));	
		SolucionSudoku s = SolucionSudoku.of(sv);
		return s;
	}

}
