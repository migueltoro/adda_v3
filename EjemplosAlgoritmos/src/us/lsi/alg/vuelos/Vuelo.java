package us.lsi.alg.vuelos;

public class Vuelo {
	
	private Integer id;
	private String from;
	private String to;
	private double horaDeSalida;
	private double duracion;
	public static Integer n = 0;
	
	public static  Vuelo ofFormat(String[] s) {
		return new Vuelo(s[0],s[1], Double.parseDouble(s[2]),Double.parseDouble(s[3]));
	}
	
	public static Vuelo of(String from, String to, double timeSalida,double duracion) {
		return new Vuelo(from, to, timeSalida, duracion);
	}
	
	public static Double getVertexPassWeight(String vertex, Vuelo edgeIn, Vuelo edgeOut) {
		double r=0.;
		double horaDeLlegada;
		double horaDeSalida;
		if (edgeIn != null && edgeOut !=null) {
			horaDeLlegada = edgeIn.getHoraDeLlegada();
			horaDeSalida = edgeOut.getHoraDeSalida();
			r = horaDeSalida - horaDeLlegada;
			if (r < 0) {
				r += 24.;
			} 
		}
		return r;
	}
	
	
	Vuelo(String from, String to, double horaDeSalida, double duracion) {
		super();
		this.from = from;
		this.to = to;
		this.horaDeSalida = horaDeSalida;
		this.duracion = duracion;
		this.id = n;
		n++;
	}

	public Vuelo() {
		super();
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public double getHoraDeSalida() {
		return horaDeSalida;
	}

	public double getDuracion() {
		return duracion;
	}

	public double getHoraDeLlegada() {
		return horaDeSalida+duracion;
	}	

	@Override
	public String toString() {
		return String.format("(%s,%s,%.2f,%.2f,%.2f)",from,to,horaDeSalida,duracion,getHoraDeLlegada());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vuelo other = (Vuelo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
