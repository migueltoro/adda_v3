package us.lsi.complexity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem.Evaluation;

import us.lsi.recursivos.problemasdelistas.ProblemasDeListas;
import us.lsi.common.Trio;
import us.lsi.curvefitting.DataFile;
import us.lsi.curvefitting.Fitting;
import us.lsi.curvefitting.FittingType;
import us.lsi.curvefitting.GenData;
import us.lsi.graphics.CanvasPlot;
import us.lsi.graphics.MatPlotLib;

public class QuickSort {
	
	private static Random rr = new Random(System.nanoTime());
	private static List<Integer> list;
	
	public static void generaListaEnteros(Integer t) {
		List <Integer> ls = new ArrayList<Integer>();
		for (int i=0;i<t;i++) {
			ls.add(0+rr.nextInt(1000000));
		}
		list = ls;
	}
	
	private static Consumer<Integer> pre = t -> generaListaEnteros(t);
	private static Consumer<Integer> time = t -> {
		ProblemasDeListas.umbral = 50;
		ProblemasDeListas.quickSort(list,Comparator.naturalOrder());
	};
	
	/**
	 * GenData Param
	 * @param algorithm Una función que calcula el timepo de ejecución de un algoritmo de tamaño t
	 * @param ficheroTiempos El fichero de salida
	 * @param tMin Mínimo valor del tamaño
	 * @param tMax Máximo valor del tamaño
	 * @param tInc Incremento del tamaño
	 * @param numIter Número de iteraciones para cada tamaño
	 * @param numIterWarmup Número de iteraciones de warmup
	 */
	public static void test1(String file) {		
		Function<Integer,Long> f1 = GenData.time(pre,time);
		GenData.tiemposEjecucion(f1,file,50,50000,500,30,5);
	}
	
	public static void test2(String file) {		
		List<WeightedObservedPoint> data = DataFile.points(file);
		Trio<Function<Double, Double>,String,Evaluation> f = Fitting.fitCurve(data, FittingType.POWERLOG);
		System.out.println(f);
		MatPlotLib.show(file, f.first(), f.second());
	}
	
	public static void test3() {
		MatPlotLib.showCombined("Tiempos",
				List.of("ficheros/quickSort5.txt","ficheros/quickSort20.txt", "ficheros/quickSort50.txt"), 
				List.of("Umbral 5","Umbral 20","Umbral 50"));
	}
	
	public static void test4(String file) {
		List<WeightedObservedPoint> data = DataFile.points(file);
		Trio<Function<Double, Double>,String,Evaluation> f = Fitting.fitCurve(data, FittingType.POWERLOG2);
		System.out.println(f);
		CanvasPlot.show(file, f.first(), f.second());
	}
	
	public static void test5() {
		CanvasPlot.showCombined("Tiempos",
				List.of("ficheros/quickSort50.txt","ficheros/quickSort5.txt","ficheros/quickSort20.txt"), 
				List.of("Umbral 50","Umbral 5","Umbral 20"));
	}

	public static void main(String[] args) {
		String file = "ficheros/quickSort50.txt";
//		test1();
		test2(file);
	}

}
