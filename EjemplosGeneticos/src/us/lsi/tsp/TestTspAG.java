package us.lsi.tsp;

import java.io.IOException;
import java.util.List;

import us.lsi.ag.agchromosomes.AChromosome;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agchromosomes.Chromosomes;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.grafos.datos.Ciudad;


public class TestTspAG {

	public static void main(String[] args) throws IOException {
		AlgoritmoAG.ELITISM_RATE  = 0.30;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 50;
		
		StoppingConditionFactory.NUM_GENERATIONS = 40000;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionFactory.StoppingConditionType.GenerationCount;
		
		
		DatosTspAG p = new DatosTspAG("ficheros/andalucia.txt");
		AChromosome<List<Integer>,List<Double>, List<Ciudad>> cv = Chromosomes.ofPermutation(p);
		AlgoritmoAG<List<Integer>, List<Double>,List<Ciudad>> ap = AlgoritmoAG.of(cv);
		ap.ejecuta();
		
		System.out.println("================================");
		List<Integer> dc = ap.getBestAChromosome().decode();
		System.out.println(dc);
		System.out.println(ap.getBestAChromosome().fitness());
		System.out.println(ap.bestSolution());
		System.out.println("================================");
		System.out.println(ap.stoppingCondition());
	}

}
