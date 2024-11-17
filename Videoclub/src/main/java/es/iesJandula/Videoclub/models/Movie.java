package es.iesJandula.Videoclub.models;

import es.iesJandula.Videoclub.utils.Costantes;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Movie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long moviId;
	
	@Column(length = 50, nullable = false)
	private String title;
	
	@Column(length = 5, nullable = false)
	private String duracion;
	
	@Column(length = 10)
	private String alquilada=Costantes.MOVIE_DISPONIBLE;

}
