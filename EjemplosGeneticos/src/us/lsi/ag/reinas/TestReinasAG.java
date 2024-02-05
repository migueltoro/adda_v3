package us.lsi.ag.reinas;

import java.util.List;
import java.util.Set;

import org.apache.commons.math3.genetics.Chromosome;

import us.lsi.ag.PermutationData;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;
import us.lsi.common.Set2;
import us.lsi.reinas.datos.Reina;



public class TestReinasAG {

	

	public static void main(String[] args){
		
		AlgoritmoAG.ELITISM_RATE  = 0.20;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.8;
		AlgoritmoAG.POPULATION_SIZE = 40;
		
		StoppingConditionFactory.NUM_GENERATIONS = 6000;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		StoppingConditionFactory.FITNESS_MIN = 0.;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.SolutionsNumber;
		
		
		DatosReinasAG.numeroDeReinas = 10;
		PermutationData<List<Reina>> p = DatosReinasAG.create();
		AlgoritmoAG<List<Integer>,List<Reina>> ap = AlgoritmoAG.of(p);
		ap.ejecuta();
		System.out.println("================================");
		
		System.out.println("================================");

		Chromosome cr = ap.getBestChromosome();
		System.out.println(cr.fitness());
		System.out.println(ap.bestSolution());
		Set<Integer> dp = Set2.empty();
		System.out.println(DatosReinasAG.numeroDeReinas+","+dp.size());
		}	

}

