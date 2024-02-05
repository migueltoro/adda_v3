package us.lsi.ag.agchromosomes;

import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

import us.lsi.ag.ChromosomeData;

import org.apache.commons.math3.genetics.BinaryChromosome;

/**
 * @author Miguel Toro
 * 
 * <p> 
 * Un cromosoma cuya valor decodificado es una lista de ceros y unos del tama�o especificado en el problema.
 * La implementaci�n es una adaptaci�n de la clase {@link org.apache.commons.math3.genetics.Chromosome Chromosome} de Apache. </p>
 *
 */
public class ABinaryChromosome<S> extends BinaryChromosome {
	
	/**
	 * Dimensi�n del cromosoma
	 */
	
	protected static Integer DIMENSION;
	protected static ChromosomeData<Object,Object>  data;
	
	@SuppressWarnings("unchecked")
	public static <E,S >void iniValues(ChromosomeData<E,S> data){
		ABinaryChromosome.data = (ChromosomeData<Object, Object>) data;
		ABinaryChromosome.DIMENSION = data.size();
	}

	public ABinaryChromosome(List<Integer> representation) throws InvalidRepresentationException {
		super(representation);
	}

	public ABinaryChromosome(Integer[] representation) throws InvalidRepresentationException {
		super(representation);
	}

	@Override
	public AbstractListChromosome<Integer> newFixedLengthChromosome(List<Integer> ar) {
		return new ABinaryChromosome<>(ar);
	}
	
	public static <S> ABinaryChromosome<S> getInitialChromosome() {
		List<Integer> ls = BinaryChromosome.randomBinaryRepresentation(ABinaryChromosome.DIMENSION);
		return new ABinaryChromosome<>(ls);
	}
	
	public List<Integer> decode(){
		return super.getRepresentation();
	}

	@Override
	public double fitness() {
		return ABinaryChromosome.data.fitnessFunction(decode());
	}
	
}
