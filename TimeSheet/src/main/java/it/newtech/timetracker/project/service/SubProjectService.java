package it.newtech.timetracker.project.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import it.newtech.timetracker.project.entity.Project;
import it.newtech.timetracker.project.entity.SubProject;
import it.newtech.timetracker.project.repository.ProjectRepository;
import it.newtech.timetracker.project.repository.SubProjectRepository;
import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class SubProjectService {
	
	  private SubProjectRepository subProjectRepository;
	  private ProjectRepository projectRepository;


	  
	  public SubProject createSubProject (Long projectId , SubProject subProject) {
		  return projectRepository.findById(projectId).map(project ->{
		  SubProject subProj = new SubProject();
		  subProj.setName(subProject.getName());
		  subProj.setProject(project);
		  return subProjectRepository.save(subProj);
		  }).orElse(null);
		  
	  }

}
