package ies.jandula.VideoClubBien.utils;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ControllerException {
	  @ExceptionHandler(VideoclubException.class)
	    public ResponseEntity<Map<String, String>> handleVideoclubException(VideoclubException ex) {
	        return new ResponseEntity<>(ex.getBodyExceptionMessage(), HttpStatus.valueOf(ex.getCode()));
	    }
	}