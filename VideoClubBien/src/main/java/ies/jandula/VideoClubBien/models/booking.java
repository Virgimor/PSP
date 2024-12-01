package ies.jandula.VideoClubBien.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booking")
@Entity
public class booking {
	
	@EmbeddedId
	private BookingId id; 
	
	@ManyToOne
	@MapsId("movieId")
	private Movies movies; 
	
	@ManyToOne
	@MapsId("userId")
	private Users user; 	
	
	
	

}
