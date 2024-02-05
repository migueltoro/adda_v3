package us.lsi.ag.agchromosomes;

import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;
import org.apache.commons.math3.genetics.RandomKey;

import us.lsi.ag.ChromosomeData;
import us.lsi.ag.ChromosomeDoubleData;

public class ARandomKey<E> extends RandomKey<E> {

	private static ChromosomeData<Object,Object> data = null;
	public static Integer DIMENSION = null;
	
	@SuppressWarnings("unchecked")
	public static <E,S> void iniValues(ChromosomeData<E,S> data){
		ARandomKey.data = (ChromosomeData<Object, Object>) data; 
		ARandomKey.DIMENSION = ARandomKey.data.size();
	}
	
	public static <E,S> ARandomKey<E> getInitialChromosome() {
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
		return ARandomKey.data.fitnessFunction(this.decode());
	}
	
	@SuppressWarnings("unchecked")
	public E decode() {
		List<Double> ls = super.getRepresentation();
		return ((ChromosomeDoubleData<E,Object>) ARandomKey.data).decode(ls);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
