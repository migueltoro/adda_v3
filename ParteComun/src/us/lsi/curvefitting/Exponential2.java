package us.lsi.curvefitting;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.fitting.SimpleCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoint;

/**
 * @author migueltoro
 *
 * Función de la forma a*b^n + c
 */
public class Exponential2 implements ParametricUnivariateFunction {
	
	private static Exponential2 pl = null;
	
	public static Exponential2 of() {
		if(pl == null) pl = new Exponential2();
		return pl;
	}

	@Override
	public double[] gradient(double n, double... p) {
		Double a = p[0];
		Double b = p[1];
		Double a0 = Math.pow(b,n);
		Double b0 = a*n*Math.pow(b,n-1);
		Double c0 = 1.;
		double[] r = {a0,b0,c0};
		return r;
	}

	@Override
	public double value(double n, double... p) {
		Double a = p[0];
		Double b = p[1];
		Double c = p[2];
		return a*Math.pow(b,n) + c;
	}
	
	public double[] fit(List<WeightedObservedPoint> points, double[] start) {
		final SimpleCurveFitter fitter = SimpleCurveFitter.create(Exponential2.of(),start);
		return fitter.fit(points);
	}
	
	public void print(double n, double... p) {
		String r = String.format("Values: n = %.2f,p = %s",n,Arrays.stream(p).boxed()
				.map(x->String.format("%.2f", x)).collect(Collectors.joining(",")));
		System.out.println(r);
		System.out.println("Value = "+this.value(n, p));
		double[] g = this.gradient(n, p);
		System.out.println("Gradiente = "+String.format("%s",Arrays.stream(g).boxed()
				.map(x->String.format("%.2f", x)).collect(Collectors.joining(","))));
	}

}

