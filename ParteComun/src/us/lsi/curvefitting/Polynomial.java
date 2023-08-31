package us.lsi.curvefitting;

import java.util.List;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem.Evaluation;

public class Polynomial {
	
	public static Polynomial of(Integer grado) {
		if(pl == null) pl = new Polynomial(grado);
		return pl;
	}

	private static Polynomial pl = null;
	private Integer grado;
	private PolynomialCurveFitter2 fitter = null;		
	private Evaluation evaluation;
	
	private Polynomial(Integer grado) {
		super();
		this.grado = grado;
		this.fitter = PolynomialCurveFitter2.create(grado);
	}

	public double[] fit(List<WeightedObservedPoint> points, double[] start) {
		double[] r = this.fitter.fit(points);
		this.evaluation = this.fitter.getProblem(points).evaluate(RealVectors.toRealVector(r));
		return r;
	}
	
	public Evaluation getEvaluation() {
		return evaluation;
	}

	public Integer getGrado() {
		return grado;
	}
	
	public double value(double n, double... p) {
		return new PolynomialFunction(p).value(n);
	}
	
}
