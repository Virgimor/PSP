package ies.jandula.VideoClubBien.rest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import ies.jandula.VideoClubBien.models.Users;
import ies.jandula.VideoClubBien.repository.UsersRepository;
import ies.jandula.VideoClubBien.utils.VideoclubException;


@RestController
@RequestMapping("/videoclub")
public class UsersController {
    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/users")
    public ResponseEntity<String> uploadUsers(@RequestParam("jsonFile") MultipartFile jsonFile) throws VideoclubException {
        try {
            List<Users> users = parseJsonFile(jsonFile);
            usersRepository.saveAll(users);
            return ResponseEntity.ok("Usuarios cargados correctamente.");
        } catch (IOException e) {
            throw new VideoclubException(400, "Error al cargar los usuarios: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new VideoclubException(500, "Error inesperado al procesar los usuarios.", e);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<Users>> downloadUsers() {
        List<Users> users = usersRepository.findAll();
        return ResponseEntity.ok(users);
    }

    private List<Users> parseJsonFile(MultipartFile file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.asList(mapper.readValue(file.getInputStream(), Users[].class));
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/cargarFichero")
    public ResponseEntity<?> cargarUsuraios(@RequestParam(value="csvFile") MultipartFile csvFile) throws IOException{
    	
    	List<Users> listaUsuarios = new ArrayList<Users>();
    	Scanner scanner = new Scanner(csvFile.getInputStream());
    	try (scanner){
    		
    		scanner.nextLine();
    		while(scanner.hasNextLine()) {
    			
    			String linea = scanner.nextLine();
    			String[] lineaDelFicheroTroceada = linea.split(",");
    			
    			Users users = new Users();
    			
    			users.setUserId(Integer.valueOf(lineaDelFicheroTroceada[0]));
    			users.setUserName(lineaDelFicheroTroceada[1]);
    			
    			listaUsuarios.add(users);
    			
    			this.usersRepository.saveAllAndFlush(listaUsuarios);
    		}
    		
    	} catch (Exception e) {
			// TODO: handle exception
		}
    	
		return ResponseEntity.ok().build();
    	
    }
}
