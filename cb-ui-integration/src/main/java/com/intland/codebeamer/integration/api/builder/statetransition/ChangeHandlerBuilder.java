package com.intland.codebeamer.integration.api.builder.statetransition;

import com.intland.codebeamer.integration.api.builder.trackerview.TrackerViewId;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.swagger.client.internal.api.TrackerApi;
import com.intland.swagger.client.model.IdNamed;
import com.intland.swagger.client.model.TrackerStateTransition;

public class ChangeHandlerBuilder extends AbstractTrackerStateTransitionBuilder<ChangeHandlerBuilder> {

	public ChangeHandlerBuilder(TrackerStateTransition transition, TrackerApi trackerApi, TrackerId trackerId) {
		super(transition, trackerApi, trackerId);
	}

	public ChangeHandlerBuilder status(String statusName) {
		IdNamed status = findStatusByName(statusName);
		trackerStateTransition.fromStatus(status);
		trackerStateTransition.toStatus(status);
		return this;
	}

	public ChangeHandlerBuilder changes(TrackerViewId guardId) {
		super.guard(guardId);
		return this;
	}

}
