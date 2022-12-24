package us.lsi.curvefitting;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class GenData {
	
	public static Function<Integer,Long> time(Consumer<Integer> algorithm){
		return t-> {
		Long t0 = System.nanoTime();
		algorithm.accept(t);
		Long t1 = System.nanoTime(); 
		return t1-t0;
		};
	}
	
	public static Function<Integer,Long> time(Consumer<Integer> preparation, Consumer<Integer> algorithm){
		return t-> {
		preparation.accept(t);
		Long t0 = System.nanoTime();
		algorithm.accept(t);
		Long t1 = System.nanoTime(); 
		return t1-t0;
		};
	}

	/**
	 * @param algorithm Una función que calcula el timepo de ejecución de un algoritmo de tamaño t
	 * @param ficheroTiempos El fichero de salida
	 * @param tMin Mínimo valor del tamaño
	 * @param tMax Máximo valor del tamaño
	 * @param tInc Incremento del tamaño
	 * @param numIter Número de iteraciones para cada tamaño
	 * @param numIterWarmup Número de iteraciones de warmup
	 */
	public static void tiemposEjecucion(Function<Integer,Long> algorithm,String ficheroTiempos,
			Integer tMin,Integer tMax,Integer tInc,Integer numIter,Integer numIterWarmup) {
		Map<Integer, Double> tiempos = new HashMap<>();
		for (int t = tMin; t < tMax; t += tInc) {
			for (int i = 0; i < numIterWarmup; i++) {
				algorithm.apply(t);
			}
			Long[] res = new Long[numIter];
			for (int z = 0; z < numIter; z++) {
				res[z] = algorithm.apply(t);
			}
			Double m = Arrays.stream(res).mapToLong(x -> x).average().getAsDouble();
			tiempos.put(t, m);
		}
		System.out.println(tiempos);
		DataFile.saveData(ficheroTiempos, tiempos);
	}
}
