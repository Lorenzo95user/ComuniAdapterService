package dev.archety.project.controllers;

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

import dev.archety.project.DTO.ComuneDTO;
import dev.archety.project.models.Comune;
import dev.archety.project.services.ComuneService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ComuneController {

   
	private  ComuneService service;

	@GetMapping("all")
	public ResponseEntity<?> getAllCom(){
		List<Comune> AllComuni = service.getAllComune();
		return (!AllComuni.isEmpty()) ? ResponseEntity.ok(AllComuni) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("com/all")
	public ResponseEntity<List<ComuneDTO>> getAllDto(){
		var allDto = service.getAllCom();
		return (!allDto.isEmpty()) ? ResponseEntity.ok(service.getAllCom()) : ResponseEntity.noContent().build();
	}
	
	
	@GetMapping("find/cap/{nome}")
	public ResponseEntity<?> getCapByComu(@PathVariable String nome){
		var cap = service.getCapByCom(nome);
		return (!cap.isEmpty()) ? ResponseEntity.ok(cap) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("find/cap")
	public ResponseEntity<?> getCapByComun(@RequestParam String nome){
		var cap = service.getCapByCom(nome);
		return (!cap.isEmpty()) ? ResponseEntity.ok(cap) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("find/sigla")
	public ResponseEntity<?> getSiglaByDen(@RequestParam String nome) {
		var sigla = service.getSiglaProvByDenominazione(nome);
		return (!sigla.isEmpty()) ? ResponseEntity.ok(sigla) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("find/den/reg")
	public ResponseEntity<?> getDenRegByDenIta(@RequestParam String nome) {
		var nomeRegione = service.getDenomByNomeCom(nome);
		return (!nomeRegione.isEmpty()) ? ResponseEntity.ok(nomeRegione) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("find/den/prov")
	public ResponseEntity<?> getDenProByDenIta(@RequestParam String nome) {
		var provincia = service.getDenomProvByNomeCom(nome);
		return (!provincia.isEmpty()) ? ResponseEntity.ok(provincia) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("regioni")
	public ResponseEntity<?> getRegioni() {
	    var regioni = service.getTutteRegioni();
	    return (!regioni.isEmpty()) ? ResponseEntity.ok(regioni) : ResponseEntity.noContent().build();
	}

	@GetMapping("province")
	public ResponseEntity<?> getProvince(@RequestParam String regione) {
	    var province = service.getProvincePerRegione(regione);
	    return (!province.isEmpty()) ? ResponseEntity.ok(province) : ResponseEntity.noContent().build();
	}

	@GetMapping("comuni")
	public ResponseEntity<?> getComuni(@RequestParam String provincia) {
	    var comuni = service.getComuniPerProvincia(provincia);
	    return (!comuni.isEmpty()) ? ResponseEntity.ok(comuni) : ResponseEntity.noContent().build();
	}
	

	@GetMapping("comune/{nome}")
	public ResponseEntity<?> getComune(@PathVariable String nome) {
		var comune = service.getComune(nome);
		return (!comune.isEmpty()) ? ResponseEntity.ok(comune) : ResponseEntity.noContent().build();
	}
	
	@PostMapping("/comune/add")
	public ResponseEntity<?> addCom(@Valid @RequestBody ComuneDTO dto){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.addDto(dto));
	}
	
	@DeleteMapping("/comune/remove/{nome}/{cap}")
	public ResponseEntity<?> removeComune(@Valid @PathVariable String nome ,@Valid @PathVariable String cap) {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body((service.removeComune(nome,cap)));
	}
	
	@PutMapping("update/{nome}/{cap}")
	public ResponseEntity<?> upComune(@Valid @PathVariable String nome ,@Valid @PathVariable String cap ,@Valid @RequestBody ComuneDTO dto) {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.updateComune(nome,cap,dto));
	}
	@GetMapping("combyid/{nome}/{cap}")
	public ResponseEntity<?> comuneById(@PathVariable String nome ,@PathVariable String cap) {
		var comune = service.getByIdAndNome(nome, cap);
		 return (comune!=null) ? ResponseEntity.ok(comune) : ResponseEntity.noContent().build();
	}
}
