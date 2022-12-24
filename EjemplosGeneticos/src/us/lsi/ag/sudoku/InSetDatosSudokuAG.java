package us.lsi.ag.sudoku;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import us.lsi.ag.ValuesInSetData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.sudoku.datos.Casilla;
import us.lsi.sudoku.datos.DatosSudoku;
import us.lsi.sudoku.datos.SolucionSudoku;

public class InSetDatosSudokuAG implements ValuesInSetData<SolucionSudoku> {

	List<Casilla> casillasLibres;
	Integer n; 
	Map<Integer,List<Integer>> values = new HashMap<>();
	
	public InSetDatosSudokuAG() {	
		super();
		this.casillasLibres = DatosSudoku.casillasLibres();		  
		this.n = casillasLibres.size();
	}
	
	@Override
	public Integer size() {
		return n;
	}

	@Override
	public List<Integer> values(Integer index) {
		if(!values.containsKey(index)) {
			List<Integer> r = DatosSudoku.getValoresLibresEnCasilla(DatosSudoku.getCasillaLibre(index));
			values.put(index, r);
		}
		return  values.get(index);
	}

	@Override
	public Double fitnessFunction(List<Integer> cr) {
		SolucionSudoku s = solucion(cr);
		return -(double)s.getErrores();
	}

	private void setValorEnCasilla(Integer ic, Integer a) {
		DatosSudoku.getCasillaLibre(ic).setValue(a);
	}
	
	@Override
	public SolucionSudoku solucion(List<Integer> dc) {
		IntStream.range(0,n).forEach(i->setValorEnCasilla(i,dc.get(i)));	
		SolucionSudoku s = SolucionSudoku.of();
		return s;
	}

}
