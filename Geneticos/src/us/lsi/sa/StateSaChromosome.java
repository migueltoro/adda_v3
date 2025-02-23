package us.lsi.sa;

import org.apache.commons.math3.genetics.Chromosome;
import us.lsi.ag.ChromosomeData;
import us.lsi.ag.agchromosomes.AChromosome;

public class StateSaChromosome<V,G,S> implements StateSa<V,G,S>  {

	public static <V,G,S> StateSaChromosome<V,G,S> of(AChromosome<V,G,S> achromosome){
		return new StateSaChromosome<>(achromosome);
	}
	
	@SuppressWarnings("unchecked")
	public static <V,G,S> StateSaChromosome<V,G,S> of(Chromosome chromosome){
		AChromosome<V,G,S> achromosome = (AChromosome<V,G,S>) chromosome;
		return new StateSaChromosome<>(achromosome);
	}
	
	public static <V,G,S> Chromosome random(AChromosome<V,G,S> achromosome) {	
		Chromosome chr = achromosome.initialChromosome();
		return chr;
	}
	
	private ChromosomeData<V,S> data;
	private AChromosome<V,G,S> achromosome;
	
	private StateSaChromosome(AChromosome<V,G,S> achromosome) {
		super();
		this.data = achromosome.data();	
		this.achromosome = achromosome;	
	}
	
	public ChromosomeData<V, S> data() {
		return data;
	}

	public AChromosome<V,G,S> achromosome() {
		return achromosome;
	}
	
	@Override
	public double fitness() {
		return -this.achromosome.fitness();
	}
	@Override
	public StateSa<V,G,S> mutate() {
		Chromosome c = this.achromosome.mutationPolicy().mutate(this.chromosome());
		return StateSaChromosome.of(c);
	}

	@Override
	public StateSa<V,G,S> random() {
		Chromosome c = StateSaChromosome.random(this.achromosome);
		return StateSaChromosome.of(c);
	}

	@Override
	public StateSa<V,G,S> copy() {
		return StateSaChromosome.of(this.achromosome);
	}
	
	public V decode() {
		return this.achromosome.decode();
	}
	
	public Chromosome chromosome() {
		return (Chromosome) this.achromosome;
	}
}
