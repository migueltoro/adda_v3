package us.lsi.p3;



public record Pasillo(Double mts,Integer id) {
	public static Integer n =0;
	public static Pasillo ofFormat(String[] v) {
		Pasillo p = new Pasillo(Double.valueOf(v[2]),Pasillo.n);
		Pasillo.n++;
		return p;
	}

}
