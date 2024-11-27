package ies.jandula.Bonoloto.models;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultadoLoteria {
	
	private List<Integer> numeros;
	
	private int reintegro;
	
	private String premio;

}
