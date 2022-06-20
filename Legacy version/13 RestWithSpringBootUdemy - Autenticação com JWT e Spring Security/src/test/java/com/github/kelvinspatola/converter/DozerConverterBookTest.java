package com.github.kelvinspatola.converter;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.kelvinspatola.converter.mocks.MockPerson;
import com.github.kelvinspatola.data.model.Book;
import com.github.kelvinspatola.data.vo.v1.BookVO;

public class DozerConverterBookTest {
	 MockPerson inputObject;

	    @Before
	    public void setUp() {
	        inputObject = new MockPerson();
	    }

	    @Test
	    public void parseEntityToVOTest() {
	        BookVO output = DozerConverter.parseObject(inputObject.mockEntity(), BookVO.class);
	        Assert.assertEquals("Author Test0", output.getAuthor());
	        Assert.assertEquals("Title Test0", output.getTitle());
	    }

	    @Test
	    public void parseEntityListToVOListTest() {
	        List<BookVO> outputList = DozerConverter.parseListObjects(inputObject.mockEntityList(), BookVO.class);
	        BookVO outputZero = outputList.get(0);
	        
	        Assert.assertEquals(Long.valueOf(0), outputZero.getKey());
	        Assert.assertEquals("Author Test0", outputZero.getAuthor());
	        Assert.assertEquals(Float.valueOf(0), outputZero.getPrice());
	        Assert.assertEquals("Title Test0", outputZero.getTitle());
	        
	        BookVO outputSeven = outputList.get(7);
	        
	        Assert.assertEquals(Long.valueOf(7), outputSeven.getKey());
	        Assert.assertEquals("Author Test7", outputSeven.getAuthor());
	        Assert.assertEquals(Float.valueOf(7), outputSeven.getPrice());
	        Assert.assertEquals("Title Test7", outputSeven.getTitle());
	        
	        BookVO outputTwelve = outputList.get(12);
	        
	        Assert.assertEquals(Long.valueOf(12), outputTwelve.getKey());
	        Assert.assertEquals("Author Test12", outputTwelve.getAuthor());
	        Assert.assertEquals(Float.valueOf(12), outputTwelve.getPrice());
	        Assert.assertEquals("Title Test12", outputTwelve.getTitle());
	    }

	    @Test
	    public void parseVOToEntityTest() {
	        Book output = DozerConverter.parseObject(inputObject.mockVO(), Book.class);
	        Assert.assertEquals(Long.valueOf(0), output.getId());
	        Assert.assertEquals("Author Test0", output.getAuthor());
	        Assert.assertEquals(Float.valueOf(0), output.getPrice());
	        Assert.assertEquals("Title Test0", output.getTitle());
	    }

	    @Test
	    public void parserVOListToEntityListTest() {
	        List<Book> outputList = DozerConverter.parseListObjects(inputObject.mockVOList(), Book.class);
	        Book outputZero = outputList.get(0);
	        
	        Assert.assertEquals(Long.valueOf(0), outputZero.getId());
	        Assert.assertEquals("Author Test0", outputZero.getAuthor());
	        Assert.assertEquals(Float.valueOf(0), outputZero.getPrice());
	        Assert.assertEquals("Title Test0", outputZero.getTitle());
	        
	        Book outputSeven = outputList.get(7);
	        
	        Assert.assertEquals(Long.valueOf(7), outputSeven.getId());
	        Assert.assertEquals("Author Test7", outputSeven.getAuthor());
	        Assert.assertEquals(Float.valueOf(7), outputSeven.getPrice());
	        Assert.assertEquals("Title Test7", outputSeven.getTitle());
	        
	        Book outputTwelve = outputList.get(12);
	        
	        Assert.assertEquals(Long.valueOf(12), outputTwelve.getId());
	        Assert.assertEquals("Author Test12", outputTwelve.getAuthor());
	        Assert.assertEquals(Float.valueOf(12), outputTwelve.getPrice());
	        Assert.assertEquals("Title Test12", outputTwelve.getTitle());
	    }
}
