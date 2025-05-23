package us.lsi.ag.agchromosomes;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.CrossoverPolicy;
import org.apache.commons.math3.genetics.ElitisticListPopulation;
import org.apache.commons.math3.genetics.GeneticAlgorithm;
import org.apache.commons.math3.genetics.MutationPolicy;
import org.apache.commons.math3.genetics.Population;
import org.apache.commons.math3.genetics.SelectionPolicy;
import org.apache.commons.math3.genetics.StoppingCondition;
import org.apache.commons.math3.random.JDKRandomGenerator;

import us.lsi.ag.ChromosomeData;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.common.Preconditions;

/**
 * <p> Implementaci�n de un Algoritmo Gen�tico </p>
 * 
 * 
 * @author Miguel Toro
 *
 */
public class AlgoritmoAG<V,S> {
	
	/**
	 * @param <C> Tipo del cromosoma
	 * @param p Problema
	 * @return AlgoritmoAG
	 */
	
	public static <V,S> AlgoritmoAG<V,S> of(ChromosomeData<V,S> chromosomeData) {
		return new AlgoritmoAG<V,S>(chromosomeData);
	}
	
	/**
	 * Tama�o de la poblaci�n. Usualmente de un valor cercano a la DIMENSION de los cromosomas o mayor
	 */
	public static int POPULATION_SIZE = 30;
	
	/**
	 * Tasa de elitismo. El porcentaje especificado de los mejores cromosomas pasa a la siguiente generaci�n sin cambio
	 */
	public static double ELITISM_RATE = 0.2;
	
	/**
	 * Tasa de cruce: Indica con qu� frecuencia se va a realizar la cruce. 
	 * Si no hay un cruce, la descendencia es copia exacta de los padres. 
	 * Si hay un cruce, la descendencia est� hecha de partes del cromosoma de los padres. 
	 * Si la probabilidad de cruce es 100%, entonces todos los hijos se hacen mediante cruce de los padres
	 * Si es 0%, la nueva generaci�n se hace de copias exactas de los cromosomas de los padres.
	 * El cruce se hace con la esperanza de que los nuevos cromosomas tendr�n las partes buenas de los padres
	 * y tal vez los nuevos cromosomas ser�n mejores. Sin embargo, es bueno dejar una cierta parte de la poblaci�n sobrevivir a la siguiente generaci�n.
	 * 
	 * <br>
	 * Tasa de cruce. Valores usuales entre  0,.8 y 0.95
	 */
	
	public static double CROSSOVER_RATE = 0.8;
	
	/**
	 * La tasa de de mutaci�n indica con qu� frecuencia ser�n mutados cada uno de los cromosomas mutados. 
	 * Si no hay mutaci�n, la descendencia se toma despu�s de cruce sin ning�n cambio. 
	 * Si se lleva a cabo la mutaci�n, se cambia una parte del cromosoma. 
	 * Si probabilidad de mutaci�n es 100%, toda cromosoma se cambia, si es 0%, no se cambia ninguno. 
	 * La mutaci�n se hace para evitar que se caiga en un m�ximo local.
	 * 
	 * 
	 * Tasa de mutaci�n. Valores usales entre 0.5 y 1.
	 */
	public static double MUTATION_RATE = 0.6;
	

	public static long INITIAL_TIME;
	public static long FINAL_TIME;
	
	
	public AChromosome<V,?,S>  aChromosome;
	public ChromosomeData<V,S> data;
	public CrossoverPolicy crossOverPolicy;
	public MutationPolicy mutationPolicy;
	public SelectionPolicy selectionPolicy;
	private StoppingCondition stopCond;

	/**
	 * Lista con los mejores cromosomas de cada una de la generaciones si se usa la condici�n de parada SolutionsNumbers.
	 * En otro caso null.
 	 */
	public static List<Chromosome> bestChromosomes;
	

	protected static Population initialPopulation;
	
	
	protected static Chromosome bestFinal;
	protected static Population finalPopulation;
	public static Double bestFitNess;
	
