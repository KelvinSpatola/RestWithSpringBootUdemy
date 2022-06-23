package com.github.kelvinspatola.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.kelvinspatola.controllers.BookController;
import com.github.kelvinspatola.data.vo.v1.BookVO;
import com.github.kelvinspatola.exceptions.RequiredObjectIsNullException;
import com.github.kelvinspatola.exceptions.ResourceNotFoundException;
import com.github.kelvinspatola.mapper.DozerMapper;
import com.github.kelvinspatola.model.Book;
import com.github.kelvinspatola.repositories.BookRepository;

@Service
public class BookServices {
	private Logger logger = Logger.getLogger(BookServices.class.getName());
	
	@Autowired
	private BookRepository repository;

	public List<BookVO> findAll() {
		logger.info("Searching all books!");
		var bookList = DozerMapper.parseListObjects(repository.findAll(), BookVO.class);
		bookList.forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
		return bookList;
	}

	public BookVO findById(Long id) {
		logger.info("Searching for one book!");
		// get a Book object by its id
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records for this ID!"));
		// and convert it to a BookVO object
		var vo = DozerMapper.parseObject(entity, BookVO.class);
		// add a link to the corresponding BookController method that controls this request (HEATEOAS)
		vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		return vo;
	}
	
	public BookVO create(BookVO bookVO) {
		if (bookVO == null) throw new RequiredObjectIsNullException();
		
		logger.info("Creating a book!");
		// convert this personVO to a Book object.
		var entity = DozerMapper.parseObject(bookVO, Book.class);
		// save this Book object in the DB and then convert it back to a BookVO object and return it.
		var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public BookVO update(BookVO bookVO) {
		if (bookVO == null) throw new RequiredObjectIsNullException();
		
		logger.info("Updating a book!");
		var entity = repository.findById(bookVO.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records for this ID!"));
		entity.setAuthor(bookVO.getAuthor());
		entity.setLaunchDate(bookVO.getLaunchDate());
		entity.setPrice(bookVO.getPrice());
		entity.setTitle(bookVO.getTitle());
		var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public void delete(Long id) {
		logger.info("Deleting one book!");
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records for this ID!"));
		repository.delete(entity);
	}
}
