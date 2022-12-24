package us.lsi.ag;

import java.util.List;

import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.tiposrecursivos.ast.Exp;
import us.lsi.tiposrecursivos.ast.Operator;
import us.lsi.tiposrecursivos.ast.Type;

public interface ExpressionData extends ChromosomeData<Exp,Exp>{
	
	/**
	 * @return La longitud de la cabeza de un gen
	 */
	Integer headLength();
	
	/**
	 * @return Número de Genes
	 */
	Integer numGenes();
	
	/**
	 * @return Número de variables
	 */
	Integer numVariables();
	
	/**
	 * @return Numero de constantes
	 */
	Integer numConstants();
	
	/**
	 * @return El rango máximo del valor de cada constante. Cada constante tendrá un valor en el rango 0..getMaxValueConstant()-1
	 */
	Integer maxValueConstant();
	
	/**
	 * @return Tipo de las constantes
	 */
	Type constType();
	
	/**
	 * @return Operadores disponibles
	 */
	List<Operator> operators();	
	
	/**
	 * @return Operador n-ario para combinar los resultados de los genes
	 */
	Operator.Nary nAryOperator();
	
	default Integer maxArity() {
		return operators().stream().mapToInt(x ->x.id().arity()).max().getAsInt();
	}
	
	default Integer tailLength() {
		return headLength() * (maxArity() - 1) + 1;
	}
	
	default Integer numItemsPorGen() {
		return headLength() + tailLength();
	}
	
	default Integer size() {
		return numItemsPorGen()*numGenes() + numConstants();
	}
	
	default ChromosomeType type() {
		return ChromosomeType.Expression;
	}
}
