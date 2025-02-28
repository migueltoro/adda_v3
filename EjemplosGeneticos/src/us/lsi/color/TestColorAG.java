package us.lsi.color;

import java.util.Map;

import java.util.List;
import us.lsi.ag.RangeIntegerData;
import us.lsi.ag.agchromosomes.AChromosome;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;
import us.lsi.common.String2;
import us.lsi.grafos.datos.Ciudad;

public class TestColorAG {

	public static void main(String[] args){
		setConstantes();
		
		
		RangeIntegerData<Map<Ciudad,Integer>> problem = new DatosColorAG("./ficheros/Andalucia.txt");		
		DatosColorAG.maxNumColors = 5;
		AlgoritmoAG<List<Integer>,  Map<Ciudad, Integer>> alg = AlgoritmoAG.of(problem);
		alg.ejecuta();
		
	    AChromosome<List<Integer>, ?, Map<Ciudad, Integer>> mejorSolucion = alg.getBestAChromosome();
		System.out.println("================================");
		System.out.println("Numero de colores: "+mejorSolucion.fitness());
		String2.toConsole(alg.bestSolution().entrySet(),"Coloreado obtenido");
		System.out.println("================================");
	}

	private static void setConstantes() {
		// Condiciones "evolutivas" (tasas y demás)
		AlgoritmoAG.ELITISM_RATE  = 0.3;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 100;
		
		// Condiciones de parada
		StoppingConditionFactory.NUM_GENERATIONS = 100;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.GenerationCount;
	}	

}
