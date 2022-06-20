package com.github.kelvinspatola.converter.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.kelvinspatola.data.model.Book;
import com.github.kelvinspatola.data.vo.v1.BookVO;

public class MockBook {
	
	public Book mockEntity() {
		return mockEntity(0);
	}

	public BookVO mockVO() {
		return mockVO(0);
	}

	public List<Book> mockEntityList() {
		List<Book> books = new ArrayList<Book>();
		for (int i = 0; i < 14; i++) {
			books.add(mockEntity(i));
		}
		return books;
	}

	public List<BookVO> mockVOList() {
		List<BookVO> books = new ArrayList<>();
		for (int i = 0; i < 14; i++) {
			books.add(mockVO(i));
		}
		return books;
	}

	private Book mockEntity(Integer number) {
		Book book = new Book();
		book.setId(number.longValue());
		book.setAuthor("Author Test" + number);
		book.setLaunchDate(new Date());
		book.setPrice(number.floatValue());
		book.setTitle("Title Test" + number);
		return book;
	}

	private BookVO mockVO(Integer number) {
		BookVO book = new BookVO();
		book.setKey(number.longValue());
		book.setAuthor("Author Test" + number);
		book.setLaunchDate(new Date());
		book.setPrice(number.floatValue());
		book.setTitle("Title Test" + number);
		return book;
	}
}
