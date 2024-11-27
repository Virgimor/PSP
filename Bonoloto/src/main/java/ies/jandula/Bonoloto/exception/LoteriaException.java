package ies.jandula.Bonoloto.exception;

import java.util.HashMap;
import java.util.Map;

public class LoteriaException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7270729338548696237L;
	
private Integer loteriaErrorId;
	
	private String message;
	
	public LoteriaException() {
		super();
		
		
	}
	
	public LoteriaException(Integer loteriaErrorId, String message) {
		super();
		this.loteriaErrorId=loteriaErrorId;
		this.message= message;
	}
	
	public LoteriaException( String message, Throwable exception) {
		super();
		
		this.message= message;
	}
	
	
	public LoteriaException(Integer loteriaErrorId, String message, Throwable exception) {
		super();
		this.loteriaErrorId=loteriaErrorId;
		this.message= message;
	}
	
	public Object getBodyExceptionMessage()
	{
		Map<String, Object> mapBodyException = new HashMap<>() ;
		
		mapBodyException.put("movieErrorId", this.loteriaErrorId) ;
		mapBodyException.put("message", this.message) ;
		
		return mapBodyException ;
	}

}
