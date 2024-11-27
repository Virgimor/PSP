package ies.jandula.Bonoloto.rest;

import java.io.IOException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ies.jandula.Bonoloto.exception.LoteriaException;
import ies.jandula.Bonoloto.models.LotteryResults;
import ies.jandula.Bonoloto.models.LotteryResultsId;
import ies.jandula.Bonoloto.repository.LotteryResultsRepository;
import ies.jandula.Bonoloto.utils.DatesUtil;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@NoArgsConstructor
@RequestMapping(value = "/loteria")
public class LotteryController {
	
	@Autowired
	private LotteryResultsRepository lotteryResultsRepository;
	
	@RequestMapping(method = RequestMethod.POST, value = "/resultado", consumes = {"application/json"})
	public ResponseEntity<?> cargaHistorico(MultipartFile csvFile) throws IOException{
		
		Scanner scanner =null; 
		scanner.nextLine();
		
		while(scanner.hasNextLine()) {
			String stringContent = new String(csvFile.getBytes());
			String[] elemntos = stringContent.split(",");
			
			LotteryResultsId loteriaId = new LotteryResultsId();
			
			try {
				loteriaId.setFehca(DatesUtil.crearFechaDesdeString(elemntos[0]));
			} catch (LoteriaException  e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			loteriaId.setNumero1(Integer.valueOf(elemntos[1]));
			loteriaId.setNumero2(Integer.valueOf(elemntos[2]));
			loteriaId.setNumero3(Integer.valueOf(elemntos[3]));
			loteriaId.setNumero4(Integer.valueOf(elemntos[4]));
			loteriaId.setNumero5(Integer.valueOf(elemntos[5]));
			loteriaId.setNumero6(Integer.valueOf(elemntos[6]));
			
			LotteryResults loteria = new LotteryResults();
			loteria.setLotteryResultsId(loteriaId);
			loteria.setComplementario(Integer.valueOf(elemntos[7]));
			if(elemntos[8]!=null) {
				loteria.setReintegro(Integer.valueOf(elemntos[8]));
			}
			
			this.lotteryResultsRepository.saveAndFlush(loteria);
		}
		
		return ResponseEntity.ok("Fichero subido correctamente");
		
	}

}
