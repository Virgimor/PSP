/*********************************************************************************************************************************
 This class has been automatically generated using KLTT-APIRestGenerator project, don't do manual file modifications.
 Sun Nov 27 12:18:50 CET 2022

 "Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements;
  and to You under the Apache License, Version 2.0. "
**********************************************************************************************************************************/

package es.fmbc.psp.ud2.sumador_enteros_positivos.rest ;

import java.io.IOException;
import java.util.StringTokenizer;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.fmbc.psp.ud2.sumador_enteros_positivos.exception.SumadorError;
import es.fmbc.psp.ud2.sumador_enteros_positivos.model.Numeros;
import es.fmbc.psp.ud2.sumador_enteros_positivos.model.Resultado;

/**
 * ------------------------------------------------
 * @author API Rest Generator
 * ------------------------------------------------
 */
@RequestMapping(value = "/sumar", produces = {"application/json"})

public final class RestHandlerSumador
{
	/**
	 * Public constructor
	 */
    public RestHandlerSumador()
    {
	    // Empty constructor because of Spring dependency
    }

	@RequestMapping(method = RequestMethod.GET, value = "/by_path/{numero1}/{numero2}/"	)
	public ResponseEntity<?> addByPath(@PathVariable(value="numero1") final Integer numero1, 
									   @PathVariable(value="numero2") final Integer numero2)
	{
		try
		{
			// Check the numbers
			this.checkNumbers(numero1, numero2) ;
			
			int outcome = numero1 + numero2 ;
			
			return ResponseEntity.ok().body(outcome) ;
		}
		catch (SumadorError sumadorError)
		{
			return ResponseEntity.status(400).body(sumadorError.getBodyExceptionMessage()) ;
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/by_query/")
	public ResponseEntity<?> addByQuery(@RequestParam(value="numero1", required=false) final Integer numero1,
										@RequestParam(value="numero2", required=false) final Integer numero2)
	{
		try
		{
			// Check the numbers
			this.checkNumbers(numero1, numero2) ;

			int outcome = numero1 + numero2 ;

			return ResponseEntity.ok().body(outcome) ;
		}
		catch (SumadorError sumadorError)
		{
			return ResponseEntity.status(400).body(sumadorError.getBodyExceptionMessage()) ;
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/by_header/")
	public ResponseEntity<?> addByHeader(@RequestHeader(value="numero1", required=true) final Integer numero1, 
										 @RequestHeader(value="numero2", required=true) final Integer numero2)
	{
		try
		{
			// Check the numbers
			this.checkNumbers(numero1, numero2) ;

			int outcome = numero1 + numero2 ;
			
			return ResponseEntity.ok().body(outcome) ;
		}
		catch (SumadorError sumadorError)
		{
			return ResponseEntity.status(400).body(sumadorError.getBodyExceptionMessage()) ;
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/by_body/", consumes = {"application/json"})
	public ResponseEntity<?> addByBody(@RequestBody(required=true) final Numeros numeros)
	{
		try
		{	
			// Check the numbers
			this.checkNumbers(numeros.getNumero1(), numeros.getNumero2()) ;

			int outcome = numeros.getNumero1() + numeros.getNumero2() ;
			
			Resultado resultado = new Resultado() ;
			resultado.setValor(outcome) ;
			
			return ResponseEntity.ok().body(resultado) ;
		}
		catch (SumadorError sumadorError)
		{
			return ResponseEntity.status(400).body(sumadorError.getBodyExceptionMessage()) ;
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/by_file/", 
					consumes = {"multipart/form-data"}, produces = {"multipart/form-data"})
	public ResponseEntity<?> addByFile(@RequestParam(value="numeros", required=false) final MultipartFile numeros)
	{    	
		try
		{    	
			String numerosString = new String(numeros.getBytes()) ;
			
			StringTokenizer stringTokenizer = new StringTokenizer(numerosString, " ") ;
			
			int numero1 = Integer.valueOf(stringTokenizer.nextToken()) ;
			int numero2 = Integer.valueOf(stringTokenizer.nextToken()) ;
			
			int outcome = numero1 + numero2 ;
			
			// Check the numbers
			this.checkNumbers(numero1, numero2) ;
			
			String outcomeString = "" + outcome ;
			
			InputStreamResource outcomeInputStreamResource = 
					new InputStreamResource(new java.io.ByteArrayInputStream(outcomeString.getBytes())) ;

			return ResponseEntity.ok().body(outcomeInputStreamResource) ;
		}
		catch (SumadorError sumadorError)
		{
			return ResponseEntity.status(400).body(sumadorError.getBodyExceptionMessage()) ;
		}
		catch (IOException ioException)
		{
			SumadorError sumadorError = new SumadorError(3, ioException.getMessage()) ;
			
			return ResponseEntity.status(500).body(sumadorError.getBodyExceptionMessage()) ;
		}
	}
	
	/**
	 * Check both numbers
	 * 
	 * @param numero1 with the number one
	 * @param numero2 with the number two
	 * @throws SumadorError with an occurred exception
	 */
	private void checkNumbers(int numero1, int numero2) throws SumadorError
	{
		if (numero1 < 0)
		{
			throw new SumadorError(1, "El número 1 es negativo: " + numero1) ;
		}
		else if (numero2 < 0)
		{
			throw new SumadorError(2, "El número 2 es negativo: " + numero2) ;
		}
	}
}
