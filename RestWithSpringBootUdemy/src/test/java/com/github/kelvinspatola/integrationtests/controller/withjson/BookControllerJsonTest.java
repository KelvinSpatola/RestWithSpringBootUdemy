package com.github.kelvinspatola.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kelvinspatola.configs.TestConfigs;
import com.github.kelvinspatola.integrationtests.testcontainers.AbstractIntegrationTest;
import com.github.kelvinspatola.integrationtests.vo.AccountCredentialsVO;
import com.github.kelvinspatola.integrationtests.vo.BookVO;
import com.github.kelvinspatola.integrationtests.vo.TokenVO;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
class BookControllerJsonTest extends AbstractIntegrationTest {
	
	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	
	private static BookVO book;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		book = new BookVO();
	}
	
	@Test
	@Order(0)
	public void authorization() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");
		
		var accessToken = given()
				.basePath("/auth/signin")
				.port(TestConfigs.SERVER_PORT)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(user)
				.when().post().then().statusCode(200).extract().body().as(TokenVO.class).getAccessToken();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/book/v1")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}

	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockBook();
		
		var content =given()
				.spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(book)
				.when().post().then().statusCode(200).extract().body().asString();
		
		BookVO persistedBook = objectMapper.readValue(content, BookVO.class);
		book = persistedBook;
		
		assertNotNull(persistedBook);
		assertNotNull(persistedBook.getId());
		assertNotNull(persistedBook.getFirstName());
		assertNotNull(persistedBook.getFirstName());
		assertNotNull(persistedBook.getAddress());
		assertNotNull(persistedBook.getGender());
		
		assertTrue(persistedBook.getId() > 0);
		
		assertEquals("Nelson", persistedBook.getFirstName());
		assertEquals("Piquet", persistedBook.getLastName());
		assertEquals("Brasília - DF - Brasil", persistedBook.getAddress());
		assertEquals("Male", persistedBook.getGender());
	}
	
	@Test
	@Order(2)
	public void testUpdate() throws JsonMappingException, JsonProcessingException {
		book.setLastName("Piquet Souto Maior");
		
		var content =given()
				.spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(book)
				.when().post().then().statusCode(200).extract().body().asString();
		
		BookVO persistedBook = objectMapper.readValue(content, BookVO.class);
		book = persistedBook;
		
		assertNotNull(persistedBook);
		assertNotNull(persistedBook.getId());
		assertNotNull(persistedBook.getFirstName());
		assertNotNull(persistedBook.getFirstName());
		assertNotNull(persistedBook.getAddress());
		assertNotNull(persistedBook.getGender());
		
		assertEquals(book.getId(), persistedBook.getId());
		
		assertEquals("Nelson", persistedBook.getFirstName());
		assertEquals("Piquet Souto Maior", persistedBook.getLastName());
		assertEquals("Brasília - DF - Brasil", persistedBook.getAddress());
		assertEquals("Male", persistedBook.getGender());
	}

	@Test
	@Order(3)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		mockBook();
		
		var content =given()
				.spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_EXAMPLE_AUTHORIZED)
				.pathParam("id", book.getId())
				.when().get("{id}").then().statusCode(200).extract().body().asString();
		
		BookVO persistedBook = objectMapper.readValue(content, BookVO.class);
		book = persistedBook;
		
		assertNotNull(persistedBook);
		assertNotNull(persistedBook.getId());
		assertNotNull(persistedBook.getFirstName());
		assertNotNull(persistedBook.getFirstName());
		assertNotNull(persistedBook.getAddress());
		assertNotNull(persistedBook.getGender());
		
		assertEquals(book.getId(), persistedBook.getId());
		
		assertEquals("Nelson", persistedBook.getFirstName());
		assertEquals("Piquet Souto Maior", persistedBook.getLastName());
		assertEquals("Brasília - DF - Brasil", persistedBook.getAddress());
		assertEquals("Male", persistedBook.getGender());
	}
	
	@Test
	@Order(4)
	public void testDelete() throws JsonMappingException, JsonProcessingException {
		given()
			.spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.pathParam("id", book.getId())
			.when().delete("{id}").then().statusCode(204);
	}
	
	@Test
	@Order(5)
	public void testFindAll() throws JsonMappingException, JsonProcessingException {
		var content = given()
				.spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.when().get().then().statusCode(200)
				.extract()
				.body()
				.asString();
				
		List<BookVO> people = objectMapper.readValue(content, new TypeReference<List<BookVO>>() {});
		
		BookVO foundBookOne = people.get(0);
		
		assertNotNull(foundBookOne);
		assertNotNull(foundBookOne.getId());
		assertNotNull(foundBookOne.getFirstName());
		assertNotNull(foundBookOne.getFirstName());
		assertNotNull(foundBookOne.getAddress());
		assertNotNull(foundBookOne.getGender());
		
		assertEquals(1, foundBookOne.getId());
		
		assertEquals("Kelvin", foundBookOne.getFirstName());
		assertEquals("Spátola", foundBookOne.getLastName());
		assertEquals("Aveiro - Portugal", foundBookOne.getAddress());
		assertEquals("Male", foundBookOne.getGender());
		
		BookVO foundBookSix = people.get(5);
		
		assertNotNull(foundBookSix);
		assertNotNull(foundBookSix.getId());
		assertNotNull(foundBookSix.getFirstName());
		assertNotNull(foundBookSix.getFirstName());
		assertNotNull(foundBookSix.getAddress());
		assertNotNull(foundBookSix.getGender());
		
		assertEquals(9, foundBookSix.getId());
		
		assertEquals("Nikola", foundBookSix.getFirstName());
		assertEquals("Tesla", foundBookSix.getLastName());
		assertEquals("Croacia", foundBookSix.getAddress());
		assertEquals("Male", foundBookSix.getGender());
	}
	
	@Test
	@Order(6)
	public void testFindAllWithoutToken() throws JsonMappingException, JsonProcessingException {
		
		RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
				.setBasePath("/api/book/v1")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		given()
				.spec(specificationWithoutToken)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.when().get().then().statusCode(403);
	}
	
	private void mockBook() {
		book.setFirstName("Nelson");
		book.setLastName("Piquet");
		book.setAddress("Brasília - DF - Brasil");
		book.setGender("Male");
	}
}
