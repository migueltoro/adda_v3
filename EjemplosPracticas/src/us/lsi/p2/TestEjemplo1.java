package us.lsi.p2;


import java.util.List;
import java.util.function.Function;

import org.apache.commons.math3.fitting.WeightedObservedPoint;

import us.lsi.common.Pair;
import us.lsi.curvefitting.DataFile;
import us.lsi.curvefitting.Fit;
import us.lsi.curvefitting.GenData;
import us.lsi.curvefitting.PowerLog;
import us.lsi.graphics.MatPlotLib;

import us.lsi.iterativorecursivos.IterativosyRecursivosSimples;

public class TestEjemplo1 {
	
	private static Integer nMin = 100; // n mínimo para el cálculo de potencia
	private static Integer nMax = 100000; // n máximo para el cálculo de potencia
//	private static Integer nIncr = 5100; // incremento en los valores de n del cálculo de potencia
	private static Integer nIncr = 3330; // incremento en los valores de n del cálculo de potencia
	private static Integer nIter = 50; // número de iteraciones para cada medición de tiempo
//	private static Integer nIterWarmup = 100; // número de iteraciones para warmup
	private static Integer nIterWarmup = 10000; // número de iteraciones para warmup
	
//	private static Double a = 100.;
	private static Double a = 3.;
	
	public static void genDataPr() {
		String file = "ficheros_generados/pr.txt";
		Function<Integer,Long> f1 = GenData.time(t -> Ejemplo1.potenciaR(a,t));
//		Integer tMin,Integer tMax,Integer tInc,Integer numIter,Integer numIterWarmup
		GenData.tiemposEjecucionAritmetica(f1,file,nMin,nMax,nIncr,nIter,nIterWarmup);
	}
	
	public static void genDataLin() {
		String file = "ficheros_generados/lin.txt";
		Function<Integer,Long> f1 = GenData.time(t -> Ejemplo1.potenciaIter(a,t));
//		Integer tMin,Integer tMax,Integer tInc,Integer numIter,Integer numIterWarmup
		GenData.tiemposEjecucionAritmetica(f1,file,nMin,nMax,nIncr,nIter,nIterWarmup);
	}
	
	
	
	public static void genDataLog() {
		String file = "ficheros_generados/log.txt";
		Function<Integer,Long> f1 = GenData.time(t -> IterativosyRecursivosSimples.pot(a.longValue(),t));
//		Integer tMin,Integer tMax,Integer tInc,Integer numIter,Integer numIterWarmup
		GenData.tiemposEjecucionAritmetica(f1,file,nMin,nMax,nIncr,nIter,nIterWarmup);
	}
	
	
	
	
	public static void showPr() {
		String file = "ficheros_generados/pr.txt";
		List<WeightedObservedPoint> data = DataFile.points(file);
		Fit pl = PowerLog.of(List.of(Pair.of(2, 0.),Pair.of(3, 0.)));
		pl.fit(data);
		System.out.println(pl.getExpression());
		System.out.println(pl.getEvaluation().getRMS());
		MatPlotLib.show(file, pl.getFunction(), pl.getExpression());
	}
	
	public static void showLin() {
		String file = "ficheros_generados/lin.txt";
		List<WeightedObservedPoint> data = DataFile.points(file);
		Fit pl = PowerLog.of(List.of(Pair.of(2, 0.),Pair.of(3, 0.)));
		pl.fit(data);
		System.out.println(pl.getExpression());
		System.out.println(pl.getEvaluation().getRMS());
		MatPlotLib.show(file, pl.getFunction(), pl.getExpression());
	}
	
	
	public static void showLog() {
		String file = "ficheros_generados/log.txt";
		List<WeightedObservedPoint> data = DataFile.points(file);
		Fit pl = PowerLog.of(List.of(Pair.of(1, 0.),Pair.of(2, 1.),Pair.of(3, 0.)));
		pl.fit(data);
		System.out.println(pl.getExpression());
		System.out.println(pl.getEvaluation().getRMS());
		MatPlotLib.show(file, pl.getFunction(), pl.getExpression());
	}
	
	
	public static void showCombined() {
		MatPlotLib.showCombined("Tiempos",
				List.of("ficheros_generados/pr.txt","ficheros_generados/lin.txt","ficheros_generados/log.txt"), 
				List.of("Recursiva","Iterativa","log"));
//		List.of("ficheros_generados/pr.txt","ficheros_generados/lin.txt"), 
//		List.of("Recursiva","Iterativa"));
	}
	

	public static void main(String[] args) {
		genDataPr();
		genDataLin();
		
		genDataLog();
		
		showPr();
		showLin();
		
		showLog();
		
		showCombined();
	}


}
