package us.lsi.p1;

import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Ejemplo1 {

	public static String ejemplo1(Integer a, Integer b, UnaryOperator<Integer> f, String sp, String pf, String sf) {
		String res = "";
		res += String.format("Entrada: %s, %s,%s,%s,%s,%s\n", a, b, f, sp, pf, sf);
		res += String.format("%-30s%s\n", "1. Iterativa (while): ", ejemplo1_iterativo(a, b, f, sp, pf, sf));
		res += String.format("%-30s%s\n", "2. Recursiva: ", ejemplo1_recursivo(a, b, f, sp, pf, sf));
		res += String.format("%-30s%s\n", "3. Funcional: ", ejemplo1_funcional(a, b, f, sp, pf, sf));
		return res;
	}

	public static String ejemplo1_funcional(Integer a, Integer b, UnaryOperator<Integer> f, String sp, String pf,
			String sf) {
		
		return Stream.iterate(a, x -> x <= b, f)
				.map(x -> x * x)
				.map(x -> x.toString())
				.collect(Collectors.joining(sp, pf, sf));
	}

	public static String ejemplo1_iterativo(Integer a, Integer b, UnaryOperator<Integer> f, String sp, String pf,
			String sf) {
		String ac = pf;
		Boolean isFirst = true;
		Integer e = a;
		while (e <= b) {
			Integer t = e * e;
			String ts = t.toString();
			if (isFirst) {
				ac = ac+ts;
				isFirst = false;
			} else {
				ac = ac+sp+ts;;
			}
			e = f.apply(e);
		}
		return ac+sf;
	}

	public static String ejemplo1_recursivo(Integer a, Integer b, UnaryOperator<Integer> f, String sp, String pf,
			String sf) {
		Integer e = a;
		String ac = pf;
		ac = ejemplo1_recursivo(b, f, sp,e,true,ac);
		return ac+sf;
	}

	public static String ejemplo1_recursivo(Integer b, UnaryOperator<Integer> f, String sp,Integer e, Boolean isFirst, String ac) {	
		if (e <= b) {
			Integer t = e * e;
			String ts = String.format("%d", t);
			if (isFirst) {
				ac = ac+ts;
				isFirst = false;
			} else {
				ac = ac+sp+ts;
			}
			e = f.apply(e);
			ac = ejemplo1_recursivo(b,f,sp,e,isFirst,ac);
		}
		return ac;
	}
}
