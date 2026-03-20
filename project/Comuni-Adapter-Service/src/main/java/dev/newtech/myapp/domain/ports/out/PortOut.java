package dev.newtech.myapp.domain.ports.out;

import java.util.List;

import dev.newtech.myapp.domain.model.ComuneDto;


public interface PortOut {

	public List<ComuneDto> getAllComune();

	public List<String> getRegioni();

	public List<String> getProvince(String Regione);

	public List<String> getComuni(String Provincia);

	public List<String> getCapByComune(String comune);
	
	public List<String> getSiglaByComune ( String comune);
}
