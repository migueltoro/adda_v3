package us.lsi.common;

public record Pair<A, B>(A first,B second) {
	
	public static <A,B>  Pair<A,B> of(A a, B b){
		return new Pair<>(a,b);
	}

	@Override
	public String toString() {
		return String.format("(%s,%s)",first,second);
	}
		
}

