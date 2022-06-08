package com.github.kelvinspatola.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.kelvinspatola.model.Person;
import com.github.kelvinspatola.services.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {
	@Autowired
	PersonService service;
	
	@GetMapping
	public List<Person> findfindAll() {
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public Person findById(@PathVariable("id") Long id) {
		return service.findById(id);
	}
	
	@PostMapping
	public Person create(@RequestBody Person person) {
		return service.create(person);
	}
	
	@PutMapping
	public Person update(@RequestBody Person person) {
		return service.update(person);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
}