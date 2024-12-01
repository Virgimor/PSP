package ies.jandula.VideoClubBien.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ies.jandula.VideoClubBien.models.Movies;

public interface MoviesRepository extends JpaRepository<Movies, Integer> {

	// Método de consulta personalizado para buscar películas por título
	// Los registros que contengan un titulo con la cadena de caracteres especifica
	List<Movies> findByTituloContaining(String titulo); // "Containing" for partial matches
}
