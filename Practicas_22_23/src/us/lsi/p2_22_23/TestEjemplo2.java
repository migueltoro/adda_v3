package us.lsi.p2_22_23;

import java.util.List;
import java.util.function.Function;

import org.apache.commons.math3.fitting.WeightedObservedPoint;

import us.lsi.common.Pair;
import us.lsi.curvefitting.DataFile;
import us.lsi.curvefitting.Fitting;
import us.lsi.curvefitting.FittingType;
import us.lsi.curvefitting.GenData;
import us.lsi.graphics.MatPlotLib;

public class TestEjemplo2 {
	
	public static void genDataR() {
		String file = "ficheros_generados/fib_r.txt";
		Function<Integer,Long> f1 = GenData.time(t -> Ejemplo2.fibR(t));
//		Integer tMin,Integer tMax,Integer tInc,Integer numIter,Integer numIterWarmup
		GenData.tiemposEjecucion(f1,file,500,100300,500,10,10);
	}
	
	public static void genDataRM() {
		String file = "ficheros_generados/fib_rm.txt";
		Function<Integer,Long> f1 = GenData.time(t -> Ejemplo2.fibRM(t));
//		Integer tMin,Integer tMax,Integer tInc,Integer numIter,Integer numIterWarmup
		GenData.tiemposEjecucion(f1,file,500,100300,500,10,10);
	}
	
	public static void genDataIter() {
		String file = "ficheros_generados/fib_iter.txt";
		Function<Integer,Long> f1 = GenData.time(t -> Ejemplo2.fibIter(t));
//		Integer tMin,Integer tMax,Integer tInc,Integer numIter,Integer numIterWarmup
		GenData.tiemposEjecucion(f1,file,500,100300,500,10,10);
	}
	
	public static void genDataPotM() {
		String file = "ficheros_generados/fib_pm.txt";
		Function<Integer,Long> f1 = GenData.time(t -> Ejemplo2.fibonacciPotMat(t));
//		Integer tMin,Integer tMax,Integer tInc,Integer numIter,Integer numIterWarmup
		GenData.tiemposEjecucion(f1,file,500,100300,500,10,10);
	}
	
	public static void showPotMat() {
		String file = "ficheros_generados/fib_pm.txt";
		List<WeightedObservedPoint> data = DataFile.points(file);
		Pair<Function<Double, Double>, String> f = Fitting.fitCurve(data, FittingType.POWER2);
		System.out.println(f);
		MatPlotLib.show(file, f.first(), f.second());
	}
	
	public static void showIter() {
		String file = "ficheros_generados/fib_iter.txt";
		List<WeightedObservedPoint> data = DataFile.points(file);
		Pair<Function<Double, Double>, String> f = Fitting.fitCurve(data, FittingType.POWER2);
		System.out.println(f);
		MatPlotLib.show(file, f.first(), f.second());
	}
	
	public static void showCombined() {
		MatPlotLib.showCombined("Tiempos",
				List.of("ficheros_generados/fib_pm.txt","ficheros_generados/fib_pm.txt"), 
				List.of("PotenciaMatriz","Iterativa"));
	}
	

	public static void main(String[] args) {
		genDataIter();
		genDataPotM();
		showIter();
		showPotMat();
		showCombined();
	}



}
