package it.newtech.territory.DTO;

import it.newtech.territory.DTO.ComuneDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComuneDTO {
    
    private String nome;
    private String sigla;
    private String provincia;
    private String cap;
    private String istat;
	@Override
	public String toString() {
		return "[nome=" + nome + ", sigla=" + sigla + ", provincia=" + provincia + ", cap=" + cap + ", istat="
				+ istat + "]";
	}
    
    
    

  
    

    
}
