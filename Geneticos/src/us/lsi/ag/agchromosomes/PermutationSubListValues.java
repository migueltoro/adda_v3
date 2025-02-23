package us.lsi.ag.agchromosomes;

import java.util.List;
import java.util.stream.IntStream;

import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.PermutationData;
import us.lsi.common.List2;

public class PermutationSubListValues<S> implements ChromosomeValues<List<Integer>, List<Double>, S> {

	public static <S> PermutationSubListValues<S> of(PermutationData<S> data) {
        return new PermutationSubListValues<S>(data);
    }

    private PermutationData<S> data;

    private PermutationSubListValues(PermutationData<S> data) {
        this.data = data;
    }
    
    @Override
    public PermutationData<S> data() {
        return data;
    }

    @Override
    public List<Integer> decodeValues(List<Double> ls){
		Integer n = ls.size()/2;
		List<Integer> rp = AuxiliaryAg.convert(ls.subList(0, n),data.normalSequence());
		List<Integer> bn = IntStream.range(n,2*n).boxed().map(i->ls.get(i)<0.5?0:1).toList();
		List<Integer> r = List2.empty();
		IntStream.range(0, n).boxed().filter(i->bn.get(i)==1).forEach(i->r.add(rp.get(i)));
		return r;
	}

    @Override
    public Integer dimension() {
        return 2*data.normalSequence().size();
    }
}
