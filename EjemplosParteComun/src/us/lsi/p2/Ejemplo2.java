package us.lsi.p2;

import java.util.HashMap;
import java.util.Map;

import us.lsi.common.Files2;
import us.lsi.common.Pair;

public class Ejemplo2 {

	public static long g_recursivo_conMemoria(int a, int b) {
		return g_recursivo_conMemoria(a, b, new HashMap<>());
	}

	private static Long g_recursivo_conMemoria(int a, int b, Map<Pair<Integer, Integer>, Long> m) {
		Long res = m.get(Pair.of(a, b));
		if (res == null) {
			if (a < 2 && b < 2) {
				res = a + b*b + 0L;
			} else if (a < 2 || b < 2) {
				res = a*a + b + 0L;
			} else {
				res = g_recursivo_conMemoria(a / 2, b - 1, m) + g_recursivo_conMemoria(a / 3, b - 2, m)
						+ g_recursivo_conMemoria(a - 2, b / 4, m);
			}
			m.put(Pair.of(a, b), res);
		}
		return res;
	}

	private static long g_recursivo_sinMemoria(int a, int b) {
		if (a < 2 && b < 2) {
			return a + b*b + 0L;
		} else if (a < 2 || b < 2) {
			return a*a + b + 0L;
		} else {
			return g_recursivo_sinMemoria(a / 2, b - 1) + g_recursivo_sinMemoria(a / 3, b - 2)
					+ g_recursivo_sinMemoria(a - 2, b / 4);
		}
	}

	private static long g_iterativo_con_Map(int a, int b) {
		Map<Pair<Integer, Integer>, Long> res = new HashMap<>();
		for (int i = 0; i <= a; i++) {
			for (int j = 0; j <= b; j++) {
				if (i < 2 && j < 2) {
					res.put(Pair.of(i, j), i + j*j + 0L);
				} else if (i < 2 || j < 2) {
						res.put(Pair.of(i, j), i*i + j + 0L);
				} else {
					long v1 = res.get(Pair.of(i / 2, j - 1));
					long v2 = res.get(Pair.of(i / 3, j - 2));
					long v3 = res.get(Pair.of(i - 2, j / 4));
					res.put(Pair.of(i, j), v1 + v2 + v3);
				}
			}
		}
		return res.get(Pair.of(a, b));
	}

	// ==================================== TESTS ====================================
	public static void main(String[] args) {
		Files2.streamFromFile("ficheros/Ejemplo2DatosEntrada.txt")
		.map(s -> Pair.of(Integer.valueOf(s.split(",")[0]), Integer.valueOf(s.split(",")[1])))
		.forEach(p -> {
			System.out.println("(a, b) = (" + p.first() + ", " + p.second() + ")");
			System.out.println("F. Recursiva sin memoria: " + g_recursivo_sinMemoria(p.first(), p.second()));
			System.out.println("F. Recursiva con memoria: " + g_recursivo_conMemoria(p.first(), p.second()));
			System.out.println("F. Iterativa con Map:\t  " + g_iterativo_con_Map(p.first(), p.second()));
			System.out.println("F. Iterativa con array:\t  " + g_iterativo_con_array(p.first(), p.second()));
			System.out.println("===============================================================");
		});
	}
	
	
	
	// No se acepta, no se explica. Tiene que hacerse con Map como en los apuntes
	private static long g_iterativo_con_array(int a, int b) {
		long[][] res = new long[a + 1][b + 1];
		for (int i = 0; i <= a; i++) {
			for (int j = 0; j <= b; j++) {
				if (i < 2 && j < 2) {
					res[i][j] = i + j*j;
				} else if(i < 2 || j < 2) {
					res[i][j] = i*i + j; 
				} else {
					res[i][j] = res[i / 2][j - 1] + res[i / 3][j - 2] + res[i - 2][j / 4];
				}
			}
		}
		return res[a][b];
	}	
	
}
