package us.lsi.asignacion;

import java.util.List;
import us.lsi.ag.PermutationData;
import us.lsi.ag.agchromosomes.AChromosome;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;

public class TestAsignacionTareasAG {

	public static void main(String[] args){
		
		AlgoritmoAG.ELITISM_RATE  = 0.20;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.8;
		AlgoritmoAG.POPULATION_SIZE = 40;
		
		StoppingConditionFactory.NUM_GENERATIONS = 600;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		StoppingConditionFactory.FITNESS_MIN = 0.;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.GenerationCount;
		

		PermutationData<List<Integer>> d = DatosAsignacionTareasAG.create("ficheros/asignacionDeTareas.txt");
		AlgoritmoAG<List<Integer>,List<Integer>> ap = AlgoritmoAG.of(d);
		ap.ejecuta();
		System.out.println("================================");
		
		System.out.println("================================");

		AChromosome<List<Integer>, ?, List<Integer>> cr = ap.getBestAChromosome();
		System.out.println(cr.fitness());
		System.out.println(cr.solution());
		System.out.println("Asignacion de tareas: " );

		List<Integer> ls = cr.decode();
		Double sumCoste = 0.;
		for (int i = 0; i < ls.size(); i++) {
			Double coste = DatosAsignacionTareasAG.a.getCoste(i,ls.get(i));
			System.out.println("Tarea(" + i +  ", " + ls.get(i) + ")=" + coste );
			sumCoste = sumCoste + DatosAsignacionTareasAG.a.getCoste(i,ls.get(i));

		}
		System.out.println("Coste: " + sumCoste);
	}	

}

