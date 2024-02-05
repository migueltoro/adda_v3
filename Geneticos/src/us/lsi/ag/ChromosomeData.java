package us.lsi.ag;

import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.CrossoverPolicy;
import org.apache.commons.math3.genetics.MutationPolicy;
import org.apache.commons.math3.genetics.SelectionPolicy;

public interface ChromosomeData<E,S> {
	
	/**
	 * @return Numero de casillas del cromosoma
	 */
	Integer size();
	
	/**
	 * @param cr Valores del cromosoma
	 * @return La función de fitnes del cromosoma
	 */
	Double fitnessFunction(E value);
	
	/**
	 * @param cr Valores del cromosoma
	 * @return La solución definida por el cromosoma
	 */
	S solucion(E value);

	CrossoverPolicy crossOverPolicy();
	
	MutationPolicy mutationPolicy();
	
	SelectionPolicy selectionPolicy();
	
	void iniValues(ChromosomeData<E,S> data);
	
	Chromosome initialChromosome();
	
    E decode(Chromosome chromosome);

}
