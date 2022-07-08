package com.github.kelvinspatola.integrationtests.controller.withyaml.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;
import lombok.extern.java.Log;

@Log
public class YMLMapper implements ObjectMapper {

	private com.fasterxml.jackson.databind.ObjectMapper objectMapper;
	protected TypeFactory typeFactory;
	
	
	
	public YMLMapper() {
		this.objectMapper = new com.fasterxml.jackson.databind.ObjectMapper(new YAMLFactory());
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		typeFactory = TypeFactory.defaultInstance();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Object deserialize(ObjectMapperDeserializationContext context) {
		try {
			String dataToDeserialize = context.getDataToDeserialize().asString();
			Class type = (Class) context.getType();
			
			log.info("Trying to deserialize object of type" + type);			
			return objectMapper.readValue(dataToDeserialize, typeFactory.constructType(type));
			
		} catch (JsonMappingException e) {
			log.info("Error while deserializing the object");
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			log.info("Error while deserializing the object");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object serialize(ObjectMapperSerializationContext context) {
		try {
			log.info("Trying to serialize the object");			
			return objectMapper.writeValueAsString(context.getObjectToSerialize());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
}