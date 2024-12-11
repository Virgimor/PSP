package es.psp.moreno_ortega_unidad1.models;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class PartidosId implements Serializable
{/**
	 * 
	 */
	private static final long serialVersionUID = 867810575288223975L;
	
	private String fecha;
	
	private String equipoLocal;
	
	private String equipoVisitante;

}
