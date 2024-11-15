/*********************************************************************************************************************************
 This class has been automatically generated using KLTT-APIRestGenerator project, don't do manual file modifications.
 Sun Nov 27 12:18:49 CET 2022

 "Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements;
  and to You under the Apache License, Version 2.0. "
**********************************************************************************************************************************/

package es.fmbc.psp.ud2.mi_primer_microservicio.models ;

/** 
 * Clase - Numeros
 * 
 * Será la que envíe el cliente en el body de la request
 */
public final class Numeros
{
	/** Atributo - Número 1 */
	private Integer numero1 ;
	
	/** Atributo - Número 2 */
	private Integer numero2 ;

	/**
	 * Este constructor es necesario para que Spring Boot convierta el JSON a Objeto
	 */
	public Numeros()
	{
		// No se necesita implementación
	}

	/**
	 * @param numero1 con información del número 1
	 */
	public void setNumero1(final Integer numero1)
	{
		this.numero1 = numero1 ;
	}
	
	/**
	 * @return el número 1
	 */
	public Integer getNumero1()
	{
		return this.numero1 ;
	}

	/**
	 * @param numero2 con información del número 2
	 */
	public void setNumero2(final Integer numero2)
	{
		this.numero2 = numero2 ;
	}
	
	/**
	 * @return el numero2
	 */
	public Integer getNumero2()
	{
		return this.numero2 ;
	}
}
