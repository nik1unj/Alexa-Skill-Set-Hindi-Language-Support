package com.nsr.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.interceptor.RequestInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LocalInterCeptor implements RequestInterceptor {

	@Override
	public void process(HandlerInput input) {

		String locale = input.getRequestEnvelope().getRequest().getLocale();
		Map<String, Object> attributeMap = input.getAttributesManager().getRequestAttributes();
		JSONParser jsonParser = new JSONParser();

		InputStream is = LocalInterCeptor.class.getResourceAsStream("/languages/" + locale + ".json");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		Object obj = new Object();

		try {
			obj = jsonParser.parse(reader);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> map = objectMapper.convertValue(obj, Map.class);
		attributeMap.put("map", map);

	}

}