package us.lsi.alg.sudoku;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import org.jgrapht.GraphPath;

import us.lsi.alg.sudoku.DatosSudoku.SolucionSudoku;
import us.lsi.graphs.virtual.SimpleEdgeAction;
import us.lsi.graphs.virtual.VirtualVertex;

public record SudokuVertex(Integer index, Sudoku sudoku) 
		implements VirtualVertex<SudokuVertex,SimpleEdgeAction<SudokuVertex,Integer>,Integer>{

	public static SudokuVertex of(Integer index, Sudoku sudoku) {
		SudokuVertex sp = new SudokuVertex(index, sudoku);
		sp.sudoku.sortIndices(index,DatosSudoku.numeroDeCasillas);
		return sp;
	}
	
	public static Predicate<SudokuVertex> goal() {
		return sv->sv.index == DatosSudoku.numeroDeCasillas;
	}
	
	public static Predicate<SudokuVertex> goalHasSolution() {
		return sv->sv.sudoku.errores() == 0;
	}
	
	public static Double heuristic(SudokuVertex v1, Predicate<SudokuVertex> goal, SudokuVertex v2) {
		return (double) -v1.sudoku().errores();
	}
	
	public static SudokuVertex first(Sudoku sudoku) {
		Integer index = IntStream.range(0,DatosSudoku.numeroDeCasillas).boxed()
				.filter(i->sudoku.numValoresLibresEnCasilla(sudoku.casillaIndex(i)) > 0)
				.findFirst()
				.get();
		return new SudokuVertex(index, sudoku);
	}
	
	public SudokuVertex copy() {
		return SudokuVertex.of(this.index(),this.sudoku());
	}
	
	public static SolucionSudoku of(GraphPath<SudokuVertex,SimpleEdgeAction<SudokuVertex,Integer>> path) {
		return new SolucionSudoku(path.getVertexList().get(path.getVertexList().size()-1).sudoku());
	}
	
	@Override
	public Boolean isValid() {
		return true;
	}

	@Override
	public List<Integer> actions() {
		if(this.index == DatosSudoku.numeroDeCasillas) return List.of();
		Casilla c = this.sudoku().casillaIndex(this.index);
		return this.sudoku().valoresLibresEnCasilla(c).stream().toList();
	}

	@Override
	public SudokuVertex neighbor(Integer a) {
		Sudoku sp = this.sudoku().copy();
		Casilla c = sp.casillaIndex(this.index);
		sp.setValueInCasilla(c,a);
		return SudokuVertex.of(this.index+1,sp);
	}

	@Override
	public SimpleEdgeAction<SudokuVertex, Integer> edge(Integer a) {
		return SimpleEdgeAction.of(this,this.neighbor(a),a,1.);
	}
	
	

}
