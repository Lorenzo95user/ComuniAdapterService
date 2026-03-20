package dev.newtech.myapp.infrastructure.adapters.primary;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.newtech.myapp.domain.service.ComuneService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/comuni")
@AllArgsConstructor
@Slf4j
public class AdapterIn {

	private final ComuneService service;

	@GetMapping("all")
	public ResponseEntity<?> getAll() {
		log.debug("Carico citta");
		var all = service.getComuneAll();
		return (!all.isEmpty()) ? ResponseEntity.ok(all) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping("regioni")
	public ResponseEntity<?> getRegioni() {
		log.debug("Carico Regioni");
		var regioni = service.getRegioni();
		return (!regioni.isEmpty()) ? ResponseEntity.ok(regioni) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping("province")
	public ResponseEntity<?> getProvince(@RequestParam String regione) {
		log.debug("Parametro inserito : {}", regione);
		var province = service.getProvince(regione);
		return (!province.isEmpty()) ? ResponseEntity.ok(province) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping("comuni")
	public ResponseEntity<?> getComuni(@RequestParam String provincia) {
		log.debug("Parametro inserito : {}", provincia);
		var comune = service.getComuni(provincia);
		return (!comune.isEmpty()) ? ResponseEntity.ok(comune) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@GetMapping("cap")
	public ResponseEntity<?> getCapByComune(@RequestParam String comune){
		log.debug("Comune inserito : {}",comune);
		var cap = service.getCapByComune(comune);
		return (!cap.isEmpty()) ? ResponseEntity.ok(cap) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@GetMapping("sigla")
	public ResponseEntity<?> getSiglaByComune(@RequestParam String comune){
		log.debug("Comune inserito : {}",comune);
		var sigla = service.getSiglaByComune(comune);
		return (!sigla.isEmpty()) ? ResponseEntity.ok(sigla) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
