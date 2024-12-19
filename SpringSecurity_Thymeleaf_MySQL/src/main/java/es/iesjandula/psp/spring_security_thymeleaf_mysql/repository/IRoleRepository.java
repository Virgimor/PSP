package es.iesjandula.psp.spring_security_thymeleaf_mysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.iesjandula.psp.spring_security_thymeleaf_mysql.models.Role;

public interface IRoleRepository extends JpaRepository<Role, Integer>
{
	Role findByRole(String role) ;
}
