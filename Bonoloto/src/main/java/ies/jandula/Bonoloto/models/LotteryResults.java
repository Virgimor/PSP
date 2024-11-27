package ies.jandula.Bonoloto.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class LotteryResults {
	
	@EmbeddedId
	private LotteryResultsId lotteryResultsId;
	
	@Column(nullable = false)
	private Integer complementario;
	
	@Column(nullable = false)	
	private Integer reintegro;

}
