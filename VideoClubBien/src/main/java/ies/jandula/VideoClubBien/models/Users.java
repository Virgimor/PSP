package ies.jandula.VideoClubBien.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
public class Users 
{

	@Id
	@Column
	private Integer userId; 
	
	@Column(unique = true)
	private String userName; 
	
	@OneToMany(mappedBy = "user")
	private List<InfoReview>infoReview; 
	
	@OneToMany(mappedBy = "userError")
	private List<MovieError>moviesError; 
	
	
	
}
