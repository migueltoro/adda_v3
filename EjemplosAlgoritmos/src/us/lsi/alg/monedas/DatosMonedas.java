package us.lsi.alg.monedas;

public class DatosMonedas {
	
	public static Integer n;
	public static Integer valorInicial;
	
	public static void datosIniciales(String fichero, Integer valorInicial) {
		Moneda.datos(fichero);
		DatosMonedas.valorInicial = valorInicial;
		DatosMonedas.n = Moneda.monedas.size();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
