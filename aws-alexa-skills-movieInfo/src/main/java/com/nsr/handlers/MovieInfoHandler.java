package com.nsr.handlers;

import static com.amazon.ask.request.Predicates.intentName;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsr.models.Movie;

public class MovieInfoHandler implements RequestHandler {
	public static final String MOVIE_SLOT = "movie";
	public static final String MVOIE_KEY = "MOVIE";
	static String movieName = null;
	static AWSLambda client = AWSLambdaClientBuilder.defaultClient();
	static InvokeRequest invokeRequest = new InvokeRequest();

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("movieInfoIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {

		String speechText = null, repromptText = null;

		Request request = input.getRequestEnvelope().getRequest();
		IntentRequest intentRequest = (IntentRequest) request;

		// Get Intent
		Intent intent = intentRequest.getIntent();

		// Get Slot
		Map<String, Slot> slots = intent.getSlots();
		Slot movieNameSlot = slots.get(MOVIE_SLOT);
		
		Map<String, Object> map = input.getAttributesManager().getRequestAttributes();
    	ObjectMapper objectMapper = new ObjectMapper();
		Object mapObject=map.get("map");
		Map<String, Object> movieInfoMap=	objectMapper.convertValue(mapObject, Map.class);

		if (movieNameSlot != null && movieNameSlot.getResolutions() != null
				&& movieNameSlot.getResolutions().toString().contains("ER_SUCCESS_MATCH")) {

			// Get slot value
			String slotMovieName = movieNameSlot.getValue();
			input.getAttributesManager().setSessionAttributes(Collections.singletonMap(MVOIE_KEY, slotMovieName));

			// Get movie name enter by user
			Stream<String> movieStream = movieNameSlot.getResolutions().getResolutionsPerAuthority().stream()
					.map(resolution -> resolution.getValues().stream().map(value -> {
						String name = value.getValue().getName();
						return name;
					})).findFirst().orElse(null);
			
			movieName = movieStream.findAny().get();
			Movie movieObject = new Movie();
			try {
				movieObject = (Movie) handleRequest();
			} catch (ParseException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	
			speechText = movieObject.getMovieName() + movieObject.getMovieDesc() + movieInfoMap.get("movieNameMessage")
					+ movieInfoMap.get("movieMessage") + movieObject.getReleaseYear().toString()
					+ movieInfoMap.get("responseMessage");

			repromptText = "You can ask me your favorite movie?";

		} else {

			speechText = movieInfoMap.get("inValidMessage").toString();
			repromptText = "I'm not sure what your favorite movie";
		}

		return input.getResponseBuilder().withSimpleCard("MovieSession", speechText).withSpeech(speechText)
				.withReprompt(repromptText).withShouldEndSession(false).build();
	}

	public static Object handleRequest() throws ParseException, JsonParseException, JsonMappingException, IOException {

		JSONObject payloadObject = new JSONObject();

		payloadObject.put("httpMethod", "GET");
		payloadObject.put("movieName", movieName);
		String payload = payloadObject.toString();

		invokeRequest.withFunctionName("arn:aws:lambda:us-east-2:342123803606:function:Alexa-Movie-Info-Test")
				.withPayload(payload);

		InvokeResult invokeResult = client.invoke(invokeRequest);
		String result = new String(invokeResult.getPayload().array());
		ObjectMapper objectMapper = new ObjectMapper();
		Movie movie = objectMapper.readValue(result, Movie.class);
		String movieDescAndRating = movieName + " is a " + movie.getMovieDesc() + " and movie release year is : "
				+ movie.getReleaseYear().toString();

		// return movieDescAndRating;
		return movie;
	}

}