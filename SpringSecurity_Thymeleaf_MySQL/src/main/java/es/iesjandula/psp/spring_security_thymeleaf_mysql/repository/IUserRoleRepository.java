package es.iesjandula.psp.spring_security_thymeleaf_mysql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.iesjandula.psp.spring_security_thymeleaf_mysql.models.User;
import es.iesjandula.psp.spring_security_thymeleaf_mysql.models.UserRole;
import es.iesjandula.psp.spring_security_thymeleaf_mysql.models.UserRoleId;

public interface IUserRoleRepository extends JpaRepository<UserRole, UserRoleId>
{
	List<UserRole> findByIdUser(User user) ;
}
