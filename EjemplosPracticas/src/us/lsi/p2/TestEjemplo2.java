package us.lsi.p2;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.commons.math3.fitting.WeightedObservedPoint;

import us.lsi.common.Pair;
import us.lsi.curvefitting.DataFile;
import us.lsi.curvefitting.Exponential;
import us.lsi.curvefitting.Fit;
import us.lsi.curvefitting.GenData;
import us.lsi.curvefitting.PowerLog;
import us.lsi.graphics.MatPlotLib;

public class TestEjemplo2 {

	public static void genData(Consumer<Integer> algorithm,String file,Integer tMin,Integer tMax,Integer tInc,Integer numIter,Integer numIterWarmup) {
		Function<Integer,Long> f1 = GenData.time(algorithm);
		GenData.tiemposEjecucionAritmetica(f1,file,tMin,tMax,tInc,numIter,numIterWarmup);
	}
	
	public static void genDataGeom(Consumer<Integer> algorithm,String file,Integer tMin,Integer tMax,Integer tInc,Integer numIter,Integer numIterWarmup) {
		Function<Integer,Long> f1 = GenData.time(algorithm);
		GenData.tiemposEjecucionGeometrica(f1,file,tMin,tMax,tInc,numIter,numIterWarmup);
	}
	
	public static void showPowerLog_Fixed(String file,String label,List<Pair<Integer, Double>> fixedParams) {
		List<WeightedObservedPoint> data = DataFile.points(file);
		Fit pl = PowerLog.of(fixedParams);
		pl.fit(data);
		System.out.println(pl.getExpression());
		System.out.println(pl.getEvaluation().getRMS());
		MatPlotLib.show(file, pl.getFunction(),String.format("%s = %s",label,pl.getExpression()));
	}

	public static void showPowerLog_Cons(String file,String label,List<Pair<Integer, Double>> fixedParamsvalues, Integer cp, Double minPv, Double maxPv,
			Double k) {
		List<WeightedObservedPoint> data = DataFile.points(file);
		Fit pl = PowerLog.of(fixedParamsvalues,cp,minPv,maxPv,k);
		pl.fit(data);
		System.out.println(pl.getExpression());
		System.out.println(pl.getEvaluation().getRMS());
		MatPlotLib.show(file, pl.getFunction(), String.format("%s = %s",label,pl.getExpression()));
	}

	
	public static void showExponential(String file,String label) {
		List<WeightedObservedPoint> data = DataFile.points(file);
		Fit pl = Exponential.of();
		pl.fit(data);
		System.out.println(pl.getExpression());
		System.out.println(pl.getEvaluation().getRMS());
		MatPlotLib.show(file, pl.getFunction(), String.format("%s = %s",label,pl.getExpression()));
	}
	

	public static void genDataR() {
		String file = "ficheros_generados/fib_r.txt";
		Function<Integer,Long> f1 = GenData.time(t -> Ejemplo2.fibR(t));
//		Integer tMin,Integer tMax,Integer tInc,Integer numIter,Integer numIterWarmup
//		GenData.tiemposEjecucion(f1,file,500,100300,500,10,10);
		GenData.tiemposEjecucionAritmetica(f1,file,5,35,3,10,10);
	}
	
	public static void genDataRM() {
		String file = "ficheros_generados/fib_rm.txt";
		Function<Integer,Long> f1 = GenData.time(t -> Ejemplo2.fibRM(t));
//		Integer tMin,Integer tMax,Integer tInc,Integer numIter,Integer numIterWarmup
//		GenData.tiemposEjecucion(f1,file,500,100300,500,10,10);
		GenData.tiemposEjecucionAritmetica(f1,file,500,50300,500,10,10);
	}
	
	public static void genDataIter() {
		String file = "ficheros_generados/fib_iter.txt";
		Function<Integer,Long> f1 = GenData.time(t -> Ejemplo2.fibIter(t));
//		Integer tMin,Integer tMax,Integer tInc,Integer numIter,Integer numIterWarmup
		GenData.tiemposEjecucionAritmetica(f1,file,500,100300,500,10,10);
	}
	
	public static void genDataPotM() {
		String file = "ficheros_generados/fib_pm.txt";
		Function<Integer,Long> f1 = GenData.time(t -> Ejemplo2.fibonacciPotMat(t));
//		Integer tMin,Integer tMax,Integer tInc,Integer numIter,Integer numIterWarmup
		GenData.tiemposEjecucionAritmetica(f1,file,500,100300,500,10,10);
	}
	
	public static void showPotMat() {
		String file = "ficheros_generados/fib_pm.txt";
		List<WeightedObservedPoint> data = DataFile.points(file);
		Fit pl = PowerLog.of(List.of(Pair.of(2, 1.),Pair.of(3, 0.)));
		pl.fit(data);
		System.out.println(pl.getExpression());
		System.out.println(pl.getEvaluation().getRMS());
		MatPlotLib.show(file, pl.getFunction(), pl.getExpression());
	}
	
	public static void showIter() {
		String file = "ficheros_generados/fib_iter.txt";
		List<WeightedObservedPoint> data = DataFile.points(file);
		Fit pl = PowerLog.of(List.of(Pair.of(2, 1.),Pair.of(3, 0.)));
		pl.fit(data);
		System.out.println(pl.getExpression());
		System.out.println(pl.getEvaluation().getRMS());
		MatPlotLib.show(file, pl.getFunction(), pl.getExpression());
	}
	
