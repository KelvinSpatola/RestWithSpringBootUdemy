package com.github.kelvinspatola.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.kelvinspatola.exceptions.ResourceNotFoundException;
import com.github.kelvinspatola.model.Person;
import com.github.kelvinspatola.repositories.PersonRepository;

@Service
public class PersonServices {
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	private PersonRepository repository;

	public List<Person> findAll() {
		logger.info("Searching all people!");
		return repository.findAll();
	}

	public Person findById(Long id) {
		logger.info("Searching for one person!");
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records for this ID!"));
	}
	
	public Person create(Person person) {
		logger.info("Creating a person!");
		return repository.save(person);
	}
	
	public Person update(Person person) {
		logger.info("Updating a person!");
		var entity = repository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No records for this ID!"));
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		return repository.save(entity);
	}
	
	public void delete(Long id) {
		logger.info("Deleting one person!");
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records for this ID!"));
		repository.delete(entity);
	}
}
