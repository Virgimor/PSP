package es.iesjandula.psp.spring_security_thymeleaf_mysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.iesjandula.psp.spring_security_thymeleaf_mysql.models.User;

public interface IUserRepository extends JpaRepository<User, Integer>
{
	User findByUserName(String userName);
}
