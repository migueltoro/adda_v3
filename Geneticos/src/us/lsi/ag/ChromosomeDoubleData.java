package us.lsi.ag;

import java.util.List;

import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.CrossoverPolicy;
import org.apache.commons.math3.genetics.MutationPolicy;
import org.apache.commons.math3.genetics.RandomKeyMutation;
import org.apache.commons.math3.genetics.SelectionPolicy;
import org.apache.commons.math3.genetics.TournamentSelection;

import us.lsi.ag.agchromosomes.ACrossOverPolicy;
import us.lsi.ag.agchromosomes.ACrossOverPolicy.CrossoverType;
import us.lsi.ag.agchromosomes.ARandomKey;

public interface ChromosomeDoubleData<E,S> extends ChromosomeData<E, S> {
	
	public E decode(List<Double> ls);
	
	public static CrossoverType crossoverType = CrossoverType.OnePoint;
	
	default CrossoverPolicy crossOverPolicy() {
		return ACrossOverPolicy.getCrossoverPolicyKey(crossoverType);
	}
	
	default MutationPolicy mutationPolicy() {
		return new RandomKeyMutation();
	}
	
	public static int TOURNAMENT_ARITY = 2;
	
	default SelectionPolicy selectionPolicy() {
		return new TournamentSelection(TOURNAMENT_ARITY);
	}
	
	@Override
	default void iniValues(ChromosomeData<E,S> data) {
		ARandomKey.iniValues(data);
	}
	
	@Override
	default Chromosome initialChromosome() {
		return ARandomKey.getInitialChromosome();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	default E decode(Chromosome chromosome) {
		return ((ARandomKey<E>)chromosome).decode();
	}

}
