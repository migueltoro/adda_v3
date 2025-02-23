package us.lsi.ag.agchromosomes;

import java.util.List;


import us.lsi.ag.ExpressionData;
import us.lsi.tiposrecursivos.ast.Exp;

public class ExpressionValues implements ChromosomeValues<Exp, List<Double>, Exp> {
	
	public static ExpressionValues of(ExpressionData data) {
		return new ExpressionValues(data);
	}

	private ExpressionData data;

	private ExpressionValues(ExpressionData data) {
		this.data = data;
	}
	
	@Override
	public ExpressionData data() {
		return data;
	}

	@Override
	public Exp decodeValues(List<Double> ls) {
		return AuxExpression.decode(ls);
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
	
	

}
