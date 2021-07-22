/*
     Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

     Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
     except in compliance with the License. A copy of the License is located at

         http://aws.amazon.com/apache2.0/

     or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
     the specific language governing permissions and limitations under the License.
*/

package com.nsr.handlers;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Map;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HelpIntentHandler implements RequestHandler {

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("AMAZON.HelpIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		Map<String, Object> map = input.getAttributesManager().getRequestAttributes();
		ObjectMapper objectMapper = new ObjectMapper();
		Object mapObject = map.get("map");
		Map<String, Object> helpMap = objectMapper.convertValue(mapObject, Map.class);

		String speechText = helpMap.get("helpMessage").toString();

		return input.getResponseBuilder().withSpeech(speechText).withSimpleCard("MovieSession", speechText)
				.withReprompt(speechText).withShouldEndSession(true).build();
	}
}
