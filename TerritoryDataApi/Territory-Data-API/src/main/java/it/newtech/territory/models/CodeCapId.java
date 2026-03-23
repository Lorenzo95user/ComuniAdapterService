package it.newtech.territory.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeCapId  implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "cap")
    private String cap;
	
	@Column(name = "codice_istat")
    private String codiceIstat;

}
