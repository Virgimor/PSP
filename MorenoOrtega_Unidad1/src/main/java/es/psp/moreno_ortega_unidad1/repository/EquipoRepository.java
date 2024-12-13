package es.psp.moreno_ortega_unidad1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.psp.moreno_ortega_unidad1.dto.ClasificacionEquipoDto;
import es.psp.moreno_ortega_unidad1.models.Equipo;

public interface EquipoRepository extends JpaRepository<Equipo, String>
{
	@Query("SELECT new es.psp.moreno_ortega_unidad1.dto.ClasificacionEquipoDto(e.nombre, e.puntuacion) FROM Equipo e ORDER BY e.puntuacion DESC")
	List<ClasificacionEquipoDto> findByOrderByPuntuacionDesc();
	
	
	
	Equipo findByNombre(String nombre);

}
