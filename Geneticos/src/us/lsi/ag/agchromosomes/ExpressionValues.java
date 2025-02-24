package us.lsi.ag.agchromosomes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.ag.ExpressionData;
import us.lsi.common.List2;
import us.lsi.common.Preconditions;
import us.lsi.tiposrecursivos.ast.Const;
import us.lsi.tiposrecursivos.ast.Exp;
import us.lsi.tiposrecursivos.ast.Operator;
import us.lsi.tiposrecursivos.ast.Operators;
import us.lsi.tiposrecursivos.ast.Type;
import us.lsi.tiposrecursivos.ast.Var;

public class ExpressionValues implements ChromosomeValues<Exp, List<Double>, Exp> {
	
	public static ExpressionValues of(ExpressionData data) {
		return new ExpressionValues(data);
	}

	/**
	 * Datos del cromosoma
	 */
	private ExpressionData data;
	/**
	 * Indice de la primera constante en el cromosoma.
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

	private ExpressionValues(ExpressionData data) {
		this.data = data;
		operators = data.operators();
		constantsIndex = data.numGenes()*data.numItemsPorGen();
		maxRanges = IntStream.range(0,data.size()).map(i->getMax(i)).boxed().collect(Collectors.toList());
		variables = getVariables(data.numVariables());
	}
	
	@Override
	public ExpressionData data() {
		return data;
	}

	@Override
	public Exp decodeValues(List<Double> ls) {
		List<Integer> items = IntStream.range(0,ls.size()).boxed().map(i->scale(ls.get(i),i)).toList();
		System.out.println("1 == "+items);
		System.out.println("2 == "+this.data.numConstants());
		constants = IntStream.range(0,this.data.numConstants()).boxed()
				.map(i->getConstant(i,getConstantValue(ls,i)))
				.toList();
		System.out.println("3 == "+constants);
		List<Exp> exps = new ArrayList<>();
		for(int i=0; i< this.data.numGenes();i++){
			List<Integer> g = gen(items,i);
			List<List<Operator>> levels = levelsOfGen(g);
			exps.add(Exp.ofOperatorsInLevels(levels).get(0));
		}
		Exp r = Exp.of(exps,data.nAryOperator());
		return r.simplify();
	}

	@Override
	public Integer dimension() {
		return data.size();
	}
	
	public Integer maxArity() {
		return data.operators().stream().mapToInt(x ->x.operatorId().arity()).max().getAsInt();
	}
	
	public Integer tailLength() {
		return data.headLength() * (maxArity() - 1) + 1;
	}
	
	public Integer numItemsPorGen() {
		return data.headLength() + tailLength();
	}
	
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
//	public static void iniValues(ChromosomeData<Exp,Exp> d) {
//		ExpressionData data = (ExpressionData) d;
//		AuxExpression.data =  data;
//		AuxExpression.operators = data.operators();
//		constantsIndex = data.numGenes()*data.numItemsPorGen();
//		maxRanges = IntStream.range(0,data.size()).map(i->getMax(i)).boxed().collect(Collectors.toList());
//		variables = getVariables(data.numVariables());
//	}
	
	/**
	 * @param i Un indice en maxRanges 
	 * @return Un valor que especifica el rango de valores en maxRanges[i]
	 */
	private Integer getMax(int i) {		
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
	
	public Const getConstant(int i, Object value) {
		value = switch(data.constType()) {
		case Boolean -> null;
		case Double -> Operators.toDouble(value);
		case Int -> Operators.toInt(value);
		case String -> null;
		case None -> throw new UnsupportedOperationException("Unimplemented case: " + data.constType());
		};
		return Const.of(nombresDeConstantes.get(i),value,data.constType());
	}
	
	public Double getConstantValue(List<Double> ls, int i) {
		return ls.get(constantsIndex+i)*data.maxValueConstant();
	}
	
	public static Operator getOperator(int i) {
		return operators.get(i);
	}
	
	public Operator operatorOfInt(int i) {
		Operator op;
		if(i<this.data.numConstants()) op = getConstant(i);
		else if(i<this.data.numConstants()+this.data.numVariables()) 
			op = getVariable(i-this.data.numConstants());
		else op = getOperator(i-(this.data.numConstants()+this.data.numVariables()));
		return op;	
	}
	
	public List<Integer> gen(List<Integer> ls, int i){
		Preconditions.checkArgument(i < this.data.numGenes());
		return ls.subList(this.data.numItemsPorGen()*i,this.data.numItemsPorGen()*i+this.data.numItemsPorGen());
	}
	
	public List<List<Operator>> levelsOfGen(List<Integer> g) {
		Integer index = 0;
		Integer level = 0;
		List<List<Operator>> r = new ArrayList<>();
		r.add(List2.of(operatorOfInt(g.get(index))));
		index++;
		Integer a = r.get(level).stream().mapToInt(op -> op.operatorId().arity()).sum();
		while (a >0 && index < g.size()) {
			a = r.get(level).stream().mapToInt(op -> op.operatorId().arity()).sum();
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
		return (int) (maxRanges.get(i)*e);
	}
	
	public Exp decode(List<Double> ls) {
		List<Integer> items = IntStream.range(0,ls.size()).boxed().map(i->scale(ls.get(i),i)).toList();
		System.out.println("1 == "+items);
		System.out.println("2 == "+this.data.numConstants());
		constants = IntStream.range(0,this.data.numConstants()).boxed()
				.map(i->getConstant(i,getConstantValue(ls,i)))
				.toList();
		System.out.println("3 == "+constants);
		List<Exp> exps = new ArrayList<>();
		for(int i=0; i<this.data.numGenes();i++){
			List<Integer> g = gen(items,i);
			List<List<Operator>> levels = levelsOfGen(g);
			exps.add(Exp.ofOperatorsInLevels(levels).get(0));
		}
		Exp r = Exp.of(exps,data.nAryOperator());
		return r.simplify();
	}
	

}
