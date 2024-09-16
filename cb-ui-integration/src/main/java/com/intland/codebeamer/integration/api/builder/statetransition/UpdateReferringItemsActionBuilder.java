package com.intland.codebeamer.integration.api.builder.statetransition;

import java.util.function.Function;

import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.swagger.client.internal.api.TrackerApi;

public class UpdateReferringItemsActionBuilder extends AbstractTransitionActionBuilder<UpdateReferringItemsActionBuilder> {

	private final TrackerId trackerId;
	private final TrackerId referringTrackerId;

	public UpdateReferringItemsActionBuilder(TrackerApi trackerApi, TrackerId trackerId, TrackerId referringTrackerId) {
		super(trackerApi);
		this.trackerId = trackerId;
		this.referringTrackerId = referringTrackerId;
		transitionActionInfo.setName("referringItemUpdater");
	}

	@SafeVarargs
	public final UpdateReferringItemsActionBuilder fieldUpdates(Function<FieldUpdateBuilder, FieldUpdateBuilder>... fieldUpdateBuilders) {
		return fieldUpdates(referringTrackerId, fieldUpdateBuilders);
	}

	public UpdateReferringItemsActionBuilder upstreamField(String fieldName) {
		return super.upstreamField(fieldName, trackerId, referringTrackerId);
	}

	public UpdateReferringItemsActionBuilder downstreamField(String fieldName) {
		return super.downstreamField(fieldName, referringTrackerId);
	}

	public UpdateReferringItemsActionBuilder statuses(String... statuses) {
		return super.statuses(referringTrackerId, statuses);
	}

}
