package es.iesJandula.Videoclub.rest;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	public ResponseEntity<?> uploadMovies(@RequestParam(value="archivo" ,required = true) final MultipartFile archivoMovie){
		try {    	
			// Validar que el archivo no sea nulo
            if (archivoMovie == null || archivoMovie.isEmpty()) {
                return ResponseEntity.badRequest().body("Archivo no proporcionado o vacío");
            }
		     // Leer datos del archivo JSON
		    byte[] jsonData = archivoMovie.getBytes();

		    // Crear instancia de ObjectMapper
		    ObjectMapper objectMapper = new ObjectMapper();
		
		    // Convertir el JSON string a una lista de objetos Movie
		    List<Movie> movies = objectMapper.readValue(jsonData,
		            objectMapper.getTypeFactory().constructCollectionType(List.class, Movie.class));
		
		    movieRepository.saveAllAndFlush(movies);
		
		
		    return ResponseEntity.ok("Archivo subido con exito");
		}
		catch (IOException ioException)
		{
			ioException.printStackTrace();
			MovieError movieError = new MovieError(1, "La lista de peliculas no tiene el formato correcto", ioException);
			
			return ResponseEntity.status(500).body("Error al cargar el archivo: " + ioException.getMessage());
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/movie", consumes= {"multipart/form-data"})
	public ResponseEntity<?> downloadMovies() throws FileNotFoundException, IOException{
		  List<Movie> movies = movieRepository.findAll();

	        ObjectMapper objectMapper = new ObjectMapper();
	        try {
	            String jsonArray = objectMapper.writeValueAsString(movies);
	             FileOutputStream outputStream = new FileOutputStream("moviesDownload.txt");
	                byte[] strToBytes = jsonArray.getBytes();
	                outputStream.write(strToBytes);

	                outputStream.close();
	        } catch (JsonProcessingException jsonProcessingException) {
	            // TODO Auto-generated catch block
	        	jsonProcessingException.printStackTrace();
	        }
	        return ResponseEntity.ok("Archivo descargado con exito");
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/user", consumes= {"multipart/form-data"})
	public ResponseEntity<?> uploadUser(@RequestParam(value="archivoUser" ,required = true) final MultipartFile archivoUser){
		try {    	
			// Validar que el archivo no sea nulo
            if (archivoUser == null || archivoUser.isEmpty()) {
                return ResponseEntity.badRequest().body("Archivo no proporcionado o vacío");
            }
		     // Leer datos del archivo JSON
		    byte[] jsonData = archivoUser.getBytes();

		    // Crear instancia de ObjectMapper
		    ObjectMapper objectMapper = new ObjectMapper();
		
		    // Convertir el JSON string a una lista de objetos Movie
		    List<User> users = objectMapper.readValue(jsonData,
		            objectMapper.getTypeFactory().constructCollectionType(List.class, Movie.class));
		
		    userRepository.saveAllAndFlush(users);
		
		
		    return ResponseEntity.ok("Archivo subido con exito");
		}
		catch (IOException ioException)
		{
			ioException.printStackTrace();
			
			return ResponseEntity.status(500).body("Error al cargar el archivo: " + ioException.getMessage());
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/user", consumes= {"multipart/form-data"})
	public ResponseEntity<?> downloadUser() throws FileNotFoundException, IOException{
		  List<User> users = userRepository.findAll();

	        ObjectMapper objectMapper = new ObjectMapper();
	        try {
	            String jsonArray = objectMapper.writeValueAsString(users);
	             FileOutputStream outputStream = new FileOutputStream("usersDownload.txt");
	                byte[] strToBytes = jsonArray.getBytes();
	                outputStream.write(strToBytes);

	                outputStream.close();
	        } catch (JsonProcessingException jsonProcessingException) {
	            // TODO Auto-generated catch block
	        	jsonProcessingException.printStackTrace();
	        }
	        return ResponseEntity.ok("Archivo descargado con exito");
	}
	
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
		pelicula= (Movie) movieRepository.findByTitle(movie.getTitle());
		
		return ResponseEntity.ok(pelicula);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/booking/{movieId}")
	public ResponseEntity<?> bookingMovie(@RequestParam (value = "userId", required = true)Long userId,
										  @PathVariable(value = "movieId", required = true) Long movieId){
		
		Alquiler alquiler = new Alquiler();
		Movie movie = new Movie();
		
		alquiler = alquilerRepository.findByIdAndId(userId, movieId);
		
		alquilerRepository.saveAndFlush(alquiler);
		movie.setAlquilada(Costantes.MOVIE_ALQUILADA);
		
		
		return ResponseEntity.ok(alquiler);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/booking/{movieId}")
	public ResponseEntity<?> cancelBookingMovie(@RequestParam (value = "userId", required = true)Long userId,
										  		@PathVariable(value = "movieId", required = true) Long movieId){
		
		Alquiler alquiler = new Alquiler();
		Movie movie = new Movie();
		
		alquiler = alquilerRepository.findByIdAndId(userId, movieId);
		
		alquilerRepository.delete(alquiler);
		movie.setAlquilada(Costantes.MOVIE_DISPONIBLE);
		
		return ResponseEntity.ok(alquiler);
	}

}
