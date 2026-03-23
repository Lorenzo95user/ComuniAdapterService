package it.newtech.territory.services;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

import it.newtech.territory.DTO.ComuneDTO;
import it.newtech.territory.models.CodeCapId;
import it.newtech.territory.models.Comune;
import it.newtech.territory.repositories.ComuneRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class ComuneService {
	

	private ComuneRepository repo;

	public List<ComuneDTO> getComune(String nome ) {
		try {
			log.debug("Sto cercando il comune {} in formato DTO" , nome);
			List<Comune> comuni = repo.findByDenominazioneItaliana(nome);
			if (comuni.isEmpty()) {
				log.warn("Errore nel caricamento del Comune {}", nome);
				return List.of();
			};
			List<ComuneDTO> listaDto = comuni.stream().map(c ->{
				ComuneDTO dto=new ComuneDTO();
				dto.setNome(c.getDenominazioneIta());
				dto.setProvincia(c.getDenominazioneProvincia());
				dto.setSigla(c.getSiglaProvincia());
				dto.setCap(c.getId().getCap());
				dto.setIstat(c.getId().getCodiceIstat());
				return dto;}).toList(); 
				return listaDto;
		    	}catch (Exception e) {
		    		log.error("ERRORE TECNICO nel recupero del Comune {}",nome);
		    		return List.of();
		    	}
	}
	public String addDto(ComuneDTO dto) {
		try {
			log.debug("Sto aggiungendo il Comune : {} ", dto);
			Comune com = new Comune();
			com.setId(new CodeCapId());
		    com.setDenominazioneIta(dto.getNome());
		    com.setDenominazioneProvincia(dto.getProvincia());
		    com.setSiglaProvincia(dto.getSigla());
		    com.getId().setCap(dto.getCap());
		    com.getId().setCodiceIstat(dto.getIstat());
		    repo.save(com);return ("Comune aggiungo: \n"+dto);
			}catch (Exception e) {
				log.error("qualcosa è andato storto , dati inseriti: \n{}",dto);
				return ("Errore nell'inserimento dei dati , dati inseriti: \n"+dto);
				}
	}
	public String removeComune(String nome,String cap) {
		
		try {
			log.debug("Sto rimuovendo il comune {}",nome);
			if(repo.findByDenominazioneItaliana(nome).isEmpty()) {
					log.warn("Il Comune di {} non esiste",nome);
					return ("Comune: \n" + nome +" "+cap + "\n" + "non trovato");
				}
			CodeCapId cod = repo.findCodeCapIdByNome(nome,cap);
			repo.deleteComuneByIdAndNome(nome,cod);
			return ("Comune " + nome + " eliminato");
			}catch (Exception e) {
				log.error("Problema nella rimozione del Comune {}: ",nome);
				return ("errore " +e.getMessage() + " nella ricerca del comune: " + nome);
		}
	}
	public Comune updateComune(String nome ,String cap , ComuneDTO dto){
		try {
			log.debug("Sto cercando il Comune di {} da modificare",nome);
			Comune com= repo.findComuneByIdAndNome(nome, repo.findCodeCapIdByNome(nome, cap));
			if(com == null) {
				log.warn("Il Comune da modificare non è stato trovato\n dati Comune inseriti : {}",dto );
				return null;
			}
			repo.delete(com);
			Comune c = new Comune();
			c.setDenominazioneIta(dto.getNome());
			c.setDenominazioneProvincia(dto.getProvincia());
			c.setSiglaProvincia(dto.getSigla());
			c.setId(new CodeCapId(dto.getCap(),dto.getIstat()));
			repo.save(c);
			return c;
			}catch (Exception e) {
				log.error("Problema nella modifica dati del Comune {}",nome);
				return null;
			}
	}
	public List<ComuneDTO> getAllCom(){
		try {
			log.debug("Sto caricando tutti i comuni come DTO");
			List<Comune> comuni = repo.findAll();
			if(comuni.isEmpty()) {
				log.warn("Problema nella lettura del file");
				return List.of();
				}
			List<ComuneDTO> listadto = comuni.stream().map(c -> {ComuneDTO dto = new ComuneDTO();
			dto.setNome(c.getDenominazioneIta());
			dto.setProvincia(c.getDenominazioneProvincia());
			dto.setSigla(c.getSiglaProvincia());
			dto.setCap(c.getId().getCap());
			dto.setIstat(c.getId().getCodiceIstat());
			return dto;
			}).toList();
			return listadto;
		}catch (Exception e) {
			log.error("Errore nella lettura dei file in formato DTO");
			return List.of();
		}
	}
	public List<String> getCapByCom(String nome){
		try {
			log.debug("Cerco il CAP del Comune {}",nome);
			List<String> list = repo.findCapByDenominazioneIta(nome);
			if(list.isEmpty()) {
				log.warn("Cap del Comune {} non trovato",nome);
				return List.of();
			}
			return list;
		}catch (Exception e) {
			log.error("Errore nel recupero CAP : {}", e);
			return List.of();
		}
	}
	public String getSiglaProvByDenominazione(String nome) {
		try {
			log.debug("Cerco la sigla Provincia del Comune {}",nome);
			String str = repo.findSiglaProvinciaByDenominazioneIta(nome);
			if(str.isEmpty()) {
				log.warn("La sigla del Comune cercato non esiste");
				return null;
			}
			return str;
		}catch (Exception e) {
			log.error("Errore nella ricerca della sigla Provincia");
			return null;
		}
	}
	public String getDenomByNomeCom(String nome) {
		try {
		log.debug("Cerco la Regione del Comune {}",nome);
		String str = repo.findDenominazioneRegioneByDenominazioneIta(nome);
		if (str.isEmpty()) {
			log.warn("Regione non trovata");
			return "Regione non trovata";
		}return str;
		}catch (Exception e) {
			return e.getMessage();
		}
	}
	public String getDenomProvByNomeCom(String nome) {
		try {
			log.debug("Cerco la Provincia del Comune {}",nome);
			String string = repo.findDenominazioneProvinciaByDenominazioneIta(nome);
			if(string.isEmpty()) {
				log.warn("Provincia non trovata");
				return "Provincia non trovata";
			}
			return string;
		}catch (Exception e) {
			return e.getMessage();
		}
	}
	public List<String> getTutteRegioni(){
		try {
		log.debug("Carico tutte le Regioni");
		return repo.findAllRegioni();
		}catch (Exception e) {
			log.error("Errore nel caricamente regioni : {}",e);
			List<String> list = Collections.singletonList("Errore nel caricamento regioni");
			return list;
		}
	}
	public List<String> getProvincePerRegione(String regione){
		try {
			log.debug("Carico le Province della Regione {}",regione);
			return repo.findProvinceByRegione(regione);
		}catch (Exception e) {
			log.error("Errore nel caricamento Province : {}" , e);
			List<String> str = Collections.singletonList("Errore caricamento province");
			return  str;
		}
	}
	public List<String> getComuniPerProvincia(String provincia){
		try {
			log.debug("Carico i Comuni della Provincia {}",provincia);
			return repo.findComuniByProvincia(provincia);
		}catch (Exception e) {
			log.error("Errore caricamento comuni : {}",e);
			List<String> list = Collections.singletonList("Errore nel caricamento dei comuni");
			return list;
		}
	}
	public Comune getByIdAndNome(String nome , String cap) {
		try {
			log.debug("Cerco Comune dal nome e dal CAP");
			Comune com = repo.findComuneByIdAndNome(nome, repo.findCodeCapIdByNome(nome, cap));
			if (com.equals(null)) {
				log.warn("Il Comune non esiste");
				return null;
			}return com;
		}catch (Exception e) {
			log.error("Errore nella ricerca del Comune {}",nome);
			return null;
		}
	}
	public List<Comune> getAllComune (){
		try {
			log.debug("In cerca di comuni");
			var comuni = repo.findAll();
			if(comuni.isEmpty()) {
				log.warn("Comuni non trovati");
				return List.of();
			}log.debug("Comuni trovati : {}" , comuni);
			return comuni;
		}catch(Exception e) {
			log.error("Errore : {}" , e);
			return List.of();
		}
	}

	
}
