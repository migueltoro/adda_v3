package us.lsi.p1;

import java.util.stream.Stream;

public class Ejercicio5 {

	public static String ejercicio5(Integer x, Integer y, Integer z) {
		String res = "";
		res += String.format("Entrada: %s, %s, %s\n",x,y,z);
		res += "1. Iterativo: " + Ejercicio5.iterativo(x, y, z) + "\n";
		res += "2. Recursivo no final: " + Ejercicio5.recursivo_no_final(x, y, z) + "\n";
		res += "3. Recursivo final: " + Ejercicio5.recursivo_final(x, y, z) + "\n";
		res += "4. Notación funcional: " + Ejercicio5.funcional(x, y, z) + "\n";
		return res;
	}
	
	public static String solucionBase(Integer a, Integer b, Integer c) {
		String r ="";
		if (a < 3 && b < 3 && c < 3) {
			r = String.format("(%d)",a * b * c);
		} else if (a < 5 || b < 5 || c < 5) {
			r = String.format("(%d)",a + b + c);
		} 
		return r;
	}
	
	public static Boolean esCasoBase(Integer a, Integer b, Integer c) {
		Boolean caso1 = a < 3 && b < 3 && c < 3;
		Boolean caso2 = a < 5 || b < 5 || c < 5;
		return caso1 || caso2;
	}

	public static String iterativo(Integer a, Integer b, Integer c) {
		String ac = "";
		while(!esCasoBase(a,b,c)) {
			if (a % 2 == 0 && b % 2 == 0 && c % 2 == 0) {	
				a = a / 2; b = b - 2; c = c / 2; ac  = String.format("%s%d",ac,a * b * c);
			} else {
				a = a / 3; b = b - 3; c = c / 3; ac = String.format("%s%d",ac,a + b + c);
			}
		}
		return ac+solucionBase(a,b,c);
	}
	
	

	public static String recursivo_no_final(Integer a, Integer b, Integer c) {
		String r;
		if(esCasoBase(a,b,c)) {
			r = solucionBase(a,b,c);
		} else if (a % 2 == 0 && b % 2 == 0 && c % 2 == 0) {
			r = String.format("%d",a * b * c)+recursivo_no_final(a / 2, b - 2, c / 2);
		} else {
			r = String.format("%d",a + b + c)+recursivo_no_final(a / 3, b - 3, c / 3);
		}
		return r;
	}

	public static String recursivo_final(Integer a, Integer b, Integer c) {
		return recursivo_final(a, b, c, "");
	}

	public static String recursivo_final(Integer a, Integer b, Integer c, String ac) {
		if(esCasoBase(a,b,c)) {
			ac = ac+solucionBase(a,b,c);
		} else {
			if (a % 2 == 0 && b % 2 == 0 && c % 2 == 0) {	
				ac  = ac+String.format("%d",a * b * c); a = a / 2; b = b - 2; c = c / 2; 
			} else {
				ac = ac+String.format("%d",a + b + c); a = a / 3; b = b - 3; c = c / 3; 
			}
			ac =recursivo_final(a, b, c, ac);
		}
		return ac;
	}
	
	public static record T4(Integer a, Integer b, Integer c, String ac) {

		public static T4 of(Integer a, Integer b, Integer c, String ac) {
			return new T4(a, b, c, ac);
		}

		public Boolean esCasoBase() {
			Boolean caso1 = a() < 3 && b() < 3 && c() < 3;
			Boolean caso2 = a() < 5 || b() < 5 || c() < 5;
			return caso1 || caso2;
		}

		public T4 siguiente() {
			Integer a1=a(),b1=b(),c1=c(); String ac1= ac();
			if (a1 % 2 == 0 && b1 % 2 == 0 && c1 % 2 == 0) {	
				a1 = a1 / 2; b1 = b1 - 2; c1 = c1 / 2; ac1  = ac1+String.format("%d",a1 * b1 * c1);
			} else {
				a1 = a1 / 3; b1 = b1 - 3; c1 = c1 / 3; ac1 = ac1+String.format("%d",a1 + b1 + c1);
			}
			return T4.of(a1,b1,c1,ac1);
		}
	}

	public static String funcional(Integer a, Integer b, Integer c) {

		T4 res = Stream.iterate(T4.of(a, b, c, ""), t -> t.siguiente())
				.filter(p -> p.esCasoBase())
				.findFirst()
				.get();
		return res.ac() + solucionBase(res.a(), res.b(), res.c());

	}

}

