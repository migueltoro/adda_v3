package us.lsi.alg.mochila.manual;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import us.lsi.common.Files2;

public class DatosMochila {
	
	public record ObjetoMochila(Integer codigo, Integer valor, Integer peso, Integer numMaxDeUnidades, Double ratioValorPeso) 
	       implements Comparable<ObjetoMochila>{

		private static Integer nCodigo = 0;
		public static ObjetoMochila parse (String s){		
			String[] v = s.split("[ ,]");
			Integer ne = v.length;
			if(ne != 3) throw new IllegalArgumentException("Formato no adecuado en línea  "+s);	
			Integer valor = Integer.parseInt(v[0].trim());
			Integer peso = Integer.parseInt(v[1].trim());
			Integer numMaxDeUnidades = Integer.parseInt(v[2]);
			Integer codigo = nCodigo;
			Double ratioValorPeso = ((double)valor)/peso;
			nCodigo++;
			return new ObjetoMochila(codigo,valor,peso,numMaxDeUnidades,ratioValorPeso);
		}	
		
		@Override
		public int compareTo(ObjetoMochila other) {
				return this.ratioValorPeso().compareTo(other.ratioValorPeso());
		}
	}
		
	public static List<ObjetoMochila> objetosDisponibles;
	private static Comparator<ObjetoMochila> ordenObjetos;
	public static Integer capacidadInicial;
	public static Integer numeroDeObjetos;
	public static Integer n;

	
	public static void datos(String fichero) {
		ordenObjetos = Comparator.reverseOrder();
		objetosDisponibles = Files2.streamFromFile(fichero)
				.map(s -> ObjetoMochila.parse(s))
				.sorted(ordenObjetos)
				.collect(Collectors.<ObjetoMochila> toList());
		numeroDeObjetos = objetosDisponibles.size();
		n = numeroDeObjetos;
	}
	
	public static ObjetoMochila objeto(Integer index) {
		return objetosDisponibles.get(index);
	}
	
	public static Integer valor(Integer index) {
		return objetosDisponibles.get(index).valor();
	}
	
	public static Integer peso(Integer index) {
		return objetosDisponibles.get(index).peso();
	}
	
	public static Integer numMaxDeUnidades(Integer index) {
		return objetosDisponibles.get(index).numMaxDeUnidades();
	}

}
