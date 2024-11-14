package es.iesJandula.Videoclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.iesJandula.Videoclub.models.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer>{

}
