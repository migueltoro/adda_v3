package us.lsi.ag;

import java.util.List;
import java.util.stream.IntStream;


/**
 * @author Miguel Toro
 *
 * @param <S> El tipo de soluci�n del problema
 */
public interface InSetData<S> extends ChromosomeData<List<Integer>,S> {
	
	/**
	 * @pre 0 &le; i &lt; getVariableNumber()
	 * @param i Un entero 
	 * @return El conjunto de valores de la variable i
	 */
	List<Integer> values(Integer i);

	
	default List<Integer> decode(List<Double> ls){
		return IntStream.range(0,ls.size()).boxed()
				.map(i->AuxiliaryAg.convert(ls.get(i),this.values(i))).toList();
	}

}
