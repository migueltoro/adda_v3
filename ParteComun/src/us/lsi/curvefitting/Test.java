package us.lsi.curvefitting;

import java.util.List;
import java.util.function.Function;


import org.apache.commons.math3.fitting.WeightedObservedPoint;

import us.lsi.common.Pair;
import us.lsi.graphics.MatPlotLib;

public class Test {

	public static void main(String[] args) {
		List<WeightedObservedPoint> data = DataFile.points("ficheros/datos_polinomial.txt");
		Pair<Function<Double, Double>, String> f = Fitting.fitCurve(data, FittingType.POLYNOMIAL);
		System.out.println(f);
		MatPlotLib.show("ficheros/datos_polinomial.txt", f.first(), f.second());
	}

}
