package us.lsi.ag;

import java.util.List;

import org.apache.commons.math3.genetics.BinaryMutation;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.CrossoverPolicy;
import org.apache.commons.math3.genetics.MutationPolicy;
import org.apache.commons.math3.genetics.SelectionPolicy;
import org.apache.commons.math3.genetics.TournamentSelection;

import us.lsi.ag.agchromosomes.ABinaryChromosome;
import us.lsi.ag.agchromosomes.ACrossOverPolicy;
import us.lsi.ag.agchromosomes.ACrossOverPolicy.CrossoverType;

public interface BinaryData<S> extends ChromosomeData<List<Integer>,S>{
	
	public static CrossoverType crossoverType = CrossoverType.OnePoint;
	
	default CrossoverPolicy crossOverPolicy() {
		return ACrossOverPolicy.getCrossoverPolicyBin(crossoverType);
	}
	
	default MutationPolicy mutationPolicy() {
		return new BinaryMutation();
	}
	
	public static int TOURNAMENT_ARITY = 2;
	
	default SelectionPolicy selectionPolicy() {
		return new TournamentSelection(TOURNAMENT_ARITY);
	}
	
	@Override
	default void iniValues(ChromosomeData<List<Integer>,S> data) {
		ABinaryChromosome.iniValues(data);
	}
	
	@Override
	default Chromosome initialChromosome() {
		return ABinaryChromosome.getInitialChromosome();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	default List<Integer> decode(Chromosome chromosome) {
		return ((ABinaryChromosome<S>)chromosome).decode();
	}
}
