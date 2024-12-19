package es.iesjandula.psp.spring_security_thymeleaf_mysql.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_role")
public class UserRole
{
	@EmbeddedId
	private UserRoleId userRoleId ;
	
	@ManyToOne
	@JoinColumn(name = "id_user")
	@MapsId("idUser")
	private User idUser ;
	
	@ManyToOne
	@JoinColumn(name = "id_role")
	@MapsId("idRole")
	private Role idRole ;
}
