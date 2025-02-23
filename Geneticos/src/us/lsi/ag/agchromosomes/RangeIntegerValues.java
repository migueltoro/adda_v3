package us.lsi.ag.agchromosomes;

import java.util.List;
import java.util.stream.IntStream;

import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.ChromosomeData;
import us.lsi.ag.RangeIntegerData;

public class RangeIntegerValues<S> implements ChromosomeValues<List<Integer>, List<Double>, S> {
	
	public static <S> RangeIntegerValues<S> of(RangeIntegerData<S> data) {
		return new RangeIntegerValues<S>(data);
	}

	private RangeIntegerData<S> data;

	RangeIntegerValues(RangeIntegerData<S> data) {
		this.data = data;
	}
	
	@Override
	public ChromosomeData<List<Integer>, S> data() {
		return data;
	}

	@Override
	public List<Integer> decodeValues(List<Double> ls) {
		return IntStream.range(0, ls.size()).boxed()
				.map(i -> AuxiliaryAg.convert(ls.get(i), data.min(i), data.max(i))).toList();
	}

	@Override
	public Integer dimension() {
		return data.size();
	}
	
}
