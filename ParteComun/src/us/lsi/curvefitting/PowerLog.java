package us.lsi.curvefitting;

import java.util.List;

import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.apache.commons.math3.fitting.SimpleCurveFitter;

/**
 * @author migueltoro
 * 
 * Una curva de la forma a*n^b*(ln n)^c + d
 *
 */
public class PowerLog implements ParametricUnivariateFunction {
	
	private static PowerLog pl = null;
	
	public static PowerLog of() {
		if(pl == null) pl = new PowerLog();
		return pl;
	}
	
	public PowerLog() {
		super();
	}

	@Override
	public double[] gradient(double n, double... p) {
		Double a = p[0];
		Double b = p[1];
		Double c = p[2];
		Double a0 = Math.pow(n,b)*Math.pow(Math.log(n),c);
		Double b0 = a*Math.pow(n,b)*Math.pow(Math.log(n),c+1);
		Double c0 = a*Math.pow(n,b)*Math.log(Math.log(n))*Math.pow(Math.log(n),c);
		Double d0 = 1.;
		double[] r = {a0,b0,c0,d0};
		return r;
	}

	@Override
	public double value(double n, double... p) {
		Double a = p[0];
		Double b = p[1];
		Double c = p[2];
		Double d = p[3];
		return a*Math.pow(n,b)*Math.pow(Math.log(n),c) + d;
	}
	
	public double[] fit(List<WeightedObservedPoint> points, double[] start) {
		final SimpleCurveFitter fitter = SimpleCurveFitter.create(PowerLog.of(),start);
		return fitter.fit(points);
	}
	
	public void print(double n, double... p) {
		String r = String.format("Values: n = %.2f,a = %.2f,b = %.2f,c = %.2f,d = %.2f",n, p[0],p[1],p[2],p[3]);
		System.out.println(r);
		System.out.println("Value = "+this.value(n, p));
		double[] g = this.gradient(n, p);
		System.out.println("Gradiente = "+String.format("%.2f,%.2f,%.2f,%.2f",g[0],g[1],g[2],g[3]));
	}

}
