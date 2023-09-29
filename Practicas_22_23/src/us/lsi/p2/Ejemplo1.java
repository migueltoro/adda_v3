package us.lsi.p2;

public class Ejemplo1 {
	/*
	PI2 - Ejemplo 1

	Implementar de forma recursiva e iterativa un algoritmo para el cálculo de la potencia a^n, 
	siendo a de tipo Double y n de tipo Integer. Analizar sus tiempos de ejecución.
	*/
		
	public static Double potenciaR(Double a, Integer n) {
		Double res;
        if (n==0){ 
        	res = 1.;
        } 
        else{ 
        	res= a * potenciaR(a, n-1); 
        }
        return res;
	}
	
	
	public static Double potenciaIter(Double a, Integer n) {
        Double res = 1.;
 
        for (int i = 0; i < n; i++) {
            res = res * a;
        }
 
        return res;
	}
	
	
}

