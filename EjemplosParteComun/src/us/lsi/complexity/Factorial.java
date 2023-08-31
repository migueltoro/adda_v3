package us.lsi.complexity;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.commons.math3.Field;
import org.apache.commons.math3.FieldElement;
import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem.Evaluation;
import org.apache.commons.math3.fraction.BigFractionField;

import us.lsi.common.Trio;
import us.lsi.curvefitting.DataFile;
import us.lsi.curvefitting.Fitting;
import us.lsi.curvefitting.FittingType;
import us.lsi.curvefitting.GenData;
import us.lsi.graphics.CanvasPlot;
import us.lsi.graphics.MatPlotLib;

public class Factorial {
	
	public static <E extends FieldElement<E>> E factorialI(Integer n, Field<E> field){
		E r = field.getOne();
		Integer i = n;
		while(i>0) {
			r = r.multiply(i);
			i = i-1;
		}
		return r;
	}
	
	public static <E extends FieldElement<E>> E factorialR(Integer n, Field<E> field){
		return factorialR(n, n, field);
	}
	
	public static <E extends FieldElement<E>> E factorialR(Integer i, Integer n, Field<E> field){
		E r; 
		if (i > 0) {
			r = factorialR(i-1,n,field).multiply(i);
		} else 
			r = field.getOne();
		return r;
	}
	
	public static void test1() {
		String file = "ficheros/factorialI.txt";
		Consumer<Integer> c1 = t->Factorial.factorialI(t,BigFractionField.getInstance());
		Function<Integer,Long> f1 = GenData.time(c1);
		GenData.tiemposEjecucion(f1,file,500,50000,500,1,1);
	}
	
	public static void test2() {
		String file = "ficheros/factorialR.txt";
		Consumer<Integer> c1 = t->Factorial.factorialR(t,BigFractionField.getInstance());
		Function<Integer,Long> f1 = GenData.time(c1);
		GenData.tiemposEjecucion(f1,file,500,10000,500,1,1);
	}
	
	public static void test3() {
		String file = "ficheros/factorialI.txt";
		List<WeightedObservedPoint> data = DataFile.points(file);
		Trio<Function<Double, Double>,String,Evaluation> f = Fitting.fitCurve(data, FittingType.POWERLOG3);
		System.out.println(f);
		MatPlotLib.show(file, f.first(), f.second());
	}
	
	public static void test4() {
		String file = "ficheros/factorialR.txt";
		List<WeightedObservedPoint> data = DataFile.points(file);
		Trio<Function<Double, Double>,String,Evaluation> f = Fitting.fitCurve(data, FittingType.POWERLOG3);
		System.out.println(f);
		MatPlotLib.show(file, f.first(), f.second());
	}
	
	public static void test5() {
		MatPlotLib.showCombined("Tiempos",
				List.of("ficheros/factorialI.txt","ficheros/factorialR.txt"), 
				List.of("Iterativo","Recursivo"));
	}
	
	public static void test6() {
		String file = "ficheros/factorialR.txt";
		List<WeightedObservedPoint> data = DataFile.points(file);
		Trio<Function<Double, Double>,String,Evaluation> f = Fitting.fitCurve(data, FittingType.POWERLOG3);
		System.out.println(f);
		CanvasPlot.show(file, f.first(), f.second());
	}
	
	public static void test7() {
		CanvasPlot.showCombined("Tiempos",
				List.of("ficheros/factorialI.txt","ficheros/factorialR.txt"), 
				List.of("Iterativo","Recursivo"));
	}

	public static void main(String[] args) {
		test4();
	}

}
