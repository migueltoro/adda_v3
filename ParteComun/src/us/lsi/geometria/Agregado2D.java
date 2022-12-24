package us.lsi.geometria;

import java.util.List;

import us.lsi.common.List2;



public class Agregado2D implements ObjetoGeometrico2D {
	
	public static Agregado2D create() {
		return new Agregado2D();
	}

	public static Agregado2D create(
			List<ObjetoGeometrico2D> objetosGeometricos) {
		return new Agregado2D(objetosGeometricos);
	}

	public static Agregado2D create(Agregado2D a) {
		return new Agregado2D(a);
	}

	private List<ObjetoGeometrico2D> objetosGeometricos;
		
	private Agregado2D() {
		this.objetosGeometricos = List2.empty();
	}
	
	private Agregado2D(List<ObjetoGeometrico2D> objetosGeometricos) {
		this.objetosGeometricos = objetosGeometricos;
	}
	
	private Agregado2D(Agregado2D a) {
		super();
		this.objetosGeometricos = a.getObjetosGeometricos();
	}

	@Override
	public ObjetoGeometrico2D rota(Punto2D p, Double angulo) {
		Agregado2D od = Agregado2D.create();
		
		for(ObjetoGeometrico2D a:this.objetosGeometricos){
			od.add(a.rota(p, angulo));
		}
			
		return od;
	}

	@Override
	public ObjetoGeometrico2D traslada(Vector2D v) {
				Agregado2D od = Agregado2D.create();
				
				for(ObjetoGeometrico2D a:this.objetosGeometricos){
					od.add(a.traslada(v));
				}
					
				return od;
	}
	
	
	public void add(ObjetoGeometrico2D a){
		this.objetosGeometricos.add(a);
	}

	public List<ObjetoGeometrico2D> getObjetosGeometricos() {
		return objetosGeometricos;
	}
	
	

}
