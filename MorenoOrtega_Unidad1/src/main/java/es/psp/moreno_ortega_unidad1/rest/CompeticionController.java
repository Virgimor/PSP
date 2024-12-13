package es.psp.moreno_ortega_unidad1.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.psp.moreno_ortega_unidad1.dto.ClasificacionEquipoDto;
import es.psp.moreno_ortega_unidad1.dto.EquipoDto;
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
			
			if(this.jugadorRepository.findByNombre(jugador.getNombre()) != null) 
			{
				String mensajeError="El jugador ya existe";
				log.error(mensajeError);
				throw new CompeticionesExeption(100, mensajeError);
			}
			
			Jugador jugadorNuevo = new Jugador();
			jugadorNuevo.setNombre(jugador.getNombre());
			jugadorNuevo.setPosicion(jugador.getPosicion());
				
			this.jugadorRepository.saveAndFlush(jugadorNuevo);
			
			log.info("Jugador creado con éxito");
			return ResponseEntity.status(204).build();			
		} 
		catch (CompeticionesExeption competicionesExeption) 
		{
			// TODO: handle exception
			return ResponseEntity.status(404).body(competicionesExeption.getBodyExceptionMessage());
		}
		catch (Exception exception) 
		{
			CompeticionesExeption competicionesExeption = new CompeticionesExeption(120, "Error generico", exception);
			
			return ResponseEntity.status(500).body(competicionesExeption.getBodyExceptionMessage());
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/equipos", consumes = "application/json")
	public ResponseEntity<?> añadirEquipo(@RequestBody(required = true) EquipoDto equipo)
	{
		try 
		{
			
			if(this.equipoRepository.findById(equipo.getNombre()).isPresent()) 
			{
				String mensajeError="El equipo ya existe";
				log.error(mensajeError);
				throw new CompeticionesExeption(101, mensajeError);
			}
					
			Equipo equipoNuevo = new Equipo();
			equipoNuevo.setNombre(equipo.getNombre());
			equipoNuevo.setCiudad(equipo.getCiudad());
			equipoNuevo.setPuntuacion(0);

			this.equipoRepository.saveAndFlush(equipoNuevo);
			
			for(String nombreJugador : equipo.getJugadores())
			{
				Jugador jugador = this.jugadorRepository.findByNombre(nombreJugador);
				
				if (jugador == null)
				{
					String mensajeError="El jugador no existe";
					log.error(mensajeError);
					throw new CompeticionesExeption(102, mensajeError);
				}
				
				jugador.setEquipo(equipoNuevo) ;
				
				this.jugadorRepository.saveAndFlush(jugador) ;
			}
			
			log.info("Equipo creado con éxito");
			return ResponseEntity.status(204).build();
			
		} 
		catch (CompeticionesExeption competicionesExeption) 
		{
			if(competicionesExeption.getCodigo()==101) {
				
				return ResponseEntity.status(404).body(competicionesExeption.getBodyExceptionMessage());
			}
			else {
				return ResponseEntity.status(405).body(competicionesExeption.getBodyExceptionMessage());
			}
			
		} 
		catch (Exception exception) 
		{
			CompeticionesExeption competicionesExeption = new CompeticionesExeption(121, "Error generico", exception);
			
			return ResponseEntity.status(500).body(competicionesExeption.getBodyExceptionMessage());
		}
		

		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/partidos")
	public ResponseEntity<?> registrarPartido(@RequestHeader(required = true, value = "nombreEquipoLocar") String nombreEquipoLocal,
											  @RequestHeader(required = true, value = "nombreEquipoVisitante") String nombreEquipoVisitante,
											  @RequestHeader(required = true, value = "fecha") String fecha,
											  @RequestHeader(required = true, value = "golesLocal") Integer golesLocal,
											  @RequestHeader(required = true, value = "golesVisitante") Integer golesVisitante)
	{
		try 
		{
			PartidosId partidosId = new PartidosId();
			partidosId.setFecha(fecha);
			
			Partidos partidos = new Partidos();
			
			
			Optional<Equipo> optinalEquipoLocal= this.equipoRepository.findById(nombreEquipoLocal);
			if(!this.equipoRepository.findById(nombreEquipoLocal).isPresent()) 
			{
				String mensajeError="El equipo local no existe";
				log.error(mensajeError);
				throw new CompeticionesExeption(103, mensajeError);
			}
			
			partidos.setEquipoLocal(optinalEquipoLocal.get());
			
			Optional<Equipo> optinalEquipoVisitante= this.equipoRepository.findById(nombreEquipoVisitante);
			if(!this.equipoRepository.findById(nombreEquipoVisitante).isPresent()) 
			{
				String mensajeError="El equipo visitante no existe";
				log.error(mensajeError);
				throw new CompeticionesExeption(104, mensajeError);
			}
			
			partidos.setEquipoVisitante(optinalEquipoVisitante.get());
			partidos.setPartidosId(partidosId);
			
			if(!isGolesValidos(golesLocal)||!isGolesValidos(golesVisitante)) 
			{
				String mensajeError="Los goles deben ser un entero mayor o igual que cero";
				log.error(mensajeError);
				throw new CompeticionesExeption(105, mensajeError);
			}
			
			partidos.setGolesLocal(golesLocal);
			partidos.setGolesVisitante(golesVisitante);
			
			this.partidosRepository.saveAndFlush(partidos);
			
			if(golesLocal>golesVisitante)
			{
				
				this.actualizarPuntuacionEquipo(nombreEquipoLocal, 3);
				
			}
			else if(golesLocal==golesVisitante) {
				
				this.actualizarPuntuacionEquipo(nombreEquipoLocal, 1);
				
				this.actualizarPuntuacionEquipo(nombreEquipoVisitante, 1);
			}
			else
			{
				this.actualizarPuntuacionEquipo(nombreEquipoVisitante, 3);
			}
			
			log.info("Partido creado con éxito");
			return ResponseEntity.status(204).build();
		} 
		
		catch (CompeticionesExeption competicionesExeption) 
		{
			
			if(competicionesExeption.getCodigo()==103) {
				
				return ResponseEntity.status(404).body(competicionesExeption.getBodyExceptionMessage());
			}
			else if(competicionesExeption.getCodigo()==104) {
				
				return ResponseEntity.status(405).body(competicionesExeption.getBodyExceptionMessage());
			}
			else {
				return ResponseEntity.status(406).body(competicionesExeption.getBodyExceptionMessage());
			}
		}
		catch (Exception exception) 
		{
			
			CompeticionesExeption competicionesExeption = new CompeticionesExeption(122, "Error generico", exception);
			
			return ResponseEntity.status(500).body(competicionesExeption.getBodyExceptionMessage());
		} 		
		
	}

	private void actualizarPuntuacionEquipo(String nombreEquipoLocal, int puntosGanados)
	{
		Equipo equipoLocal = this.equipoRepository.findByNombre(nombreEquipoLocal);
		equipoLocal.setPuntuacion(equipoLocal.getPuntuacion() + puntosGanados);
		this.equipoRepository.saveAndFlush(equipoLocal);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/equipos/clasificacion")
	public ResponseEntity<?> clasificacion ()
	{
		
		List<ClasificacionEquipoDto> equipos = this.equipoRepository.findByOrderByPuntuacionDesc();
//		List<ClasificacionEquipoDto> listaEquipos = new ArrayList<ClasificacionEquipoDto>();
		
		//Itera sobre cada equipo para transformalo a ClasificacionEquipoDto
//		for(Equipo equipo: equipos) {
//			
//			ClasificacionEquipoDto clasificacionEquipoDto = new ClasificacionEquipoDto();
//			clasificacionEquipoDto.setNombre(equipo.getNombre());
//			clasificacionEquipoDto.setPuntuacion(equipo.getPuntuacion());
//			
//			listaEquipos.add(clasificacionEquipoDto);
//
//			
//		}
				
		
		return ResponseEntity.ok(equipos);
		
	}
	
	private boolean isGolesValidos(int num) 
	{
		
		return num>=0;
	}

}
