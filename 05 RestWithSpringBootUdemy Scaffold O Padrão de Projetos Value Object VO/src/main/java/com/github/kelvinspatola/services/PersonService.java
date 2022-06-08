package com.github.kelvinspatola.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.kelvinspatola.exception.ResourceNotFoundException;
import com.github.kelvinspatola.model.Person;
import com.github.kelvinspatola.repository.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
	PersonRepository repository;
	
	public Person create(Person person) {
		return repository.save(person);
	}
		
	public List<Person> findAll() {
		return repository.findAll();
	}
	
	public Person findById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
	}
	
	public Person update(Person person) {
		Person entity = repository.findById(person.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		return repository.save(entity) ;
	}
	
	public void delete(Long id) {
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
		
		repository.delete(entity);
	}
}
