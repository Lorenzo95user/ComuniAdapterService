package dev.newtech.myapp.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import dev.newtech.myapp.domain.model.ComuneDto;
import dev.newtech.myapp.domain.ports.in.PortIn;
import dev.newtech.myapp.domain.ports.out.PortOut;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class ComuneService implements PortIn {

	private final PortOut port;

	@Override
	public List<ComuneDto> getComuneAll() {

		var comuni = port.getAllComune();
		if (comuni.isEmpty()) {
			throw new ResourceNotFound("Nessun comune trovato");
		}
		return comuni;
	}

	@Override
	public List<String> getRegioni() {
		var regioni = port.getRegioni();
		if (regioni.isEmpty()) {
			throw new ResourceNotFound("Nessuna Regione trovata");
		}
		return regioni;
	}

	@Override
	public List<String> getProvince(String regione) {
		var provincie = port.getProvince(regione);
		if (provincie.isEmpty()) {
			throw new ResourceNotFound("Nessuna Provincia trovata per la Regione " + regione);
		}
		return provincie;
	}

	@Override
	public List<String> getComuni(String provincia) {
		var comuni = port.getComuni(provincia);
		if (comuni.isEmpty()) {
			throw new ResourceNotFound("Nessun Comune trovato per la Provincia " + provincia);
		}
		return comuni;
	}

	@Override
	public List<String> getCapByComune(String comune) {
		var cap = port.getCapByComune(comune);
		if(cap.isEmpty()) {
			throw new ResourceNotFound("Comune "+ comune + " non trovato");
		}return cap;
	}

	@Override
	public List<String> getSiglaByComune(String comune) {
		var sigla = port.getSiglaByComune(comune);
		if(sigla.isEmpty()) {
			throw new ResourceNotFound("Comune "+ comune + " non trovato");
		}return sigla;
	}
	
}
