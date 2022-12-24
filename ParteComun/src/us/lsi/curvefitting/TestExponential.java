package us.lsi.curvefitting;

import java.util.List;
import java.util.Locale;

import org.apache.commons.math3.fitting.WeightedObservedPoint;

public class TestExponential {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "us"));
		double[] start = {1.,1.,1.,1.};
		Exponential.of().print(30,start);
		List<WeightedObservedPoint> points = DataFile.points("ficheros/datos_exponential.txt");		
		final double[] s_coeff = Exponential.of().fit(points,start);
		System.out.println(String.format("Solutions = a = %.2f,b = %.2f,c = %.2f,d = %.2f",s_coeff[0],s_coeff[1],s_coeff[2],s_coeff[3]));
	}

}
