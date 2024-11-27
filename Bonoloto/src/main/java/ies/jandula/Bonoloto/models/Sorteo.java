package ies.jandula.Bonoloto.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Sorteo {
	
	@Id
	private String fehca;
	@Column(nullable = false)
	private int numero1;
	@Column(nullable = false)
	private int numero2;
	@Column(nullable = false)
	private int numero3;
	@Column(nullable = false)
	private int numero4;
	@Column(nullable = false)
	private int numero5;
	@Column(nullable = false)
	private int numero6;
	
	@Column(nullable = false)
	private Integer complementario;
	
	@Column(nullable = true)	
	private Integer reintegro;

}
