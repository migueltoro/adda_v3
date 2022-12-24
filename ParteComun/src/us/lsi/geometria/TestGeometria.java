package us.lsi.geometria;



public class TestGeometria {

	
	public static void main(String[] args) {
		Recta2D r = Recta2D.create(Punto2D.getOrigen(), Punto2D.of(1., 1.));
		Recta2D rc = Recta2D.create(Punto2D.getOrigen(), Punto2D.of(-1., 1.));
		Recta2D rm = Recta2D.create(Punto2D.of(2., 2.),Punto2D.of(-1.,-1.));
		Recta2D r1 = r.getParalela(Punto2D.of(0.,1.));
		Recta2D r2 = r.getPerpendicular(Punto2D.of(1.,1.));	
		Recta2D r0 = r.rota(Punto2D.getOrigen(), Math.PI/2);
		Vector2D v1 = Vector2D.of(1., 1.);
		Vector2D v2 = Vector2D.of(-1., -1.);
		Vector2D v3 = Vector2D.of(1., 0.);
		System.out.println(r+","+rm+","+r1+","+r2+","+r.equals(rm));
		System.out.println(r.getAnguloEnGrados()-rm.getAnguloEnGrados());
		System.out.println(r.getPunto()+","+r.getVector());
		System.out.println(r0.getPunto()+","+r0.getVector());
		System.out.println(Punto2D.of(1., 1.).rota(Punto2D.getOrigen(), Math.PI/2));
		System.out.println(v3.proyectaSobre(v1)+","+v3.proyectaSobre(v2));
		System.out.println(r.cortaA(rc));
		System.out.println(r.getDistancia(Punto2D.of(-1., 1.)));	
		r = Recta2D.create(Punto2D.of(-400.,-400.), Punto2D.of(400., 400.));
		Semiplano2D s = Semiplano2D.create(r,Punto2D.of(-100.,100.));
		System.out.println(s);
		System.out.println(r.getPunto()+","+r.getPunto().add(r.getVector()));
		
		Recta2D r5 = Recta2D.create(Punto2D.of(1., 1.), Vector2D.of(1., 1.));
		Semiplano2D s1 = Semiplano2D.create(r5, Punto2D.of(-10.,10.));
		System.out.println(s1);
	}

}
