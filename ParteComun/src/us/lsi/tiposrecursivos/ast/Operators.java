package us.lsi.tiposrecursivos.ast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.lsi.common.Preconditions;

public class Operators {
	
	public static Map<OperatorId,Operator> operators = new HashMap<>();
	
	public static Integer toInt(Object value) {
		return ((Number)value).intValue();
	}
	
	public static Double toDouble(Object value) {
		return ((Number)value).doubleValue();
	}
	
	public static Operator get0(String name) {
		OperatorId id = OperatorId.of0(name);
		Operator op = Operators.operators.getOrDefault(id,null);
		Preconditions.checkNotNull(op,String.format("No existe el operador %s",id));
		return op;
	}
	
	public static Operator get1(String name,Type tp) {
		OperatorId id = OperatorId.of1(name, tp);
		Operator op = Operators.operators.getOrDefault(id,null);
		Preconditions.checkNotNull(op,String.format("No existe el operador %s",id));
		return op;
	}
	
	public static Operator get2(String name,Type tp1,Type tp2) {
		OperatorId id = OperatorId.of2(name, tp1, tp2);
		Operator op = Operators.operators.getOrDefault(id,null);
		Preconditions.checkNotNull(op,String.format("No existe el operador %s",id));
		return op;
	}
	
	public static Operator getN(String name,Type tps) {
		OperatorId id = OperatorId.ofN(name, tps);
		Operator op = Operators.operators.getOrDefault(id,null);
		Preconditions.checkNotNull(op,String.format("No existe el operador %s",id));
		return op;
	}
	

	public static void addOperators() {
		Operator plusInt = Operator.of2("+",Type.Int,Type.Int,Type.Int,(e1,e2)->toInt(e1)+toInt(e2));
		Operators.operators.put(plusInt.id(), plusInt);
		Operator multInt = Operator.of2("*",Type.Int,Type.Int,Type.Int,(e1,e2)->toInt(e1)*toInt(e2));
		Operators.operators.put(multInt.id(), multInt);
		Operator plusDouble = Operator.of2("+",Type.Double,Type.Double,Type.Double,(e1,e2)->toDouble(e1)+toDouble(e2));
		Operators.operators.put(plusDouble.id(), plusDouble);
		Operator multDouble = Operator.of2("*",Type.Double,Type.Double,Type.Double,(e1,e2)->toDouble(e1)*toDouble(e2));
		Operators.operators.put(multDouble.id(), multDouble);
		Operator toDouble = Operator.of1("toDouble",Type.Int,Type.Double,e->toDouble(e));
		Operators.operators.put(toDouble.id(),toDouble);
		Operator toInt = Operator.of1("toInt",Type.Double,Type.Int,e->toInt(e));
		Operators.operators.put(toInt.id(),toInt);
		Operator sqrt = Operator.of1("sqrt",Type.Double,Type.Double,e->Math.sqrt(toDouble(e)));
		Operators.operators.put(sqrt.id(),sqrt);
		Operator sqrt2 = Operator.of1("sqrt",Type.Int,Type.Double,e->Math.sqrt(toInt(e)));
		Operators.operators.put(sqrt2.id(),sqrt2);
		Operator pot2 = Operator.of1("^2",Type.Double,Type.Double,e->toDouble(e)*toDouble(e));
		Operators.operators.put(pot2.id(),pot2);
		Operator pot22 = Operator.of1("^2",Type.Int,Type.Int,e->toInt(e)*toInt(e));
		Operators.operators.put(pot22.id(),pot22);
		Operator pot3 = Operator.of1("^3",Type.Double,Type.Double,e->toDouble(e)*toDouble(e)*toDouble(e));
		Operators.operators.put(pot3.id(),pot3);
		Operator pot32 = Operator.of1("^3",Type.Int,Type.Int,e->toInt(e)*toInt(e)*toInt(e));
		Operators.operators.put(pot32.id(),pot32);
		Operator plusN = Operator.ofN("+",Type.Double,Type.Double,(List<Object> ls)->ls.stream()
				.mapToDouble(e->Operators.toDouble(e)).sum());
		Operators.operators.put(plusN.id(),plusN);
		Operator plusN2 = Operator.ofN("+",Type.Int,Type.Int,(List<Object> ls)->ls.stream()
				.mapToDouble(e->Operators.toInt(e)).sum());
		Operators.operators.put(plusN2.id(),plusN2);
	}
	
	

}
