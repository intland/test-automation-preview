package com.intland.codebeamer.integration.api.builder.statetransition;

import java.util.function.Function;

import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.swagger.client.internal.api.TrackerApi;

public class CreateReferringItemsActionBuilder extends AbstractTransitionActionBuilder<CreateReferringItemsActionBuilder> {

	private final TrackerId trackerId;
	private final TrackerId referringTrackerId;

	public CreateReferringItemsActionBuilder(TrackerApi trackerApi, TrackerId trackerId, TrackerId referringTrackerId) {
		super(trackerApi);
		this.trackerId = trackerId;
		this.referringTrackerId = referringTrackerId;
		transitionActionInfo.setName("referringItemCreator");
	}

	@SafeVarargs
	public final CreateReferringItemsActionBuilder fieldUpdates(Function<FieldUpdateBuilder, FieldUpdateBuilder>... fieldUpdateBuilders) {
		return fieldUpdates(referringTrackerId, fieldUpdateBuilders);
	}

	public CreateReferringItemsActionBuilder upstreamField(String fieldName) {
		return upstreamField(fieldName, trackerId, referringTrackerId);
	}

	public CreateReferringItemsActionBuilder downstreamField(String fieldName) {
		return downstreamField(fieldName, referringTrackerId);
	}
}
