package es.iesJandula.Proyecto1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import es.iesJandula.Proyecto1.rest.RestHandlerProyecto1;

@SpringBootApplication
public class Proyecto1Application {

	public static void main(String[] args) {
		SpringApplication.run(Proyecto1Application.class, args);
	}

}
