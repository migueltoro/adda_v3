package us.lsi.ag;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.List2;

public interface RangePermutationData<S>  extends ChromosomeDoubleData<List<Integer>,S> {
	
	Integer sizeRange();
	/**
	 * @pre 0 &le; i &lt; getVariableNumber()
	 * @param i Un entero 
	 * @return El m�ximo valor, sin incluir, del rango de valores de la variable i
	 */
	Integer max(Integer i);
	/**
	 * @pre 0 &le; i &lt; 
	 * @param i Un entero getVariableNumber()
	 * @return El mínimo valor del rango de valores de la variable i
	 */
	Integer min(Integer i);
	
	Integer itemsNumber();
	
	/**
	 * @pre <code> 0 &le; index &lt; getObjetos().size() </code>
	 * @param index Indice en la lista de objetos disponibles
	 * @return La multiplicidad mínima del objeto de índice <code> index </code>. 
	 * La multiplicidad máxima del objeto <code> i </code> estar� en el rango <code> 0..getMax(i) </code>
	 */
	
	default Integer maxMultiplicity(int index){ return 1; }
	
	
    /**
     * @return La secuencia normal asociada al problema. Siendo <code> n </code> el n�mero de objetos la secuencia normal est� formado por la concatenaci�n de  
     * <code> n </code> sublistas <code> L(i) </code>. Cada <code> L(i) </code> est� formada por <code> getMax(i) </code> copias del entero <code> i </code>.
     * con <code> i </code> en el rango <code> 0..n-1 </code>.
     */
    default List<Integer> normalSequence() {
		List<Integer> r = IntStream.range(0,itemsNumber())
				.boxed()
				.flatMap(x->List2.nCopies(x,maxMultiplicity(x)).stream())
				.collect(Collectors.toList());
		return r;
	}
    
    default Integer sizePermutation() {
    	return normalSequence().size();
    }  
    
    default Integer size() {
    	return this.sizeRange()+this.sizePermutation();
    }
    
    default List<Integer> decode(List<Double> ls){
		List<Integer> r1 = IntStream.range(0,ls.size()).boxed()
				.map(i->AuxiliaryAg.convert(ls.get(i),this.min(i),this.max(i)))
				.toList();
		List<Integer> r2 = AuxiliaryAg.convert(ls.subList(this.sizeRange(),this.size()),this.normalSequence());
		return List2.concat(r1, r2);
	}

}
