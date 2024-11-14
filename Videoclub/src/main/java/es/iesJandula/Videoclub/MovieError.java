package es.iesJandula.Videoclub;

import java.util.HashMap;
import java.util.Map;

public class MovieError extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9080070553502409936L;
	
	private int movieErrorId;
	
	private String message;
	
	
	public MovieError(int movieErrorId, String message) {
		super();
		this.movieErrorId=movieErrorId;
		this.message= message;
	}
	
	public Object getBodyExceptionMessage()
	{
		Map<String, Object> mapBodyException = new HashMap<>() ;
		
		mapBodyException.put("movieErrorId", this.movieErrorId) ;
		mapBodyException.put("message", this.message) ;
		
		return mapBodyException ;
	}

}