	public static void showRec() {
		String file = "ficheros_generados/fib_r.txt";
		List<WeightedObservedPoint> data = DataFile.points(file);
		Fit pl = Exponential.of();
		pl.fit(data);
		System.out.println(pl.getExpression());
		System.out.println(pl.getEvaluation().getRMS());
		MatPlotLib.show(file, pl.getFunction(), pl.getExpression());
	}
	
	public static void showRecM() {
		String file = "ficheros_generados/fib_rm.txt";
		List<WeightedObservedPoint> data = DataFile.points(file);
		Fit pl = PowerLog.of(List.of(Pair.of(3, 0.)),2,0.,1.,2000000.);
		pl.fit(data);
		System.out.println(pl.getExpression());
		System.out.println(pl.getEvaluation().getRMS());
		MatPlotLib.show(file, pl.getFunction(), pl.getExpression());
	}
	
	public static void showCombined() {
		MatPlotLib.showCombined("Tiempos",
				List.of("ficheros_generados/fib_r.txt","ficheros_generados/fib_rm.txt","ficheros_generados/fib_pm.txt","ficheros_generados/fib_pm.txt",
						"ficheros_generados/fib_r_d.txt","ficheros_generados/fib_rm_d.txt","ficheros_generados/fib_pm_d.txt","ficheros_generados/fib_pm_d.txt"), 
				List.of("Recursivo","RecursivoMemoria","PotenciaMatriz","Iterativa",
						"Recursivo_Double","RecursivoMemoria_Double","PotenciaMatriz_Double","Iterativa_Double"));
	}
	
	

	public static void main(String[] args) {
		
//		genData(Ejemplo2::fibR,"ficheros_generados/fib_r.txt",5,35,3,10,10);
//		genData(Ejemplo2::fibRM,"ficheros_generados/fib_rm.txt",500,50300,500,10,10);
//		genData(Ejemplo2::fibIter,"ficheros_generados/fib_iter.txt",500,100300,500,10,10);
//		genData(Ejemplo2::fibonacciPotMat,"ficheros_generados/fib_pm.txt",500,100300,500,10,10);
//		genData(Ejemplo2::fibR_Double,"ficheros_generados/fib_r_d.txt",5,35,3,10,10);
//		genData(Ejemplo2::fibRM_Double,"ficheros_generados/fib_rm_d.txt",500,50300,500,10,1000);
//		genData(Ejemplo2::fibIter_Double,"ficheros_generados/fib_iter_d.txt",500,100300,500,10,1000);
//		genData(Ejemplo2::fibonacciPotMat_Double,"ficheros_generados/fib_pm_d.txt",500,100300,500,100,1000000);
	
		genData(Ejemplo2::fibR,"ficheros_generados/fib_r.txt",5,35,3,10,10);
		genDataGeom(Ejemplo2::fibRM,"ficheros_generados/fib_rm.txt",32,50000,2,10,10);
		genDataGeom(Ejemplo2::fibIter,"ficheros_generados/fib_iter.txt",32,1000000,2,10,10);
		genDataGeom(Ejemplo2::fibonacciPotMat,"ficheros_generados/fib_pm.txt",32,1000000,2,10,10);
		genData(Ejemplo2::fibR_Double,"ficheros_generados/fib_r_d.txt",5,35,3,10,10);
		genDataGeom(Ejemplo2::fibRM_Double,"ficheros_generados/fib_rm_d.txt",32,50000,2,10,1000);
		genDataGeom(Ejemplo2::fibIter_Double,"ficheros_generados/fib_iter_d.txt",32,1000000,2,10,1000);
		genDataGeom(Ejemplo2::fibonacciPotMat_Double,"ficheros_generados/fib_pm_d.txt",32,1000000,2,100,10000000);
	
		showExponential("ficheros_generados/fib_r.txt","rec");
		showPowerLog_Cons("ficheros_generados/fib_rm.txt","rec_mem",List.of(Pair.of(3, 0.)),2,0.,1.,4000000.);
		showPowerLog_Fixed("ficheros_generados/fib_iter.txt","iter1",List.of(Pair.of(2, 0.),Pair.of(3, 0.)));
		showPowerLog_Fixed("ficheros_generados/fib_iter.txt","iter2",List.of(Pair.of(2, 1.),Pair.of(3, 0.)));
		showPowerLog_Fixed("ficheros_generados/fib_pm.txt","pm",List.of(Pair.of(2, 1.),Pair.of(3, 0.)));
		showExponential("ficheros_generados/fib_r_d.txt","rec_d");
		showPowerLog_Cons("ficheros_generados/fib_rm_d.txt","rec_mem_d",List.of(Pair.of(3, 0.)),2,0.,1.,2000000.);
		showPowerLog_Fixed("ficheros_generados/fib_iter_d.txt","iter_d1",List.of(Pair.of(2, 0.),Pair.of(3, 0.)));
		showPowerLog_Fixed("ficheros_generados/fib_iter_d.txt","iter_d2",List.of(Pair.of(2, 1.),Pair.of(3, 0.)));
		showPowerLog_Fixed("ficheros_generados/fib_pm_d.txt","pm_d1",List.of(Pair.of(2, 0.),Pair.of(3, 0.)));
		showPowerLog_Fixed("ficheros_generados/fib_pm_d.txt","pm_d2",List.of(Pair.of(2, 1.),Pair.of(3, 0.)));
		
//		genDataR();
//		showRec();
//		genDataRM();
//		showRecM();
//		genDataIter();
//		genDataPotM();
//		showIter();
//		showPotMat();

		showCombined();
	}



}
