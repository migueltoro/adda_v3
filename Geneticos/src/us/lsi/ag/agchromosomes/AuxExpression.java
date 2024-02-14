package us.lsi.ag.agchromosomes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.ag.ChromosomeData;
import us.lsi.ag.ExpressionData;
import us.lsi.common.List2;
import us.lsi.common.Preconditions;
import us.lsi.tiposrecursivos.ast.Const;
import us.lsi.tiposrecursivos.ast.Exp;
import us.lsi.tiposrecursivos.ast.Operator;
import us.lsi.tiposrecursivos.ast.Operators;
import us.lsi.tiposrecursivos.ast.Type;
import us.lsi.tiposrecursivos.ast.Var;


/**
 * @author Miguel Toro
 *
 * @param <T> Tipo del resultado de la expresi�n
 * 
 * <p> 
 * La implementaci�n 
 * sigue las ideas de <a href="https://en.wikipedia.org/wiki/Gene_expression_programming"> Gene expression programming</a></p>
 * 
 * <p> Para formar la expresiones resultado de decodificar un cromosoma disponemos de p operadores, v variables y c constantes. 
 * Asumimos que un cromosoma est� formado por n genes m�s c constantes. 
 * Cada gen tiene r elementos separados en dos partes: la cabeza de longitud definida h 
 * y la cola de longitud t = h (ma - 1) + 1 siendo ma la maxima aridad de los operadores usados. 
 * Se cumple r = h+t. El n�mero s de elementos del cromosoma es s = n*r+c. 
 * Asumimos, adem�s que en la cabeza de cada gene hay operadores, numerados en el rango 0..p-1, 
 * variables numeradas en el rango 0..v-1 y constantes en el rango 0..c-1. </p>
 * 
 * <p> Modelamos un cromosoma con una lista de enteros de tama�o s. 
 * Los n primeros segmentos de tama�o r representan los n genes y el segmento final de m elementos las constantes. 
 * Dentro de cada gen el primer segmento de longitud h es la cabeza y estar� formada por enteros en el rango 0..p+v+c-1. 
 * El segundo segmento es la cola de longitud t y formada por enteros en el rango 0..v+c+1. 
 * El segmento final de constantes de tama�o c est� formado por reales en el rango 0..maxValueConstant. 
 * Asumimos que un entero i en el rango 0..p+v+c-1 representa la constante i si 0 &le; i &lt; constantsIndex. 
 * Un entero i en el rango 0..p+v+c-1 representa la variable  i-constantsIndex si constantsIndex &le; i &lt; constantsIndex+variableIndex. 
 * Un entero i en el rango 0..p+v+c-1 representa el operador i-constantsIndex+variableIndex si constantsIndex+variableIndex &le; i. </p>
 * 
 */
public class AuxExpression {

	/**
	 * Datos del cromosoma
	 */
	private static ExpressionData data;

	/**
	 * Indice de la primera contante en el cromosoma.
	 * Las contantes est�n ubicadas despu�s de los genes
	 */
	private static Integer constantsIndex;
		
	/**
	 * El rango de valores que puede tomar cada casilla del cromosoma.
	 * La casilla i contendr� un valor v tal que 0 &lg; v &lt;maxRanges.get(i)
	 */
	private static List<Integer> maxRanges;	
	
	/**
	 * Las variables disponibles
	 */
	private static List<Var> variables;
	
	/**
	 * Las variables disponibles
	 */
	private static List<Const> constants;
	
	/**
	 * Operadores disponibles
	 */
	private static List<Operator> operators;
	
	/**
	 * Identificadores de las variables a usar
	 */
	public static List<String> nombresDeVariables = Arrays.asList("x","y","z"+"t","u","v"+"w");
	
	/**
	 * Identificadores de las constantes a usar
	 */
	public static List<String> nombresDeConstantes = Arrays.asList("a","b","c"+"d","e","f"+"g");
	
	/**
	 * @pos Inicializa los par�metros de la clase
	 * @param problema El problema a resolver
	 * @param <T> El tipo de la expresi�n que representa el cromosoma
	 */
	public static void iniValues(ChromosomeData<Exp,Exp> d) {
		ExpressionData data = (ExpressionData) d;
		AuxExpression.data =  data;
		AuxExpression.operators = data.operators();
		constantsIndex = data.numGenes()*data.numItemsPorGen();
		maxRanges = IntStream.range(0,data.size()).map(i->getMax(i)).boxed().collect(Collectors.toList());
		variables = getVariables(data.numVariables());
	}
	
