package es.psp.moreno_ortega_unidad1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.psp.moreno_ortega_unidad1.models.Jugador;

public interface JugadorRepository extends JpaRepository<Jugador, Integer>
{

	Jugador findByNombre(String nombre);

}
