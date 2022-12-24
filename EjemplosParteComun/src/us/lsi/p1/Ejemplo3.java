package us.lsi.p1;

import java.util.Optional;
import java.util.stream.Stream;

public class Ejemplo3 {

	public static String ejemplo3(String a, String b) {
		String res = "";
		res += String.format("Entrada: %s,%s\n", a, b);
		res += String.format("%-30s%s\n", "1. Iterativa (while): ", iterativa(a, b));
		res += String.format("%-30s%s\n", "2. Recursiva final: ", recursiva(a, b));
		res += String.format("%-30s%s\n", "3. Funcional: ", funcional(a, b));

		return res;
	}

	public static int recursiva(String a, String b) {
		return buscaPosicionBinaria(a, b, 0, a.length());
	}

	private static int buscaPosicionBinaria(String a, String b, int i, int j) {
		if(i < j) {
			int k = (i + j) / 2;
			if (a.charAt(k) == b.charAt(k)) i = buscaPosicionBinaria(a, b, k + 1, j);
			else i = buscaPosicionBinaria(a, b, i, k);
		}
		return i;
	}

	private static Integer iterativa(String a, String b) {
		Integer i = 0;
		Integer j = a.length();
		while (i < j) {
			Integer k = (i + j) / 2;
			if (a.charAt(k) == b.charAt(k)) i = k + 1;
			else j = k;
		}
		return i;
	}

	public static Integer funcional(String a, String b) {
		RangoString ir = RangoString.of(0, a.length(), a, b);
		Stream<RangoString> stream = Stream.iterate(ir,r -> r.next());
		Optional<RangoString> res = stream
				.filter(r -> r.i() == r.j())
				.findFirst();
		return res.isPresent() ? res.get().i() : -1;
	}

	/**
	 * 
	 * @apiNote Record usado en la notación funcional.
	 *
	 */
	public static record RangoString(Integer i, Integer j, Integer k, String a, String b) {

		public static RangoString of(Integer i, Integer j, String a, String b) {
			return new RangoString(i, j, (i + j) / 2, a, b);
		}

		public RangoString next() {
			Integer k = (i + j) / 2;
			RangoString r;
			if (a.charAt(k) == b.charAt(k)) r = RangoString.of(k+1, j, a, b);
			else r = RangoString.of(i, k, a, b);
			return r;
		}

	}

}
