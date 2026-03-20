package dev.newtech.myapp.domain.ports.in;

import java.util.List;

import dev.newtech.myapp.domain.model.ComuneDto;


public interface PortIn {
	
	public List<ComuneDto> getComuneAll();
	
	public List<String> getRegioni();
	
	public List<String> getProvince(String regione);
	
	public List<String> getComuni(String provincia);
	
	public List<String> getCapByComune(String comune);
	
	public List<String> getSiglaByComune ( String comune);

}
