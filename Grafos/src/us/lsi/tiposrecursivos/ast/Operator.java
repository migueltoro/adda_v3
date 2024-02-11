package us.lsi.tiposrecursivos.ast;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface Operator {
	
	OperatorId id();
	Type resultType();
	
	public static Operator of0(String name,Type rt,Object value) {
		OperatorId id = OperatorId.of0(name);
		return new Zero(id,rt,value);
	}
	
	public static Operator of1(String name,Type tp,Type rt,Function<Object,Object> function) {
		OperatorId id = OperatorId.of1(name, tp);
		return new Unary(id,rt,function);
	}
	
	public static Operator of2(String name,Type tp1,Type tp2,Type tr,BiFunction<Object,Object,Object> function) {
		OperatorId id = OperatorId.of2(name,tp1,tp2);
		return new Binary(id,tr,function);
	}
	
	public static Operator ofN(String name,Type tps,Type tr,Function<List<Object>,Object> function) {
		OperatorId id = OperatorId.ofN(name, tps);
		return new Nary(id,tr,function);
	}
	
	public record Zero(OperatorId id, Type resultType, Object value) implements Operator {}
	
	public record Unary(OperatorId id, Type resultType, Function<Object,Object> function) implements Operator {}
	
	public record Binary(OperatorId id, Type resultType, BiFunction<Object,Object,Object> function) implements Operator {}
	
	public record Nary(OperatorId id, Type resultType, Function<List<Object>,Object> function) implements Operator {}
	
}
