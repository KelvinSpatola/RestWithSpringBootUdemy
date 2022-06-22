package com.github.kelvinspatola.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.kelvinspatola.data.vo.v1.PersonVO;
import com.github.kelvinspatola.exceptions.ResourceNotFoundException;
import com.github.kelvinspatola.mapper.DozerMapper;
import com.github.kelvinspatola.model.Person;
import com.github.kelvinspatola.repositories.PersonRepository;

@Service
public class PersonServices {
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	private PersonRepository repository;

	public List<PersonVO> findAll() {
		logger.info("Searching all people!");
		return DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
	}

	public PersonVO findById(Long id) {
		logger.info("Searching for one person!");
		// get a Person object by its id
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records for this ID!"));
		// and convert it to a PersonVO object
		return DozerMapper.parseObject(entity, PersonVO.class);
	}
	
	public PersonVO create(PersonVO personVO) {
		logger.info("Creating a person!");
		// convert this personVO to a Person object.
		var entity = DozerMapper.parseObject(personVO, Person.class);
		// save this Person object in the DB and then convert it back to a PersonVO object and return it.
		return DozerMapper.parseObject(repository.save(entity), PersonVO.class);
	}
	
	public PersonVO update(PersonVO person) {
		logger.info("Updating a person!");
		var entity = repository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No records for this ID!"));
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		return DozerMapper.parseObject(repository.save(entity), PersonVO.class);
	}
	
	public void delete(Long id) {
		logger.info("Deleting one person!");
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records for this ID!"));
		repository.delete(entity);
	}
}
