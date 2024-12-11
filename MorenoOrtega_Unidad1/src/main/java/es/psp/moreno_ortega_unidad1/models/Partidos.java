package es.psp.moreno_ortega_unidad1.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Partidos 
{
	
	@EmbeddedId
	private PartidosId partidosId;
	
	@Column
	private Integer golesLocal;
	
	@Column
	private Integer golesVisitante;
	
	@ManyToOne
	@MapsId("equipoLocal")
	@JoinColumn(name = "equipo_local", referencedColumnName = "nombre")
	private Equipo equipoLocal;
	
	@ManyToOne
	@MapsId("equipoVisitante")
	@JoinColumn(name = "equipo_visitante", referencedColumnName = "nombre")
	private Equipo equipoVisitante;

}
