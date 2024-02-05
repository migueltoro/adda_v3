package us.lsi.ag.agchromosomes;

import org.apache.commons.math3.genetics.CrossoverPolicy;
import org.apache.commons.math3.genetics.CycleCrossover;
import org.apache.commons.math3.genetics.NPointCrossover;
import org.apache.commons.math3.genetics.OnePointCrossover;
import org.apache.commons.math3.genetics.OrderedCrossover;
import org.apache.commons.math3.genetics.UniformCrossover;

public class ACrossOverPolicy {
	
	public enum CrossoverType{Cycle,NPoint,OnePoint,Ordered,Uniform};
	
	/**
	 * Tipo del operador de cruce
	 */
	public static CrossoverType crossoverType = CrossoverType.OnePoint;
	
	/**
	 * N�mero de puntos usados en la partici�n si se usa un operador de cruce de tipo NPointCrossover
	 */
	public static int NPOINTCROSSOVER = 3;
	/**
	 * La ratio si se usa el operador de cruce de tipo UniformCrossover
	 */
	public static double RATIO_UNIFORMCROSSOVER = 0.7;
	
	/**
	 * @param tipo El tipo del cromosoma
	 * @param problema Las propiedades del probblema a resolver
	 * @return Un operador de cruce adecuado para un cromosma del tipo indicado
	 */
	public static CrossoverPolicy getCrossoverPolicyBin(CrossoverType tipo){
		CrossoverPolicy crossOverPolicy = null;	
		switch(tipo){
		case Cycle: crossOverPolicy = new CycleCrossover<Integer>();break;
		case NPoint: crossOverPolicy = new NPointCrossover<Integer>(NPOINTCROSSOVER);break;
		case OnePoint: crossOverPolicy = new OnePointCrossover<Integer>();break;
		case Ordered: crossOverPolicy = new OrderedCrossover<Integer>(); break;
		case Uniform: crossOverPolicy = new UniformCrossover<Integer>(RATIO_UNIFORMCROSSOVER); break;
		}
		return crossOverPolicy;
	}
	
	public static CrossoverPolicy getCrossoverPolicyKey(CrossoverType tipo) {
		CrossoverPolicy crossOverPolicyKey = null;
		switch (crossoverType) {
		case Cycle: crossOverPolicyKey = new CycleCrossover<Double>(); break;
		case NPoint: crossOverPolicyKey = new NPointCrossover<Double>(NPOINTCROSSOVER); break;
		case OnePoint: crossOverPolicyKey = new OnePointCrossover<Double>(); break;
		case Ordered: crossOverPolicyKey = new OrderedCrossover<Double>(); break;
		case Uniform: crossOverPolicyKey = new UniformCrossover<Double>(RATIO_UNIFORMCROSSOVER); break;
		}
		return crossOverPolicyKey;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
