package us.lsi.ag.sudoku;

import java.util.List;

import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agchromosomes.ChromosomeFactory;
import us.lsi.ag.agchromosomes.ChromosomeFactory.CrossoverType;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;
import us.lsi.sudoku.datos.DatosSudoku;
import us.lsi.sudoku.datos.SolucionSudoku;

public class TestInsetSudokuAG {
	
	
	public static void main(String[] args) {
		AlgoritmoAG.ELITISM_RATE  = 0.1;
		AlgoritmoAG.CROSSOVER_RATE = 0.6;
		AlgoritmoAG.MUTATION_RATE = 0.6;
		AlgoritmoAG.POPULATION_SIZE = 30;
		
		StoppingConditionFactory.NUM_GENERATIONS = 1000;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		StoppingConditionFactory.FITNESS_MIN = 0.;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.SolutionsNumber;
		
		ChromosomeFactory.crossoverType = CrossoverType.OnePoint;
		ChromosomeFactory.TOURNAMENT_ARITY = 2;
		
		DatosSudoku.tamSubCuadro = 3;
		DatosSudoku.iniDatos("ficheros/sudoku.txt");
		
		for(int y = 8; y >=0; y--) {
			var d = DatosSudoku.getValoresOcupadosEnFila(y);
			System.out.printf("Fila %d, %d,%s\n",y,d.size(),d);
		}
		for(int x = 0; x <9; x++) {
			var d = DatosSudoku.getValoresOcupadosEnColumna(x);
			System.out.printf("Comuna %d, %d, %s\n",x,d.size(),d);
		}
		for(int c = 0; c <9; c++) {
			var d = DatosSudoku.getValoresOcupadosEnSubCuadro(c);
			System.out.printf("Subcuadro %d, %d, %s\n",c,d.size(),d);
		}
		
		InSetDatosSudokuAG p = new InSetDatosSudokuAG();
		
		AlgoritmoAG<List<Integer>,SolucionSudoku> a = null;
		List<Integer> values = null;
		Double fitnessMax = -100.;
		
		for (int i = 0; i < 10; i++) {
			a = AlgoritmoAG.of(p);	
			a.ejecuta();
			Double f = a.getBestChromosome().fitness();
			System.out.println(a.getBestChromosome().fitness());
			if(f> fitnessMax) {
				fitnessMax = f;
				values = a.getBestChromosome().decode();
			}	
		}
		System.out.println(p.solucion(values));
		for(int y = 8; y >=0; y--) {
			var d = DatosSudoku.getValoresOcupadosEnFila(y);
			System.out.printf("Fila %d, %d,%s\n",y,d.size(),d);
		}
		for(int x = 0; x <9; x++) {
			var d = DatosSudoku.getValoresOcupadosEnColumna(x);
			System.out.printf("Comuna %d, %d, %s\n",x,d.size(),d);
		}
		for(int c = 0; c <9; c++) {
			var d = DatosSudoku.getValoresOcupadosEnSubCuadro(c);
			System.out.printf("Subcuadro %d, %d, %s\n",c,d.size(),d);
		}
	}
		
}

