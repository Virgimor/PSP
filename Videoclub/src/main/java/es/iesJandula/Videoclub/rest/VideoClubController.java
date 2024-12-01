package es.iesJandula.Videoclub.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.iesJandula.Videoclub.exception.MovieError;
import es.iesJandula.Videoclub.models.Alquiler;
import es.iesJandula.Videoclub.models.AlquilerId;
import es.iesJandula.Videoclub.models.Movie;
import es.iesJandula.Videoclub.models.User;
import es.iesJandula.Videoclub.repository.AlquilerRepository;
import es.iesJandula.Videoclub.repository.MovieRepository;
import es.iesJandula.Videoclub.repository.UserRepository;
import es.iesJandula.Videoclub.utils.Costantes;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = "/videoClub")
@Log4j2
@NoArgsConstructor
public class VideoClubController {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AlquilerRepository alquilerRepository;
	
	@RequestMapping(method = RequestMethod.POST, value = "/cargarPeliculas")
	public ResponseEntity<?> cargarPeliculas(@RequestParam("csvFile") MultipartFile csvFile) throws IOException, MovieError{
		
		List<Movie> listaMovies = new ArrayList<Movie>();
		Scanner scanner = new Scanner(csvFile.getInputStream());
		
			
			scanner.nextLine();
			while(scanner.hasNextLine()) {
				
				String linea = scanner.nextLine();
				String[] lineaDelFicheroTroceada = linea.split(",");
				
				Movie movies = new Movie();
				
				movies.setMovieId(Long.valueOf(lineaDelFicheroTroceada[0]));
				movies.setTitle(lineaDelFicheroTroceada[1]);
				movies.setDuracion(lineaDelFicheroTroceada[2]);
				
				listaMovies.add(movies);
				this.movieRepository.saveAllAndFlush(listaMovies);
				
				
				
			}
			
			return ResponseEntity.ok().build();
		
	}
	
    @RequestMapping(method = RequestMethod.POST, value = "/cargarUsuarios")
    public ResponseEntity<?> cargarUsuraios(@RequestParam(value="csvFile") MultipartFile csvFile) throws IOException{
    	
    	List<User> listaUsuarios = new ArrayList<User>();
    	Scanner scanner = new Scanner(csvFile.getInputStream());
    	try (scanner){
    		
    		scanner.nextLine();
    		while(scanner.hasNextLine()) {
    			
    			String linea = scanner.nextLine();
    			String[] lineaDelFicheroTroceada = linea.split(",");
    			
    			User users = new User();
    			
    			users.setUserId(Long.valueOf(lineaDelFicheroTroceada[0]));
    			users.setUserName(lineaDelFicheroTroceada[1]);
    			
    			listaUsuarios.add(users);
    			
    			this.userRepository.saveAllAndFlush(listaUsuarios);
    		}
    		
    	} catch (Exception e) {
			// TODO: handle exception
		}
    	
		return ResponseEntity.ok().build();
    	
    }
	
	@RequestMapping(method = RequestMethod.GET, value = "/getMovie")
	public ResponseEntity<?> getMovie(){
		
		List<Movie> listaPeliculas = new ArrayList<Movie>();
		
		listaPeliculas = movieRepository.findAllByAlquilada(Costantes.MOVIE_DISPONIBLE);
		
		return ResponseEntity.ok(listaPeliculas);
		
	}
	
	@RequestMapping(value = "/booking", method = RequestMethod.POST)
	public ResponseEntity<?> searchMovies(@RequestBody(required = true) Movie movie){
		
		List<Movie> listaPeliculas = movieRepository.findByTitle(movie.getTitle());
		return ResponseEntity.ok(listaPeliculas);
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/booking/{movieId}")
	public ResponseEntity<?> bookingMovie(@PathVariable(value = "movieId", required = true) Long movieId,
										  @RequestParam(value = "userId", required = true) Long userId){
		
		try {
			
			if( movieRepository.findByMovieId(movieId) == null) {
				throw new MovieError(1, "La pelicula no existe");
			}
		
			if( userRepository.findByUserId(userId) == null) {
				throw new MovieError(2, "El usuairo no existe");
			}
			AlquilerId alquilerId = new AlquilerId();
			
			alquilerId.setMovieId(movieId);
			alquilerId.setUserId(userId);
			Alquiler alquiler = new Alquiler();
			
			alquiler.setAlquilerId(alquilerId);
			alquilerRepository.saveAndFlush(alquiler);
			
			return ResponseEntity.ok("Película reservada correctamente");
			
		}catch (MovieError movieError) {
			
			return ResponseEntity.status(400).body(movieError.getBodyExceptionMessage());
			
		}
		catch (Exception exception) {
			
			MovieError movieError = new MovieError(404, "El usuario no existe", exception);
			
			return ResponseEntity.status(404).body(movieError.getBodyExceptionMessage()) ;
			
		}
		
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/booking/{movieId}")
	public ResponseEntity<?> cancelBookingMovie(@PathVariable(value = "movieId", required = true) Long movieId,
												@RequestParam(value = "userId", required = true) Long userId) {
		
		try {
			if( movieRepository.findByMovieId(movieId) == null) {
				throw new MovieError(1, "La pelicula no existe");
			}
		
			if( userRepository.findByUserId(userId) == null) {
				throw new MovieError(2, "El usuairo no existe");
			}
			
			AlquilerId alquilerId = new AlquilerId();
			
			alquilerId.setMovieId(movieId);
			alquilerId.setUserId(userId);
			Alquiler alquiler = new Alquiler();
			
			alquiler.setAlquilerId(alquilerId);		
			alquilerRepository.delete(alquiler);
			
			return ResponseEntity.ok("Cancelación de reservada realizada");
		}catch (MovieError movieError) {

			return ResponseEntity.status(400).body(movieError.getBodyExceptionMessage());
		
		}catch (Exception exception) {
			
			MovieError movieError = new MovieError(404, "El usuario no existe", exception);
			
			return ResponseEntity.status(404).body(movieError.getBodyExceptionMessage()) ;
			
		}
		
	}

}
