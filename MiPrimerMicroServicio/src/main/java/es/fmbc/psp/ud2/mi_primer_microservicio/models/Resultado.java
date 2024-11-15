/*********************************************************************************************************************************
 This class has been automatically generated using KLTT-APIRestGenerator project, don't do manual file modifications.
 Sun Nov 27 12:18:49 CET 2022

 "Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements;
  and to You under the Apache License, Version 2.0. "
**********************************************************************************************************************************/

package es.fmbc.psp.ud2.mi_primer_microservicio.models ;

/** 
 * Class - Resultado
 * 
 * Será la que envíe el servidor en el body de la response
 */
public final class Resultado
{
	/** Attribute - Valor */
	private Integer valor ;

	/**
	 * Este constructor es necesario para que Spring Boot convierta el Objeto a JSON
	 */
	public Resultado()
	{
		// No se necesita implementación
	}

	/**
	 * @param valor con información del valor
	 */
	public void setValor(final Integer valor)
	{
		this.valor = valor ;
	}
	
	/**
	 * @return el valor
	 */
	public Integer getValor()
	{
		return this.valor ;
	}

	@Override
	public String toString()
	{
		return "Resultado [valor=" + this.valor + "]";
	}
}
