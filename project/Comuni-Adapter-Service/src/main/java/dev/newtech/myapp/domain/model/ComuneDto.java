package dev.newtech.myapp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComuneDto {
	
	
	private String nome;
	private String sigla;
	private String provincia;
	private String cap;
	private String istat;
	@Override
	public String toString() {
		return "Nome=" + nome + ", Sigla=" + sigla + ", Provincia=" + provincia + ", CAP=" + cap + ", Codice Istat="
				+ istat + " ";
	}

}
