package us.lsi.curvefitting;

import java.util.List;

import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.apache.commons.math3.fitting.SimpleCurveFitter;

/**
 * @author migueltoro
 * 
 * Una curva de la forma a*n*(ln n) + b
 *
 */
public class NLogN implements ParametricUnivariateFunction {
	
	private static NLogN pl = null;
	
	public static NLogN of() {
		if(pl == null) pl = new NLogN();
		return pl;
	}
	
	public NLogN() {
		super();
	}

	@Override
	public double[] gradient(double n, double... p) {
		Double a0 = n*Math.log(n);
		Double b0 = 1.;
		double[] r = {a0,b0};
		return r;
	}

	@Override
	public double value(double n, double... p) {
		Double a = p[0];
		Double b = p[1];
		return a*n*Math.log(n) + b;
	}
	
	public double[] fit(List<WeightedObservedPoint> points, double[] start) {
		final SimpleCurveFitter fitter = SimpleCurveFitter.create(NLogN.of(),start);
		return fitter.fit(points);
	}
	
	public void print(double n, double... p) {
		String r = String.format("Values: n = %.2f,a = %.2f,b = %.2f",n, p[0],p[1]);
		System.out.println(r);
		System.out.println("Value = "+this.value(n, p));
		double[] g = this.gradient(n, p);
		System.out.println("Gradiente = "+String.format("%.2f,%.2f",g[0],g[1]));
	}

}

