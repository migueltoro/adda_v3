package us.lsi.sa;

import org.apache.commons.math3.genetics.Chromosome;
import us.lsi.ag.ChromosomeData;

public class StateSaChromosome<E,S> implements StateSa<E,S>  {

	public static <E,S> StateSaChromosome<E,S> of(ChromosomeData<E,S> data){
		return new StateSaChromosome<>(data);
	}
	
	public static <E,S> StateSaChromosome<E,S> of(Chromosome chromosome){
		return new StateSaChromosome<>(chromosome);
	}
	
	public static <E,S> Chromosome random(ChromosomeData<E,S> data) {	
		Chromosome chr = data.initialChromosome();
		return chr;
	}
	
	private static ChromosomeData<?, ?> data;
	private Chromosome chromosome;
	
	private StateSaChromosome(ChromosomeData<E,S> data) {
		super();
		StateSaChromosome.data = data;	
		this.chromosome = StateSaChromosome.random(data);	
	}
	
	private StateSaChromosome(Chromosome chromosome) {
		super();
		this.chromosome = chromosome;	
	}
	
	@SuppressWarnings("unchecked")
	public ChromosomeData<E, S> data() {
		return (ChromosomeData<E, S>) data;
	}

	public Chromosome chromosome() {
		return chromosome;
	}
	
	@Override
	public double fitness() {
		return -this.chromosome.fitness();
	}
	@Override
	public StateSa<E,S> mutate() {
		Chromosome c = StateSaChromosome.data.mutationPolicy().mutate(this.chromosome);
		return StateSaChromosome.of(c);
	}

	@Override
	public StateSa<E,S> random() {
		Chromosome c = StateSaChromosome.random(data);
		return StateSaChromosome.of(c);
	}

	@Override
	public StateSa<E,S> copy() {
		return StateSaChromosome.of(this.chromosome);
	}
	
	public E decode() {
		return this.data().decode(this.chromosome);
	}
	
}
