package us.lsi.ag;

public interface BinaryData<S> extends RangeData<Integer,S>{
	
	default Integer max(Integer i) {
		return 2;
	}
	
	default Integer min(Integer i) {
		return 0;
	}
}
