package us.lsi.curvefitting;

import java.util.List;

import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem.Evaluation;

/**
 * @author migueltoro
 * 
 * Una curva de la forma a*(ln n) + b
 *
 */
public class Log implements ParametricUnivariateFunction {
	
	private static Log pl = null;
	private SimpleCurveFitter2 fitter = null;		
	private Evaluation evaluation;
	
	public static Log of() {
		if(pl == null) pl = new Log();
		return pl;
	}
	
	public Log() {
		super();
		this.evaluation = null;
		this.fitter = SimpleCurveFitter2.create(this,new double[] { 1., 1.});
	}

	@Override
	public double[] gradient(double n, double... p) {
		Double a0 = Math.log(n);
		Double b0 = 1.;
		double[] r = {a0,b0};
		return r;
	}

	@Override
	public double value(double n, double... p) {
		Double a = p[0];
		Double b = p[1];
		return a*Math.log(n) + b;
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
		String r = String.format("Values: n = %.2f,a = %.2f,b = %.2f,c = %.2f,d = %.2f",n, p[0],p[1],p[2],p[3]);
		System.out.println(r);
		System.out.println("Value = "+this.value(n, p));
		double[] g = this.gradient(n, p);
		System.out.println("Gradiente = "+String.format("%.2f,%.2f,%.2f,%.2f",g[0],g[1],g[2],g[3]));
	}

}

