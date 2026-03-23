package dev.newtech.myapp.infrastructure.adapters.secondary;

import org.springframework.stereotype.Component;

import dev.newtech.myapp.domain.model.ComuneDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Component
public class Mapper {
	
	
	public ComuneDto toComuneDto(Comune comune) {
		ComuneDto dto = new ComuneDto();
		dto.setNome(comune.getDenominazioneIta());
		dto.setProvincia(comune.getDenominazioneProvincia());
		dto.setSigla(comune.getSiglaProvincia());
		dto.setCap(comune.getId().getCap());
		dto.setIstat(comune.getId().getCodiceIstat());
		return dto;
	}

}
