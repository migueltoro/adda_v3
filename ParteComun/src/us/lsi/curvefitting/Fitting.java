package us.lsi.curvefitting;


import java.util.List;
import java.util.function.Function;

import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem.Evaluation;
import us.lsi.common.Trio;


public class Fitting {

	// Este método devuelve un par: función para obtener puntos de la curva de
	// ajuste, y String para mostrar coeficientes del ajuste
	public static Trio<Function<Double, Double>, String, Evaluation> fitCurve(
			List<WeightedObservedPoint> points,FittingType tipoAjuste) {

		// Curva de ajuste
		final double[] coeff;
		String ajusteString = null;
		Function<Double, Double> f = null;
		Evaluation evaluation = null;

		switch (tipoAjuste) {
		case LINEAL: // a + n*b
			// Cálculo de coeficientes
			Polynomial p1 = Polynomial.of(1);
			coeff = p1.fit(points, new double[] { 1., 1., 1., 1. });
			System.out.println(String.format("Solutions = a = %.2f,b = %.3g", coeff[0], coeff[1]));
			// Título de la gráfica con los coeficientes
			ajusteString = String.format("%.2f + n*%.3g", coeff[0], coeff[1]);
			f = x -> coeff[0] + coeff[1] * x;
			evaluation = p1.getEvaluation();
			break;
		case POWERLOG: // a*n^b*(ln n)^c + d
			PowerLog p2 = PowerLog.of();
			coeff = p2.fit(points, new double[] { 1., 1., 1., 1. });
			System.out.println(String.format("Solutions = a = %.2f,b = %.2f,c = %.2f,d = %.2f", coeff[0], coeff[1],
					coeff[2], coeff[3]));
			ajusteString = String.format("%.2f * n^%.2f * (ln n)^%.2f + %.2f", coeff[0], coeff[1], coeff[2], coeff[3]);
			f = x -> p2.value(x, coeff);
			evaluation = p2.getEvaluation();
			break;		
		case POWERLOG2: // a*n^b*(ln n)^c
			PowerLog2 p3 = PowerLog2.of();
			coeff = p3.fit(points, new double[] {1., 1., 1.});
			System.out.println(String.format("Solutions = a = %.2f,b = %.2f,c = %.2f", coeff[0], coeff[1],
					coeff[2]));
			ajusteString = String.format("%.2f * n^%.2f * (ln n)^%.2f", coeff[0], coeff[1], coeff[2]);
			f = x -> p3.value(x, coeff);
			evaluation = p3.getEvaluation();
			break;
		case POWERLOG3: // a*n^b*(ln n)
			PowerLog3 p4 = PowerLog3.of();
			coeff = p4.fit(points, new double[] {1., 1.});
			System.out.println(String.format("Solutions = a = %.2f,b = %.2f", coeff[0], coeff[1]));
			ajusteString = String.format("%.2f * n^%.2f * (ln n)", coeff[0], coeff[1]);
			f = x -> p4.value(x, coeff);
			evaluation = p4.getEvaluation();
			break;
		case POLYNOMIAL:// a*n^b + ... + d
			Polynomial p6 = Polynomial.of(3);
			coeff = p6.fit(points, new double[] { 1., 1., 1., 1. });
			System.out.println(String.format("Solutions = a = %.2f,b = %.2f,c = %.2f,d = %.2f", coeff[0], coeff[1],
					coeff[2], coeff[3]));
			ajusteString = String.format("%.2f * x^3 + %.2f x^2 + %.2f * x + %.2f", coeff[3], coeff[2], coeff[1],
					coeff[0]);
			f = x -> p6.value(x, coeff);
			evaluation = p6.getEvaluation();
			break;
		case POWER: // a*n^b + c
			coeff = Power.of().fit(points, new double[] { 1., 1., 1. });
			System.out.println(String.format("Solutions = a = %.2f,b = %.2f,c = %.2f", coeff[0], coeff[1], coeff[2]));
			ajusteString = String.format("%.2f * n^%.2f + %.2f", coeff[0], coeff[1], coeff[2]);
			f = x -> Power.of().value(x, coeff);
			evaluation = Power.of().getEvaluation();
			break;
		case POWER2: // a*n^b
			coeff = Power2.of().fit(points, new double[] { 1., 1. });
			System.out.println(String.format("Solutions = a = %.2f,b = %.2f", coeff[0], coeff[1]));
			ajusteString = String.format("%.3g * n^%.2f", coeff[0], coeff[1]);
			f = x -> Power2.of().value(x, coeff);
			evaluation = Power2.of().getEvaluation();
			break;
		case LOG: // a*(ln n) + b
			coeff = Log.of().fit(points, new double[] { 1., 1. });
			System.out.println(String.format("Solutions = a = %.2f,b = %.2f", coeff[0], coeff[1]));
			ajusteString = String.format("%.2f * (ln n) + %.2f", coeff[0], coeff[1]);
			f = x -> Log.of().value(x, coeff);
			evaluation = Log.of().getEvaluation();
			break;
		case LOG2:
			coeff = Log2.of().fit(points, new double[] { 1. });
			System.out.println(String.format("Solutions = a = %.2f", coeff[0]));
			ajusteString = String.format("%.2f * (ln n)", coeff[0]);
			f = x -> Log2.of().value(x, coeff);
			evaluation = Log2.of().getEvaluation();
			break;
		case LOG3: // a*(ln n)^b
			Log3 p5 = Log3.of();
			coeff = p5.fit(points, new double[] {1., 1.});
			System.out.println(String.format("Solutions = a = %.2f,b = %.2f", coeff[0], coeff[1]));
			ajusteString = String.format("%.2f * (ln n)^%.2f", coeff[0], coeff[1]);
			f = x -> p5.value(x, coeff);
			evaluation = p5.getEvaluation();
			break;
		case NLOGN: // a*n*(ln n) + b
			coeff = NLogN.of().fit(points, new double[] { 1., 1. });
			System.out.println(String.format("Solutions = a = %.2f,b = %.2f", coeff[0], coeff[1]));
			ajusteString = String.format("%.2f * n * (ln n) + %.2f", coeff[0], coeff[1]);
			f = x -> NLogN.of().value(x, coeff);
			evaluation = NLogN.of().getEvaluation();
			break;
		case NLOGN2: // a*n*(ln n)
			coeff = NLogN2.of().fit(points, new double[] { 1. });
			System.out.println(String.format("Solutions = a = %.2f", coeff[0]));
			ajusteString = String.format("%.2f * n * (ln n)", coeff[0]);
			f = x -> NLogN2.of().value(x, coeff);
			evaluation = NLogN2.of().getEvaluation();
			break;
		case EXP: // a*b^(c*n) + d
			coeff = Exponential.of().fit(points, new double[] { 1., 1., 1., 1. });
			System.out.println(String.format("Solutions = a = %.2f,b = %.2f,c = %.2f,d = %.2f", coeff[0], coeff[1],
					coeff[2], coeff[3]));
			ajusteString = String.format("%.2f * %.2f^(%.2f * n) + %.2f", coeff[0], coeff[1], coeff[2], coeff[3]);
			f = x -> Exponential.of().value(x, coeff);
			evaluation = Exponential.of().getEvaluation();
			break;
		case EXP2: // a*b^n + c
			coeff = Exponential2.of().fit(points, new double[] { 1., 1., 1. });
			System.out.println(String.format("Solutions = a = %.2f,b = %.2f,c = %.2f", coeff[0], coeff[1], coeff[2]));
			ajusteString = String.format("%.2f * %.2f^n + %.2f", coeff[0], coeff[1], coeff[2]);
			f = x -> Exponential2.of().value(x, coeff);
			evaluation = Exponential2.of().getEvaluation();
			break;
		case EXP3: // a*b^n
			coeff = Exponential3.of().fit(points, new double[] { 1., 1. });
			System.out.println(String.format("Solutions = a = %.2f,b = %.2f", coeff[0], coeff[1]));
			ajusteString = String.format("%.2f * %.2f^n", coeff[0], coeff[1]);
			f = x -> Exponential3.of().value(x, coeff);
			evaluation = Exponential3.of().getEvaluation();
			break;

		default:
			break;
		}
		return Trio.of(f, ajusteString,evaluation);

	}

}




