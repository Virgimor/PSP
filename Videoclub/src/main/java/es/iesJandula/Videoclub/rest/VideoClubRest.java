package es.iesJandula.Videoclub.rest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.iesJandula.Videoclub.MovieError;
import es.iesJandula.Videoclub.models.Movie;
import es.iesJandula.Videoclub.repository.MovieRepository;
import es.iesJandula.Videoclub.repository.UserRepository;
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
	
	@RequestMapping(method = RequestMethod.POST, value="/movie", consumes= {"multipart/form-data"})
	public ResponseEntity<?> uploadMovies(@RequestParam(required = false) final MultipartFile archivo, HttpSession session){
		try
		{    	
			String contenidoJson = new String(archivo.getBytes());
			
			List<Movie> movie = Arrays.asList(new ObjectMapper().readValue(contenidoJson, Movie[].class));
			
			session.setAttribute("peliculas", movie);
			return new ResponseEntity<>("Películas almacenadas en sesión exitosamente", HttpStatus.OK);
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

}
