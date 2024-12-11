package es.psp.moreno_ortega_unidad1.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.psp.moreno_ortega_unidad1.dto.JugadorDto;
import es.psp.moreno_ortega_unidad1.models.Equipo;
import es.psp.moreno_ortega_unidad1.models.Jugador;
import es.psp.moreno_ortega_unidad1.models.Partidos;
import es.psp.moreno_ortega_unidad1.models.PartidosId;
import es.psp.moreno_ortega_unidad1.repository.EquipoRepository;
import es.psp.moreno_ortega_unidad1.repository.JugadorRepository;
import es.psp.moreno_ortega_unidad1.repository.PartidosRepository;
import es.psp.moreno_ortega_unidad1.utils.CompeticionesExeption;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@NoArgsConstructor
@RequestMapping(value = "/competicion")
public class CompeticionController 
{
	
	@Autowired
	private JugadorRepository jugadorRepository;
	
	@Autowired
	private EquipoRepository equipoRepository;
	
	@Autowired
	private PartidosRepository partidosRepository;
	
	@RequestMapping(method = RequestMethod.POST, value = "/jugadores", consumes = "application/json")
	public ResponseEntity<?> añadirJugador(@RequestBody(required = true) JugadorDto jugador)
	{
		
		try 
		{
			String mensajeError="El jugador ya existe";
			if(this.jugadorRepository.findByNombre(jugador.getNombre()) != null) 
			{
				log.error(mensajeError);
				throw new CompeticionesExeption(404, mensajeError);
			}
			log.info("Jugador creado con éxito");
			//Jugador jugadorNuevo = this.jugadorRepository.saveAndFlush(jugador);
			
			return ResponseEntity.status(204).build();			
		} 
		catch (CompeticionesExeption competicionesExeption) 
		{
			// TODO: handle exception
			return ResponseEntity.status(404).body(competicionesExeption.getBodyExceptionMessage());
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/equipos", consumes = "application/json")
	public ResponseEntity<?> añadirEquipo(@RequestBody(required = true) Equipo equipo)
	{
		try 
		{
			
			if(this.equipoRepository.findById(equipo.getNombre()).isPresent()) 
			{
				String mensajeError="El equipo ya existe";
				log.error(mensajeError);
				throw new CompeticionesExeption(404, mensajeError);
			}
					
			
			log.info("Equipo creado con éxito");
			Equipo equipoNuevo =this.equipoRepository.saveAndFlush(equipo);
			
			return ResponseEntity.status(204).build();
			
		} 
		catch (CompeticionesExeption competicionesExeption) 
		{
			// TODO: handle exception
			return ResponseEntity.status(404).body(competicionesExeption.getBodyExceptionMessage());
		} 
		catch (Exception exception) 
		{
			CompeticionesExeption competicionesExeption = new CompeticionesExeption(405, "El jugador no existe", exception);
			
			return ResponseEntity.status(405).body(competicionesExeption.getBodyExceptionMessage());
		} 

		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/partidos")
	public ResponseEntity<?> registrarPartido(@RequestHeader(required = true, value = "nombreEquipoLocar") String nombreEquipoLocar,
											  @RequestHeader(required = true, value = "nombreEquipoVisitante") String nombreEquipoVisitante,
											  @RequestHeader(required = true, value = "fecha") String fecha,
											  @RequestHeader(required = true, value = "golesLocal") Integer golesLocal,
											  @RequestHeader(required = true, value = "golesVisitante") Integer golesVisitante)
	{
		try 
		{
			
			if(!this.equipoRepository.findById(nombreEquipoLocar).isPresent()) 
			{
				String mensajeError="El equipo local no existe";
				log.error(mensajeError);
				throw new CompeticionesExeption(404, mensajeError);
			}
			
			if(!this.equipoRepository.findById(nombreEquipoVisitante).isPresent()) 
			{
				String mensajeError="El equipo visitante no existe";
				log.error(mensajeError);
				throw new CompeticionesExeption(405, mensajeError);
			}
			
			if(!isGolesValidos(golesLocal)||!isGolesValidos(golesVisitante)) 
			{
				String mensajeError="Los goles deben ser un entero mayor o igual que cero";
				log.error(mensajeError);
				throw new CompeticionesExeption(406, mensajeError);
			}
			
			PartidosId partidosId = new PartidosId();
			partidosId.setEquipoLocal(nombreEquipoLocar);
			partidosId.setEquipoVisitante(nombreEquipoVisitante);
			partidosId.setFecha(fecha);
			Partidos partidos = new Partidos();
			partidos.setPartidosId(partidosId);
			partidos.setGolesLocal(golesLocal);
			partidos.setGolesVisitante(golesVisitante);
			
			this.partidosRepository.save(partidos);
			
			return ResponseEntity.status(204).build();
		} 
		catch (CompeticionesExeption competicionesExeption) 
		{
			// TODO: handle exception
			return ResponseEntity.status(404).body(competicionesExeption.getBodyExceptionMessage());
		}
		catch (Exception exception) 
		{
			CompeticionesExeption competicionesExeption = new CompeticionesExeption(405, "El jugador no existe", exception);
			
			return ResponseEntity.status(405).body(competicionesExeption.getBodyExceptionMessage());
		} 		
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/equipos/clasificacion")
	public ResponseEntity<?> clasificacion ()
	{
		List<Equipo> listaEquipos = new ArrayList<Equipo>();
		
		listaEquipos = this.equipoRepository.findByOrderByPuntuacionAsc();
		
		return ResponseEntity.ok(listaEquipos);
		
	}
	
	
	
	private boolean isGolesValidos(int num) 
	{
		
		return num>=0;
	}

}
