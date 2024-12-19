package es.iesjandula.psp.spring_security_thymeleaf_mysql.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.iesjandula.psp.spring_security_thymeleaf_mysql.dto.UserRegistrationDto;
import es.iesjandula.psp.spring_security_thymeleaf_mysql.models.Role;
import es.iesjandula.psp.spring_security_thymeleaf_mysql.models.User;
import es.iesjandula.psp.spring_security_thymeleaf_mysql.models.UserRole;
import es.iesjandula.psp.spring_security_thymeleaf_mysql.models.UserRoleId;
import es.iesjandula.psp.spring_security_thymeleaf_mysql.repository.IRoleRepository;
import es.iesjandula.psp.spring_security_thymeleaf_mysql.repository.IUserRepository;
import es.iesjandula.psp.spring_security_thymeleaf_mysql.repository.IUserRoleRepository;
import es.iesjandula.psp.spring_security_thymeleaf_mysql.utils.MyException;
import jakarta.transaction.Transactional;

/**
 * @author David Martinez
 * interfaz de spring security que se implementa para que podamos
 * consultar qué usuarios tienen autoridad o no para acceder a recursos y ver si existen o no
 *
 */
@Service
public class MyUserDetailsService implements UserDetailsService
{
	@Autowired
	private IUserRepository iUserRepository ;
	
	@Autowired
	private IRoleRepository iRoleRepository ;
	
	@Autowired
	private IUserRoleRepository iUserRoleRepository ;

	/**
	 * Este método sirve para comprobar si existe el usuario
	 * 
	 * @param username nombre de usuario
	 * @return true si el usuario existe
	 */
    public boolean existsByUsername(String username)
    {
        return this.iUserRepository.findByUserName(username) != null ;
    }

    /**
     * Este método sirve para almacenar un nuevo usuario
     * @param userRegistrationDto información del usuario recogida en el formulario
     * @param encodedPassword contraseña cifrada
     * @throws MyException excepcion
     */
    public void saveUser(UserRegistrationDto userRegistrationDto, String encodedPassword) throws MyException
    {
    	if(this.existsByUsername(userRegistrationDto.getUsername())) 
    	{
    		throw new MyException("El usuario ya existe") ;
    	}
    	
    	// Primero creamos el usuario ...
        User user = new User() ;
        user.setUserName(userRegistrationDto.getUsername()) ;
        user.setPassword(encodedPassword) ;
        user.setActive(true) ;
          
        
        List<Role> rolesList = new ArrayList<Role>() ;
        
        // Lo siguiente es asociar todos los roles al usuario
        for (String roleString : userRegistrationDto.getRolesAsList())
        {
        	// Obtenemos la referencia al Role
        	Role role = this.iRoleRepository.findByRole(roleString) ;
        	
    		// Si el role no existe, lanzaremos una excepción
    		if (role == null)
    		{
    			throw new MyException("Role no encontrado") ;
    		}
    		
    		rolesList.add(role) ;
        }

		// ... y lo añadimos a BBDD
        this.iUserRepository.saveAndFlush(user) ;
        
        for (Role role : rolesList)
        {
        	// Creamos una nueva instancia de UserRole y UserRoleId
        	UserRoleId userRoleId = new UserRoleId(user.getId(), role.getId()) ;
        	UserRole userRole 	  = new UserRole() ;
        	
        	// Setteamos todos los metodos
        	userRole.setUserRoleId(userRoleId) ;
        	userRole.setIdUser(user) ;
        	userRole.setIdRole(role) ;
        	
        	// Guardamos en BBDD
        	this.iUserRoleRepository.saveAndFlush(userRole) ;
        }
    }
    
    public void updateUser(UserRegistrationDto userRegistrationDto) throws MyException
    {    	
    	if(!this.existsByUsername(userRegistrationDto.getUsername())) 
    	{
    		throw new MyException("El usuario no existe") ;
    	}
    	
    	User foundUser = this.iUserRepository.findByUserName(userRegistrationDto.getUsername());
        
    	List<UserRole> userRoles = this.iUserRoleRepository.findByIdUser(foundUser);
    	for (UserRole role : userRoles)
    	{
    		this.iUserRoleRepository.delete(role);
    	}
    	
        List<Role> rolesList = new ArrayList<Role>() ;
        
        // Lo siguiente es asociar todos los roles al usuario
        for (String roleString : userRegistrationDto.getRolesAsList())
        {
        	// Obtenemos la referencia al Role
        	Role role = this.iRoleRepository.findByRole(roleString) ;
        	
    		// Si el role no existe, lanzaremos una excepción
    		if (role == null)
    		{
    			throw new MyException("Role no encontrado") ;
    		}
    		
    		rolesList.add(role) ;
        }

        this.iUserRepository.saveAndFlush(foundUser) ;
        
        for (Role role : rolesList)
        {
        	// Creamos una nueva instancia de UserRole y UserRoleId
        	UserRoleId userRoleId = new UserRoleId(foundUser.getId(), role.getId()) ;
        	UserRole userRole 	  = new UserRole() ;
        	
        	// Setteamos todos los metodos
        	userRole.setUserRoleId(userRoleId) ;
        	userRole.setIdUser(foundUser) ;
        	userRole.setIdRole(role) ;
        	
        	// Guardamos en BBDD
        	this.iUserRoleRepository.saveAndFlush(userRole) ;
        }
    }
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException
	{
		// Busca el usuario por su "username" que viene por parámetro
		User user = this.iUserRepository.findByUserName(userName) ;

		// Si el usuario no existe, lanzaremos una excepción
		if (user == null)
		{
			throw new UsernameNotFoundException("Usuario no encontrado") ;
		}
		
		// Buscamos los roles asociados al usuario
		List<UserRole> userRoles = this.iUserRoleRepository.findByIdUser(user) ;

		// Llamamos a este método para que en base a los roles (entendidos por nuestra aplicación),
		// los convierta en GrantedAuthority, que es lo que entiende Spring Security en cuanto a roles
		List<GrantedAuthority> authorities = this.getUserAuthority(userRoles) ;

		// Con el usuario y sus lista de authorities, obtenemos los detalles del usuario
		// Es una especie de usuario, pero entendida por Spring Security
		return this.getUserDetails(user, authorities) ;
	}

	/**
	 * Convierte nuestra lista de roles en una lista de GrantedAutority 
	 * que es el mismo concepto pero entendido por Spring Security 
	 * 
	 * @param  userRoles lista de roles del usuario
	 * @return lista de GrantedAuthority
	 */
	private List<GrantedAuthority> getUserAuthority(List<UserRole> userRoles)
	{
		List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>() ;

		for (UserRole userRole : userRoles)
		{
			roles.add(new SimpleGrantedAuthority(userRole.getIdRole().getRole())) ;
		}
		
		return roles ;
	}

	/**
	 * Con el usuario y sus lista de authorities, obtenemos los detalles del usuario
	 * Es una especie de usuario, pero entendida por Spring Security
	 * 
	 * @param user usuario de nuestro modelo de datos
	 * @param authorities lista de GrantedAuthority
	 * @return una instancia de UserDetails
	 */
	private UserDetails getUserDetails(User user, List<GrantedAuthority> authorities)
	{
		boolean accountNonExpired 	  = true ;
		boolean credentialsNonExpired = true ;
		boolean accountNonLocked	  = true ;
		
		return new org.springframework.security.core.userdetails.User(user.getUserName(),
																	  user.getPassword(),
																	  user.getActive(),
																	  accountNonExpired, 
																	  credentialsNonExpired,
																	  accountNonLocked, 
																	  authorities) ;
	}
}
