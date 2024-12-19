package es.iesjandula.psp.spring_security_thymeleaf_mysql.utils;

public class MyException extends Exception
{
	/** Serial Version UID */
	private static final long serialVersionUID = 1776244348037059911L ;
	
	/**
	 * @param message mensaje del error
	 */
	public MyException(String message) 
	{
		super(message) ;
	}
}
