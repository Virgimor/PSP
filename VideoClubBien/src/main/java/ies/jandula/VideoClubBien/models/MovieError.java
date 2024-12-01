package ies.jandula.VideoClubBien.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movieError")
@Entity
public class MovieError {

	@Id
	@Column
	private int movieErrorId;
	
	@Column
	private String menssage; 
	
	@ManyToOne
	private Users userError; 
	
	@ManyToOne
	private Movies moviesError; 
	
	
	
}
