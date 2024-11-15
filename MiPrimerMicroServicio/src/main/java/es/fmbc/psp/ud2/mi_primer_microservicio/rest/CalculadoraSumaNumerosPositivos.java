/*********************************************************************************************************************************
 This class has been automatically generated using KLTT-APIRestGenerator project, don't do manual file modifications.
 Sun Nov 27 12:18:50 CET 2022

 "Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements;
  and to You under the Apache License, Version 2.0. "
**********************************************************************************************************************************/

package es.fmbc.psp.ud2.mi_primer_microservicio.rest ;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.StringTokenizer;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.fmbc.psp.ud2.mi_primer_microservicio.exception.MiPrimerMicroServicioException;
import es.fmbc.psp.ud2.mi_primer_microservicio.models.Numeros;
import es.fmbc.psp.ud2.mi_primer_microservicio.models.Resultado;

/**
 * Esta clase va a ser la encargada de realizar labores de suma (calculadora) con números positivos.
 * Para ello, va a tener un basePath principal llamado /sumar_numeros_positivos, y por defecto, va a devolver (producir) JSONs
 * 
 * Vamos a ver diferentes ejemplos de cómo generar llamadas por PATH, QUERY, HEADER, BODY, y peticiones y respuestas con ficheros
 * 
 * @author Paco Benítez
 */
