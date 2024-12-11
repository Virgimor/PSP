package es.psp.moreno_ortega_unidad1.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Jugador 
{
	@Id
	@Column(length = 100)
	private String nombre;
	
	
	@Column(length = 100)
	private String posicion;
	
	@ManyToOne
	private Equipo equipo;

}
