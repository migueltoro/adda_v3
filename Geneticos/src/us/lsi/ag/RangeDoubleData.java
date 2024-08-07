package us.lsi.ag;

import java.util.List;
import java.util.stream.IntStream;

public interface RangeDoubleData<S> extends RangeData<Double, S> {
	
	default List<Double> decode(List<Double> ls){
		return IntStream.range(0,ls.size()).boxed()
				.map(i->AuxiliaryAg.convert(ls.get(i),this.min(i),this.max(i)))
				.toList();
	}

}
