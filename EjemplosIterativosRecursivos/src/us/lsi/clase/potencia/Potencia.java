package us.lsi.clase.potencia;

public class Potencia {
	
	/**
	 * 
	 * @param base Base
	 * @param n Exponente
	 * @return Valor de base^n de forma iterativa
	 */
	public static Long pot(Long base, Integer n){
		Long r = base;
		Long u = 1L;
		while( n > 0){
	       if(n%2==1){
			     u = u * r;
		   }
		   r = r * r;
		   n = n/2;
		}
		return u;
	}
	

}
