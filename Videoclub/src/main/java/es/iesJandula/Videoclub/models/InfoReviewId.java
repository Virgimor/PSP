package es.iesJandula.Videoclub.models;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class InfoReviewId implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4452012575728293114L;

	private int userName;
	
	private String review;

}
