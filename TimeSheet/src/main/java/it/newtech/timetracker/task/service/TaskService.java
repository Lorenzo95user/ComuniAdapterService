package it.newtech.timetracker.task.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.newtech.timetracker.task.entity.Task;
import it.newtech.timetracker.task.repository.TaskRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TaskService {
	 
	
	private TaskRepository repo;
	
	public List<Task> getAllTask(){
		return repo.findAll();
	}
	
	public Optional<Task> getById(Long id){
		return repo.findById(id);
	}
}
