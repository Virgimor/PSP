package es.psp.moreno_ortega_unidad1.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import es.psp.moreno_ortega_unidad1.dto.JugadorDto;
import es.psp.moreno_ortega_unidad1.models.Jugador;

public interface JugadorRepository extends JpaRepository<Jugador, Integer>
{
	
	Jugador findByDorsal(Jugador jugador);

	Jugador findByNombre(String nombre);


}
