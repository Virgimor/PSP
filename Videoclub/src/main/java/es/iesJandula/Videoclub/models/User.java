package es.iesJandula.Videoclub.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class User {
	
	@Id
	private Long userId;
	
	@Column(length = 100, nullable = false)
	private String userName;

}
