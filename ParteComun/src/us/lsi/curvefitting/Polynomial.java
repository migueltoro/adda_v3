package us.lsi.curvefitting;

import java.util.List;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoint;

public class Polynomial {
	
	public static Polynomial of(Integer grado) {
		return new Polynomial(grado);
	}

	private Integer grado;
	
	private Polynomial(Integer grado) {
		super();
		this.grado = grado;
	}

	public double[] fit(List<WeightedObservedPoint> points, double[] start) {
		PolynomialCurveFitter fitter = PolynomialCurveFitter.create(grado);
		return fitter.fit(points);
	}
	
}
