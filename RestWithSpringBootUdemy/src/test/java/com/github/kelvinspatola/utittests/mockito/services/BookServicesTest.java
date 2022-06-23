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

import com.github.kelvinspatola.data.vo.v1.BookVO;
import com.github.kelvinspatola.exceptions.RequiredObjectIsNullException;
import com.github.kelvinspatola.model.Book;
import com.github.kelvinspatola.repositories.BookRepository;
import com.github.kelvinspatola.services.BookServices;
import com.github.kelvinspatola.unittests.mapper.mocks.MockBook;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServicesTest {

	MockBook input;

	@InjectMocks
	private BookServices service;

	@Mock
	BookRepository repository;

	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockBook();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {
		final Long id = 1L;
		Book entity = input.mockEntity(id.intValue());
		entity.setId(id);

		when(repository.findById(id)).thenReturn(Optional.of(entity));

		var result = service.findById(id);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/book/v1/" + id.intValue() + ">;rel=\"self\"]"));
		assertEquals("Author Test" + id, result.getAuthor());
		assertEquals(id.doubleValue(), result.getPrice());
		assertEquals("Title Test" + id, result.getTitle());
	}

	@Test
	void testCreate() {
		final Long id = 1L;
		Book entity = input.mockEntity(id.intValue());
		Book persisted = entity;
		persisted.setId(id);

		BookVO vo = input.mockVO(id.intValue());
		vo.setKey(id);

		when(repository.save(entity)).thenReturn(persisted);

		var result = service.create(vo);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/book/v1/" + id.intValue() + ">;rel=\"self\"]"));
		assertEquals("Author Test" + id, result.getAuthor());
		assertEquals(id.doubleValue(), result.getPrice());
		assertEquals("Title Test" + id, result.getTitle());
	}

	@Test
	void testCreateWithNullBook() {
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
		Book entity = input.mockEntity(id.intValue());
		entity.setId(id);

		Book persisted = entity;
		persisted.setId(id);

		BookVO vo = input.mockVO(id.intValue());
		vo.setKey(id);

		when(repository.findById(id)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);

		var result = service.update(vo);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/book/v1/" + id.intValue() + ">;rel=\"self\"]"));
		assertEquals("Author Test" + id, result.getAuthor());
		assertEquals(id.doubleValue(), result.getPrice());
		assertEquals("Title Test" + id, result.getTitle());
	}

	@Test
	void testUpdateWithNullBook() {
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
		Book entity = input.mockEntity(id.intValue());
		entity.setId(id);

		when(repository.findById(id)).thenReturn(Optional.of(entity));

		service.delete(id);
	}

	@Test
	void testFindAll() {
		List<Book> list = input.mockEntityList();

		when(repository.findAll()).thenReturn(list);

		var books = service.findAll();

		assertNotNull(books);
		assertEquals(14, books.size());

		for (int i = 0; i < books.size(); i++) {
			var book = books.get(i);
			assertNotNull(book);
			assertNotNull(book.getKey());
			assertNotNull(book.getLinks());
			assertTrue(book.toString().contains("links: [</api/book/v1/" + i + ">;rel=\"self\"]"));
			assertEquals("Author Test" + i, book.getAuthor());
			assertEquals(i, book.getPrice());
			assertEquals("Title Test" + i, book.getTitle());
		}
	}
}
