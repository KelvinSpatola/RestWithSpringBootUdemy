package com.github.kelvinspatola.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.kelvinspatola.model.Person;
import com.github.kelvinspatola.services.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {
	@Autowired
	PersonService service;
	
	@RequestMapping(method=RequestMethod.GET, 
			produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Person> findfindAll() {
		return service.findAll();
	}
	
	@RequestMapping(value="/{id}", 
			method=RequestMethod.GET, 
			produces=MediaType.APPLICATION_JSON_VALUE)
	public Person findById(@PathVariable("id") String id) {
		return service.findById(id);
	}
	
	@RequestMapping(method=RequestMethod.POST, 
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public Person create(@RequestBody Person person) {
		return service.create(person);
	}
	
	@RequestMapping(method=RequestMethod.PUT, 
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public Person update(@RequestBody Person person) {
		return service.update(person);
	}
	
	
	@RequestMapping(value="/{id}", 
			method=RequestMethod.DELETE)
	public void delete(@PathVariable("id") String id) {
		service.delete(id);
	}
}
