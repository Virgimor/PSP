package es.psp.moreno_ortega_unidad1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClasificacionEquipoDto {
	
	private String nombre;
	
	private Integer puntuacion;

}
