package es.iesJandula.Videoclub.models;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AlquilerId implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4452012575728293114L;

	private Long userId;
	
	private Long movieId;
	

}
