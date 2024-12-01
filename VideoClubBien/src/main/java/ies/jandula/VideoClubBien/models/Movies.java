package ies.jandula.VideoClubBien.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movies")
@Entity
public class Movies {

	@Id
	@Column
	private Integer movieId; 
	
	@Column
	private String titulo;

	@Column
	private String duracion;
	
	
	
	
}
