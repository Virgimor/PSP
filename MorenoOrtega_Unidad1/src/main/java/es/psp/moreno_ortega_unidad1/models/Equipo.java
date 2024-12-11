package es.psp.moreno_ortega_unidad1.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

}
