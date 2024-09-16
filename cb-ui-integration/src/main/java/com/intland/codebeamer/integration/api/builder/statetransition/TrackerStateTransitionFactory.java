package com.intland.codebeamer.integration.api.builder.statetransition;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.swagger.client.internal.ApiException;
import com.intland.swagger.client.internal.api.TrackerApi;
import com.intland.swagger.client.internal.api.TrackerConfigApi;
import com.intland.swagger.client.model.SaveStateTransitionsRequest;
import com.intland.swagger.client.model.TrackerStateTransition;

public final class TrackerStateTransitionFactory {

	private List<TrackerStateTransition> transitions = new LinkedList<>();

	private final TrackerConfigApi trackerConfigApi;
	private final TrackerApi trackerApi;

	private final Tracker tracker;

	public TrackerStateTransitionFactory(Tracker tracker, TrackerConfigApi trackerConfigApi, TrackerApi trackerApi) {
		this.tracker = tracker;
		this.trackerConfigApi = trackerConfigApi;
		this.trackerApi = trackerApi;
		this.transitions = new ArrayList<>(findExistingTransitions());
	}

	public TrackerStateTransitionFactory addStateTransition(Function<TrackerStateTransitionBuilder, TrackerStateTransitionBuilder> transitionBuilder) {
		TrackerStateTransition transition = new TrackerStateTransition();
		transitionBuilder.apply(new TrackerStateTransitionBuilder(transition, trackerApi, tracker.id())).build();
		transitions.add(transition);
		return this;
	}

	public TrackerStateTransitionFactory addChangeHandler(Function<ChangeHandlerBuilder, ChangeHandlerBuilder> changeHandlerBuilder) {
		TrackerStateTransition transition = new TrackerStateTransition();
		changeHandlerBuilder.apply(new ChangeHandlerBuilder(transition, trackerApi, tracker.id())).build();
		transition.eventId(3);
		transition.name("FieldUpdate");
		transitions.add(transition);
		return this;
	}

	public TrackerStateTransitionFactory updateStateTransition(String name, Function<TrackerStateTransitionBuilder, TrackerStateTransitionBuilder> transitionBuilder) {
		TrackerStateTransition transition = transitions.stream()
				.filter(it -> it.getName().equals(name))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("Field to be updated should exist on tracker: %s"
						.formatted(name)));

		transitionBuilder.apply(new TrackerStateTransitionBuilder(transition, trackerApi, tracker.id())).build();
		return this;
	}

	public TrackerStateTransitionFactory deleteTransition(String name) {
		transitions.removeIf(it -> it.getName().equals(name));
		return this;
	}

	private List<TrackerStateTransition> findExistingTransitions() {
		try {
			return trackerConfigApi.getTrackerStateTransitions(tracker.id().id(), null).getTransitionsJson();
		} catch (ApiException e) {
			throw new RuntimeException(e);
		}
	}

	public Tracker buildAndAdd() {
		try {
			trackerConfigApi.saveTrackerStateTransitions(tracker.id().id(), new SaveStateTransitionsRequest().transitions(transitions));
		} catch (ApiException e) {
			throw new RuntimeException(e);
		}
		return tracker;
	}
}
