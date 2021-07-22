package com.nsr.handlers;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;

public class MovieInfoStreamHandler extends SkillStreamHandler {

    private static Skill getSkill() {
    	System.out.println("inside getSkill method");
        return Skills.standard()
                .addRequestHandlers(
                        new MovieInfoHandler(),
                        new LaunchRequestHandler(),
                        new CancelandStopIntentHandler(),
                        new SessionEndedRequestHandler(),
                        new HelpIntentHandler(),
                        new FallbackIntentHandler(),
                        new YesIntentHandler(),
                        new NoIntentHandler())
                .addRequestInterceptor(new LocalInterCeptor())
                // Add your skill id below
                .withSkillId("add your key")
                .build();
    }

    public MovieInfoStreamHandler() {
        super(getSkill());
        System.out.println("inside MovieInfoStreamHandler method");
    }

}
