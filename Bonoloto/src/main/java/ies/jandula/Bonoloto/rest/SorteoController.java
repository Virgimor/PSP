package ies.jandula.Bonoloto.rest;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ies.jandula.Bonoloto.exception.LoteriaException;
import ies.jandula.Bonoloto.models.ResultadoLoteria;
import ies.jandula.Bonoloto.models.Sorteo;
import ies.jandula.Bonoloto.repository.SorteoRepository;
import ies.jandula.Bonoloto.utils.DatesUtil;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@NoArgsConstructor
@RequestMapping(value = "/loteria")
public class SorteoController {
	
	@Autowired
	private SorteoRepository sorteoRepository;
	
	LoteriaException loteriaException = new LoteriaException();
	
	@RequestMapping(method = RequestMethod.POST, value = "/resultado")
	public ResponseEntity<?> cargaHistorico(@RequestParam("csvFile") MultipartFile csvFile) throws IOException{
		
		List<Sorteo> listaLoteria = new ArrayList<Sorteo>();
		Scanner scanner =new Scanner(csvFile.getInputStream()); 
		try (scanner){
			scanner.nextLine();
			
			while(scanner.hasNextLine()) {
				String stringContent = scanner.nextLine();
				String[] elemntos = stringContent.split(",");
				
				Sorteo loteria = new Sorteo();
				
				loteria.setFehca(elemntos[0]);
				
				loteria.setNumero1(Integer.parseInt(elemntos[1]));
				loteria.setNumero2(Integer.parseInt(elemntos[2]));
				loteria.setNumero3(Integer.parseInt(elemntos[3]));
				loteria.setNumero4(Integer.parseInt(elemntos[4]));
				loteria.setNumero5(Integer.parseInt(elemntos[5]));
				loteria.setNumero6(Integer.parseInt(elemntos[6]));
				
				loteria.setComplementario(Integer.parseInt(elemntos[7]));
				if(elemntos.length==9) {
					loteria.setReintegro(Integer.parseInt(elemntos[8]));
				}
				
				listaLoteria.add(loteria);
				
			}
			this.sorteoRepository.saveAllAndFlush(listaLoteria);
			
			
		}catch (NumberFormatException e) {
			// TODO: handle exception
			
			
		}finally {
			scanner.close();
		}
		return ResponseEntity.ok("Fichero subido correctamente");
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/resultado/{num1}/{num2}/{num3}/{num4}/{num5}/{num6}/{reintegro}/")
	ResponseEntity<?> comprobarPremio(@PathVariable(value = "num1", required = true) int num1,
									  @PathVariable(value = "num2", required = true) int num2,
									  @PathVariable(value = "num3", required = true) int num3,
									  @PathVariable(value = "num4", required = true) int num4,
									  @PathVariable(value = "num5", required = true) int num5,
									  @PathVariable(value = "num6", required = true) int num6,
									  @PathVariable(value = "reintegro", required = true) int reintegro,
									  @RequestParam(value = "fecha", required = false) String fecha){
		
		if(!isValidNumero(num1)||!isValidNumero(num1)||!isValidNumero(num1)||!isValidNumero(num1)||!isValidNumero(num1)||
				!isValidNumero(num1)||!isValidReintegro(reintegro)) {
			
			return ResponseEntity.status(400).body(loteriaException.getBodyExceptionMessage());
		}
		/*if() {
			return ResponseEntity.status(403).body(loteriaException.getBodyExceptionMessage());
		}*/
		if(!isValidLotteryDate(fecha, "dd/MM/yyyy")) {
			return ResponseEntity.status(404).body(loteriaException.getBodyExceptionMessage());
		}
		
		Sorteo sorteo = new Sorteo();
		ResultadoLoteria resultadoLoteria=new ResultadoLoteria();
		
		
		
		if(this.sorteoRepository.findByNumero1(num1)&&this.sorteoRepository.findByNumero2(num2)==sorteo.getNumero2()&&
			this.sorteoRepository.findByNumero3(num3)==sorteo.getNumero3()&&this.sorteoRepository.findByNumero4(num4)==sorteo.getNumero4()&&
			this.sorteoRepository.findByNumero5(num5)==sorteo.getNumero5()&&this.sorteoRepository.findByNumero6(num6)==sorteo.getNumero6()&&
					this.sorteoRepository.findByReintegro(reintegro)==sorteo.getReintegro()){
			
			resultadoLoteria.setNumeros(List.of(num1,num2,num3,num4,num5,num6));
			resultadoLoteria.setReintegro(reintegro);
			resultadoLoteria.setPremio("1ª Categoría (6 aciertos)");
			
		}
		if(num1==sorteo.getNumero1()&&num2==sorteo.getNumero2()&&num3==sorteo.getNumero3()&&num4==sorteo.getNumero4()&&
				num5==sorteo.getNumero5()&&reintegro==sorteo.getReintegro()){
			
			resultadoLoteria.setNumeros(List.of(num1,num2,num3,num4,num5));
			resultadoLoteria.setReintegro(reintegro);
			resultadoLoteria.setPremio("2ª Categoría (6 aciertos)");
			
		}
		if(num1==sorteo.getNumero1()&&num2==sorteo.getNumero2()&&num3==sorteo.getNumero3()&&num4==sorteo.getNumero4()&&
				num5==sorteo.getNumero5()){
			
			resultadoLoteria.setNumeros(List.of(num1,num2,num3,num4,num5));
			resultadoLoteria.setPremio("3ª Categoría (5 aciertos)");
			
		}
		if(num1==sorteo.getNumero1()&&num2==sorteo.getNumero2()&&num3==sorteo.getNumero3()&&num4==sorteo.getNumero4()){
			
			resultadoLoteria.setNumeros(List.of(num1,num2,num3,num4));
			resultadoLoteria.setPremio("4ª Categoría (4 aciertos)");
			
		}
		if(num1==sorteo.getNumero1()&&num2==sorteo.getNumero2()&&num3==sorteo.getNumero3()){
			
			resultadoLoteria.setNumeros(List.of(num1,num2,num3));
			resultadoLoteria.setPremio("5ª Categoría (3 aciertos)");
			
		}
		if(num1==sorteo.getNumero1()){
			
			resultadoLoteria.setNumeros(List.of(num1,num2,num3,num4,num5));
			resultadoLoteria.setPremio("6ª Categoría (reintegro)");
		}
		
		return ResponseEntity.ok(resultadoLoteria);
		
	}
	
	private boolean isValidNumero(int num) {
		
		return num>=1 && num<=49;
	}
	
	private boolean isValidReintegro(int num) {
		
		return num>=0 && num<=9;
	}
	
	private boolean isDuplicado(int num1, int num2) {
		
		return num1!=num2;
	}
	
	private boolean isValidLotteryDate(String fecha, String fechaFormateada) {
        // Validación de la fecha en formato 'dd/MM/yyyy'

		DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern(fechaFormateada);
		try {
			dateTimeFormatter.parse(fecha);
		}catch (DateTimeParseException e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

}
