package ies.jandula.VideoClubBien.models;
import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BookingId implements Serializable {
	
	private static final long serialVersionUID=1L;

	@Column(name = "movie_id", insertable = false, updatable = false) 
	private Integer movieId;

	@Column(name = "user_id", insertable = false, updatable = false) 
	private Integer userId;
	
	//poner fecha

}
