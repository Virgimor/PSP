package es.iesJandula.Videoclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.iesJandula.Videoclub.models.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer>{
	
	List<Movie> findAllByAlquilada(String alquilada);

	Movie findAll(Movie movie); 

}
