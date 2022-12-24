package us.lsi.expression;


import java.util.Locale;

import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agchromosomes.ChromosomeFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;
import us.lsi.common.String2;
import us.lsi.tiposrecursivos.ast.Exp;


public class TestExpression {

	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		
		AlgoritmoAG.ELITISM_RATE  = 0.30;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 50;
		
		StoppingConditionFactory.NUM_GENERATIONS = 50;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.SolutionsNumber;
		StoppingConditionFactory.FITNESS_MIN = -1.;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		
		ChromosomeFactory.crossoverType = ChromosomeFactory.CrossoverType.OnePoint;
		
		DatosExpression d = new DatosExpression();
		AlgoritmoAG<Exp,Exp> ap = AlgoritmoAG.of(d);
		ap.ejecuta();

		String2.toConsole("================================");
		String2.toConsole("%.2f",ap.getBestChromosome().fitness());
		String2.toConsole("%s",d.solucion(ap.getBestChromosome().decode()));
		String2.toConsole("================================");
//		for(int i=0;i<d.numConstants();i++){
//			System.out.println(String.format("%s = %f",d.getConstant(i).getName(),d.getConstant(i).getValue()));
//		}

	}

}
