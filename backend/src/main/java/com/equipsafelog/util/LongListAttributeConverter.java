package com.equipsafelog.util;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;

public class LongListAttributeConverter implements AttributeConverter<List<Long>, String> {
	ObjectMapper mapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(List<Long> longList) {
		try {
			return mapper.writeValueAsString(longList);
		} catch (JsonProcessingException e) {
			return null;
		}
	}

	@Override
	public List<Long> convertToEntityAttribute(String string) {
		try {
			return mapper.readValue(string, new TypeReference<List<Long>>() {
			});
		} catch (JsonProcessingException e) {
			return new ArrayList<Long>();
		}
	}
}