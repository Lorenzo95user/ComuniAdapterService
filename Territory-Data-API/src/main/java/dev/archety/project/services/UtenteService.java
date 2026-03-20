package dev.archety.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.archety.project.models.Utente;
import dev.archety.project.repositories.UtenteRepository;

@Service
public class UtenteService {
	
	
	@Autowired
	private UtenteRepository repo;
	
	public String addUtente(Utente utente) {
		repo.save(utente);
		return "Utente salvato";
		
	}

}
