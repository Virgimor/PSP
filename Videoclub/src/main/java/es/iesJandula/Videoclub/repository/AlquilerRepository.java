package es.iesJandula.Videoclub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.iesJandula.Videoclub.models.Alquiler;
import es.iesJandula.Videoclub.models.AlquilerId;

public interface AlquilerRepository extends JpaRepository<Alquiler, AlquilerId>{

	Optional<Alquiler> findById(AlquilerId id);

	Alquiler findByIdAndId(Long userId, Long movieId);


	

}
