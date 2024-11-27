package ies.jandula.Bonoloto.utils;

import java.util.Date;

import ies.jandula.Bonoloto.exception.LoteriaException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DatesUtil {
	
	public static Date crearFechaDesdeString(String fechaString) throws LoteriaException{
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			return formatter.parse(fechaString);
		}
		catch (ParseException parseException) {
			throw new LoteriaException("Fecha con formato incorrecta: " + fechaString, parseException);
		}
	}

}
