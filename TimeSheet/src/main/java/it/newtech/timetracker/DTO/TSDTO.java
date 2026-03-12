package it.newtech.timetracker.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TSDTO {
	
	private String nome_impiegato;
	private String nome_progetto;
	private String nome_compito;
	private LocalDateTime ora_inizio;
	private LocalDateTime ora_fine;
	private String azienda;
	
	

}
