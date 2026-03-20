package it.newtech.timetracker.DTO;

import java.util.List;

import it.newtech.timetracker.project.entity.Project;
import it.newtech.timetracker.project.entity.SubProject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
	
	private String nomeProgetto;
	private String cliente;
	private String sotto_progetto;
	private String compito;
	private Double hours;

}
