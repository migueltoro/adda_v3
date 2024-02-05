package us.lsi.ag;

import java.util.List;
import java.util.stream.IntStream;

import us.lsi.common.List2;

public interface PermutationSubListData<S>  extends PermutationData<S> {
	
	default List<Integer> decode(List<Double> ls){
		Integer n = ls.size()/2;
		List<Integer> rp = AuxiliaryAg.convert(ls.subList(0, n),this.normalSequence());
		List<Integer> bn = IntStream.range(n,2*n).boxed().map(i->ls.get(i)<0.5?0:1).toList();
		List<Integer> r = List2.empty();
		IntStream.range(0, n).boxed().filter(i->bn.get(i)==1).forEach(i->r.add(rp.get(i)));
		return r;
	}
	
	default Integer size() {
    	return 2*normalSequence().size();
    }

}
