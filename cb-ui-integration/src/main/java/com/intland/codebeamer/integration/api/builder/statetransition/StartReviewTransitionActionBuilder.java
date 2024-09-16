package com.intland.codebeamer.integration.api.builder.statetransition;

import java.util.function.Function;

import com.intland.swagger.client.internal.api.TrackerApi;

public class StartReviewTransitionActionBuilder extends AbstractTransitionActionBuilder<StartReviewTransitionActionBuilder> {

	public StartReviewTransitionActionBuilder(TrackerApi trackerApi) {
		super(trackerApi);
		transitionActionInfo.setName("startReview");
	}

	public StartReviewTransitionActionBuilder params(Function<CreateReviewParamsBuilder, CreateReviewParamsBuilder> paramBuilderFunction) {
		transitionActionInfo.parameters(paramBuilderFunction.apply(new CreateReviewParamsBuilder()).build());
		return this;
	}

}
