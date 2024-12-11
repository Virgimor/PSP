package es.psp.moreno_ortega_unidad1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.psp.moreno_ortega_unidad1.models.Partidos;
import es.psp.moreno_ortega_unidad1.models.PartidosId;

public interface PartidosRepository extends JpaRepository<Partidos, PartidosId>
{

}
