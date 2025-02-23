package us.lsi.sa;

import us.lsi.ag.agchromosomes.AChromosome;

public interface StateSa<V,G,S> {
	double fitness();
	StateSa<V,G,S> mutate();
	StateSa<V,G,S> random();
	StateSa<V,G,S> copy();
	AChromosome<V,G,S> achromosome();
}
