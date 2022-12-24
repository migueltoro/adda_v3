package us.lsi.ag;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.List2;

/**
 * @author Miguel Toro
 *
 * @param <S> Tipo de la solución
 * 
 * 
 * <p> Se parte de un conjunto de <code> n </code> objetos distintos y unas multiplicidades máximas para cada uno de ellos
 * A partir de esa información se construye la secuencia normal que asumimos de tamaño <code> r </code>. </p>
 * 
 * <p> La secuencia normal asociada al problema. Siendo <code> n </code> el número de objetos la secuencia normal está formado por la concatenación de  
 * <code> n </code> sublistas <code> L(i) </code>. Cada <code> L(i) </code> está formada por <code> getMax(i) </code> copias del entero <code> i </code>.
 * con <code> i </code> en el rango <code> 0..n-1 </code>. </p>
 *  
 * <p> Los problemas adecuados para ser modelados con este tipo son aquellos cuya solución 
 * es un multiconjunto o una lista, posiblemente con repetición, de los objetos dados. </p>
 */

public interface SeqNormalData<S> extends ChromosomeData<List<Integer>,S> {	
		
		Integer itemsNumber();
	
		/**
		 * @pre <code> 0 &le; index &lt; getObjetos().size() </code>
		 * @param index Indice en la lista de objetos disponibles
		 * @return La multiplicidad máxima del objeto de índice <code> index </code>. 
		 * La multiplicidad máxima del objeto <code> i </code> estará en el rango <code> 0..getMax(i) </code>
		 */
		
		default Integer maxMultiplicity(int index){ return 1; }
		
		
	    /**
	     * @return La secuencia normal asociada al problema. Siendo <code> n </code> el número de objetos la secuencia normal está formado por la concatenación de  
	     * <code> n </code> sublistas <code> L(i) </code>. Cada <code> L(i) </code> está formada por <code> getMax(i) </code> copias del entero <code> i </code>.
	     * con <code> i </code> en el rango <code> 0..n-1 </code>.
	     */
	    default List<Integer> normalSequence() {
			List<Integer> r = IntStream.range(0,itemsNumber())
					.boxed()
					.flatMap(x->List2.ofTam(x,maxMultiplicity(x)).stream())
					.collect(Collectors.toList());
			return r;
		}
	    
	    default Integer size() {
	    	return normalSequence().size();
	    }
	
}
