package us.lsi.tiposrecursivos.ast;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import us.lsi.common.Preconditions;
import us.lsi.common.Printers;

public sealed interface Exp permits Unary, Binary, Var, Const, CallFunction, Nary{
	
	Set<Var> vars();
	Operator operator();
	Object value();
	Type type();
	String name();
	void toDot(PrintStream file, Map<Object,Integer> map);
	Boolean isConst();
	Exp simplify();
	
	public static void toDot(String file, Exp e) {
		PrintStream p = Printers.file(file);
		Map<Object,Integer> map = new HashMap<>();
		map.put("maxValue",0);
		String txt = "digraph Exp { \n \n"; 
		p.println(txt);
		e.toDot(p,map);
		p.println("}");
		p.close();
	}
	
	public static Exp of(List<Exp> operands, Operator op) {
		Exp r = switch(op.id().arity()) {
		case 0 -> operands.get(0);
		case 1 -> Unary.of(operands.get(0),op.id().name());
		case 2 -> Binary.of(operands.get(0),operands.get(1),op.id().name());
		case -1 -> Nary.of(operands,op.id().name());
		default -> null;
		};
		return r;
	}
	
	public static List<Exp> ofOperatorsInLevels(List<List<Operator>> operators) {
		return Exp.of(operators,0);
	}
	
	private static List<Exp> of(List<List<Operator>> operators, Integer level) {
		Integer n = operators.size();
		List<Exp> r;
		if (level == n - 1)
			r = operators.get(level).stream().map(op -> (Exp) op).toList();
		else {
			List<Operator> ls = operators.get(level);
			List<Exp> next = of(operators, level + 1);
			Integer index = 0;
			r = new ArrayList<>();
			for (int i = 0; i < ls.size(); i++) {
				Exp e = null;
				Operator op = ls.get(i);
				if (op.id().arity() == 0) e = (Exp) op;
				else if (op.id().arity() == 1) {
					e = Unary.of(next.get(index), op.id().name());
					index += 1;
				} else if (op.id().arity() == 2) {
					e = Binary.of(next.get(index), next.get(index + 1), op.id().name());
					index += 2;
				} else {
					Preconditions.checkArgument(0 <= op.id().arity() && op.id().arity() <= 2,
							String.format("La aridad es %d", op.id().arity()));
				}
				r.add(e);
			}
		}
		return r;
	}
}
