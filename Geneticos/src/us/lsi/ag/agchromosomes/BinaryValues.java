package us.lsi.ag.agchromosomes;

import java.util.List;
import us.lsi.ag.BinaryData;

public class BinaryValues<S> implements ChromosomeValues<List<Integer>, List<Integer>, S> {
	
	public static <S> BinaryValues<S> of(BinaryData<S> data) {
		return new BinaryValues<S>(data);
	}

	private BinaryData<S> data;

	BinaryValues(BinaryData<S> data) {
		this.data = data;
	}
	
	@Override
	public BinaryData<S> data() {
		return data;
	}

	@Override
	public List<Integer> decodeValues(List<Integer> ls) {
		return ls;
	}

	@Override
	public Integer dimension() {
		return data.size();
	}
	
}
