package us.lsi.alg.sudoku;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.alg.sudoku.DatosSudoku.SolucionSudoku;
import us.lsi.graphs.alg.BTR;
import us.lsi.graphs.virtual.SimpleEdgeAction;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;
import us.lsi.graphs.virtual.EGraph;

public class TestSudokuBTRandom {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosSudoku.iniDatos("ficheros/sudoku2.txt");
		SudokuVertex e1 = SudokuVertex.first(DatosSudoku.sudoku);
		Predicate<SudokuVertex> goal = SudokuVertex.goal();
		
		EGraph<SudokuVertex,SimpleEdgeAction<SudokuVertex,Integer>> graph = 
				EGraph.virtual(e1,goal,PathType.Last, Type.One)
				.vertexWeight(v->(double)v.sudoku().errores())
				.goalHasSolution(SudokuVertex.goalHasSolution())
				.build();
		
		
		BTR<SudokuVertex,SimpleEdgeAction<SudokuVertex,Integer>,SolucionSudoku> ms = 
				BTR.of(
				graph,
				SudokuVertex::of,
				v->DatosSudoku.numeroDeCasillas-v.index());
		
		BTR.threshold = 15;
//		BackTrackingRandom.solutionsNumber = 1;
		
		Optional<GraphPath<SudokuVertex, SimpleEdgeAction<SudokuVertex, Integer>>> gp = ms.search();
//		SudokuVertex lv = List2.last(path.getVertexList());
		System.out.println("Solucion = \n"+SudokuVertex.of(gp.get()));
		System.out.println("Iteraciones = \n"+ms.iterations);
	}

}
