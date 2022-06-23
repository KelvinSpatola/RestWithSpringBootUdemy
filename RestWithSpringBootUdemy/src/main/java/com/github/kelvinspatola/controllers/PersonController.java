package com.github.kelvinspatola.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.kelvinspatola.data.vo.v1.PersonVO;
import com.github.kelvinspatola.services.PersonServices;
import com.github.kelvinspatola.util.MediaType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "People", description = "Endpoints for managing People")
public class PersonController {
	@Autowired
	private PersonServices service;
	
	// FIND ALL
	@GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(
			summary = "Finds all People", 
			description = "Some description to this endpoint.", 
			tags = { "People" }, 
			responses = { 
					@ApiResponse(
							description = "Success", 
							responseCode = "200", 
							content = { 
									@Content(
											mediaType = "application/json", 
											array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
											)
									}),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
					}
			)
	public List<PersonVO> findAll() {
		return service.findAll();
	}

	// FIND BY ID
	@GetMapping(
			value = "/{id}", 
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(
			summary = "Finds a person by their ID", 
			description = "Some description to this endpoint.", 
			tags = { "People" }, 
			responses = { 
					@ApiResponse(
							description = "Success", responseCode = "200", 
							content = @Content(schema = @Schema(implementation = PersonVO.class))
								),
					@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
					}
			)
	public PersonVO findById(@PathVariable(value = "id") Long id) {
		return service.findById(id);
	}
	
	// CREATE
	@PostMapping(
			consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }, 
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(
			summary = "Adds a new person", 
			description = "Adds a new person by passing in a JSON, XML or YML representation of the person", 
			tags = { "People" }, 
			responses = { 
					@ApiResponse(
							description = "Success", responseCode = "200", 
							content = @Content(schema = @Schema(implementation = PersonVO.class))
								),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
					}
			)
	public PersonVO create(@RequestBody PersonVO person) {
		return service.create(person);
	}
	
	// UPDATE
	@PutMapping(
			consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }, 
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(
			summary = "Updates a person", 
			description = "Updates a person by passing in a JSON, XML or YML representation of the person", 
			tags = { "People" }, 
			responses = { 
					@ApiResponse(
							description = "Updated", responseCode = "200", 
							content = @Content(schema = @Schema(implementation = PersonVO.class))
								),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
					}
			)
	public PersonVO update(@RequestBody PersonVO person) {
		return service.create(person);
	}
	
	// DELETE
	@DeleteMapping(value = "/{id}")
	@Operation(
			summary = "Deletes a person", 
			description = "Deletes a person by passing an ID", 
			tags = { "People" }, 
			responses = { 
					@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
					}
			)
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
