package us.lsi.expression;

import org.apache.commons.math3.genetics.ListPopulation;

import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agchromosomes.ChromosomeFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.common.String2;
import us.lsi.tiposrecursivos.ast.Exp;

public class Test2 {

	public static void main(String[] args) {
		
		AlgoritmoAG.POPULATION_SIZE = 5;
		StoppingConditionFactory.NUM_GENERATIONS = 1;
		
		ChromosomeFactory.crossoverType = ChromosomeFactory.CrossoverType.OnePoint;
		DatosExpression d = new DatosExpression();
		
		AlgoritmoAG<Exp,Exp> ap = AlgoritmoAG.of(d);
		ListPopulation p = (ListPopulation) ap.randomPopulation();
		String2.toConsole("%s",p.toString());
	}

}
