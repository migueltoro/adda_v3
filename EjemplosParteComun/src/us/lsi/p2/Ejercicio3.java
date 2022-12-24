package us.lsi.p2;

import java.util.HashMap;
import java.util.Map;

import us.lsi.common.Files2;

public class Ejercicio3 {

	public static long f_recursivo_conMemoria(int n) {
		return f_recursivo_conMemoria(n, new HashMap<>());
	}

	private static Long f_recursivo_conMemoria(int n, Map<Integer, Long> m) {
		Long res = m.get(n);
		if (res == null) {
			if (n == 0) {
				res = 2L;
			} else if (n == 1) {
				res = 4L;
			} else if (n == 2) {
				res = 6L;
			} else {
				res = 6*f_recursivo_conMemoria(n - 3, m) + 
				4*f_recursivo_conMemoria(n - 2, m) + 2*f_recursivo_conMemoria(n - 1, m);
			}
			m.put(n, res);
		}
		return res;
	}

	private static Long f_recursivo_sinMemoria(int n) {
		if (n == 0) {
			return 2L;
		} else if (n == 1) {
			return 4L;
		} else if (n == 2) {
			return 6L;
		} else {
			return 2*f_recursivo_sinMemoria(n - 1) + 
			4*f_recursivo_sinMemoria(n - 2) + 6*f_recursivo_sinMemoria(n - 3);
		}
	}

	private static Long f_iterativo(int n) {
		if (n == 0) {
			return 2L;
		} else if (n == 1) {
			return 4L;
		} else if (n == 2) {
			return 6L;
		} else {
			int i = 3;
			long res=-1, n_1 = 6, n_2 = 4, n_3 = 2;
			while (i++ <= n) {
				res = 2*n_1 + 4*n_2 + 6*n_3;
				n_3 = n_2;
				n_2 = n_1;
				n_1 = res;
			}
			return res;
		}
	}
	
	// ==================================== TESTS ====================================
	public static void main(String[] args) {
		Files2.streamFromFile("ficheros/Ejercicio3DatosEntrada.txt")
		.map(s -> Integer.parseInt(s.split("=")[1].trim()))
		.forEach(n -> test(n));
	}

	private static void test(Integer n) {
		System.out.println("Entero de entrada: " + n);
		System.out.println("Recursiva sin memoria: "+f_recursivo_sinMemoria(n));
		System.out.println("Recursiva con memoria: "+f_recursivo_conMemoria(n));
		System.out.println("Iterativa:\t\t  "+f_iterativo(n));
		System.out.println("===============================================================");
	}
}
