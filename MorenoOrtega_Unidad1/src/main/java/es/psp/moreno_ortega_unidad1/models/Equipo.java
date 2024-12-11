package es.psp.moreno_ortega_unidad1.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Equipo 
{
	
	@Id
	private String nombre;
	
	@Column
	private String ciudad;
	
	@Column
	private Integer puntuacion;
	
	@OneToMany(mappedBy = "equipo")
	private List<Jugador> jugadores;

}
