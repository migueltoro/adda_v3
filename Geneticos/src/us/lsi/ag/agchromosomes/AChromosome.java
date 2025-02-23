package us.lsi.ag.agchromosomes;

import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.CrossoverPolicy;
import org.apache.commons.math3.genetics.MutationPolicy;
import org.apache.commons.math3.genetics.SelectionPolicy;

import us.lsi.ag.ChromosomeData;

public interface AChromosome<V,G,S> {
	
	CrossoverPolicy crossOverPolicy();
	
	MutationPolicy mutationPolicy();
	
	SelectionPolicy selectionPolicy();
	
	Chromosome initialChromosome();
	
	double fitness();
	
	S solution();
	
	V decode();
	
	V decode(Chromosome chromosome);
	
	V decodeValues(G g);
	
	Integer dimension();
	
	ChromosomeData<V,S> data();
}
