package us.lsi.curvefitting;

import java.util.List;

import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem.Evaluation;

/**
 * @author migueltoro
 * 
 * Una curva de la forma a*n^b*(ln n)
 *
 */
public class PowerLog3 implements ParametricUnivariateFunction {
	
	private static PowerLog3 pl = null;
	private SimpleCurveFitter2 fitter = null;		
	private Evaluation evaluation;
	
	public static PowerLog3 of() {
		if(pl == null) pl = new PowerLog3();
		return pl;
	}
	
	public PowerLog3() {
		super();
		this.evaluation = null;
		this.fitter = SimpleCurveFitter2.create(this,new double[] { 1.,1.});
	}

	@Override
	public double[] gradient(double n, double... p) {
		Double a = p[0];
		Double b = p[1];
		Double a0 = Math.pow(n,b)*Math.log(n);
		Double b0 = a*Math.pow(n,b)*Math.pow(Math.log(n),2);
		double[] r = {a0,b0};
		return r;
	}

	@Override
	public double value(double n, double... p) {
		Double a = p[0];
		Double b = p[1];
		return a*Math.pow(n,b)*Math.log(n);
	}
	
	public double[] fit(List<WeightedObservedPoint> points, double[] start) {
		double[] r = this.fitter.fit(points);
		this.evaluation = this.fitter.getProblem(points).evaluate(RealVectors.toRealVector(r));
		return r;
	}
	
	public Evaluation getEvaluation() {
		return evaluation;
	}
	
	public void print(double n, double... p) {
		String r = String.format("Values: n = %.2f,a = %.2f,b = %.2f,c = %.2f",n, p[0],p[1],p[2]);
		System.out.println(r);
		System.out.println("Value = "+this.value(n, p));
		double[] g = this.gradient(n, p);
		System.out.println("Gradiente = "+String.format("%.2f,%.2f,%.2f",g[0],g[1],g[2]));
	}

}
