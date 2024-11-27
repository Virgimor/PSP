package ies.jandula.Bonoloto.models;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class LotteryResultsId implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 2325923384141482503L;
	
	private int numero1;
	
	private int numero2;
	
	private int numero3;
	
	private int numero4;
	
	private int numero5;
	
	private int numero6;
	
	private Date fehca;
}
