package us.lsi.camino;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import us.lsi.ag.agchromosomes.AChromosome;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agchromosomes.Chromosomes;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.grafos.datos.Ciudad;

public class TestCamino {
	
	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));		
		testsFichero(1,"Cadiz", "Granada",c -> c.habitantes() > 100000,w -> w > 100.);
		testsFichero(2,"Toledo","Guadalajara",c -> c.habitantes() <= 200000,w -> w >= 120.);
		testsFichero(3,"C01","C25",c -> c.habitantes() > 25000,w -> w < 200.);

	}
	
	public static void testsFichero(Integer i, String origen, String destino, 
			Predicate<Ciudad> pv1, Predicate<Double> pe1) {
		
			AlgoritmoAG.POPULATION_SIZE = 500;
			StoppingConditionFactory.NUM_GENERATIONS = 4000;
			
			CaminoDatos.iniGrafo("ficheros/camino"+i+".txt", pv1, pe1, origen, destino);
			
			CaminoDatos.sh = false;	
			CaminoData d = new CaminoData();
			AChromosome<List<Integer>,List<Double>, List<Ciudad>> cv = Chromosomes.ofPermutation(d);
			AlgoritmoAG<List<Integer>, List<Double>, List<Ciudad>> alg = AlgoritmoAG.of(cv);
			alg.ejecuta();
			System.out.println("Genetico "+i+" ####################");
			System.out.println(alg.bestSolution());
			CaminoDatos.sh = true;		
			System.out.println("####################");
	}

}
