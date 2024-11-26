package es.iesJandula.Videoclub.models;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class AlquilerId implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4452012575728293114L;

	@ManyToOne
	private User user;
	
	@ManyToOne
	private Movie movie;
	
	private Date fecha;
	
	public AlquilerId(long userId, long movieId) {
		
	}

}
