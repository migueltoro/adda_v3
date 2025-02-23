package us.lsi.expression;

import java.util.List;

import org.apache.commons.math3.genetics.ListPopulation;

import us.lsi.ag.agchromosomes.AChromosome;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agchromosomes.Chromosomes;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.common.String2;
import us.lsi.tiposrecursivos.ast.Exp;

public class Test2 {

	public static void main(String[] args) {
		
		AlgoritmoAG.POPULATION_SIZE = 5;
		StoppingConditionFactory.NUM_GENERATIONS = 1;
	
		DatosExpression d = new DatosExpression();
		AChromosome<Exp, List<Double>, Exp> cv = Chromosomes.ofExp(d);
		AlgoritmoAG<Exp,List<Double>,Exp> ap = AlgoritmoAG.of(cv);
		ListPopulation p = (ListPopulation) ap.randomPopulation();
		String2.toConsole("%s",p.toString());
	}

}
