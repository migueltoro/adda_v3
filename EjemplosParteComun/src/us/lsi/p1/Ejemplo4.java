package us.lsi.p1;

import java.util.stream.Stream;

import us.lsi.common.Preconditions;

public class Ejemplo4 {

	public static String ejemplo4(Long a, Integer n) {
		String res = "";
		res += String.format("Entrada: %s,%s\n", a, n);
		res += String.format("%-30s%s\n", "1. Iterativo: " , Ejemplo4.iterativo(a, n));
		res += String.format("%-30s%s\n", "2. Notación funcional: " , Ejemplo4.funcional(a, n)); 
		res += String.format("%-30s%s\n", "3. Recursivo no final: " , Ejemplo4.recursiva_no_final(a, n));
		res += String.format("%-30s%s\n", "4. Recursivo final: " , Ejemplo4.recursiva_final(a, n));
		return res;

	}

	public static Long iterativo(Long a, Integer n) {
		Preconditions.checkArgument(n >= 0);
		Long r = a;
		Long res = 1L;
		while (n > 0) {
			if (n % 2 == 1) res = res * r;
			r = r * r;
			n = n / 2;
		}
		return res;
	}

	public static Long recursiva_no_final(Long a, Integer n) {
		Preconditions.checkArgument(n >= 0);
		Long r;
		if (n == 0) r = 1L;
		else if(n % 2 == 1) r = a * recursiva_no_final(a * a, n / 2);
		else r= recursiva_no_final(a * a, n / 2);
		return r;
	}

	public static Long recursiva_final(Long a, Integer n) {
		return recursiva_final(n, a, 1L);
	}

	public static Long recursiva_final(Integer n, Long r, Long res) {
		if (n > 0) {
			if (n % 2 == 1) res = res * r;
			r = r * r;
			n = n / 2;
			res = recursiva_final(n,r,res);
		}
		return res;
	}

	public static Long funcional(Long a, Integer n) {
		R4 r = R4.of(n,a,1L);
		r = Stream.iterate(r,t->t.next())
				.filter(t -> t.n() == 0)
				.findFirst()
				.get();
		return r.res();
	}
	
	public static record R4(Integer n, Long r, Long res) {
		public static R4 of(Integer n, Long r, Long res) {
			return new R4(n, r,res);
		}
		public R4 next() {
			R4 s;
			if (n % 2 == 1) s = R4.of(n/2, r*r, res*r);
			else s = R4.of(n/2, r*r, res);
			return s;
		}
		
	}

}
