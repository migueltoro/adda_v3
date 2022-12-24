package us.lsi.ag.agstopping;

import org.apache.commons.math3.genetics.FixedElapsedTime;
import org.apache.commons.math3.genetics.FixedGenerationCount;
import org.apache.commons.math3.genetics.StoppingCondition;

import us.lsi.common.Preconditions;



public class StoppingConditionFactory {

	/**
	 * <p> Distintos tipos de condiciones de parada </p>
	 * 
	 * <ul>
	 * <li> ElapsedTime: Para cuando el tiempo transcurrido se el especificado en <code> elapsedTime </code>.
	 * <li> GenerationCount: Para cuando el número de generaciones sea igual al especificado en <code> NUM_GENERATIONS </code>
	 * <li> SolutionsNumber: Para cuando en una generación encuentra al menos SOLUTIONS_NUMBER de cromososmas 
	 * con <code> fitness</code>  igual o mayor <code> FITNESS </code> o <code> NUM_GENERATIONS </code> ha sido superado.
	 * </ul> 
	 *
	 */
	public enum StoppingConditionType{ElapsedTime,GenerationCount,SolutionsNumber};
	
	/**
	 * Condición de parada
	 */
	public static StoppingConditionType stoppingConditionType = StoppingConditionType.GenerationCount;
	/**
	 * Número de soluciones a encontrar si fijamos el criterio de parada en SolutionsNumber
	 */
	public static int SOLUTIONS_NUMBER_MIN = 1;
	/**
	 * Tiempo máximo transcurrido para finalizar el algoritmo si usamos la condición de finalización ElapsedTime.
	 */
	public static long MAX_ELAPSEDTIME = Long.MAX_VALUE;
	
	/**
	 * Valor mínimo de la fitness de los cromosomas en las soluciones que vamos buscando si fijamos el criterio de parada en SolutionsNumber
	 */
	public static double FITNESS_MIN = 0.;	
	/**
	 * Número de generaciones máximo para fijar le criterio de parada
	 */
	public static int NUM_GENERATIONS = Integer.MAX_VALUE;
	
	public static StoppingCondition getStoppingCondition(){
		StoppingCondition stopCond = null;
		switch(stoppingConditionType){
		case ElapsedTime: stopCond = new FixedElapsedTime(MAX_ELAPSEDTIME);break;
		case GenerationCount: stopCond = new FixedGenerationCount(NUM_GENERATIONS); break;
		case SolutionsNumber: stopCond = new SolutionsNumber(SOLUTIONS_NUMBER_MIN,NUM_GENERATIONS); break;
		}
		Preconditions.checkState(stopCond!=null);
		return stopCond;
	}	
}
