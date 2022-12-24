package us.lsi.p1;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Ejercicio1 {

	public static String ejercicio1(List<String> ls, Predicate<String> pS, Predicate<Integer> pI,
			Function<String, Integer> f) {
		String res = "";
		res += String.format("Entrada: %s\n", ls);
		res += String.format("%-30s%s\n", "1. iterativa (while): " , ejercicio1_iterativo(ls, pS, pI, f));
		res += String.format("%-30s%s\n", "2. recursiva final: " , ejercicio1_recursiva_final(ls, pS, pI, f));
		res += String.format("%-30s%s\n", "3. Funcional: " , ejercicio1_funcional(ls, pS, pI, f));
		return res;
	}

	public static boolean ejercicio1_funcional(List<String> ls, Predicate<String> pS, Predicate<Integer> pI,
			Function<String, Integer> f) {
		return ls.stream()
				.filter(pS)
				.map(f)
				.anyMatch(pI);
	}

	public static boolean ejercicio1_iterativo(List<String> ls, Predicate<String> pS, Predicate<Integer> pI,
			Function<String, Integer> f) {
		Integer i = 0;
		Boolean res = false;
		while (i < ls.size() && !res) {
			String s = ls.get(i);
			res = pS.test(s) && pI.test(f.apply(s));
			i++;
		}
		return res;
	}

	public static boolean ejercicio1_recursiva_final(List<String> ls, Predicate<String> pS, Predicate<Integer> pI,
			Function<String, Integer> f) {
		return ejercicio1_recursiva_final(ls, pS, pI, f, 0, false);
	}

	public static boolean ejercicio1_recursiva_final(List<String> ls, Predicate<String> pS, Predicate<Integer> pI,
			Function<String, Integer> f, Integer i, boolean ac) {	
		if (i < ls.size() && !ac) {
			String s = ls.get(i);
			ac = ac || (pS.test(s) && pI.test(f.apply(s)));
			ac = ejercicio1_recursiva_final(ls, pS, pI, f, i + 1, ac);
		}
		return ac;
	}

}
