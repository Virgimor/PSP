package ies.jandula.query.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ies.jandula.query.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;

@Service
public class ControllerInstituto {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	//cuando se crea una intancia de controllerInstituto
	@PostConstruct
	public void init() {
		System.out.println(this.usuarioRepository.findByNif("123456789A"));
		
		//System.out.println(this.usuarioRepository.findByNifContaining("1"));
		//System.out.println(this.usuarioRepository.findByAprobadoFalse());
		//System.out.println(this.usuarioRepository.findByAprobadoTrue());
		//System.out.println(this.usuarioRepository.findByEdadOrderByEdadAsc(10));
		//System.out.println(this.usuarioRepository.findTop10ByDireccionOrderByEdadDesc("1"));
		
		//System.out.println(this.usuarioRepository.findByEdadBetween(20, 30));
	}

}
