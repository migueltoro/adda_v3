package us.lsi.curvefitting;


import java.util.List;
import java.util.function.Function;

import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import us.lsi.common.Pair;


public class Fitting {

	// Este método devuelve un par: función para obtener puntos de la curva de
	// ajuste, y String para mostrar coeficientes del ajuste
	public static Pair<Function<Double, Double>, String> fitCurve(
			List<WeightedObservedPoint> points,FittingType tipoAjuste) {

		// Curva de ajuste
		final double[] coeff;
		String ajusteString = null;
		Function<Double, Double> f = null;

		switch (tipoAjuste) {
		case LINEAL: // a + n*b
			// Cálculo de coeficientes
			coeff = Polynomial.of(1).fit(points, new double[] { 1., 1., 1., 1. });
			System.out.println(String.format("Solutions = a = %.2f,b = %.3g", coeff[0], coeff[1]));
			// Título de la gráfica con los coeficientes
			ajusteString = String.format("%.2f + n*%.3g", coeff[0], coeff[1]);
			f = x -> coeff[0] + coeff[1] * x;
			break;
		case POWERLOG: // a*n^b*(ln n)^c + d
			coeff = PowerLog.of().fit(points, new double[] { 1., 1., 1., 1. });
			System.out.println(String.format("Solutions = a = %.2f,b = %.2f,c = %.2f,d = %.2f", coeff[0], coeff[1],
					coeff[2], coeff[3]));
			ajusteString = String.format("%.2f * n^%.2f * (ln n)^%.2f + %.2f", coeff[0], coeff[1], coeff[2], coeff[3]);
			f = x -> PowerLog.of().value(x, coeff);
			break;		
		case POWERLOG2: // a*n^b*(ln n)^c
			coeff = PowerLog2.of().fit(points, new double[] {1., 1., 1.});
			System.out.println(String.format("Solutions = a = %.2f,b = %.2f,c = %.2f", coeff[0], coeff[1],
					coeff[2]));
			ajusteString = String.format("%.2f * n^%.2f * (ln n)^%.2f", coeff[0], coeff[1], coeff[2]);
			f = x -> PowerLog2.of().value(x, coeff);
			break;
		case POWERLOG3: // a*n^b*(ln n)
			coeff = PowerLog3.of().fit(points, new double[] {1., 1.});
			System.out.println(String.format("Solutions = a = %.2f,b = %.2f", coeff[0], coeff[1]));
			ajusteString = String.format("%.2f * n^%.2f * (ln n)", coeff[0], coeff[1]);
			f = x -> PowerLog3.of().value(x, coeff);
			break;
		case POLYNOMIAL:// a*n^b + ... + d
			final double[] coeff2 = Polynomial.of(3).fit(points, new double[] { 1., 1., 1., 1. });
			System.out.println(String.format("Solutions = a = %.2f,b = %.2f,c = %.2f,d = %.2f", coeff2[0], coeff2[1],
					coeff2[2], coeff2[3]));
			ajusteString = String.format("%.2f * x^3 + %.2f x^2 + %.2f * x + %.2f", coeff2[3], coeff2[2], coeff2[1],
					coeff2[0]);
			f = x -> new PolynomialFunction(coeff2).value(x);
			break;
		case POWER: // a*n^b + c
			coeff = Power.of().fit(points, new double[] { 1., 1., 1. });
			System.out.println(String.format("Solutions = a = %.2f,b = %.2f,c = %.2f", coeff[0], coeff[1], coeff[2]));
			ajusteString = String.format("%.2f * n^%.2f + %.2f", coeff[0], coeff[1], coeff[2]);
			f = x -> Power.of().value(x, coeff);
			break;
		case POWER2: // a*n^b
			coeff = Power2.of().fit(points, new double[] { 1., 1. });
			System.out.println(String.format("Solutions = a = %.2f,b = %.2f", coeff[0], coeff[1]));
			ajusteString = String.format("%.3g * n^%.2f", coeff[0], coeff[1]);
			f = x -> Power2.of().value(x, coeff);
			break;
		case LOG: // a*(ln n) + b
			coeff = Log.of().fit(points, new double[] { 1., 1. });
			System.out.println(String.format("Solutions = a = %.2f,b = %.2f", coeff[0], coeff[1]));
			ajusteString = String.format("%.2f * (ln n) + %.2f", coeff[0], coeff[1]);
			f = x -> Log.of().value(x, coeff);
			break;
		case LOG2:
			coeff = Log2.of().fit(points, new double[] { 1. });
			System.out.println(String.format("Solutions = a = %.2f", coeff[0]));
			ajusteString = String.format("%.2f * (ln n)", coeff[0]);
			f = x -> Log2.of().value(x, coeff);
			break;
		case NLOGN: // a*n*(ln n) + b
			coeff = NLogN.of().fit(points, new double[] { 1., 1. });
			System.out.println(String.format("Solutions = a = %.2f,b = %.2f", coeff[0], coeff[1]));
			ajusteString = String.format("%.2f * n * (ln n) + %.2f", coeff[0], coeff[1]);
			f = x -> NLogN.of().value(x, coeff);
			break;
		case NLOGN2: // a*n*(ln n)
			coeff = NLogN2.of().fit(points, new double[] { 1. });
			System.out.println(String.format("Solutions = a = %.2f", coeff[0]));
			ajusteString = String.format("%.2f * n * (ln n)", coeff[0]);
			f = x -> NLogN2.of().value(x, coeff);
			break;
		case EXP: // a*b^(c*n) + d
			coeff = Exponential.of().fit(points, new double[] { 1., 1., 1., 1. });
			System.out.println(String.format("Solutions = a = %.2f,b = %.2f,c = %.2f,d = %.2f", coeff[0], coeff[1],
					coeff[2], coeff[3]));
			ajusteString = String.format("%.2f * %.2f^(%.2f * n) + %.2f", coeff[0], coeff[1], coeff[2], coeff[3]);
			f = x -> Exponential.of().value(x, coeff);
			break;
		case EXP2: // a*b^n + c
			coeff = Exponential2.of().fit(points, new double[] { 1., 1., 1. });
			System.out.println(String.format("Solutions = a = %.2f,b = %.2f,c = %.2f", coeff[0], coeff[1], coeff[2]));
			ajusteString = String.format("%.2f * %.2f^n + %.2f", coeff[0], coeff[1], coeff[2]);
			f = x -> Exponential2.of().value(x, coeff);
			break;
		case EXP3: // a*b^n
			coeff = Exponential3.of().fit(points, new double[] { 1., 1. });
			System.out.println(String.format("Solutions = a = %.2f,b = %.2f", coeff[0], coeff[1]));
			ajusteString = String.format("%.2f * %.2f^n", coeff[0], coeff[1]);
			f = x -> Exponential3.of().value(x, coeff);
			break;

		default:
			break;
		}
		return Pair.of(f, ajusteString);

	}

}




