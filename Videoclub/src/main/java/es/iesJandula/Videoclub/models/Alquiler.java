package es.iesJandula.Videoclub.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Alquiler {
	
	@EmbeddedId
	private AlquilerId alquilerId;
	
	@ManyToOne(cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name = "user_alquiler", referencedColumnName = "userId")
	@MapsId("userId")
	private User userAlquiler;
	
	@ManyToOne(cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name = "movie_alquiler", referencedColumnName = "movieId")
	@MapsId("movieId")
	private Movie movieAlquiler;
	
//	@Column(length = 200)
//	private String review;

}
