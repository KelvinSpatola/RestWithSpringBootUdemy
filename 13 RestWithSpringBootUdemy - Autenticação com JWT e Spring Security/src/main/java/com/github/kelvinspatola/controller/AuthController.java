package com.github.kelvinspatola.controller;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.kelvinspatola.repository.UserRepository;
import com.github.kelvinspatola.security.AccountCredentialsVO;
import com.github.kelvinspatola.security.jwt.JwtTokenProvider;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtTokenProvider tokenProvider;
	
	@Autowired
	UserRepository repository;
	
	
	@ApiOperation(value = "Authenticate an user by credentials")
	@PostMapping(value = "/signin", produces = { "application/json", "application/xml", "application/x-yaml" }, 
			consumes = { "application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity create(@RequestBody AccountCredentialsVO data) { // POST
		try {
			var username = data.getUserName();
			var password = data.getPassword();
			
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			
			var user = repository.findByUserName(username);
			var token = "";
			
			if (user != null) {
				token = tokenProvider.createToken(username, user.getRoles());
			} else {
				throw new UsernameNotFoundException("Username " + username + " not found!");
			}
			
			Map<Object, Object> model = new HashMap<>();
			model.put("username", username);
			model.put("token", token);
			model.put("token", token);
			return ok(model);
			
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}

	}
	
}
