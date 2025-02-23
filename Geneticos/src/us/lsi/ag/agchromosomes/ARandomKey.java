package us.lsi.ag.agchromosomes;

import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.CrossoverPolicy;
import org.apache.commons.math3.genetics.InvalidRepresentationException;
import org.apache.commons.math3.genetics.MutationPolicy;
import org.apache.commons.math3.genetics.RandomKey;
import org.apache.commons.math3.genetics.RandomKeyMutation;
import org.apache.commons.math3.genetics.SelectionPolicy;
import org.apache.commons.math3.genetics.TournamentSelection;

import us.lsi.ag.ChromosomeData;
import us.lsi.ag.agchromosomes.ACrossOverPolicy.CrossoverType;

public class ARandomKey<V,S> extends RandomKey<Object> implements AChromosome<V,List<Double>,S> {

	private static ChromosomeValues<Object,List<Double>,Object> values = null;
	private static Integer DIMENSION = null;
	private static ChromosomeData<Object,Object> data = null;
	
	@SuppressWarnings("unchecked")
	public static <V,G,S> void iniValues(ChromosomeValues<V,List<Double>,S> values){
		ARandomKey.values = (ChromosomeValues<Object,List<Double>,Object>) values; 
		ARandomKey.DIMENSION = values.dimension();
		ARandomKey.data = (ChromosomeData<Object, Object>) values.data();
	}
	
	public static <E,S> ARandomKey<E,S> getInitialChromosome() {
		List<Double> ls = RandomKey.randomPermutation(ARandomKey.DIMENSION);
		return new ARandomKey<>(ls);
	}
	
	public ARandomKey(Double[] representation) throws InvalidRepresentationException {
		super(representation);
	}
	
	public ARandomKey(List<Double> representation) throws InvalidRepresentationException {
		super(representation);
	}
	
	@Override
	public AbstractListChromosome<Double> newFixedLengthChromosome(List<Double> ls) {
		return new ARandomKey<>(ls);
	}
	
	@Override
	public double fitness() {
		V d = this.decode();
		return ARandomKey.data.fitnessFunction(d);
	}
	
	@SuppressWarnings("unchecked")
	public V decode() {
		List<Double> ls = super.getRepresentation();
		return (V) values.decodeValues(ls);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public S solution() {
		return (S) data.solution(this.decode());
	}
	
	public static CrossoverType crossoverType = CrossoverType.OnePoint;
	
	public CrossoverPolicy crossOverPolicy() {
		return ACrossOverPolicy.getCrossoverPolicyKey(crossoverType);
	}
	
	public MutationPolicy mutationPolicy() {
		return new RandomKeyMutation();
	}
	
	public static int TOURNAMENT_ARITY = 2;
	
	public SelectionPolicy selectionPolicy() {
		return new TournamentSelection(TOURNAMENT_ARITY);
	}
	
	@Override
	public Chromosome initialChromosome() {
		return ARandomKey.getInitialChromosome();
	}
	
	@SuppressWarnings("unchecked")
	public V decodeValues(List<Double> g) {
		return (V) values.decodeValues(g);
	}
	
	public V decode(Chromosome cr) {
		return this.decode();
	}

	@Override
	public Integer dimension() {
		return DIMENSION;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ChromosomeData<V, S> data() {
		return (ChromosomeData<V, S>) data;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	
}
