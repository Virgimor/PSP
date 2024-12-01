package es.iesJandula.Videoclub.exception;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class MovieError extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9080070553502409936L;
	
	private Integer movieErrorId;
	
	private String mensaje;
	
	private Throwable excepcion;
	
	
	public MovieError(Integer movieErrorId, String mensaje) {
		super();
		this.movieErrorId=movieErrorId;
		this.mensaje= mensaje;
	}
	
	public MovieError(Integer movieErrorId, String mensaje, Throwable excepcion) {
		super(mensaje, excepcion);
		this.movieErrorId=movieErrorId;
		this.mensaje= mensaje;
		this.excepcion = excepcion;
	}
	
	public Object getBodyExceptionMessage()
	{
		Map<String, Object> mapBodyException = new HashMap<>() ;
		
		mapBodyException.put("movieErrorId", this.movieErrorId) ;
		mapBodyException.put("mensaje", this.mensaje) ;
		
		if (this.excepcion != null)
		{
			String stackTrace = ExceptionUtils.getStackTrace(this.excepcion) ;
			mapBodyException.put("excepcion", stackTrace) ;
		}
		
		return mapBodyException ;
	}

}
