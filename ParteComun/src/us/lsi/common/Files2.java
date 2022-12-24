package us.lsi.common;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class Files2 {
	
	public static void toFile(String s, String file) {
		try {
			final PrintWriter f = 
					new PrintWriter(new BufferedWriter(
							new FileWriter(file)));
			f.println(s);
			f.close();
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"No se ha podido crear el fichero " + file);
		}
	}
	
	/**
	 * @param s Una stream
	 * @param file Un fichero donde guardar los elementos de la stream
	 */
	public static void toFile(Stream<String> s, String file) {
		try {
			final PrintWriter f = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			s.forEach(x -> {
				f.println(x);
			});
			f.close();
		} catch (IOException e) {
			throw new IllegalArgumentException("No se ha podido crear el fichero " + file);
		}
	}
	
	/**
	 * @param file Un fichero
	 * @return Un stream formado por las l�neas del fichero
	 * @exception IllegalArgumentException si no se encucntra el fichero
	 */
	
	public static Stream<String> streamFromFile(String file) {
		Stream<String> r = null;
		try {
			r = Files.lines(Paths.get(file), Charset.defaultCharset());
		} catch (IOException e) {
			throw new IllegalArgumentException("No se ha encontrado el fichero " + file);
		}
		return r;
	}
	
	public static List<String> linesFromFile(String file) {
		List<String> r = null;
		try {
			r = Files.readAllLines(Paths.get(file), Charset.defaultCharset());
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"No se ha encontrado el fichero " + file);
		}
		return r;
	}
	
	public static OutputStream getOutputStream(String file) {
		OutputStream r = null;
		try {
			r = new FileOutputStream(new File(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return r;
	}

	public static PrintWriter writer = null;
	
	public static PrintWriter getWriter(String file) {
		PrintWriter r = null;
		try {
			r = new PrintWriter(new File(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return r;
	}

	public static PrintWriter getWriter() {
		return writer;
	}

	public static void setPrintWriter(String file) {
		PrintWriter r = null;
		try {
			r = new PrintWriter(new File(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		writer = r;
	}
}

