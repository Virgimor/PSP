package es.iesJandula.Videoclub.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class InfoReview {
	
	@EmbeddedId
	private InfoReviewId infoReviewId;

}
