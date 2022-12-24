package us.lsi.curvefitting;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.commons.math3.fitting.WeightedObservedPoint;

public class TestPolynomial {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "us"));
		List<WeightedObservedPoint> points = DataFile.points("ficheros/datos_polinomial.txt");
		double[] start = {1.,1.,1.,1.};		
		final double[] r = Polynomial.of(3).fit(points,start);
		System.out.println(String.format("Solutions = %s",Arrays.stream(r).boxed()
				.map(x->String.format("%.2f", x)).collect(Collectors.joining(","))));
	}

}
