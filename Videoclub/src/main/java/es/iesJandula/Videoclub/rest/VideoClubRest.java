package es.iesJandula.Videoclub.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.iesJandula.Videoclub.exception.MovieError;
import es.iesJandula.Videoclub.models.Alquiler;
import es.iesJandula.Videoclub.models.Movie;
import es.iesJandula.Videoclub.models.User;
import es.iesJandula.Videoclub.repository.AlquilerRepository;
import es.iesJandula.Videoclub.repository.MovieRepository;
import es.iesJandula.Videoclub.repository.UserRepository;
import es.iesJandula.Videoclub.utils.Costantes;
import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequestMapping(value = "/videoclub")
@RestController
@Log4j2
@NoArgsConstructor
public class VideoClubRest {
	
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AlquilerRepository alquilerRepository;
	
	@RequestMapping(method = RequestMethod.POST, value="/movie", consumes= {"multipart/form-data"})
	public ResponseEntity<?> uploadMovies(@RequestParam(required = false) final MultipartFile archivo){
		try
		{    	
			String contenidoJson = new String(archivo.getBytes());
			
			Scanner scanner = new Scanner(contenidoJson);
			
			this.movieRepository.
			
			
			
			
			return ResponseEntity.ok().body(this.movies);
		}
		catch (IOException ioException)
		{
			ioException.printStackTrace();
			
			return new ResponseEntity<>("Error al leer el archivo", HttpStatus.BAD_REQUEST);
		}
	}
	
	/*@RequestMapping(method = RequestMethod.GET, value="/movie", consumes= {"multipart/form-data"})
	public ResponseEntity<?> downloadMovies(@RequestParam(required = false) final MultipartFile movie){
		
	}*/
	
	@RequestMapping(method = RequestMethod.GET, value = "/booking")
	public ResponseEntity<?> getMovies(){
		
		List<Movie> peliculas = new ArrayList<>();
		
		peliculas= movieRepository.findAllByAlquilada(Costantes.MOVIE_DISPONIBLE);
		
		return ResponseEntity.ok(peliculas);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/booking")
	public ResponseEntity<?> searchMovies(@RequestBody Movie movie){
		
		Movie pelicula = new Movie();
		pelicula.setAlquilada(Costantes.MOVIE_DISPONIBLE);
		pelicula= movieRepository.findAll(movie);
		
		return ResponseEntity.ok(pelicula);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/booking/{movieId}")
	public ResponseEntity<?> bookingMovie(@RequestParam (value = "userId", required = true)Long userId,
										  @PathVariable(value = "movieId", required = true) Long movieId){
		
		Alquiler alquiler = new Alquiler();
		Movie movie = new Movie();
		
		alquiler = alquilerRepository.findById(userId, movieId);
		
		alquilerRepository.saveAndFlush(alquiler);
		movie.setAlquilada(Costantes.MOVIE_ALQUILADA);
		
		
		return ResponseEntity.ok(alquiler);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/booking/{movieId}")
	public ResponseEntity<?> cancelBookingMovie(@RequestParam (value = "userId", required = true)Long userId,
										  		@PathVariable(value = "movieId", required = true) Long movieId){
		
		Alquiler alquiler = new Alquiler();
		Movie movie = new Movie();
		
		alquiler = alquilerRepository.findById(userId, movieId);
		
		alquilerRepository.delete(alquiler);
		movie.setAlquilada(Costantes.MOVIE_DISPONIBLE);
		
		return ResponseEntity.ok(alquiler);
	}

}
