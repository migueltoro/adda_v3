package us.lsi.common;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class String2 {
	
	
	
	public static void toFile(String s, String file){
		try {
			final PrintWriter f = new PrintWriter(new BufferedWriter(
					new FileWriter(file)));
				f.println(s);
			f.close();
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"No se ha podido crear el fichero " + file);
		}
	}
	
	public static String[] toArray(String s, String delim){
		return Arrays.<String>stream(s.split(delim))
				.<String>map((String x) -> x.trim())
				.filter((String x) -> x.length() > 0)
				.toArray((int x)-> new String[x]);
	}
	
	public static String linea() {
		return IntStream.range(0, 100).mapToObj(i->"_").collect(Collectors.joining());
	}
	
	public static void toConsole(String s){
		System.out.println(s);
	}
	
	public static <E> void toConsole(String format, Object... elements){
		toConsole(String.format(format,elements));
	}
	
	public static <E> void toConsole(Collection<E> c, Function<E,String> f, String sp) {
		String r = c.stream().map(f).collect(Collectors.joining(sp));
		System.out.println(r);
	}
	
	public static <E> String format(Collection<E> c, Function<E,String> f, String sp) {
		return c.stream()
		  .map(f)
		  .collect(Collectors.joining(sp));
	}
	
	public static <E> void toConsole(Collection<E> c, String titulo){
		String r = c.stream()
				.map(x->x.toString())
				.collect(Collectors.joining("\n   ",titulo+" = {\n   " ,"\n}"));
		System.out.println(r);
	}
	
	public static <E> String format(Collection<E> c, String titulo){
		return c.stream()
				.map(x->x.toString())
				.collect(Collectors.joining("\n   ",titulo+" = {\n   " ,"\n}"));
	}
	
}
