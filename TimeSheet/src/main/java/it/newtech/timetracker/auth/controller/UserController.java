package it.newtech.timetracker.auth.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.newtech.timetracker.auth.entity.User;
import it.newtech.timetracker.auth.service.UserService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/user")
@AllArgsConstructor
public class UserController {
	
	private UserService uServ;
	
	
	@GetMapping("{id}")
	public Optional<User> getUserById(@PathVariable Long id) {
		return uServ.getUser(id);
	}
	
	@GetMapping
	public List<User> getAllUsers(){
		return uServ.getAll();
	}

}
