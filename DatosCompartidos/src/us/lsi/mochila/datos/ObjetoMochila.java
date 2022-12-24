package us.lsi.mochila.datos;

/**
 * <p> Esta clase implementa el tipo ObjetoMochila.</p>
 * <p> Las propiedades de estos problemas son: </p>
 * <ul>
 * <li> Código
 * <li> Valor
 * <li> Peso
 * <li> Número máximo de unidades
 * </ul> 
 * 
 * 
 * 
 * @author Miguel Toro
 *
 */
public class ObjetoMochila implements Comparable<ObjetoMochila>{
	
	public static ObjetoMochila create(Integer valor,Integer peso, Integer countMax) {
		return new ObjetoMochila(valor, peso, countMax);
	}

	/**
	 * @param s Una línea de un fichero de texto
	 * @return Construye un objeto mochila a partir de una línea de un fichero
	 */
	public static ObjetoMochila create(String s) {
		return new ObjetoMochila(s);
	}
	
	private static Integer nCodigo = 0;
	
	private Integer codigo;
	private Integer valor;
	private Integer peso;
	private Integer numMaxDeUnidades;
	
	ObjetoMochila(Integer valor, Integer peso, Integer countMax){
		this.codigo = nCodigo;
		nCodigo++;
		this.valor = valor;
		this.peso = peso;
		this.numMaxDeUnidades = countMax;
	}	
	ObjetoMochila(String s){		
		String[] v = s.split("[ ,]");
		Integer ne = v.length;
		if(ne != 3) throw new IllegalArgumentException("Formato no adecuado en línea  "+s);	
		valor = Integer.parseInt(v[0].trim());
		peso = Integer.parseInt(v[1].trim());
		numMaxDeUnidades = Integer.parseInt(v[2]);
		this.codigo = nCodigo;
		nCodigo++;
	}	
	public Integer getPeso() {
		return peso;
	}
	public Integer getValor() {
		return valor;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public Integer getNumMaxDeUnidades() {
		return numMaxDeUnidades;
	}
		
	public Double getRatioValorPeso() {
		return ((double)valor)/peso;
	}
	
	@Override
	public String toString() {
		return String.format("(%d,%d,%d,%.2f)",
				valor,peso,numMaxDeUnidades,getRatioValorPeso());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ObjetoMochila))
			return false;
		ObjetoMochila other = (ObjetoMochila) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public int compareTo(ObjetoMochila o) {
		return this.getRatioValorPeso().compareTo(o.getRatioValorPeso());
	}
	
	
	
}
