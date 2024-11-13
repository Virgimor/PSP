package es.iesJandula.Proyecto1.model;

import java.util.Objects;

public class Jugador {
	
	private String nombre=" ";
	private Integer dorsal= 0;
	private Integer goles = 0;
	private Integer tarjetas = 0;
	
	public Jugador() {
		super();
	}
	

	public Jugador(String nombre, Integer dorsal, Integer goles, Integer tarjetas) {
		super();
		this.nombre = nombre;
		this.dorsal = dorsal;
		this.goles = goles;
		this.tarjetas = tarjetas;
	}



	@Override
	public int hashCode() {
		return Objects.hash(dorsal, goles, nombre, tarjetas);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jugador other = (Jugador) obj;
		return Objects.equals(dorsal, other.dorsal) && Objects.equals(goles, other.goles)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(tarjetas, other.tarjetas);
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public Integer getDorsal() {
		return dorsal;
	}



	public void setDorsal(Integer dorsal) {
		this.dorsal = dorsal;
	}



	public Integer getGoles() {
		return goles;
	}



	public void setGoles(Integer goles) {
		this.goles = goles;
	}



	public Integer getTarjetas() {
		return tarjetas;
	}



	public void setTarjetas(Integer tarjetas) {
		this.tarjetas = tarjetas;
	}


	@Override
	public String toString() {
		return "Jugador [nombre=" + nombre + ", dorsal=" + dorsal + ", goles=" + goles + ", tarjetas=" + tarjetas + "]";
	}



	

	
	
}
