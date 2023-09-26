package us.lsi.p3_23;


public record Pasillo(String c1, String c2, Double mts) {
	public static Pasillo ofFormat(String[] v) {
		return new Pasillo(v[0], v[1], Double.valueOf(v[2]));
	}

}
