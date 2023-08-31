package us.lsi.complexity;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem.Evaluation;

import us.lsi.common.Files2;
import us.lsi.common.Trio;
import us.lsi.curvefitting.DataFile;
import us.lsi.curvefitting.Fitting;
import us.lsi.curvefitting.FittingType;
import us.lsi.graphics.MatPlotLib;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Recurrence {
	
	/**
	 * Estimación del orden de complejidad de la solución de la recurrencia f(n)=f(n/2+f(n/3)-f(n/6)+1
	 * 
	 * @param n
	 * @param file
	 * @param p
	 * @return
	 */
	public static Integer recurrence(Long n,String file,Predicate<Map.Entry<Long,Integer>> p) {
		Map<Long,Integer> mem = new HashMap<>();
		var r = recurrence(n, mem);
		System.out.println(mem);
		Stream<String> st = mem.entrySet().stream()
				.sorted(Comparator.comparing(e->e.getKey()))
				.filter(p)
				.map(e->String.format("%d,%d",e.getKey(),e.getValue()));	
		Files2.toFile(st,file);
		return r;
	}
	
	public static Integer recurrence(Long n, Map<Long,Integer> mem) {
		Integer r = null;
		if(mem.containsKey(n))
			r = mem.get(n);
		else if(n<3) {
			r = 1;
			mem.put(n, r);
		}else if(n<6) {
			r = 2;
			mem.put(n, r);
		} else {
			r = recurrence(n/2,mem)+recurrence(n/3,mem)-recurrence(n/6,mem)+1;
			mem.put(n, r);
		}
		return r;
	}
	
	public static void test1(Long n,String file,Predicate<Map.Entry<Long,Integer>> p) {		
		System.out.println(Recurrence.recurrence(n,file,p));
	}
	
	
	public static void test2(String file) {
		List<WeightedObservedPoint> data = DataFile.points(file);
		Trio<Function<Double, Double>,String,Evaluation> f = Fitting.fitCurve(data, FittingType.LOG3);
		System.out.println(f.second());
		System.out.println(f.third().getRMS());
	}
	
	public static void test21(String file) {
		List<WeightedObservedPoint> data = DataFile.points(file);
		Trio<Function<Double, Double>,String,Evaluation> f = Fitting.fitCurve(data, FittingType.LOG3);
		System.out.println(f.second());
		System.out.println(f.third().getRMS());
	}
	
	public static void test3(String file,Trio<Function<Double, Double>,String,Evaluation> f) {
		MatPlotLib.show(file, f.first(), f.second());
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "us"));
		String file = "ficheros/recurrencia3.txt";
		test1(10000L,file,e->e.getKey()>1000);
//		test2(file);
//		test21(file);
	}

}
