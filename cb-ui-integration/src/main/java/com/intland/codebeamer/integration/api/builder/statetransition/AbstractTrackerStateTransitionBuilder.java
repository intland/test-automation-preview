package com.intland.codebeamer.integration.api.builder.statetransition;

import java.util.List;
import java.util.function.Function;

import com.intland.codebeamer.integration.api.builder.trackerview.TrackerViewId;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.swagger.client.internal.ApiException;
import com.intland.swagger.client.internal.api.TrackerApi;
import com.intland.swagger.client.model.ChoiceOptions;
import com.intland.swagger.client.model.ChoiceOptionsChoiceOption;
import com.intland.swagger.client.model.IdNamed;
import com.intland.swagger.client.model.TrackerConfiguration;
import com.intland.swagger.client.model.TrackerField;
import com.intland.swagger.client.model.TrackerStateTransition;

public abstract class AbstractTrackerStateTransitionBuilder<C extends AbstractTrackerStateTransitionBuilder<C>> {

	protected final TrackerStateTransition trackerStateTransition;
	private final TrackerApi trackerApi;
	private final TrackerId trackerId;
	private TrackerConfiguration trackerConfiguration;

	public AbstractTrackerStateTransitionBuilder(TrackerStateTransition transition, TrackerApi trackerApi, TrackerId trackerId) {
		this.trackerApi = trackerApi;
		this.trackerStateTransition = transition;
		this.trackerId = trackerId;
	}

	protected C guard(TrackerViewId guardId) {
		trackerStateTransition.setGuard(new IdNamed().id(guardId.id()));
		return (C) this;
	}

	public final C updateItemPropertiesAction(Function<UpdateItemPropertiesActionBuilder, UpdateItemPropertiesActionBuilder> actionBuilder) {
		UpdateItemPropertiesActionBuilder updateItemPropertiesBuilder = new UpdateItemPropertiesActionBuilder(trackerApi, trackerId);
		trackerStateTransition.addActionsItem(actionBuilder.apply(updateItemPropertiesBuilder).build());
		return (C) this;
	}

	public final C createReferringItemsAction(TrackerId referringTrackerId, Function<CreateReferringItemsActionBuilder, CreateReferringItemsActionBuilder> actionBuilder) {
		CreateReferringItemsActionBuilder createReferringItemsBuilder = new CreateReferringItemsActionBuilder(trackerApi, trackerId, referringTrackerId);
		trackerStateTransition.addActionsItem(actionBuilder.apply(createReferringItemsBuilder).build());
		return (C) this;
	}

	public final C updateReferringItemsAction(TrackerId referringTrackerId, Function<UpdateReferringItemsActionBuilder, UpdateReferringItemsActionBuilder> actionBuilder) {
		UpdateReferringItemsActionBuilder updateReferringItemsBuilder = new UpdateReferringItemsActionBuilder(trackerApi, trackerId, referringTrackerId);
		trackerStateTransition.addActionsItem(actionBuilder.apply(updateReferringItemsBuilder).build());
		return (C) this;
	}

	public final C startReviewAction(Function<StartReviewTransitionActionBuilder, StartReviewTransitionActionBuilder> actionBuilder) {
		StartReviewTransitionActionBuilder startReviewBuilder = new StartReviewTransitionActionBuilder(trackerApi);
		trackerStateTransition.addActionsItem(actionBuilder.apply(startReviewBuilder).build());
		return (C) this;
	}

	protected IdNamed findStatusByName(String name) {
		ChoiceOptionsChoiceOption status = getStatusOptions().stream()
				.filter(option -> option.getName().equals(name))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("Could not find status: " + name));
		return new IdNamed()
				.id(status.getId())
				.name(status.getName());
	}

	private List<ChoiceOptionsChoiceOption> getStatusOptions() {
		TrackerField statusField = getTrackerConfiguration().getFields().stream()
				.filter(it -> it.getLabel().equals("Status"))
				.findFirst()
				.orElseThrow();
		ChoiceOptions choiceOptionSetting = (ChoiceOptions) statusField.getChoiceOptionSetting();
		return choiceOptionSetting.getChoiceOptions();
	}

	private TrackerConfiguration getTrackerConfiguration() {
		if (trackerConfiguration == null) {
			try {
				trackerConfiguration = trackerApi.getTrackerConfiguration(trackerId.id());
			} catch (ApiException e) {
				throw new IllegalStateException(e);
			}
		}
		return trackerConfiguration;
	}

	TrackerStateTransition build() {
		return trackerStateTransition;
	}
}
