package us.lsi.expression;

import java.util.Arrays;
import java.util.List;

import us.lsi.ag.ExpressionData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.common.Preconditions;
import us.lsi.tiposrecursivos.ast.Exp;
import us.lsi.tiposrecursivos.ast.Operator;
import us.lsi.tiposrecursivos.ast.Operator.Nary;
import us.lsi.tiposrecursivos.ast.Operators;
import us.lsi.tiposrecursivos.ast.Type;
import us.lsi.tiposrecursivos.ast.Var;

public class DatosExpression implements ExpressionData {
	
	public DatosExpression() {
		Operators.addOperators();
	}
	
	public static List<Double> lsx = Arrays.asList(0.0,50.0,100.0,150.0,200.0,250.0,300.0,350.0,400.0,450.0,500.0,550.0,600.0,650.0,700.0,750.0,
			800.0,850.0,900.0,950.0);
	public static List<Double> lsv = Arrays.asList(3.0,130003.0,1020003.0,3420003.0,8080003.0,
			1.5750003E7,2.7180003E7,4.3120003E7,6.4320003E7,9.1530003E7,1.25500003E8,1.66980003E8,
			2.16720003E8,2.75470003E8,3.43980003E8,4.23000003E8,5.13280003E8,6.15570003E8,7.30620003E8,
			8.59180003E8);		   
	@Override
	public Integer numVariables() {
		return 1;
	}

	@Override
	public Integer numConstants() {
		return 2;
	}

	@Override
	public Integer maxValueConstant() {
		return 5;
	}

	@Override
	public List<Operator> operators() {		
		Operator plus = Operators.get2("+", Type.Double, Type.Double);
		Operator multiply = Operators.get2("*", Type.Double, Type.Double);
		Operator  sqrt = Operators.get1("sqrt", Type.Double);
		Operator pot2 = Operators.get1("^2", Type.Double);
		Operator pot3 = Operators.get1("^3", Type.Double);
		List<Operator> exp = Arrays.asList(plus,multiply,pot2,pot3,sqrt);
		return exp;
	}

	@Override
	public Double fitnessFunction(Exp exp) {
		Preconditions.checkArgument(lsx.size()==lsv.size());
		List<Var> vars = exp.vars().stream().toList();
		Double r =0.;
		Double r2;
		for(int i=0;i<lsx.size();i++){
			if (!exp.isConst()) vars.get(0).setValue(lsx.get(i));
			r2 = Operators.toDouble(exp.value())-lsv.get(i);
			r = r + r2*r2;			
		}
		return -r;
	}

	@Override
	public Exp solucion(Exp e) {
		return e;
	}

	@Override
	public Integer numGenes() {
		return 3;
	}

	@Override
	public Integer headLength() {
		return 3;
	}

	@Override
	public Nary nAryOperator() {
		return (Nary) Operators.getN("+",Type.Double);
	}

	@Override
	public Type constType() {
		return Type.Int;
	}

}
