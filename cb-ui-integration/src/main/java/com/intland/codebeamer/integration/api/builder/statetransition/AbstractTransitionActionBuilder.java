package com.intland.codebeamer.integration.api.builder.statetransition;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.swagger.client.internal.ApiException;
import com.intland.swagger.client.internal.api.TrackerApi;
import com.intland.swagger.client.model.AbstractField;
import com.intland.swagger.client.model.ActionParameter;
import com.intland.swagger.client.model.ActionReferenceInfo;
import com.intland.swagger.client.model.ChoiceOptionReference;
import com.intland.swagger.client.model.FieldValueInfo;
import com.intland.swagger.client.model.OptionChoiceField;
import com.intland.swagger.client.model.Tracker;
import com.intland.swagger.client.model.TransitionActionInfo;

public abstract class AbstractTransitionActionBuilder<C extends AbstractTransitionActionBuilder<C>> {

	protected TransitionActionInfo transitionActionInfo = new TransitionActionInfo();
	protected TrackerApi trackerApi;

	public AbstractTransitionActionBuilder(TrackerApi trackerApi) {
		this.trackerApi = trackerApi;
		transitionActionInfo.parameters(new ActionParameter());
		transitionActionInfo.setStatusForTaggedSprints("--");
	}

	public TransitionActionInfo build() {
		return transitionActionInfo;
	}

	@SuppressWarnings("unchecked")
	protected C fieldUpdates(TrackerId trackerId, Function<FieldUpdateBuilder, FieldUpdateBuilder>... fieldUpdateBuilders) {
		List<AbstractField> fields = getFields(trackerId);
		List<FieldValueInfo> fieldUpdates = Arrays.stream(fieldUpdateBuilders)
				.map(f -> f.apply(new FieldUpdateBuilder(fields)).build())
				.toList();
		transitionActionInfo.fieldUpdates(fieldUpdates);
		return (C) this;
	}

	protected C upstreamField(String fieldName, TrackerId trackerId, TrackerId upstreamTrackerId) {
		List<AbstractField> fields = getFields(trackerId);
		getReferenceConfig()
				.refType("%d|%d".formatted(findFieldByName(fields, fieldName).getId(), getTracker(upstreamTrackerId).getType().getId()))
				.tracker(upstreamTrackerId.id());
		return (C) this;
	}

	protected C downstreamField(String fieldName, TrackerId downstreamTrackerId) {
		getReferenceConfig()
				.refType("%s|%d".formatted(fieldName, getTracker(downstreamTrackerId).getType().getId()))
				.tracker(downstreamTrackerId.id());
		return (C) this;
	}

	protected C statuses(TrackerId referringTrackerId, String... statuses) {
		List<AbstractField> trackerSchema = getFields(referringTrackerId);
		List<ChoiceOptionReference> statusOptions = getStatusOptions(trackerSchema);
		getReferenceConfig().statuses(Arrays.stream(statuses).map(s -> findStatusByName(statusOptions, s).getId()).toList());
		return (C) this;
	}

	private ActionReferenceInfo getReferenceConfig() {
		ActionReferenceInfo reference = transitionActionInfo.getReference();
		if (reference == null) {
			reference = new ActionReferenceInfo();
			transitionActionInfo.setReference(reference);
		}
		return reference;
	}

	protected ChoiceOptionReference findStatusByName(List<ChoiceOptionReference> statusOptions, String statusName) {
		return statusOptions.stream()
				.filter(it -> it.getName().equals(statusName))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("Could not find status: " + statusName));
	}

	protected AbstractField findFieldByName(List<AbstractField> fields, final String fieldName) {
		return fields.stream()
				.filter(field -> field.getName().equals(fieldName))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("Could not find field: " + fieldName));
	}

	protected List<ChoiceOptionReference> getStatusOptions(List<AbstractField> fields) {
		OptionChoiceField status = (OptionChoiceField) fields.stream()
				.filter(it -> it.getName().equals("Status"))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("Could not find Status field"));
		return status.getOptions();
	}

	protected Tracker getTracker(TrackerId trackerId) {
		try {
			return trackerApi.getTracker(trackerId.id());
		} catch (ApiException e) {
			throw new RuntimeException(e);
		}
	}

	private List<AbstractField> getFields(TrackerId trackerId) {
		try {
			return trackerApi.getTrackerSchema(trackerId.id());
		} catch (ApiException e) {
			throw new IllegalStateException(e);
		}
	}

}