	/**
	 * @param i Un indice en maxRanges 
	 * @return Un valor que especifica el rango de valores en maxRanges[i]
	 */
	private static Integer getMax(int i) {		
		Integer r = null;
		if (i>=constantsIndex) {
			r = data.maxValueConstant();
		} else {
			i = i%data.numItemsPorGen();
			if (i < data.headLength()) {
				r = data.numConstants() + data.numVariables() + data.operators().size();
			} else if (i < data.headLength()+ data.tailLength()) {
				r = data.numConstants() + data.numVariables();
			} 
		}
		return r;
	}	
	
	private static List<Var> getVariables(int num){
		List<Var> ls = List2.empty();
		for(int i=0; i<num;i++){
			ls.add(Var.of(nombresDeVariables.get(i),Type.Double));
		}
		return ls;
	}
	
	public static Var getVariable(int i) {
		return variables.get(i);
	}
	
	public static Const getConstant(int i) {
		return constants.get(i);
	}
	
	public static Const getConstant(int i, Object value) {
		value = switch(data.constType()) {
		case Boolean -> null;
		case Double -> Operators.toDouble(value);
		case Int -> Operators.toInt(value);
		case String -> null;
		case None -> throw new UnsupportedOperationException("Unimplemented case: " + data.constType());
		};
		return Const.of(nombresDeConstantes.get(i),value,data.constType());
	}
	
	public static Double getConstantValue(List<Double> ls, int i) {
		return ls.get(constantsIndex+i)*data.maxValueConstant();
	}
	
	public static Operator getOperator(int i) {
		return operators.get(i);
	}
	
	public static Operator operatorOfInt(int i) {
		Operator op;
		if(i<AuxExpression.data.numConstants()) op = getConstant(i);
		else if(i<AuxExpression.data.numConstants()+AuxExpression.data.numVariables()) 
			op = getVariable(i-AuxExpression.data.numConstants());
		else op = getOperator(i-(AuxExpression.data.numConstants()+AuxExpression.data.numVariables()));
		return op;	
	}
	
	public static List<Integer> gen(List<Integer> ls, int i){
		Preconditions.checkArgument(i < AuxExpression.data.numGenes());
		return ls.subList(AuxExpression.data.numItemsPorGen()*i,AuxExpression.data.numItemsPorGen()*i+AuxExpression.data.numItemsPorGen());
	}
	
	public static List<List<Operator>> levelsOfGen(List<Integer> g) {
		Integer index = 0;
		Integer level = 0;
		List<List<Operator>> r = new ArrayList<>();
		r.add(List2.of(operatorOfInt(g.get(index))));
		index++;
		Integer a = r.get(level).stream().mapToInt(op -> op.id().arity()).sum();
		while (a >0 && index < g.size()) {
			a = r.get(level).stream().mapToInt(op -> op.id().arity()).sum();
			List<Operator> lv = new ArrayList<>();
			Integer i = index;
			while (a > 0 && i < index + a) {
				lv.add(operatorOfInt(g.get(index)));
				i++;
			}
			r.add(lv);
			level++;
			index += a;
		}
		return r;
	}
	
	private static Integer scale(Double e, Integer i) {
		return (int) (AuxExpression.maxRanges.get(i)*e);
	}
	
	public static Exp decode(List<Double> ls) {
		List<Integer> items = IntStream.range(0,ls.size()).boxed().map(i->scale(ls.get(i),i)).toList();
		AuxExpression.constants = IntStream.range(0,AuxExpression.data.numConstants()).boxed()
				.map(i->getConstant(i,getConstantValue(ls,i)))
				.toList();
		List<Exp> exps = new ArrayList<>();
		for(int i=0; i<AuxExpression.data.numGenes();i++){
			List<Integer> g = gen(items,i);
			List<List<Operator>> levels = levelsOfGen(g);
			exps.add(Exp.ofOperatorsInLevels(levels).get(0));
		}
		Exp r = Exp.of(exps,data.nAryOperator());
		return r.simplify();
	}
	
}

