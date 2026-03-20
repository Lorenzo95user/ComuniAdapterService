package dev.archety.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.archety.project.models.Utente;
import dev.archety.project.services.UtenteService;

@RestController
@RequestMapping("utente")
public class UtenteController {
	
	@Autowired
	private UtenteService serv;
	
	
	@PostMapping("add")
	public String addUtente(@RequestBody Utente utente) {
		return serv.addUtente(utente);
		
	}

}
