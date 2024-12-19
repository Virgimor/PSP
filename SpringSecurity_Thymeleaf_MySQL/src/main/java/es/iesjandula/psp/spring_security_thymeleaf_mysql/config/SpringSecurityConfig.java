package es.iesjandula.psp.spring_security_thymeleaf_mysql.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import es.iesjandula.psp.spring_security_thymeleaf_mysql.utils.Constants;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig
{
	@Autowired
	private MyUserDetailsService myUserDetailsService ;

	@Bean
	public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception
	{
	    // Deshabilitar la protección CSRF, ya que se usa JWT

	    http.csrf(csrf -> csrf.disable())

        // Configuramos las reglas de autorización para las solicitudes HTTP
	    
	        .authorizeHttpRequests(authz -> authz
	        		
	            // Permitimos que todas las solicitudes a los recursos siguientes sean accesibles sin autenticación
	        		
	            .requestMatchers("/", "/submit").permitAll()
	            .requestMatchers("/index.html", "/register.html", "/register", "/login.html", "/login-error.html", "/forbidden.html", "/css/**").permitAll()
	            
	            // Para las siguientes rutas, se necesita que tengamos estas autoridades
	            
	            .requestMatchers("/admin/index.html").hasAuthority(Constants.ROLE_ADMINISTRADOR)
	            .requestMatchers("/user/index.html").hasAnyAuthority(Constants.ROLE_ADMINISTRADOR, Constants.ROLE_USUARIO)
	            
	            // Para cualquier otro recurso que no sean los anteriores, requerimos autenticación
	            
	            .anyRequest().authenticated()
	        )

	        // Configuramos la página de login
	        
	        .formLogin(login -> login
	            .loginPage("/login.html")
	            .failureUrl("/login-error.html")
	        )

	        // Configuramos el logout
	        
	        .logout(logout -> logout
	            .logoutSuccessUrl("/index.html")
	        ) ;

	    return http.build();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		// Usaremos 'BCryptPasswordEncoder' como forma de cifrar las contraseñas
		return new BCryptPasswordEncoder() ;
	}

	/**
	 * Este método devuelve una instancia de DaoAuthenticationProvider que indica 
	 * cómo se controlan los usuarios (UserDetailsService) y cómo se codifican las contraseñas (PasswordEnconder) 
	 */
	@Bean
	public DaoAuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider() ;
		
		authProvider.setUserDetailsService(this.myUserDetailsService) ;
		authProvider.setPasswordEncoder(this.passwordEncoder()) ;
		
		return authProvider ;
	}
    
    /**
     * Configuración de páginas de error personalizadas
     * @return
     */
    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer()
    {
        return factory ->
        {
            factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/not-found.html"));
            factory.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/forbidden.html"));
        } ;
    }
}

