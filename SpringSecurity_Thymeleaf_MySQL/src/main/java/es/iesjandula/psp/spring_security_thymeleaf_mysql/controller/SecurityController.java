package es.iesjandula.psp.spring_security_thymeleaf_mysql.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.iesjandula.psp.spring_security_thymeleaf_mysql.config.MyUserDetailsService;
import es.iesjandula.psp.spring_security_thymeleaf_mysql.dto.UserRegistrationDto;
import es.iesjandula.psp.spring_security_thymeleaf_mysql.utils.MyException;

@Controller
public class SecurityController
{
	@Autowired
    private MyUserDetailsService myUserDetailsService ;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder ;
	
    @RequestMapping("/")
    public String indexDefault()
    {
        return "secured/index.html";
    }
    
    @RequestMapping("/register.html")
    public String register(Model model)
    {
    	// Asociamos "user" como modelo que almacenará los datos del formulario de "register.html"
        model.addAttribute("user", new UserRegistrationDto()) ;
        
        return "secured/register.html";
    }

    @RequestMapping(method=RequestMethod.POST, value= "/register")
    public String processRegistration(UserRegistrationDto userDto, Model model)
    {
        // Comprobamos si el usuario ya existe
    	if (this.myUserDetailsService.existsByUsername(userDto.getUsername()))
        {
    		// Si existe, añadimos al modelo "registerError" a "true" para indicar que hay un error
            model.addAttribute("registerError", true) ;
            
            // Además, añadimos al modelo "registerErrorMessage" para enviar el texto "El usuario ya existe"
            model.addAttribute("registerErrorMessage", "El usuario ya existe") ;
            
            return "secured/register.html" ;
        }

    	// En caso de que no exista el usuario, seguimos el flujo normal de creación de usuario
    	
        // Ciframos la contraseña
        String encodedPassword = this.passwordEncoder.encode(userDto.getPassword());

        try
        {
        	// Guardamos el nuevo usuario en la base de datos
			this.myUserDetailsService.saveUser(userDto, encodedPassword) ;
		}
        catch (MyException exception)
        {
    		// Si existe, añadimos al modelo "registerError" a "true" para indicar que hay un error
            model.addAttribute("registerError", true) ;
            
            // Además, añadimos al modelo "registerErrorMessage" para enviar el texto del error
            model.addAttribute("registerErrorMessage", exception.getMessage()) ;
            
            return "secured/register.html" ;
		}

        return "secured/login.html" ;
    }
    

    @RequestMapping("/update.html")
    public String update(Model model)
    {
    	// Asociamos "user" como modelo que almacenará los datos del formulario de "register.html"
        model.addAttribute("user", new UserRegistrationDto()) ;
        
        return "secured/admin/update.html";
    }

    @RequestMapping(method=RequestMethod.POST, value= "/update")
    public String processRoleUpdate(UserRegistrationDto userDto, Model model)
    {
        // Comprobamos si el usuario no existe
    	if (!this.myUserDetailsService.existsByUsername(userDto.getUsername()))
        {
    		// Si existe, añadimos al modelo "registerError" a "true" para indicar que hay un error
            model.addAttribute("roleUpdateError", true) ;
            
            // Además, añadimos al modelo "registerErrorMessage" para enviar el texto "El usuario ya existe"
            model.addAttribute("roleUpdateErrorMessage", "El usuario no existe") ;
            
            return "secured/admin/update.html" ;
        }

    	// En caso de que no exista el usuario, seguimos el flujo normal de creación de usuario
    	
        // Ciframos la contraseña

        try
        {
        	// Guardamos el nuevo usuario en la base de datos
			this.myUserDetailsService.updateUser(userDto);
		}
        catch (MyException exception)
        {
    		// Si existe, añadimos al modelo "registerError" a "true" para indicar que hay un error
            model.addAttribute("roleUpdateError", true) ;
            
            // Además, añadimos al modelo "registerErrorMessage" para enviar el texto del error
            model.addAttribute("roleUpdateErrorMessage", exception.getMessage()) ;
            
            return "secured/admin/update.html" ;
		}

        return "secured/login.html" ;
    }
	
    @RequestMapping("/index.html")
    public String index()
    {
        return "secured/index.html";
    }

    @RequestMapping("/login.html")
    public String login()
    {
        return "secured/login.html";
    }

    @RequestMapping("/admin/index.html")
    public String adminIndex()
    {
        return "secured/admin/index.html";
    }

    @RequestMapping("/user/index.html")
    public String userIndex()
    {
        return "secured/user/index.html";
    }

    @RequestMapping("/login-error.html")
    public String loginError(Model model)
    {
        model.addAttribute("loginError", true);
        return "secured/login.html";
    }

    @RequestMapping("/forbidden.html")
    public String forbidden()
    {
        return "secured/forbidden.html";
    }

    @RequestMapping("/css/main.css")
    public String getCss()
    {
        return "secured/css/main.css";
    }
    
    @RequestMapping("/not-found.html")
    public String notFound()
    {
        return "secured/not-found.html";
    }
}

