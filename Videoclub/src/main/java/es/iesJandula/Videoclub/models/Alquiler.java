package es.iesJandula.Videoclub.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Alquiler {
	
	@EmbeddedId
	private AlquilerId infoReviewId;
	
	@Column(length = 200, nullable = false)
	private String review;

}
