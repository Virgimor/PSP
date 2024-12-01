package es.iesJandula.Videoclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.iesJandula.Videoclub.models.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findByUserId(Long userId);

}
