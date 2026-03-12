package it.newtech.timetracker.task.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.newtech.timetracker.task.entity.Task;
import it.newtech.timetracker.task.service.TaskService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/task")
@AllArgsConstructor
public class TaskController {
	 
	private TaskService tServ;
	
	@GetMapping
	public List<Task> getAll(){
		return tServ.getAllTask();
	}
	
	@GetMapping("{id}")
	 public Optional<Task> getTaskById(@PathVariable Long id){
		 return tServ.getById(id);
	 }
}
