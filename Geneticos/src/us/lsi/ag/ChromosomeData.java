package us.lsi.ag;

public interface ChromosomeData<V,S> {
	
	/**
	 * @return Numero de valores del cromosoma
	 */
	Integer size();
	
	/**
	 * @param cr Valores del cromosoma
	 * @return La función de fitnes del cromosoma
	 */
	Double fitnessFunction(V value);
	
	/**
	 * @param cr Valores del cromosoma
	 * @return La solución definida por el cromosoma
	 */
	S solution(V value);
}
