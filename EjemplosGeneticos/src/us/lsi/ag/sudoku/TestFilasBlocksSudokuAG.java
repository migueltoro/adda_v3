package us.lsi.ag.sudoku;

import java.util.List;

import us.lsi.ag.agchromosomes.AlgoritmoAG;

import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;
import us.lsi.alg.sudoku.DatosSudoku;
import us.lsi.alg.sudoku.SolucionSudoku;
import us.lsi.alg.sudoku.SudokuVertexI;


public class TestFilasBlocksSudokuAG {
	
	public static void main(String[] args) {
		AlgoritmoAG.ELITISM_RATE  = 0.1;
		AlgoritmoAG.CROSSOVER_RATE = 0.6;
		AlgoritmoAG.MUTATION_RATE = 0.6;
		AlgoritmoAG.POPULATION_SIZE = 30;
		
		StoppingConditionFactory.NUM_GENERATIONS = 1;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		StoppingConditionFactory.FITNESS_MIN = 0.;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.SolutionsNumber;
		
		
		DatosSudoku.leeFichero("ficheros/sudoku2.txt");

		SudokuVertexI sv = SudokuVertexI.first();
		
		BlocksDatosSudokuFilasAG p = new BlocksDatosSudokuFilasAG(sv);
		
		System.out.println(sv);
		System.out.println(p.size());
		AlgoritmoAG<List<Integer>,SolucionSudoku> a;
		
		for (int i = 0; i < 1; i++) {
			a = AlgoritmoAG.of(p);	
			a.ejecuta();
			System.out.println(SolucionSudoku.of(sv));
		}
		
		p.sv.valoresOcupados();
		System.out.println(p.check());
		System.out.println(p.sv.valoresOcupadosEnFilas());
	}	
}
