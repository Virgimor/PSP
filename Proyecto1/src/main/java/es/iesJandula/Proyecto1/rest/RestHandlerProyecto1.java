package es.iesJandula.Proyecto1.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.iesJandula.Proyecto1.exception.JugadorError;
import es.iesJandula.Proyecto1.model.Jugador;

@RequestMapping(value = "/jugador", produces = {"application/json"})
@RestController
public class RestHandlerProyecto1 {
	
	private static final int MAX_JUGADORES = 200;
	private static final Jugador jugadorNulo = new Jugador();
	private int contJugadores=0;
	private Jugador[] arrayJugadoes = new Jugador[MAX_JUGADORES];
	
	
	
	public RestHandlerProyecto1() {
		rellenarJugadoresNulos();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String Hola() {
		return "hola";
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/anadir_jugador", consumes= {"application/json"})
	public ResponseEntity<?> anadirJugador (@RequestBody (required=false)final Jugador jugador) throws JugadorError{
		
		int cont =0;
		Jugador nuevoJugador = new Jugador(jugador.getNombre(),jugador.getDorsal(),
				jugador.getGoles(), jugador.getTarjetas());
		boolean hayHueco = false;
		
		while (cont<this.arrayJugadoes.length && !hayHueco) {
			if (arrayJugadoes[cont]== jugadorNulo) {
				hayHueco=true;
				
				arrayJugadoes[cont]= nuevoJugador;
			} else {
				cont++;
			}
		}
		
		return ResponseEntity.ok().body(nuevoJugador);
	}
	
	private void rellenarJugadoresNulos() {
		
		for (int i= 0; i<MAX_JUGADORES; i++) {
			arrayJugadoes[i]=jugadorNulo;
		}
	}
	
	
	public void modificarJugador() {
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/buscarJugador")
	public ResponseEntity<?> buscarJugadorPorDorsal (@RequestParam(required = true) final Integer dorsal) throws JugadorError {
		
		boolean encontrado=false;
		Jugador jugadorRes = null;
		
		for (int i =0; i<MAX_JUGADORES; i++) {
			if(arrayJugadoes[i]!=null && arrayJugadoes[i].getDorsal()== dorsal){
				
				jugadorRes=arrayJugadoes[i];
			}
		}
		return ResponseEntity.ok().body(jugadorRes);
		
		
	}
	
	private boolean eliminarJugadorDorsal (Integer dorsal) {
		
		boolean encontrado =false;
		for (int i =0; i<MAX_JUGADORES && !encontrado; i++) {
			if(arrayJugadoes[i].getDorsal()==(dorsal)) {
				encontrado=true;
				arrayJugadoes[i]= jugadorNulo;
				contJugadores--;
			}
			
		}
		return encontrado;
	} 
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/eliminar_jugador", consumes = {"application/json"})
	public ResponseEntity<?> eliminarJugadorPorDorsal (@RequestBody(required = false) final Jugador jugador) {
		
		return ResponseEntity.ok().body(eliminarJugadorDorsal(jugador.getDorsal()));
	}
	@RequestMapping(method = RequestMethod.GET, value = "/listar_jugadores")
	private Jugador[] getJugador() {

		Jugador[] jugadorRes = new Jugador[contJugadores];
		int contJug=0;
		
		for (int i =0; i<arrayJugadoes.length && contJug<contJugadores; i++) {
			if(arrayJugadoes[i]!=null)
			jugadorRes[contJug]=arrayJugadoes[i];
			contJug++;
		}
		
		return jugadorRes;
	}
	
	/*@RequestMapping(method = RequestMethod.GET, value = "/listar_jugadores")
	public ResponseEntity<?> listaJugadores(@RequestBody(required = false) Jugador jugador) {
		for(Jugador jugadorr: this.getJugador()) {
			System.out.println(jugadorr);
		}
		return jugadorr;
	}*/
	
	//@RequestMapping(method = RequestMethod.GET, value = "/listar_jugadores")
	public String mostrarJugadores() {
		Jugador jugadorRes = null;
		for(int i =0; i<arrayJugadoes.length; i++) {
			
			jugadorRes=arrayJugadoes[i];
		}
		
		return jugadorRes.toString();
	}
}
