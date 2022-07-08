package com.github.kelvinspatola.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.dozermapper.core.DozerConverter;
import com.github.kelvinspatola.controllers.PersonController;
import com.github.kelvinspatola.data.vo.v1.PersonVO;
import com.github.kelvinspatola.exceptions.RequiredObjectIsNullException;
import com.github.kelvinspatola.exceptions.ResourceNotFoundException;
import com.github.kelvinspatola.mapper.DozerMapper;
import com.github.kelvinspatola.model.Person;
import com.github.kelvinspatola.repositories.PersonRepository;

import jakarta.transaction.Transactional;
import lombok.extern.java.Log;

@Log
@Service
public class PersonServices {
	
	@Autowired
	private PersonRepository repository;

	public List<PersonVO> findAll() {
		log.info("Searching all people!");
		var people = DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
		people.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		return people;
	}

	public PersonVO findById(Long id) {
		log.info("Searching for one person!");
		// get a Person object by its id
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records for this ID!"));
		// and convert it to a PersonVO object
		var vo = DozerMapper.parseObject(entity, PersonVO.class);
		// add a link to the corresponding PersonController method that controls this request (HEATEOAS)
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;
	}
	
	public PersonVO create(PersonVO personVO) {
		if (personVO == null) throw new RequiredObjectIsNullException();
		
		log.info("Creating a person!");
		// convert this personVO to a Person object.
		var entity = DozerMapper.parseObject(personVO, Person.class);
		// save this Person object in the DB and then convert it back to a PersonVO object and return it.
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public PersonVO update(PersonVO personVO) {
		if (personVO == null) throw new RequiredObjectIsNullException();
		
		log.info("Updating a person!");
		var entity = repository.findById(personVO.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records for this ID!"));
		entity.setFirstName(personVO.getFirstName());
		entity.setLastName(personVO.getLastName());
		entity.setAddress(personVO.getAddress());
		entity.setGender(personVO.getGender());
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	@Transactional
	public PersonVO disablePerson(Long id) {
		log.info("Disabling one person!");
		
		repository.disablePerson(id);
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		return DozerMapper.parseObject(entity, PersonVO.class);
	}
	
	public List<PersonVO> findAllDisabled() {
		return DozerMapper.parseListObjects(repository.findAllDisabled(), PersonVO.class);
	}	
	
	public void delete(Long id) {
		log.info("Deleting one person!");
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records for this ID!"));
		repository.delete(entity);
	}
}
