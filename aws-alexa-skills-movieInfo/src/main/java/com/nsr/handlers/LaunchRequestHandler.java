package com.nsr.handlers;


import static com.amazon.ask.request.Predicates.requestType;

import java.util.Map;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LaunchRequestHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(requestType(LaunchRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

    	Map<String, Object> map = input.getAttributesManager().getRequestAttributes();
	    ObjectMapper objectMapper = new ObjectMapper();
		Object mapObject=map.get("map");
		Map<String, Object> welcomeMap=	objectMapper.convertValue(mapObject, Map.class);
		
    	String speechText = welcomeMap.get("welcomeMessage").toString();
        String repromptText = "Please tell me movie name to get movie info";
        
        return input.getResponseBuilder()
                .withSimpleCard("MovieSession", speechText)
                .withSpeech(speechText)
                .withReprompt(repromptText)
                .build();
    }
}