package it.newtech.timetracker.auth.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.newtech.timetracker.auth.entity.User;
import it.newtech.timetracker.auth.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	
	private UserRepository repo;
	
	public Optional<User> getUser(Long id) {
		return repo.findById(id);
	}
	
	public List<User> getAll(){
		return repo.findAll();
	}

}
