package es.psp.moreno_ortega_unidad1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.psp.moreno_ortega_unidad1.models.Equipo;

public interface EquipoRepository extends JpaRepository<Equipo, String>
{
	List<Equipo> findByOrderByPuntuacionAsc();

}
