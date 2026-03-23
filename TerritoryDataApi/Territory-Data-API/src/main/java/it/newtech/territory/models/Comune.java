package it.newtech.territory.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;


@Entity
@Table(name = "gi_comuni_cap")
@Data
@NoArgsConstructor
public class Comune{

	@EmbeddedId
	private CodeCapId id;

    @Column(name = "denominazione_ita_altra")
    private String denominazioneItaAltra;

    @Column(name = "denominazione_ita")
    private String denominazioneIta;

    @Column(name = "sigla_provincia")
    private String siglaProvincia;

    @Column(name = "denominazione_provincia")
    private String denominazioneProvincia;
    
    @Column(name = "tipologia_provincia")
    private String tipologiaProvincia;

    @Column(name = "codice_regione")
    private String codiceRegione;

    @Column(name = "denominazione_regione")
    private String denominazioneRegione;

    @Column(name = "tipologia_regione")
    private String tipologiaRegione;

    @Column(name = "ripartizione_geografica")
    private String ripartizioneGeografica;

    @Column(name = "flag_capoluogo")
    private String flagCapoluogo;

    @Column(name = "codice_belfiore")
    private String codiceBelfiore;

    @Column(name = "lat")
    private BigDecimal lat;

    @Column(name = "lon")
    private BigDecimal lon;

    @Column(name = "superficie_kmq")
    private BigDecimal superficieKmq;
}
