package es.iesjandula.psp.spring_security_thymeleaf_mysql.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.iesjandula.psp.spring_security_thymeleaf_mysql.models.Role;
import es.iesjandula.psp.spring_security_thymeleaf_mysql.repository.IRoleRepository;
import es.iesjandula.psp.spring_security_thymeleaf_mysql.utils.Constants;
import jakarta.annotation.PostConstruct;

@Service
public class InicializacionSistema
{
	@Autowired
	private IRoleRepository iRoleRepository ;
	
	/**
	 * Este método se encarga de crear los roles
	 */
	@PostConstruct
	public void createRoles()
	{
		// Si el role no existe ...
		if (this.iRoleRepository.findByRole(Constants.ROLE_ADMINISTRADOR) == null)
		{
			// ... lo creamos
			this.iRoleRepository.saveAndFlush(new Role(1L, Constants.ROLE_ADMINISTRADOR)) ;
		}

		// Si el role no existe ...
		if (this.iRoleRepository.findByRole(Constants.ROLE_USUARIO) == null)
		{
			// ... lo creamos
			this.iRoleRepository.saveAndFlush(new Role(2L, Constants.ROLE_USUARIO)) ;
		}
	}
}
