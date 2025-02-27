package us.lsi.ag.mochila;


import java.util.List;
import java.util.Locale;

import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.mochila.datos.DatosMochila;

public class TestMochilaAGRange {

	
	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		
		AlgoritmoAG.ELITISM_RATE  = 0.30;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 50;
		
		StoppingConditionFactory.NUM_GENERATIONS = 500000;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		StoppingConditionFactory.FITNESS_MIN = 623;
		StoppingConditionFactory.stoppingConditionType = 
				StoppingConditionFactory.StoppingConditionType.SolutionsNumber;
		
		DatosMochila.capacidadInicial = 78;
		DatosMochilaAGRange d = new DatosMochilaAGRange("ficheros/objetosmochila.txt");
		AlgoritmoAG<List<Integer>,SolucionMochila> ap = AlgoritmoAG.of(d);
		System.out.println(DatosMochila.getObjetos());
		ap.ejecuta();
		System.out.println(DatosMochila.getObjetos());
		System.out.println(ap.bestSolution());
		System.out.println(ap.stoppingCondition());
		System.out.println(ap.getBestFitness());
	}

}
