package com.intland.codebeamer.integration.api.builder.statetransition;

import java.util.function.Function;

import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.swagger.client.internal.api.TrackerApi;

public class UpdateItemPropertiesActionBuilder extends AbstractTransitionActionBuilder<UpdateItemPropertiesActionBuilder> {

	private final TrackerId trackerId;

	public UpdateItemPropertiesActionBuilder(TrackerApi trackerApi, TrackerId trackerId) {
		super(trackerApi);
		this.trackerId = trackerId;
		transitionActionInfo.setName("selfUpdates");
	}

	@SafeVarargs
	public final UpdateItemPropertiesActionBuilder fieldUpdates(Function<FieldUpdateBuilder, FieldUpdateBuilder>... fieldUpdateBuilders) {
		return super.fieldUpdates(trackerId, fieldUpdateBuilders);
	}
}
