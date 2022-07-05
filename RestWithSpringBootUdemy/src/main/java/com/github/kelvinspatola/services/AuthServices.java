package com.github.kelvinspatola.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.kelvinspatola.data.vo.v1.security.AccountCredentialsVO;
import com.github.kelvinspatola.data.vo.v1.security.TokenVO;
import com.github.kelvinspatola.repositories.UserRepository;
import com.github.kelvinspatola.security.jwt.JwtTokenProvider;

@Service
public class AuthServices {
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository repository;
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity signin(AccountCredentialsVO data) {
		if (isInvalidParams(data)) 
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

		try {
			var username = data.getUsername();
			var password = data.getPassword();
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password)); // tenta fazer o login
			
			var user = repository.findByUsername(username);
			var tokenResponse = new TokenVO();
			
			if (user != null) {
				tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
			} else {
				throw new UsernameNotFoundException("Username " + username + " not found!");
			}
			return ResponseEntity.ok(tokenResponse);
			
		} catch (Exception e) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
	}
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity refreshToken(String username, String refreshToken) {
		if (isInvalidParams(username, refreshToken))
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

		var user = repository.findByUsername(username);
		var tokenResponse = new TokenVO();

		if (user != null) {
			tokenResponse = tokenProvider.refreshToken(refreshToken);
		} else {
			throw new UsernameNotFoundException("Username " + username + " not found!");
		}
		return ResponseEntity.ok(tokenResponse);
	}
	

	private boolean isInvalidParams(String username, String refreshToken) {
		return refreshToken == null || refreshToken.isBlank() || username == null || username.isBlank();
	}

	private boolean isInvalidParams(AccountCredentialsVO data) {
		return data == null || data.getUsername() == null || data.getUsername().isBlank() || data.getPassword() == null || data.getPassword().isBlank();
	}
}
