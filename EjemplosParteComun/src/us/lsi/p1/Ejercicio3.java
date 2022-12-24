package us.lsi.p1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Ejercicio3 {

	public static String ejercicio3(Integer a, Integer limit) {
		String res = "";
		res += String.format("Entrada: %s, %s\n", a, limit);
		res += String.format("%-30s%s\n", "1. Iterativa (while): ", ejercicio3_iterativo(a, limit));
		res += String.format("%-30s%s\n", "2. Recursiva: ", ejercicio3_recursivo(a, limit));
		res += String.format("%-30s%s\n", "3. Funcional: ", ejercicio3_funcional(a, limit));
		return res;
	}

	public static String ejercicio3_funcional(Integer a, Integer limit) {

		return Stream.iterate(Par.of(0, a), t -> t.v1() < limit, r->r.siguiente())
				.collect(Collectors.toList())
				.toString();

	}

	public record Par(Integer v1, Integer v2) {
		public static Par of(Integer v1, Integer v2) {
			return new Par(v1, v2);
		}
		
		public Par siguiente() {
			Integer v1n = v1 + 1;
			Integer v2n = v2;
			if (v1 % 3 != 1) {
				v2n = v1 + v2;
			}
			return new Par(v1n, v2n);
		}
	}

	public static String ejercicio3_iterativo(Integer a, Integer limit) {
		Par p = new Par(0, a);
		List<Par> res = new LinkedList<Par>();
		while (p.v1 < limit) {
			res.add(p);
			p = p.siguiente();
		}
		return String.format("%s", res.toString());
	}

	public static String ejercicio3_recursivo(Integer a, Integer limit) {
		Par p = new Par(0, a);
		List<Par> ac = new ArrayList<Par>();
		ac = ejercicio3_recursivo(a, limit, ac, p);
		return String.format("%s", ac.toString());
	}

	public static List<Par> ejercicio3_recursivo(Integer a, Integer limit, List<Par> ac, Par p) {
		if (p.v1() < limit) {
			ac.add(p);
			ac = ejercicio3_recursivo(a, limit, ac, p.siguiente());
		}
		return ac;
	}

}
