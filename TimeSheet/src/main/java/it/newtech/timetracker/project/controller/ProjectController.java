package it.newtech.timetracker.project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.newtech.timetracker.DTO.ProjectDTO;
import it.newtech.timetracker.project.entity.Project;
import it.newtech.timetracker.project.service.ProjectService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/project")
@AllArgsConstructor
public class ProjectController {
	
	private ProjectService pServ;
//	progetto da id
	@GetMapping("{id}")
	public Optional<Project> getProjectById(@PathVariable Long id){
		return pServ.getProject(id);
	}
//	tutti i progetti completi ( nome progetto , sottoprogetto e task)
	@GetMapping("all")
	public List<Project> getAllProjects(){
		return pServ.getAll();
	}

//	 sottoprogetti e tasks
	@GetMapping("/sub")
	public List<Object[]> getSubAndTask(){
		return pServ.getSubAndTask();
	}

}
