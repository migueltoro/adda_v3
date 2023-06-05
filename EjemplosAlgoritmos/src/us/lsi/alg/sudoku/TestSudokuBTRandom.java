package us.lsi.alg.sudoku;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.BTR;
import us.lsi.graphs.virtual.SimpleEdgeAction;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;
import us.lsi.graphs.virtual.EGraph;

public class TestSudokuBTRandom {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		DatosSudoku.leeFichero("ficheros/sudoku/sudoku2.txt");
		SudokuVertex e1 = SudokuVertex.first();
		
		Predicate<SudokuVertex> goal = SudokuVertex.goal();
		
		EGraph<SudokuVertex,SimpleEdgeAction<SudokuVertex,Integer>> graph = 
				EGraph.virtual(e1,goal,PathType.Last, Type.One)
				.vertexWeight(v->(double)v.errores())
				.goalHasSolution(SudokuVertex.goalHasSolution())
				.build();
		
		
		BTR<SudokuVertex,SimpleEdgeAction<SudokuVertex,Integer>,SolucionSudoku> ms = 
				BTR.of(
				graph,
				SolucionSudoku::of,
				v->DatosSudoku.n-v.index(),
				15);
		
		Optional<GraphPath<SudokuVertex, SimpleEdgeAction<SudokuVertex, Integer>>> gp = ms.search();
		System.out.println("Solucion = "+SolucionSudoku.of(gp.get().getEndVertex()));
		System.out.println("Iteraciones = "+ms.iterations);
	}

}
