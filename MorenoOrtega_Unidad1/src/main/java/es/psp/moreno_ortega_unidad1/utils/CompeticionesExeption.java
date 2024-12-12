package es.psp.moreno_ortega_unidad1.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;

import lombok.Data;

@Data
public class CompeticionesExeption extends Exception 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6322510783027387894L;
	
	private Integer codigo;
	
	private String mensaje;
	
	private Throwable excepcion;
	
	public CompeticionesExeption(Integer codigo, String mensaje)
	{
		super();
		this.codigo=codigo;
		this.mensaje= mensaje;
	}
	
	public CompeticionesExeption(Integer codigo, String message, Throwable excepcion)
	{
		super(message, excepcion);
		
		this.codigo=codigo;
		this.mensaje= message;
		this.excepcion= excepcion ;
	}
	
	public Object getBodyExceptionMessage()
	{
		Map<String, Object> mapBodyException = new HashMap<>() ;
		
		mapBodyException.put("codigo", this.codigo) ;
		mapBodyException.put("message", this.mensaje) ;
		
		if (this.excepcion != null)
		{
			String stackTrace = ExceptionUtils.getStackTrace(this.excepcion) ;
			mapBodyException.put("excepcion", stackTrace) ;
		}
		
		return mapBodyException ;
	}

}
