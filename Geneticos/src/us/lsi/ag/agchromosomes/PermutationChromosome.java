package us.lsi.ag.agchromosomes;

import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;
import org.apache.commons.math3.genetics.RandomKey;

import us.lsi.ag.Chromosome;
import us.lsi.ag.SeqNormalData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.common.Preconditions;

/**
 * @author Miguel Toro
 * 
 * <p> Una implementaci�n del tipo IndexChromosome. Toma como informaci�n la definici�n de un problema que implementa el interfaz IndexProblemAG.
 * A partir de esa informaci�n construye una secuencia normal. 
 * Asumimos que el n�mero de objetos es <code>n </code>y el tama�o de la secuencia normal <code>r</code>. 
 * La lista decodificada es una permutaci�n de la secuencia normal.</p>
 *  
 * <p> La lista decodificada est� formada por una lista de  tama�o <code>r</code>, cuyos valores son 
 * enteros en el rango <code> [0,n-1]</code>, y cada �ndice <code>i</code> se  repite un n�mero de veces igual al 
 * dado por la multiplicidad m�xima del objeto <code> i </code>
 * definida en el problema. </p>
 * 
 * <p> La implementaci�n usa un cromosoma de clave aleatoria de tama�o <code> r </code>.
 * Es un cromosoma adecuado para codificar problemas de permutaciones </p>
 *
 */
public class PermutationChromosome extends RandomKey<Integer> 
				implements SeqNormalData<Object>, Chromosome<List<Integer>> {

	public static List<Integer> normalSequence = null;
	public static SeqNormalData<Object> data;
	
	/**
	 * Dimensi�n del cromosoma
	 */
	
	public static int DIMENSION;
	
	public static void iniValues(SeqNormalData<Object> data){
		PermutationChromosome.data = data; 
		PermutationChromosome.normalSequence = PermutationChromosome.data.normalSequence();
		PermutationChromosome.DIMENSION = PermutationChromosome.normalSequence.size();
	}

	
	public PermutationChromosome(List<Double> representation)
			throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	public PermutationChromosome(Double[] representation)
			throws InvalidRepresentationException {
		super(representation);
		this.ft = this.calculateFt();
	}

	@Override
	public AbstractListChromosome<Double> newFixedLengthChromosome(List<Double> ls) {
		return new PermutationChromosome(ls);	
	}

	@Override
	public List<Integer> decode() {
		Preconditions.checkArgument(PermutationChromosome.normalSequence!=null);
		return this.decode(PermutationChromosome.normalSequence);
	}
	
	
	public static PermutationChromosome getInitialChromosome() {
		List<Double> ls = RandomKey.randomPermutation(PermutationChromosome.DIMENSION);
		return new PermutationChromosome(ls);
	}

	
	@Override
	public double fitness() {
		return ft;
	}
	
	private double ft;
	
	private double calculateFt(){
		return PermutationChromosome.data.fitnessFunction(this.decode());
	}


	@Override
	public ChromosomeType type() {
		return ChromosomeFactory.ChromosomeType.Permutation;
	}


	@Override
	public Integer size() {
		return PermutationChromosome.data.size();
	}


	@Override
	public Double fitnessFunction(List<Integer> dc) {
		return PermutationChromosome.data.fitnessFunction(dc);
	}


	@Override
	public Object solucion(List<Integer> dc) {
		return PermutationChromosome.data.solucion(dc);
	}

	@Override
	public Integer itemsNumber() {
		return PermutationChromosome.data.itemsNumber();
	}
	
}