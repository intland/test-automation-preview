package com.intland.codebeamer.integration.api.builder.statetransition;

import com.intland.codebeamer.integration.api.builder.trackerview.TrackerViewId;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.swagger.client.internal.api.TrackerApi;
import com.intland.swagger.client.model.IdNamed;
import com.intland.swagger.client.model.TrackerStateTransition;

public class TrackerStateTransitionBuilder extends AbstractTrackerStateTransitionBuilder<TrackerStateTransitionBuilder> {

	public TrackerStateTransitionBuilder(TrackerStateTransition transition, TrackerApi trackerApi, TrackerId trackerId) {
		super(transition, trackerApi, trackerId);
	}

	public TrackerStateTransitionBuilder name(String name) {
		trackerStateTransition.setName(name);
		return this;
	}

	public TrackerStateTransitionBuilder fromStatus(String fromStatusName) {
		trackerStateTransition.fromStatus(findStatusByName(fromStatusName));
		return this;
	}

	public TrackerStateTransitionBuilder toStatus(String toStatusName) {
		trackerStateTransition.toStatus(findStatusByName(toStatusName));
		return this;
	}

	public TrackerStateTransitionBuilder guard(TrackerViewId guardId) {
		trackerStateTransition.setGuard(new IdNamed().id(guardId.id()));
		return this;
	}
}
