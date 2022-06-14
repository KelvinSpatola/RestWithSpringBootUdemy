package com.github.kelvinspatola.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

public class DozerConverter {
	private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();
	
	public static <O, D> D parseObject(O origin, Class<D> destination) {
		return mapper.map(origin, destination);
	}
	
	public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
		List<D> destinationObjects = new ArrayList<>();
		
		for (Object o : origin) {
			destinationObjects.add(mapper.map(o, destination));
		}
		return destinationObjects;
	}
	
	public static <O, D> List<D> parseListObjects2(List<O> origin, Class<D> destination) {		
		return origin.stream()
				.map(o -> mapper.map(o, destination))
				.collect(Collectors.toList());
	}
}