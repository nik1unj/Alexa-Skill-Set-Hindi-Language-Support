package com.nsr.handlers;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Map;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.fasterxml.jackson.databind.ObjectMapper;


public class YesIntentHandler implements RequestHandler{

	
	@Override
	public boolean canHandle(HandlerInput input) {
		
		return input.matches(intentName("AMAZON.YesIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) { 
		Map<String, Object> map = input.getAttributesManager().getRequestAttributes();
    	ObjectMapper objectMapper = new ObjectMapper();
		Object mapObject=map.get("map");
		Map<String, Object> yesMap=	objectMapper.convertValue(mapObject, Map.class);

    	String speechText =  yesMap.get("yesMessage").toString();
		
		return input.getResponseBuilder().withSpeech(speechText)
				.withSimpleCard("MovieSession", speechText)
				.withReprompt(speechText)
				.withShouldEndSession(false)
				.build();
	}

}
