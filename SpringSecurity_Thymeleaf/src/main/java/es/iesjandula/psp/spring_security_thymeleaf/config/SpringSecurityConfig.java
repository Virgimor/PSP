package es.iesjandula.psp.spring_security_thymeleaf.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig
{

	@Bean
	public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception
	{
	    // Deshabilitar la protección CSRF, ya que se usa JWT

	    http.csrf(csrf -> csrf.disable())

        // Configuramos las reglas de autorización para las solicitudes HTTP
	    
	        .authorizeHttpRequests(authz -> authz
	        		
	            // Permitimos que todas las solicitudes a los recursos siguientes sean accesibles sin autenticación
	        		
	            .requestMatchers("/", "/submit").permitAll()
	            .requestMatchers("/index.html", "/login.html", "/login-error.html", "/forbidden.html", "/css/**").permitAll()
	            
	            // Para las siguientes rutas, se necesita que tengamos roles
	            
	            .requestMatchers("/admin/index.html").hasRole("ADMIN")
	            .requestMatchers("/user/index.html").hasAnyRole("ADMIN", "USER")
	            
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
    public InMemoryUserDetailsManager userDetailsService()
    {
        return new InMemoryUserDetailsManager(
            User.withUsername("paco").password("{noop}demo").roles("ADMIN").build(),
            User.withUsername("pepe").password("{noop}demo").roles("USER").build(),
            User.withUsername("juan").password("{noop}demo").roles("USER", "ADMIN").build()
        ) ;
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
           // factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/not-found.html"));
            factory.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/forbidden.html"));
        } ;
    }
}

