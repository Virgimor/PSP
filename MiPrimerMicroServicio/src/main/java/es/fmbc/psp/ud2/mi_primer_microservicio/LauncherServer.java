/*********************************************************************************************************************************
 This class has been automatically generated using KLTT-APIRestGenerator project, don't do manual file modifications.
 Sun Nov 27 12:18:50 CET 2022

 "Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements;
  and to You under the Apache License, Version 2.0. "
**********************************************************************************************************************************/

package es.fmbc.psp.ud2.mi_primer_microservicio ;

import org.springframework.boot.SpringApplication ;
import org.springframework.boot.autoconfigure.SpringBootApplication ;
import org.springframework.context.annotation.ComponentScan ;

/**
 * Esta clase arranca la aplicación Spring Boot como microservicio
 */
// Esta anotación es importante para que este sea el punto de arranque de la aplicación
@SpringBootApplication
//Le indicamos a partir de dónde buscar componentes y servicios
@ComponentScan(basePackages = "es.fmbc.psp.ud2.mi_primer_microservicio")
public class LauncherServer
{
	/**
	 * Main method
	 * @param args with the input arguments
	 */
    public static void main(String[] args)
    {
        SpringApplication.run(LauncherServer.class, args) ;
    }
}
