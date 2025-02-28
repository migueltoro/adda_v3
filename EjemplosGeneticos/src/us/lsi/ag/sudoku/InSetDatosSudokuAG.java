package us.lsi.ag.sudoku;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import us.lsi.ag.InSetData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;
import us.lsi.alg.sudoku.Casilla;
import us.lsi.alg.sudoku.DatosSudoku;
import us.lsi.alg.sudoku.SolucionSudoku;
import us.lsi.alg.sudoku.SudokuVertexI;
import us.lsi.common.Preconditions;


public class InSetDatosSudokuAG implements InSetData<SolucionSudoku> {


	SudokuVertexI sv;
	Map<Integer,List<Integer>> values = new HashMap<>();
	public List<Integer> decode;
	
	public InSetDatosSudokuAG(SudokuVertexI sv) {	
		super();
		this.sv = sv;	  
		for(int i = sv.index(); i<DatosSudoku.n;i++) {
			Integer ic = i;
			Casilla c = sv.casillas().get(ic);
			List<Integer> r = sv.valoresLibresEnCasilla(c).stream().toList();
			values.put(i-sv.index(), r);
		}	
	}
	
	@Override
	public Integer size() {
		return DatosSudoku.n-sv.index();
	}

	@Override
	public List<Integer> values(Integer index) {
		return  values.get(index);
	}
	
	
	public Map<Integer, List<Integer>> values() {
		return  values;
	}
	
	@Override
	public Double fitnessFunction(List<Integer> dc) {
		this.decode = dc;
		SolucionSudoku s = solution(dc);
		return -(double)s.errores();
	}
	
	@Override
	public SolucionSudoku solution(List<Integer> dc) {
		Preconditions.checkArgument(dc.size()==this.size(),String.format("%d,%d",dc.size(),this.size()));
		IntStream.range(0,this.size()).forEach(i->sv.casilla(this.sv.index()+i).setValue(dc.get(i)));	
		SolucionSudoku s = SolucionSudoku.of(sv);
		return s;
	}
	
	@Override
	public ChromosomeType type() {
		return ChromosomeType.InSet;
	}

}
