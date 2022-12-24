package us.lsi.p2;

import java.util.HashMap;
import java.util.Map;

import us.lsi.common.Files2;
import us.lsi.common.Trio;

public class Ejercicio4 {	
	
	public static long g_recursivo_conMemoria(int a, int b, int c) {
		return g_recursivo_conMemoria(a, b, c, new HashMap<>((a+1)*(b+1)*(c+1)));
	}

	private static Long g_recursivo_conMemoria(int a, int b, int c, Map<Trio<Integer, Integer, Integer>, Long> m) {
		Long res = m.get(Trio.of(a, b, c));
		if (res == null) {
			if (a < 3 || b < 3 || c < 3) {
				res = a + b*b + 2L*c;
			} else if (a>=b && a%b == 0) {
				res = g_recursivo_conMemoria(a-1, b/2, c/2, m) + 
				g_recursivo_conMemoria(a-3, b/3, c/3, m);
			} else {
				res = g_recursivo_conMemoria(a/3, b-3, c-3, m) + 
				g_recursivo_conMemoria(a/2, b-2, c-2, m);
			}
			m.put(Trio.of(a, b, c), res);
		}
		return res;
	}

	private static long g_recursivo_sinMemoria(int a, int b, int c) {
		if (a < 3 || b < 3 || c < 3) {
			return a + b*b + 2L*c;
		} else if (a>=b && a%b==0) {
			return g_recursivo_sinMemoria(a-1, b/2, c/2) + 
			g_recursivo_sinMemoria(a-3, b/3, c/3);
		} else {
			return g_recursivo_sinMemoria(a/3, b-3, c-3) + 
			g_recursivo_sinMemoria(a/2, b-2, c-2);
		}
	}

	private static Long g_iterativo_con_Map(int a, int b, int c) {
		Map<Trio<Integer, Integer, Integer>, Long> map = new HashMap<>((a+1)*(b+1)*(c+1));
		
		Long v = null;
		for (int i = 0; i <= a; i++) {
			for (int j = 0; j <= b; j++) {
				for (int k = 0; k <= c; k++) {
					if (i < 3 || j < 3 || k < 3) {
						v = i + j*j + 2L*k;
					} else if (i>=j && i%j==0) {
						v = map.get(Trio.of(i-1, j/2, k/2)) + map.get(Trio.of(i-3, j/3, k/3));
					} else {
						v = map.get(Trio.of(i/3, j-3, k-3)) + map.get(Trio.of(i/2, j-2, k-2));
					}
					map.put(Trio.of(i, j, k), v);
				}
			}
		}
		return map.get(Trio.of(a, b, c));
	}

	// ==================================== TESTS ====================================
	public static void main(String[] args) {
		Files2.streamFromFile("ficheros/Ejercicio4DatosEntrada.txt")
		.map(s -> Trio.of(Integer.valueOf(s.split(",")[0]), 
		Integer.valueOf(s.split(",")[1]), Integer.valueOf(s.split(",")[2])))
		.forEach(p -> {
			System.out.println("(a, b, c) = (" + p.first() + ", " + p.second() + ", " + p.third() + ")");
			System.out.println("Recursiva sin memoria: " + g_recursivo_sinMemoria(p.first(), p.second(), p.third()));
			System.out.println("Recursiva con memoria: " + g_recursivo_conMemoria(p.first(), p.second(), p.third()));
			System.out.println("Iterativa:\t  " + g_iterativo_con_Map(p.first(), p.second(), p.third()));
			System.out.println("===============================================================");
		});
	}	
}
