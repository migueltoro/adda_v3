package us.lsi.enero_2024.e1;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import us.lsi.common.Map2;
import us.lsi.common.TriFunction;

public class Transformacion {

	public static BiFunction<Integer, Integer, Map<Integer, String>> baseNF = 
			(a, b) -> Map.of(a + b,String.format("%d%d", a, b));

	public static TriFunction<Map<Integer, String>, Integer, Integer, Map<Integer, String>> baseF = 
			(m, a, b) -> Map2.putIfAbsent(m, a + b, String.format("%d%d", a, b));

	public static TriFunction<Map<Integer, String>, Integer, Integer, Map<Integer, String>> fa1NF = 
			(m, a, b) -> Map2.put(m, a, String.format("%d", b));

	public static TriFunction<Map<Integer, String>, Integer, Integer, Map<Integer, String>> fa2NF = 
			(m, a, b) -> Map2.put(m, b, String.format("%d%d", b, a));

	public static TriFunction<Map<Integer, String>, Integer, Integer, Map<Integer, String>> fa1F = 
			(m, a, b) -> Map2.putIfAbsent(m, a, String.format("%d", b));

	public static TriFunction<Map<Integer, String>, Integer, Integer, Map<Integer, String>> fa2F = 
			(m, a, b) -> Map2.putIfAbsent(m, b, String.format("%d%d", b, a));

	public static Map<Integer, String> fRNF(Integer a, Integer b) {
		Map<Integer, String> m;
		if (a <= 2 && b > 20)
			m = baseNF.apply(a, b);
		else if (a % 2 == 0) {
			m = fRNF(a - 1, b + 3);
			m = fa1NF.apply(m, a, b);
		} else {
			m = fRNF(a - 3, b + 5);
			m = fa2NF.apply(m, a, b);
		}
		return m;
	}

	public static Map<Integer, String> fRF(Integer a, Integer b) {
		return fRF(a, b, new HashMap<>());
	}

	public static Map<Integer, String> fRF(Integer a, Integer b, Map<Integer, String> acum) {
		if (a <= 2 && b > 20) {
			acum = baseF.apply(acum, a, b);
		} else if (a % 2 == 0) {
			acum = fa1F.apply(acum, a, b);
			acum = fRF(a - 1, b + 3, acum);
		} else {
			acum = fa2F.apply(acum, a, b);
			acum = fRF(a - 3, b + 5, acum);
		}
		return acum;
	}

	public static Map<Integer, String> iImp(Integer a, Integer b) {
		Map<Integer, String> acum = new HashMap<>();
		while (!(a <= 2 && b > 20)) {
			if (a % 2 == 0) {
				acum = fa1F.apply(acum, a, b);
				a = a - 1;
				b = b + 3;

			} else {
				acum = fa2F.apply(acum, a, b);
				a = a - 3;
				b = b + 5;
			}
		}
		if (a <= 2 && b > 20) {
			acum = baseF.apply(acum, a, b);
		}
		return acum;
	}
	
	public static record Datos(Integer a, Integer b, Map<Integer, String> acum) {
		public static Datos of(Integer a, Integer b, Map<Integer, String> acum) {
			return new Datos(a, b, acum);
		}
		public Boolean p() {
			return !(a <= 2 && b > 20);
		}
		public Datos next() {
			Integer a1; Integer b1; Map<Integer, String> acum1;
			if (a % 2 == 0) {
				acum1 = fa1F.apply(acum, a, b);
				a1 = a - 1;
				b1 = b + 3;

			} else {
				acum1 = fa2F.apply(acum, a, b);
				a1 = a - 3;
				b1 = b + 5;
			}
			return Datos.of(a1,b1,acum1);
		}
	}
	
	public static Map<Integer, String> iF(Integer a, Integer b) {
		Map<Integer, String> acum = Stream.iterate(Datos.of(a, b,new HashMap<>()),d->d.next())
				.filter(d->!d.p())
				.findFirst()
				.get()
				.acum();
		if (a <= 2 && b > 20) {
			acum = baseF.apply(acum, a, b);
		}
		return acum;
	}
	
	public static List<Entry<Integer, String>> ord(Map<Integer, String> m){
		return m.entrySet().stream().sorted(Comparator.comparing(e->e.getKey())).toList();
	}

	public static void main(String[] args) {
		int a = 20, b = 5;
		Map<Integer, String> r = fRNF(a, b);
		System.out.println("Resultados Recursivo No Final:" + ord(r));
		System.out.println("---------------------------------------");
		r = iImp(a, b);
		System.out.println("Resultados iterativa imperativa:" + ord(r));
		System.out.println("---------------------------------------");
		r = iF(a, b);
		System.out.println("Resultados iterativa funcional:" + ord(r));
		System.out.println("---------------------------------------");
		r = fRF(a, b);
		System.out.println("Resultados Recursiva Final:" + ord(r));
		System.out.println("---------------------------------------");
	}

}
