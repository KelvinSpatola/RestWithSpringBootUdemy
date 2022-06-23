package com.github.kelvinspatola.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.List;

import com.github.kelvinspatola.data.vo.v1.BookVO;
import com.github.kelvinspatola.model.Book;

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
    
    public Book mockEntity(Integer number) {
        Book book = new Book();
        book.setId(number.longValue());
        book.setAuthor("Author Test" + number);
//        book.setLaunchDate(new Date());
        book.setPrice(number.doubleValue());
        book.setTitle("Title Test" + number);
        return book;
    }

    public BookVO mockVO(Integer number) {
        BookVO bookVO = new BookVO();
        bookVO.setKey(number.longValue());
        bookVO.setAuthor("Author Test" + number);
//        bookVO.setLaunchDate(new Date());
        bookVO.setPrice(number.doubleValue());
        bookVO.setTitle("Title Test" + number);
        return bookVO;
    }

}
