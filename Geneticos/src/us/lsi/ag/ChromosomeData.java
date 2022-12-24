package us.lsi.ag;

import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;

public interface ChromosomeData<E,S> {
	
	/**
	 * @return Numero de casillas del cromosoma
	 */
	Integer size();
	
	/**
	 * @return El tipo del cromosoma
	 */
	ChromosomeType type();
	
	/**
	 * @param cr Valores del cromosoma
	 * @return La funci�n de fitnes del cromosoma
	 */
	Double fitnessFunction(E value);
	
	/**
	 * @param cr Valores del cromosoma
	 * @return La soluci�n definida por el cromosoma
	 */
	S solucion(E value);

}
