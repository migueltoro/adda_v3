package us.lsi.sa;

import org.apache.commons.math3.genetics.Chromosome;

import us.lsi.ag.ChromosomeData;

public interface StateSa<E,S> {
	double fitness();
	StateSa<E,S> mutate();
	StateSa<E,S> random();
	StateSa<E,S> copy();
	ChromosomeData<E, S> data();
	Chromosome chromosome();
}
