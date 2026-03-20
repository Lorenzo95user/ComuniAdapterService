package dev.archety.project.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor

public class Utente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String cognome;
	private LocalDate dataDiNascita;
	
	Utente(String nome , String cognome , LocalDate dataDiNascita){
		this.nome=nome;
		this.cognome=cognome;
		this.dataDiNascita=dataDiNascita;
	}
	
	
	
	

}
