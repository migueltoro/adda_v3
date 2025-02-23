package us.lsi.ag.agchromosomes;

import java.util.List;
import java.util.stream.IntStream;

import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.ChromosomeData;
import us.lsi.ag.InSetData;

public class InSetValues<S> implements ChromosomeValues<List<Integer>, List<Double>, S> {
	
	public static <S> InSetValues<S> of(InSetData<S> data) {
		return new InSetValues<S>(data);
	}

	private InSetData<S> data;

	private InSetValues(InSetData<S> data) {
		this.data = data;
	}
	
	@Override
	public ChromosomeData<List<Integer>, S> data() {
		return data;
	}

	@Override
	public List<Integer> decodeValues(List<Double> ls) {
		return IntStream.range(0,ls.size()).boxed()
				.map(i->AuxiliaryAg.convert(ls.get(i),data.values(i))).toList();
	}

	@Override
	public Integer dimension() {
		return data.size();
	}
	

}
