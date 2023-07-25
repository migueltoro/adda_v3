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

public class TestEjemplo1 {
	
	private static Integer nMin = 100; // n mínimo para el cálculo de potencia
	private static Integer nMax = 100000; // n máximo para el cálculo de potencia
	private static Integer nIncr = 5100; // incremento en los valores de n del cálculo de potencia
	private static Integer nIter = 50; // número de iteraciones para cada medición de tiempo
	private static Integer nIterWarmup = 100; // número de iteraciones para warmup
	
	private static Double a = 100.;
	
	public static void genDataPr() {
		String file = "ficheros_generados/pr.txt";
		Function<Integer,Long> f1 = GenData.time(t -> Ejemplo1.potenciaIter(a,t));
//		Integer tMin,Integer tMax,Integer tInc,Integer numIter,Integer numIterWarmup
		GenData.tiemposEjecucion(f1,file,nMin,nMax,nIncr,nIter,nIterWarmup);
	}
	
	public static void genDataLin() {
		String file = "ficheros_generados/lin.txt";
		Function<Integer,Long> f1 = GenData.time(t -> Ejemplo1.potenciaR(a,t));
//		Integer tMin,Integer tMax,Integer tInc,Integer numIter,Integer numIterWarmup
		GenData.tiemposEjecucion(f1,file,nMin,nMax,nIncr,nIter,nIterWarmup);
	}
	
	public static void showPr() {
		String file = "ficheros_generados/pr.txt";
		List<WeightedObservedPoint> data = DataFile.points(file);
		Pair<Function<Double, Double>, String> f = Fitting.fitCurve(data, FittingType.POWER2);
		System.out.println(f);
		MatPlotLib.show(file, f.first(), f.second());
	}
	
	public static void showLin() {
		String file = "ficheros_generados/lin.txt";
		List<WeightedObservedPoint> data = DataFile.points(file);
		Pair<Function<Double, Double>, String> f = Fitting.fitCurve(data, FittingType.POWER2);
		System.out.println(f);
		MatPlotLib.show(file, f.first(), f.second());
	}
	
	public static void showCombined() {
		MatPlotLib.showCombined("Tiempos",
				List.of("ficheros_generados/pr.txt","ficheros_generados/lin.txt"), 
				List.of("Recursiva","Iterativa"));
	}
	

	public static void main(String[] args) {
		genDataPr();
		genDataLin();
		showPr();
		showLin();
		showCombined();
	}


}
