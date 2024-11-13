package es.iesJandula.Proyecto1.exception;

import java.util.HashMap;
import java.util.Map;

public class JugadorError extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4960208588908004116L;
	
	private int code;
	
	private String message;

	public JugadorError(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	
	public Object getBodyExceptionMessage()
	{
		Map<String, Object> mapBodyException = new HashMap<>() ;
		
		mapBodyException.put("code", this.code) ;
		mapBodyException.put("message", this.message) ;
		
		return mapBodyException ;
	}

}
