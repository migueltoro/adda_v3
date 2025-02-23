package us.lsi.ag;


import java.util.List;

import us.lsi.tiposrecursivos.ast.Exp;
import us.lsi.tiposrecursivos.ast.Operator;
import us.lsi.tiposrecursivos.ast.Type;

public interface ExpressionData extends ChromosomeData<Exp,Exp>{
	
	/**
	 * @return La longitud de la cabeza de un gen
	 */
	Integer headLength();
	
	/**
	 * @return N�mero de Genes
	 */
	Integer numGenes();
	
	/**
	 * @return N�mero de variables
	 */
	Integer numVariables();
	
	/**
	 * @return Numero de constantes
	 */
	Integer numConstants();
	
	/**
	 * @return El rango m�ximo del valor de cada constante. Cada constante tendr� un valor en el rango 0..getMaxValueConstant()-1
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
		return operators().stream().mapToInt(x ->x.operatorId().arity()).max().getAsInt();
	}
	
	default Integer tailLength() {
		return headLength() * (maxArity() - 1) + 1;
	}
	
	default Integer numItemsPorGen() {
		return headLength() + tailLength();
	}
	
	default Integer size() {
		return this.numItemsPorGen()*this.numGenes() + this.numConstants();
	}
	
}
