package com.github.kelvinspatola.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

public class DozerMapper {
	private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();
	
	public static <O, D> D parseObject(O origin, Class<D> destinationObj) {
		return mapper.map(origin, destinationObj);
	}
	
	public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destinationObj) {
		return origin.stream().map(o -> mapper.map(o, destinationObj)).collect(Collectors.toList());
	}
}
