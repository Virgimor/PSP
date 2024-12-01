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
@Table(name = "infoReview")
@Entity
public class InfoReview 
{

	@Id
	@Column
	private String userName;
	
	@Column
	private String review;

	@ManyToOne
	private Users user; 
	
}