	public static JDKRandomGenerator random;
	
	
	/**
	 * @param problema Problema a resolver
	 */

	public AlgoritmoAG(ChromosomeData<V,S> chromosomeData) {
		super();
		AlgoritmoAG.random = new JDKRandomGenerator();		
		AlgoritmoAG.random.setSeed((int)System.currentTimeMillis());
		GeneticAlgorithm.setRandomGenerator(random);
		this.aChromosome = Chromosomes.of(chromosomeData);
		this.data = chromosomeData;
		this.selectionPolicy =  this.aChromosome.selectionPolicy();
		this.mutationPolicy = this.aChromosome.mutationPolicy();
		this.crossOverPolicy = this.aChromosome.crossOverPolicy();
		this.stopCond = StoppingConditionFactory.getStoppingCondition();
//		this.data.iniValues(this.data);
	}

	/**
	 * Inicializa aleatoriamente la población.
	 */
	public ElitisticListPopulation randomPopulation() {
		List<Chromosome> popList = new LinkedList<>();
		for (int i = 0; i < POPULATION_SIZE; i++) {
			Chromosome randChrom = this.aChromosome.initialChromosome();
			popList.add(randChrom);
		}
		return new ElitisticListPopulation(popList, popList.size(), ELITISM_RATE);
	}	

	/**
	 * Ejecuta el algoritmo
	 */
	public void ejecuta() {
		AlgoritmoAG.INITIAL_TIME = System.currentTimeMillis();
		AlgoritmoAG.initialPopulation = randomPopulation();
		Preconditions.checkNotNull(AlgoritmoAG.initialPopulation);		
		
		GeneticAlgorithm ga = new GeneticAlgorithm(
				crossOverPolicy, 
				CROSSOVER_RATE,
				mutationPolicy, 
				MUTATION_RATE, 
				selectionPolicy);	
		
		AlgoritmoAG.finalPopulation = ga.evolve(AlgoritmoAG.initialPopulation, this.stopCond);		
		Preconditions.checkNotNull(AlgoritmoAG.finalPopulation);
		AlgoritmoAG.bestFinal = AlgoritmoAG.finalPopulation.getFittestChromosome();
		AlgoritmoAG.bestFitNess = this.getBestFitness();
		AlgoritmoAG.FINAL_TIME = System.currentTimeMillis();
	}

	/**
	 * @return Población inicial
	 */
	public Population getInitialPopulation() {
		return initialPopulation;
	}

	/**
	 * @return El mejor cromosoma en la poblaci�n final
	 */
	
	protected Chromosome getBestChromosome() {
		return bestFinal;
	}
	
	@SuppressWarnings("unchecked")
	public AChromosome<V,?,S> getBestAChromosome() {
		return (AChromosome<V,?,S>)bestFinal;
	}
	
	public Double getBestFitness() {
		return bestFinal.fitness();
	}

	protected List<Chromosome> getBestChromosomes(){
		return bestChromosomes.stream()
				.collect(Collectors.toList());
	}
	
	@SuppressWarnings("unchecked")
	public List<AChromosome<V, ?, S>> getBestAChromosomes(){
		return bestChromosomes.stream()
				.map(c->(AChromosome<V,?,S>)c)
				.collect(Collectors.toList());
	}

	/**
	 * @return Población final
	 */
	public Population getFinalPopulation() {
		return finalPopulation;
	}	
	
	public S bestSolution() {
		V d = this.getBestAChromosome().decode();
		return this.data.solution(d);
	}
	
	@SuppressWarnings("unchecked")
	public Set<S> bestSolutions() {
		return this.getBestChromosomes().stream()
				.<S>map(c->this.data.solution(((AChromosome<V,?,S>)c).decode(c)))
				.collect(Collectors.toSet());
	} 
	
	public StoppingCondition stoppingCondition() {
		return this.stopCond;
	}
	
	public static Long time() {
		return (AlgoritmoAG.FINAL_TIME - AlgoritmoAG.INITIAL_TIME);
	}

}
