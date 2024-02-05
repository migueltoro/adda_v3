package us.lsi.enero_2023.e1;

import java.util.stream.Stream;

public class Transformacion {
	
	public static String fRNF(Integer a, Integer b, Integer c, String d) {
		String res;
		if (a < 3 && b < 3) {
			res = c.toString(); //base
		} else if (a<3 && b<=3) {
			res = "~" + fRNF(a - 1, b - 1, c + 4, d); //"~" + acum (1)
		} else if (a>=3 && b<3) {
			res = "-" + c + "-" + fRNF(a - 2, b - 1, c, d + "x"); // "-" + c + "-" + acum (2)
		} else {
			res = "/" + d + "/" + fRNF(a - 1, b - 2, c + 1, d); //"/" + d + "/" + acum (3)
		}
		return res;
	}
	
	public static String fRF(Integer a, Integer b, Integer c, String d) {
		return fRF(a, b, c, d, "");
	}

	public static String fRF(Integer a, Integer b, Integer c, String d, String acum) {
		String res;
		if (a < 3 && b < 3) {
			res = acum + c.toString(); //base
		} else if (a<3 && b<=3) {
			res = fRF(a - 1, b - 1, c + 4, d, acum + "~"); // (1)
		} else if (a>=3 && b<3) {
			res = fRF(a - 2, b - 1, c, d + "x", acum + "-" + c + "-"); //2
		} else {
			res = fRF(a - 1, b - 2, c + 1, d, acum + "/" + d + "/"); //3
		}
		return res;
	}
	
	public static String fIImp(Integer a, Integer b, Integer c, String d) {
		String res = "";
		while (!(a < 3 && b < 3)) {
			if (a<3 && b<=3) {
				res += "~"; // (1)
				a = a - 1;
				b = b - 1;
				c = c + 4;
			} else if (a>=3 && b<3) {
				res += "-" + c + "-"; // (2)
				a = a - 2;
				b = b - 1;
				d = d + "x";
			} else {
				res += "/" + d + "/"; // (3)
				a = a - 1;
				b = b - 2;
				c = c + 1;
			}
		}
		res += c.toString(); //base
		return res;
	}
	
	public record Tupla(Integer a, Integer b, Integer c, String d, String res) {
		
		public static Tupla of(Integer a, Integer b, Integer c, String d, String res) {
			return new Tupla(a, b, c, d, res);
		}

		public Tupla next() {
			if (a<3 && b<=3) {
				return Tupla.of(a - 1, b - 1, c + 4, d, res + "~"); // (1)
			} else if (a>=3 && b<3) {
				return Tupla.of(a - 2, b - 1, c, d + "x", res + "-" + c + "-"); // (2)
			} else {
				return Tupla.of(a - 1, b - 2, c + 1, d, res + "/" + d + "/"); // (3)
			}
		}

		public boolean isCaseBase() {
			return a < 3 && b < 3;
		}
	}

	public static String fIF(Integer a, Integer b, Integer c, String d) {
		Tupla tupla = Stream.iterate(Tupla.of(a, b, c, d, ""), t -> t.next())
				.filter(t -> t.isCaseBase())
				.findFirst()
				.get();
		return tupla.res() + tupla.c().toString(); //base
	}

	public static void main(String[] args) {
		Integer a=10; Integer b=5; Integer c=7; String d="AA";
		System.out.println(fRNF(a,b,c,d));
		System.out.println(fRF(a,b,c,d));
		System.out.println(fIImp(a,b,c,d));
		System.out.println(fIF(a,b,c,d));
	}

}
