package it.newtech.timetracker.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.newtech.timetracker.DTO.ProjectDTO;
import it.newtech.timetracker.project.entity.Project;
import it.newtech.timetracker.project.entity.SubProject;
import it.newtech.timetracker.project.repository.ProjectRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjectService {
	
	private ProjectRepository projectRepository;
	
	public Optional<Project> getProject(Long id){
		return projectRepository.findById(id);
	}
	
	public List<Project> getAll(){
		return projectRepository.findAllP();
	}
	
	
	public List<ProjectDTO> getProjClient(String name){
		List<Object[]> list = projectRepository.findClientsProjects(name);
		if(list.isEmpty()) {
			return null;
		}
			List<ProjectDTO> dtolist = list.stream().map(c ->{
				ProjectDTO dto = new ProjectDTO();
				dto.setCliente((String) c[0]);
				dto.setNomeProgetto((String) c[1]);
				dto.setSotto_progetto((String) c[2]);
				dto.setCompito((String) c[3]);
				return dto;
			}).toList();
		return dtolist;
	}
	public List<Object[]> getSubAndTask(){
		return projectRepository.findSubAndTask();
	}
	public Project createProject(Project proj) {
		Project project = new Project();
		project.setName(proj.getName());
		project.setCustomerName(proj.getCustomerName());
		return projectRepository.save(project);
		
	}

}
