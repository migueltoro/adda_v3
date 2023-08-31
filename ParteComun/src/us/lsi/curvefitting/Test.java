package us.lsi.curvefitting;

import java.util.List;
import java.util.function.Function;


import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem.Evaluation;

import us.lsi.common.Trio;
import us.lsi.graphics.MatPlotLib;

public class Test {

	public static void main(String[] args) {
		List<WeightedObservedPoint> data = DataFile.points("ficheros/datos_polinomial.txt");
		Trio<Function<Double, Double>, String,Evaluation> f = Fitting.fitCurve(data, FittingType.POLYNOMIAL);
		System.out.println(f);
		MatPlotLib.show("ficheros/datos_polinomial.txt", f.first(), f.second());
	}

}
