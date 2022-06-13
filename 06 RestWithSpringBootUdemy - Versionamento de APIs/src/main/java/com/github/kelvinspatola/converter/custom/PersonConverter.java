package com.github.kelvinspatola.converter.custom;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.github.kelvinspatola.data.model.Person;
import com.github.kelvinspatola.data.vo.v2.PersonVOV2;

@Service
public class PersonConverter {

	
	public PersonVOV2 convertEntityToVO(Person person) {
		PersonVOV2 vo = new PersonVOV2();
		vo.setId(person.getId());
		vo.setAddress(person.getAddress());
		vo.setBirthDay(new Date());
		vo.setFirstName(person.getFirstName());
		vo.setLastName(person.getLastName());
		vo.setGender(person.getGender());
		return vo;
	}
	
	public Person convertVOToEntity(PersonVOV2 vo) {
		Person entity = new Person();
		entity.setId(vo.getId());
		entity.setAddress(vo.getAddress());
		entity.setFirstName(vo.getFirstName());
		entity.setLastName(vo.getLastName());
		entity.setGender(vo.getGender());
		return entity;
	}
}