@RequestMapping(value = "/sumar_numeros_positivos", produces = {"application/json"})
@RestController
public final class CalculadoraSumaNumerosPositivos
{
	/**
	 * Este ejemplo, es un verbo GET, que recibirá dos parámetros en el path "numero1" y "numero2"
	 * Cabe destacar que se usa la anotación @PathVariable para la recepción de los parámetros path.
	 * Además, estos parámetros son requeridos, siendo lo mismo no ponerlo (por defecto, true) o poner el required=true
	 * 
	 * El valor de retorno será un ResponseEntity<?>, ya que la interrogación nos permite devolver cosas diferentes
	 * en caso de que sea un 200, 400, 500, etcétera.
	 * 
	 * Cabe destacar también que como devuelve un tipo primitivo, el servidor producirá un "application/json". Como vemos, no está definido en el método, sino al principio de la 
	 * clase, por lo que no es necesario repetirlo en este método. Otra cosa sería si este método tuviera un produces diferente al del principio de la clase, por lo que 
	 * en ese caso, sí que tendríamos que ponerlo aquí y "machacaría" el principal (línea 39)
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/by_path/{numero1}/{numero2}/"	)
	public ResponseEntity<?> sumarPathParams(@PathVariable(value="numero1", required = true) Integer numero1, 
									   		 @PathVariable(value="numero2") Integer numero2)
	{
		try
		{
			// Aquí comprobaremos si son números positivos
			this.checkNumbers(numero1, numero2) ;
			
			// Calculamos la suma
			int outcome = numero1 + numero2 ;
			
			// Generamos la respuesta. Si nos fijamos, vamos llamando método a método sobre la misma línea
			// El primero de ellos "ok()" está indicando que va a generar una respuesta será un 200
			// El segundo de ellos "body(outcome) está añadiendo a la respuesta anterior, dentro de su body, el resultado de la suma
			// que en este caso es un tipo simple "int"
			return ResponseEntity.ok().body(outcome) ;
		}
		catch (MiPrimerMicroServicioException miPrimerMicroservicio)
		{
			// En caso de excepción, devolveremos un 400 y en su body el error en formato JSON
			return ResponseEntity.status(400).body(miPrimerMicroservicio.getBodyExceptionMessage()) ;
		}
	}
	
	/**
	 * Este ejemplo, es un verbo GET, que recibirá dos parámetros en el query "numero1" y "numero2"
	 * Cabe destacar que se usa la anotación @RequestParam para la recepción de los parámetros query.
	 * Además, como vimos en el endpoint anterior, si no ponemos nada, los parámetros serán requeridos.
	 * Por ello, para este ejemplo, vamos a suponer que no son requeridos, por lo que tenemos que poner required=false
	 * 
	 * El valor de retorno será un ResponseEntity<?>, ya que la interrogación nos permite devolver cosas diferentes
	 * en caso de que sea un 200, 400, 500, etcétera.
	 * 
	 * Cabe destacar también que como devuelve un tipo primitivo, el servidor producirá un "application/json". Como vemos, no está definido en el método, sino al principio de la 
	 * clase, por lo que no es necesario repetirlo en este método. Otra cosa sería si este método tuviera un produces diferente al del principio de la clase, por lo que 
	 * en ese caso, sí que tendríamos que ponerlo aquí y "machacaría" el principal (línea 39)
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/by_query/")
	public ResponseEntity<?> sumarQueryParams(@RequestParam(value="numero1", required=false) Integer numero1,
											  @RequestParam(value="numero2", required=false) Integer numero2)
	{
		try
		{
			// Aquí comprobaremos si son números positivos
			this.checkNumbers(numero1, numero2) ;
			
			// Calculamos la suma
			int outcome = numero1 + numero2 ;
			
			// Generamos la respuesta. Si nos fijamos, vamos llamando método a método sobre la misma línea
			// El primero de ellos "ok()" está indicando que va a generar una respuesta será un 200
			// El segundo de ellos "body(outcome) está añadiendo a la respuesta anterior, dentro de su body, el resultado de la suma
			// que en este caso es un tipo simple "int"
			return ResponseEntity.ok().body(outcome) ;
		}
		catch (MiPrimerMicroServicioException miPrimerMicroservicio)
		{
			// En caso de excepción, devolveremos un 400 y en su body el error en formato JSON
			return ResponseEntity.status(400).body(miPrimerMicroservicio.getBodyExceptionMessage()) ;
		}
	}

	/**
	 * Este ejemplo, es un verbo GET, que recibirá dos parámetros en la cabecera (header) "numero1" y "numero2"
	 * Cabe destacar que se usa la anotación @RequestHeader para la recepción de los parámetros header.
	 * Además, como vimos en el endpoint anterior, si no ponemos nada, los parámetros serán requeridos.
	 * Por ello, para este ejemplo, vamos a suponer que son requeridos, por lo que podríamos dejarlos como required=true o 
	 * simplemente no ponerlo, ya que por defecto es true
	 * 
	 * El valor de retorno será un ResponseEntity<?>, ya que la interrogación nos permite devolver cosas diferentes
	 * en caso de que sea un 200, 400, 500, etcétera.
	 * 
	 * Cabe destacar también que como devuelve un tipo primitivo, el servidor producirá un "application/json". Como vemos, no está definido en el método, sino al principio de la 
	 * clase, por lo que no es necesario repetirlo en este método. Otra cosa sería si este método tuviera un produces diferente al del principio de la clase, por lo que 
	 * en ese caso, sí que tendríamos que ponerlo aquí y "machacaría" el principal (línea 39)
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/by_header/")
	public ResponseEntity<?> sumarHeaderParams(@RequestHeader(value="numero1", required=true) Integer numero1, 
										 	   @RequestHeader(value="numero2", required=true) Integer numero2)
	{
		try
		{
			// Aquí comprobaremos si son números positivos
			this.checkNumbers(numero1, numero2) ;
			
			// Calculamos la suma
			int outcome = numero1 + numero2 ;
			
			// Generamos la respuesta. Si nos fijamos, vamos llamando método a método sobre la misma línea
			// El primero de ellos "ok()" está indicando que va a generar una respuesta será un 200
			// El segundo de ellos "body(outcome) está añadiendo a la respuesta anterior, dentro de su body, el resultado de la suma
			// que en este caso es un tipo simple "int"
			return ResponseEntity.ok().body(outcome) ;
		}
		catch (MiPrimerMicroServicioException miPrimerMicroservicio)
		{
			// En caso de excepción, devolveremos un 400 y en su body el error en formato JSON
			return ResponseEntity.status(400).body(miPrimerMicroservicio.getBodyExceptionMessage()) ;
		}
	}
	
	/**
	 * Este ejemplo, es un verbo POST, ya que recibirá un body (recordemos que en los verbos GET no se debe enviar body)
	 * Es importante comentar que como el servidor va a recibir (consumir) un objeto, se debe poner consumes = {"application/json"}
	 * De esta forma, dentro del parámetro "numeros" hay un objeto de tipo "Numeros" que contiene "numero1" y "numero2"
	 * Cabe destacar que se usa la anotación @RequestBody para la recepción del objeto de la clase Numeros.
	 * Además, como vimos en el endpoint anterior, si no ponemos nada, los parámetros serán requeridos.
	 * Por ello, para este ejemplo será requerido, por lo que podríamos dejarlo como required=true o 
	 * simplemente no ponerlo, ya que por defecto es true
	 * 
	 * El valor de retorno será un ResponseEntity<?>, ya que la interrogación nos permite devolver cosas diferentes
	 * en caso de que sea un 200, 400, 500, etcétera.
	 * 
	 * Cabe destacar también que como devuelve un objeto, el servidor producirá un "application/json". Como vemos, no está definido en el método, sino al principio de la 
	 * clase, por lo que no es necesario repetirlo en este método. Otra cosa sería si este método tuviera un produces diferente al del principio de la clase, por lo que 
	 * en ese caso, sí que tendríamos que ponerlo aquí y "machacaría" el principal (línea 39)
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/by_body/", consumes = {"application/json"})
	public ResponseEntity<?> sumarBodyParam(@RequestBody(required=true) Numeros numeros)
	{
		try
		{	
			// Aquí comprobaremos si son números positivos
			this.checkNumbers(numeros.getNumero1(), numeros.getNumero2()) ;

			// Calculamos a suma
			int outcome = numeros.getNumero1() + numeros.getNumero2() ;
			
			// Creamos un objeto resultado que contendrá en "valor" la suma de numero1 + numero2
			Resultado resultado = new Resultado() ;
			
			// Seteamos "valor" con dicha suma
			resultado.setValor(outcome) ;
			
			// Generamos la respuesta. Si nos fijamos, vamos llamando método a método sobre la misma línea
			// El primero de ellos "ok()" está indicando que va a generar una respuesta será un 200
			// El segundo de ellos "body(resultado) está añadiendo a la respuesta anterior, dentro de su body, el resultado de la suma
			// que en este caso es un objeto. Spring Boot se encargará internamente de convertir el objeto a un JSON
			return ResponseEntity.ok().body(resultado) ;
		}
		catch (MiPrimerMicroServicioException miPrimerMicroservicio)
		{
			// En caso de excepción, devolveremos un 400 y en su body el error en formato JSON
			return ResponseEntity.status(400).body(miPrimerMicroservicio.getBodyExceptionMessage()) ;
		}
	}

	/**
	 * Este ejemplo, es un verbo POST, ya que recibirá un fichero (recordemos que en los verbos GET no se debe enviar ficheros)
	 * Es importante comentar que como el servidor va a recibir (consumir) un fichero, se debe poner consumes = {"multipart/form-data"}
	 * De esta forma, dentro del parámetro "numeros" hay un objeto de tipo "MultipartFile" de Spring Boot que un array de bytes, es decir, el fichero con numero1 y numero2
	 * Cabe destacar que se usa la anotación @RequestParam para la recepción del fichero con los números.
	 * Además, como vimos en el endpoint anterior, si no ponemos nada, los parámetros serán requeridos.
	 * Por ello, para este ejemplo no será requerido, por lo que debemos forzar a asignarlo como required=true
	 * 
	 * El valor de retorno será un ResponseEntity<?>, ya que la interrogación nos permite devolver cosas diferentes
	 * en caso de que sea un 200, 400, 500, etcétera.
	 * 
	 * Cabe destacar también que como devuelve un fichero, debemos cambiar el produces por defecto que hay al comienzo de la clase "application/json" por "multipart/form-data. 
	 * Si no hiciéramos eso, cogería el principal (línea 39)
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/by_file/", 
					consumes = {"multipart/form-data"}, produces = {"multipart/form-data"})
	public ResponseEntity<?> sumarFileParam(@RequestParam(value="numeros", required=false) MultipartFile numeros)
	{    	
		try
		{    
			// Obtenemos del fichero el contenido del mismo
			String numerosString = new String(numeros.getBytes()) ;
			
			// Esta clase nos va a permitir trocear la línea por espacios. Por ejemplo, si tengo la siguiente línea "34 67"
			// nos troceará con los tokens "34" y "67"
			StringTokenizer stringTokenizer = new StringTokenizer(numerosString, " ") ;
			
			// Como solo son dos números y ambos están separado por un espacio, voy haciendo una llamada a "nextToken" para conseguir cada uno de ellos
			int numero1 = Integer.valueOf(stringTokenizer.nextToken()) ;
			int numero2 = Integer.valueOf(stringTokenizer.nextToken()) ;
			
			// Aquí comprobaremos si son números positivos
			this.checkNumbers(numero1, numero2) ;
			
			// Calculamos la suma
			int outcome = numero1 + numero2 ;
			
			// Convertimos el número entero (suma de numero1 y numero2) a String
			String outcomeString = String.valueOf(outcome) ;
			
			// Para convertir un String, a un flujo de octetos, haremos uso de las clases InputStreamResource y ByteArrayInputStream
			InputStreamResource outcomeInputStreamResource = new InputStreamResource(new java.io.ByteArrayInputStream(outcomeString.getBytes())) ;
			
			// Para enviar cabeceras en la respuesta, haremos uso de la clase HttpHeaders
			HttpHeaders headers = new HttpHeaders();

			// Esta clave "Content-Disposition" nos permitirá indicar el nombre del fichero
			// En este ejemplo, hemos puesto el nombre sobre el propio String, pero podríamos haber obtenido el nombre del fichero de diversas formas
			headers.set("Content-Disposition", "attachment; filename=miNuevoFichero.txt");   

			// Generamos la respuesta. Si nos fijamos, vamos llamando método a método sobre la misma línea
			// El primero de ellos "ok()" está indicando que va a generar una respuesta será un 200
			// El segundo sirve para asignar la instancia "headers" a la respuesta. Esto será útil cuando queramos enviar muchas cabeceras de respuesta, ya que es un mapa
			// El tercero de ellos "body(resultado) está añadiendo a la respuesta anterior, dentro de su body, el resultado de la suma
		    return ResponseEntity.ok().headers(headers).body(outcomeInputStreamResource) ;
			
		    // Se podría hacer también de esta forma, siempre que solo queramos asignar una única cabecera de respuesta
//			return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=miNuevoFichero.txt").body(outcomeInputStreamResource) ;
		}
		catch (MiPrimerMicroServicioException miPrimerMicroservicio)
		{
			// En caso de excepción, devolveremos un 400 y en su body el error en formato JSON
			return ResponseEntity.status(400).body(miPrimerMicroservicio.getBodyExceptionMessage()) ;
		}
		catch (IOException ioException)
		{
			// Esta excepción puede suceder si no es capaz de parsear el fichero de entrada o convertir el fichero de salida
			MiPrimerMicroServicioException miPrimerMicroservicio = new MiPrimerMicroServicioException(3, ioException.getMessage(), ioException) ;
			
			// En caso de excepción, devolveremos un 500 y en su body el error en formato JSON
			return ResponseEntity.status(500).body(miPrimerMicroservicio.getBodyExceptionMessage()) ;
		}
	}
	
	/**
	 * Este ejemplo, es un verbo GET, que devolverá un fichero que tenemos almacenado en nuestro proyecto Eclipse
	 * 
	 * El valor de retorno será un ResponseEntity<?>, ya que la interrogación nos permite devolver cosas diferentes
	 * en caso de que sea un 200, 400, 500, etcétera.
	 * 
	 * Cabe destacar que como devuelve un fichero, debemos cambiar el produces por defecto que hay al comienzo de la clase "application/json" por "multipart/form-data. 
	 * Si no hiciéramos eso, cogería el principal (línea 39)
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/download_file/", produces = {"multipart/form-data"})
	public ResponseEntity<?> descargarFicheroNavegador()
	{    	
		try
		{   
			// Obtengo una instancia de File con la información del número
			File miFichero = new File("numeros.txt") ;
			
			// Conseguimos el contenido del fichero en un array de bytes
			byte[] contenidoDelFichero = Files.readAllBytes(miFichero.toPath()) ;
			
			// Convertimos el array de bytes en un formato que entiende Spring Boot
			InputStreamResource outcomeInputStreamResource = new InputStreamResource(new java.io.ByteArrayInputStream(contenidoDelFichero)) ;
			
			// Para enviar cabeceras en la respuesta, haremos uso de la clase HttpHeaders
			HttpHeaders headers = new HttpHeaders();

			// Esta clave "Content-Disposition" nos permitirá indicar el nombre del fichero
			// En este ejemplo, vamos a hacer uso del nombre real del fichero
			headers.set("Content-Disposition", "attachment; filename=" + miFichero.getName());   

			// Generamos la respuesta. Si nos fijamos, vamos llamando método a método sobre la misma línea
			// El primero de ellos "ok()" está indicando que va a generar una respuesta será un 200
			// El segundo sirve para asignar la instancia "headers" a la respuesta. Esto será útil cuando queramos enviar muchas cabeceras de respuesta, ya que es un mapa
			// El tercero de ellos "body(resultado) está añadiendo a la respuesta anterior, dentro de su body, el resultado de la suma
		    return ResponseEntity.ok().headers(headers).body(outcomeInputStreamResource) ;
			
		    // Se podría hacer también de esta forma, siempre que solo queramos asignar una única cabecera de respuesta
//			return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=" + miFichero.getName()).body(outcomeInputStreamResource) ;
		}
		catch (IOException ioException)
		{
			// Esta excepción puede suceder si no es capaz de convertir el fichero de salida
			MiPrimerMicroServicioException miPrimerMicroservicio = new MiPrimerMicroServicioException(3, ioException.getMessage(), ioException) ;
			
			// En caso de excepción, devolveremos un 500 y en su body el error en formato JSON
			return ResponseEntity.status(500).body(miPrimerMicroservicio.getBodyExceptionMessage()) ;
		}
	}	
	
	/**
	 * Valida si numero1 y numero2 son números positivos
	 * 
	 * @param numero1 con el número uno
	 * @param numero2 con el número dos
	 * @throws MiPrimerMicroServicioException si alguno de los números no es positivo
	 */
	private void checkNumbers(int numero1, int numero2) throws MiPrimerMicroServicioException
	{
		if (numero1 < 0)
		{
			throw new MiPrimerMicroServicioException(1, "El número 1 es negativo: " + numero1) ;
		}
		else if (numero2 < 0)
		{
			throw new MiPrimerMicroServicioException(2, "El número 2 es negativo: " + numero2) ;
		}
	}
}
