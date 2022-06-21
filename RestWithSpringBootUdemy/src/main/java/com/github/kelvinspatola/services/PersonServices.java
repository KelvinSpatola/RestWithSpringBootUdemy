package com.github.kelvinspatola.services;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.github.kelvinspatola.model.Person;

@Service
public class PersonServices {
	private final AtomicLong counter = new AtomicLong();
	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	public List<Person> findAll() {
		logger.info("Searching all people!");
		return IntStream.range(0, 8).mapToObj(this::mockPerson).collect(Collectors.toList());
	}

	public Person findById(String id) {
		logger.info("Searching for one person!");

		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("Kelvin");
		person.setLastName("Spátola");
		person.setAddress("Aveiro - Portugal");
		person.setGender("Male");
		return person;
	}
	
	public Person create(Person person) {
		logger.info("Creating a person!");
		return person;
	}
	
	public Person update(Person person) {
		logger.info("Updating a person!");
		return person;
	}
	
	public void delete(String id) {
		logger.info("Deleting one person!");
	}

	private Person mockPerson(int i) {
		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("Person name " + i);
		person.setLastName("Last name " + i);
		person.setAddress("Somewhere in Portugal");
		person.setGender(i % 2 == 0 ? "Male" : "Female");
		return person;
	}
}
