package es.iesJandula.Videoclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.iesJandula.Videoclub.models.Alquiler;
import es.iesJandula.Videoclub.models.AlquilerId;

public interface AlquilerRepository extends JpaRepository<Alquiler, AlquilerId>{
	
}
