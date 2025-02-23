package us.lsi.ag.real;


import us.lsi.ag.RangeDoubleData;
import us.lsi.ag.agchromosomes.AChromosome;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agchromosomes.Chromosomes;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;

import java.util.List;
import java.util.Locale;

public class TestReal {

	public static void main(String[] args){
		
		AlgoritmoAG.ELITISM_RATE  = 0.30;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 200;
		
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.GenerationCount;
		StoppingConditionFactory.NUM_GENERATIONS = 100;
		
		RangeDoubleData<List<Double>> d = new DatosReal();
		AChromosome<List<Double>,List<Double>,List<Double>> cv = Chromosomes.ofRangeDouble(d);
		AlgoritmoAG<List<Double>, List<Double>, List<Double>> ap = AlgoritmoAG.of(cv);
		ap.ejecuta();
		
		
		Locale.setDefault(Locale.of("en", "US"));
		System.out.println("================================");
		System.out.println(ap.bestSolution());
		System.out.println("================================");
		
	}	
	
}
