package dev.newtech.myapp.infrastructure.adapters.secondary;

import java.math.BigDecimal;

import jakarta.persistence.EmbeddedId;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Comune {

	@EmbeddedId
	private CodeCapId id;

	private String denominazioneItaAltra;

	private String denominazioneIta;

	private String siglaProvincia;

	private String denominazioneProvincia;

	private String tipologiaProvincia;

	private String codiceRegione;

	private String denominazioneRegione;

	private String tipologiaRegione;

	private String ripartizioneGeografica;

	private String flagCapoluogo;

	private String codiceBelfiore;

	private BigDecimal lat;

	private BigDecimal lon;

	private BigDecimal superficieKmq;

}
