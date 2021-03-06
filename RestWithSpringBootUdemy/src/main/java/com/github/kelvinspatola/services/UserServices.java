package com.github.kelvinspatola.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.kelvinspatola.repositories.UserRepository;

import lombok.extern.java.Log;

@Log
@Service
public class UserServices implements UserDetailsService {
	
	@Autowired
	private UserRepository repository;

	// CONSTRUCTOR
	public UserServices(UserRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Searching one user by name " + username + "!");
		
		var user = repository.findByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("Username " + username + " not found!");
		} 
		return user;
	}
	
}
