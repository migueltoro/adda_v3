package us.lsi.ag.sudoku;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.ag.BlocksData;
import us.lsi.alg.sudoku.Casilla;
import us.lsi.alg.sudoku.DatosSudoku;
import us.lsi.alg.sudoku.SolucionSudoku;
import us.lsi.alg.sudoku.SudokuVertex;

public class BlocksDatosSudokuSubTablaAG implements BlocksData<SolucionSudoku> {
	
	SudokuVertex sv;
	Integer n; 
	public List<Integer> blocksLimits;
	public List<Integer> initialValues;
	
	public BlocksDatosSudokuSubTablaAG(SudokuVertex sv){
		super();
		this.sv = sv;
		this.n = DatosSudoku.n-sv.index();
		Comparator<Casilla> cmp = Comparator.comparing(c->c.st());
		Collections.sort(sv.casillas().subList(sv.index(),DatosSudoku.n),cmp);
		this.blocksLimits = IntStream.range(sv.index(),DatosSudoku.n).boxed()
			.filter(i->i==sv.index() || sv.casilla(i).st() != sv.casilla(i-1).st())
			.map(i->i-sv.index())
			.collect(Collectors.toList());
		this.blocksLimits.set(0, 0);
		this.initialValues = IntStream.range(0,DatosSudoku.nf).boxed()
			.flatMap(f->DatosSudoku.allValues.difference(sv.valoresOcupadosEnFilas().get(f)).stream())
			.toList();
	}

	@Override
	public Integer size() {
		return this.initialValues.size();
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

	@Override
	public List<Integer> blocksLimits() {
		return blocksLimits;
	}

	@Override
	public List<Integer> initialValues() {
		return initialValues;
	}


}
