package us.lsi.geometria;

public class TestRecta {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Recta2D r = Recta2D.create(Punto2D.of(0., 0.), Punto2D.of(2., 1.));
		Vector2D v = r.getVector();
		Punto2D p = r.getPunto();
		System.out.println(r+","+v+","+p);
		System.out.println(r.contienePunto(Punto2D.of(0., 0.))+","+r.contienePunto(Punto2D.of(2., 1.)));
		System.out.println(Recta2D.create());
		Recta2D r2 = Recta2D.create(Punto2D.of(1., 1.), Vector2D.of(1., 1.));
		Recta2D r3 = Recta2D.create(Punto2D.of(2., 2.), Vector2D.of(-1., -1.));
		System.out.println(r2.equals(r3)+","+(r2.getAnguloEnGrados()-r3.getAnguloEnGrados()));
		System.out.println(r2.getAnguloEnGrados()+","+r2.getAngulo()+","+r2.getDistanciaAlOrigenConSigno());
		System.out.println(r3.getAnguloEnGrados()+","+r3.getAngulo()+","+r3.getDistanciaAlOrigenConSigno());
		System.out.println(Vector2D.of(1., 1.).getAnguloEnGrados());
		System.out.println(Vector2D.of(-1., 1.).getAnguloEnGrados());
		System.out.println(Vector2D.of(-1., -1.).getAnguloEnGrados());
		System.out.println(Vector2D.of(1., -1.).getAnguloEnGrados());
		Semiplano2D s1 = Semiplano2D.create(r3, Punto2D.of(-10.,10.));
		Semiplano2D s2 = Semiplano2D.create(r2, Punto2D.of(-10.,10.));
		System.out.println(s1);
		System.out.println(s2);
	}

}
