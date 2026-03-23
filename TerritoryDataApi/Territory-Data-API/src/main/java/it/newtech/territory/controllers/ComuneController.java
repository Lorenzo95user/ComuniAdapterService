package it.newtech.territory.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.newtech.territory.DTO.ComuneDTO;
import it.newtech.territory.models.Comune;
import it.newtech.territory.services.ComuneService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class ComuneController {

   
	private  ComuneService service;

//	comuni con tutte le specifiche
	@GetMapping("fullspec/all")
	public ResponseEntity<?> getAllComune(){
		log.debug("Ricevuta richiesta GET tutti i comuni con specifiche complete");
		List<Comune> AllComuni = service.getAllComune();
		if(AllComuni.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		StringBuilder sb = new StringBuilder ("\n----- COMUNI TROVATI -----\n");
		AllComuni.forEach(comune -> {
			sb.append("NOME : ").append(comune.getDenominazioneIta()).append(" , ").append("PROVINCIA : ")
			.append(comune.getDenominazioneProvincia()).append(" , ").append("SIGLA : ")
			.append(comune.getSiglaProvincia()).append(" , ").append("CAP : ").append(comune.getId().getCap())
			.append(" , ")
			.append("CODICE REGIONE : ").append(comune.getCodiceRegione()).append(" , ").append("REGIONE : ")
			.append(comune.getDenominazioneRegione()).append(" , ")
			.append("TIPOLOGIA REGIONE : ").append(comune.getTipologiaRegione()).append(" , ").append("CODICE BELFIORE : ")
			.append(comune.getCodiceBelfiore()).append(" , ")
			.append("CODICE ISTAT : ").append(comune.getId().getCodiceIstat()).append(" \n");
		});
		log.debug("Risultato ricerca : {}",sb.toString());
		return ResponseEntity.ok(AllComuni);
	}
	
//	ottieni comuni con provincia , sigla provincia , cap e codice istat
	@GetMapping("all")
	public ResponseEntity<List<ComuneDTO>> getAllDto(){
		log.debug("Ricevuta richiesta GET tutti i comuni in DTO");
		var dtoList = service.getAllCom();
		if(dtoList.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		StringBuilder sb = new StringBuilder ("\n----- COMUNI TROVATI -----\n");
		dtoList.forEach(dto -> {
			sb.append("NOME : ").append(dto.getNome()).append(" , ").append("PROVINCIA : ").append(dto.getProvincia()).append(" , ").append("SIGLA : ")
			.append(dto.getSigla()).append(" , ").append("CAP : ").append(dto.getCap())
			.append(" , ").append("CODICE ISTAT : ").append(dto.getIstat()).append(" \n");
		});
		log.debug("Risultato ricerca : {}",sb.toString());
		return ResponseEntity.ok(dtoList);
	}
	
	
	
//	ottieni cap da comune
	@GetMapping("find/cap")
	public ResponseEntity<?> getCapByComune(@RequestParam String comune){
		log.debug("Ricevuta richiesta GET cap comune {}",comune);
		var capList = service.getCapByCom(comune);
		if(capList.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		log.debug("CAP trovati per {}: \n{}", comune, capList);
		return ResponseEntity.ok(capList);
	}
	
//	ottieni sigla provincia da comune
	@GetMapping("find/sigla")
	public ResponseEntity<?> getSiglaByDenominazioneIta(@RequestParam String comune) {
		log.debug("Ricevuta richiesta GET sigla provincia del comune {}",comune);
		var sigla = service.getSiglaProvByDenominazione(comune);
		if(sigla==null) {
			return ResponseEntity.noContent().build();
		}
		log.debug("Sigla trovata per {}: {}", comune, sigla);
		return ResponseEntity.ok(sigla);
	}
	
//	ottieni regione da comune
	@GetMapping("find/den/reg")
	public ResponseEntity<?> getDenRegByDenominazioneIta(@RequestParam String comune) {
		log.debug("Ricevuta richiesta GET nome regione del comune {}",comune);
		var nomeRegione = service.getDenomByNomeCom(comune);
		if(!nomeRegione.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		log.debug("Regione trovata per {}: {}", comune, nomeRegione);
		return ResponseEntity.ok(nomeRegione);
	}
	
//	ottieni provincia da comune
	@GetMapping("find/den/prov")
	public ResponseEntity<?> getDenProByDenominazioneIta(@RequestParam String comune) {
		log.debug("Ricevuta richiesta GET nome provincia del comune {}",comune);
		var nomeProvincia = service.getDenomProvByNomeCom(comune);
		if(nomeProvincia.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		log.debug("Provincia trovata per {}: \n{}", comune, nomeProvincia);
		return ResponseEntity.ok(nomeProvincia);
	}
	
//	ottieni regioni italiane
	@GetMapping("regioni")
	public ResponseEntity<?> getRegioni() {
		log.debug("Ricevuta richiesta GET tutte le regioni");
	    var regioni = service.getTutteRegioni();
	    if(regioni.isEmpty()) {
	    	return ResponseEntity.noContent().build();
	    }
	    log.debug("Regione trovate : \n{}", regioni);
	    return ResponseEntity.ok(regioni); 
	}

//	ottieni province da regione
	@GetMapping("province")
	public ResponseEntity<?> getProvince(@RequestParam String regione) {
		log.debug("Ricevuta richiesta GET province della regione {}",regione);
	    var province = service.getProvincePerRegione(regione);
	    if(province.isEmpty()) {
	    	return ResponseEntity.noContent().build();
	    }
	    log.debug("Province trovate da regione {}: \n{}",regione , province);
	    return ResponseEntity.ok(province);
	}

//	ottieni comuni da provincia
	@GetMapping("comuni")
	public ResponseEntity<?> getComuni(@RequestParam String provincia) {
		log.debug("Ricevuta richiesta GET comuni della provincia {}",provincia);
	    var comuni = service.getComuniPerProvincia(provincia);
	    if(comuni.isEmpty()) {
	    	return ResponseEntity.noContent().build();
	    }
	    log.debug("Comuni trovati da provincia {}: \n{}",provincia , comuni);
	    return ResponseEntity.ok(comuni);
	}
	
//ottieni comune dal nome : con dati provincia , sigla provincia , cap e codice istat
	@GetMapping("comune/{comune}")
	public ResponseEntity<?> getComune(@PathVariable String comune) {
		log.debug("Ricevuta richiesta GET specifiche comune in DTO del comune {}",comune);
		var comuneDto = service.getComune(comune);
		if(comuneDto.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		StringBuilder sb = new StringBuilder ("\n----- COMUNI TROVATI -----\n");
		comuneDto.forEach(dto -> {
			sb.append("NOME : ").append(dto.getNome()).append(" , ").append("PROVINCIA : ").append(dto.getProvincia()).append(" , ").append("SIGLA : ")
			.append(dto.getSigla()).append(" , ").append("CAP : ").append(dto.getCap())
			.append(" , ").append("CODICE ISTAT : ").append(dto.getIstat()).append(" \n");
		});
		log.debug("Comuni trovati da {}: \n{}",comune, sb.toString());
		return ResponseEntity.ok(comuneDto);
	}
	
//	Aggiungi comune
	@PostMapping("/comune/add")
	public ResponseEntity<?> addComune(@Valid @RequestBody ComuneDTO dto){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.addDto(dto));
	}
	
//	cancella comune
	@DeleteMapping("/comune/remove/{comune}/{cap}")
	public ResponseEntity<?> removeComune(@Valid @PathVariable String comune ,@Valid @PathVariable String cap) {
		log.debug("Ricevuta richiesta DELETE comune {}",comune);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body((service.removeComune(comune,cap)));
	}
	
//	aggiorna comune
	@PutMapping("update/{comune}/{cap}")
	public ResponseEntity<?> updateComune(@Valid @PathVariable String comune ,@Valid @PathVariable String cap ,@Valid @RequestBody ComuneDTO dto) {
		log.debug("Ricevuta richiesta PUT comune {}",comune);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.updateComune(comune,cap,dto));
	}
	
}
