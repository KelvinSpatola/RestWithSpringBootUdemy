package com.github.kelvinspatola.utittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.kelvinspatola.data.vo.v1.PersonVO;
import com.github.kelvinspatola.exceptions.RequiredObjectIsNullException;
import com.github.kelvinspatola.model.Person;
import com.github.kelvinspatola.repositories.PersonRepository;
import com.github.kelvinspatola.services.PersonServices;
import com.github.kelvinspatola.unittests.mapper.mocks.MockPerson;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

	MockPerson input;

	@InjectMocks
	private PersonServices service;

	@Mock
	PersonRepository repository;

	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockPerson();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {
		final Long id = 1L;
		Person entity = input.mockEntity(id.intValue());
		entity.setId(id);

		when(repository.findById(id)).thenReturn(Optional.of(entity));

		var result = service.findById(id);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		// System.out.println(result.toString());
		assertTrue(result.toString().contains("links: [</api/person/v1/" + id.intValue() + ">;rel=\"self\"]"));
		assertEquals("First Name Test" + id, result.getFirstName());
		assertEquals("Last Name Test" + id, result.getLastName());
		assertEquals("Addres Test" + id, result.getAddress());
		assertEquals(((id % 2) == 0) ? "Male" : "Female", result.getGender());
	}

	@Test
	void testCreate() {
		final Long id = 1L;
		Person entity = input.mockEntity(id.intValue());
		Person persisted = entity;
		persisted.setId(id);

		PersonVO vo = input.mockVO(id.intValue());
		vo.setKey(id);

		when(repository.save(entity)).thenReturn(persisted);

		var result = service.create(vo);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/person/v1/" + id.intValue() + ">;rel=\"self\"]"));
		assertEquals("First Name Test" + id, result.getFirstName());
		assertEquals("Last Name Test" + id, result.getLastName());
		assertEquals("Addres Test" + id, result.getAddress());
		assertEquals(((id % 2) == 0) ? "Male" : "Female", result.getGender());
	}

	@Test
	void testCreateWithNullPerson() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});

		String expectedMessage = "It is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();
		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	void testUpdate() {
		final Long id = 1L;
		Person entity = input.mockEntity(id.intValue());
		entity.setId(id);

		Person persisted = entity;
		persisted.setId(id);

		PersonVO vo = input.mockVO(id.intValue());
		vo.setKey(id);

		when(repository.findById(id)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);

		var result = service.update(vo);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/person/v1/" + id.intValue() + ">;rel=\"self\"]"));
		assertEquals("First Name Test" + id, result.getFirstName());
		assertEquals("Last Name Test" + id, result.getLastName());
		assertEquals("Addres Test" + id, result.getAddress());
		assertEquals(((id % 2) == 0) ? "Male" : "Female", result.getGender());
	}

	@Test
	void testUpdateWithNullPerson() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.update(null);
		});

		String expectedMessage = "It is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();
		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	void testDelete() {
		final Long id = 1L;
		Person entity = input.mockEntity(id.intValue());
		entity.setId(id);

		when(repository.findById(id)).thenReturn(Optional.of(entity));

		service.delete(id);
	}

	@Test
	void testFindAll() {
		List<Person> list = input.mockEntityList();

		when(repository.findAll()).thenReturn(list);

		var people = service.findAll();

		assertNotNull(people);
		assertEquals(14, people.size());

		for (int i = 0; i < people.size(); i++) {
			var person = people.get(i);
			assertNotNull(person);
			assertNotNull(person.getKey());
			assertNotNull(person.getLinks());
			assertTrue(person.toString().contains("links: [</api/person/v1/" + i + ">;rel=\"self\"]"));
			assertEquals("First Name Test" + i, person.getFirstName());
			assertEquals("Last Name Test" + i, person.getLastName());
			assertEquals("Addres Test" + i, person.getAddress());
			assertEquals(((i % 2) == 0) ? "Male" : "Female", person.getGender());
		}
	}
}
