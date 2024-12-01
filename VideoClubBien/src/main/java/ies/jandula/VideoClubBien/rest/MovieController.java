package ies.jandula.VideoClubBien.rest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import ies.jandula.VideoClubBien.models.Movies;
import ies.jandula.VideoClubBien.repository.MoviesRepository;
import ies.jandula.VideoClubBien.utils.VideoclubException;

@RestController
@RequestMapping("/videoclub")
public class MovieController 
{
	@Autowired
	private MoviesRepository moviesRepository;

	@PostMapping("/movies")
	public ResponseEntity<String> uploadMovies(@RequestParam("jsonFile") MultipartFile jsonFile) throws VideoclubException 
	{
		try 
		{
			// Procesar el archivo JSON y almacenar los datos en sesión
			List<Movies> movies = parseJsonFile(jsonFile);
			moviesRepository.saveAllAndFlush(movies);
			return ResponseEntity.ok("Películas cargadas correctamente.");
		 } catch (IOException e) {
		        
		        throw new VideoclubException(400, "Error al cargar las películas: " + e.getMessage(), e);
		    } catch (Exception e) {
		        
		        throw new VideoclubException(500, "Error inesperado al procesar las películas.", e);
		    }
		}

	private List<Movies> parseJsonFile(MultipartFile file) throws IOException 
	{
		
		ObjectMapper mapper = new ObjectMapper();
		return Arrays.asList(mapper.readValue(file.getInputStream(), Movies[].class));
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/cargarPeliculas")
	public ResponseEntity<?> cargarPeliculas(@RequestParam("csvFile") MultipartFile csvFile) throws IOException{
		
		List<Movies> listaMovies = new ArrayList<Movies>();
		Scanner scanner = new Scanner(csvFile.getInputStream());
		
		try (scanner){
			
			scanner.nextLine();
			while(scanner.hasNextLine()) {
				
				String linea = scanner.nextLine();
				String[] lineaDelFicheroTroceada = linea.split(",");
				
				Movies movies = new Movies();
				
				movies.setMovieId(Integer.valueOf(lineaDelFicheroTroceada[0]));
				movies.setTitulo(lineaDelFicheroTroceada[1]);
				movies.setDuracion(lineaDelFicheroTroceada[2]);
				
				listaMovies.add(movies);
				this.moviesRepository.saveAllAndFlush(listaMovies);
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return ResponseEntity.ok().build();
		
	}

}
