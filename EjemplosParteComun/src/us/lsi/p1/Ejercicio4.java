package us.lsi.p1;

import java.util.Optional;
import java.util.stream.Stream;

public class Ejercicio4 {

	public static String ejercicio4(Double n, Double e) {
		String res = "";
		res += String.format("Entrada: %s, %s\n", n, e);
		res += String.format("%-30s%s\n", "1. Iterativa (while): " , iterativo(n, e));
		res += String.format("%-30s%s\n", "2. Recursiva final: " , recursiva(n, e));
		res += String.format("%-30s%s\n", "3. Funcional: " , funcional(n, e));

		return res;
	}

	public static Double iterativo(Double n, Double e) {
		Double i = 1.0;
		Double j = n;
		Double k = (i + j) / 2;
		while (Math.abs(n - Math.pow(k, 3)) > e) {		
			if (Math.pow(k, 3) <= n) {
				i = k;
			} else {
				j = k;
			}
			k = (i + j) / 2;
		}
		return k;
	}

	public static Double recursiva(Double n, Double e) {
		return recursiva(n, e, 1.0, n, (n+1.0)/2);
	}

	private static Double recursiva(Double n, Double e, Double i, Double j, Double k) {
		if (Math.abs(n - Math.pow(k, 3)) > e) {
			if (Math.pow(k, 3) <= n) {
				i = k;
			} else {
				j = k;
			}
			k = (i + j) / 2;			
			k = recursiva(n, e, i, j, k);			
		}
		return k;
	}

	
	public record RangoDouble(Double i, Double k, Double j, Double n) {
		public static RangoDouble of(Double i, Double j, Double n) {
			return new RangoDouble(i, (i+j)/2, j, n);
		}
		
		public RangoDouble next() {
			RangoDouble r;
			if (Math.pow(k(), 3) <= n) {
				r = RangoDouble.of(k(),j(),n);
			} else {
				r = RangoDouble.of(i(),k(),n);
			}
			return r;
		}

	}

	public static Double funcional(Double n, Double e) {

		Stream<RangoDouble> stream = Stream.iterate(RangoDouble.of(1.0,n,n), r->r.next());

		Optional<RangoDouble> res = stream
				.filter(p -> Math.abs(n - Math.pow(p.k(), 3)) <= e)				
				.findFirst();
		return res.isPresent() ? res.get().k() : -1;
	}

}
