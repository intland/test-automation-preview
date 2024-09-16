package com.intland.codebeamer.integration.api.builder.trackerview;

import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.swagger.client.internal.ApiException;
import com.intland.swagger.client.internal.api.TrackerApi;
import com.intland.swagger.client.model.AbstractField;
import com.intland.swagger.client.model.FilterConditionInfo;
import com.intland.swagger.client.model.FiltersView;
import com.intland.swagger.client.model.IdNamed;

import java.util.List;
import java.util.function.Function;

public class TrackerViewBuilder {

	private final TrackerApi trackerApi;
	private final TrackerId trackerId;
	private final FiltersView filtersView = new FiltersView();

	public TrackerViewBuilder(TrackerApi trackerApi, TrackerId trackerId) {
		this.trackerApi = trackerApi;
		this.trackerId = trackerId;
	}

	public TrackerViewBuilder name(String name) {
		filtersView.name(name);
		return this;
	}

	public TrackerViewBuilder description(String description) {
		filtersView.description(description);
		return this;
	}

	public TrackerViewBuilder isPublic(boolean isPublic) {
		filtersView._public(isPublic);
		return this;
	}

	public TrackerViewBuilder type(TrackerViewType type) {
		filtersView.type(type.getValue());
		return this;
	}

	public TrackerViewBuilder creationType(TrackerViewCreationType creationType) {
		filtersView.creationType(creationType.getValue());
		return this;
	}

	public TrackerViewBuilder condition(Function<TrackerViewConditionInfoBuilder, TrackerViewConditionInfoBuilder> conditionBuilder) {
		filtersView.addFiltersItem(conditionBuilder.apply(new TrackerViewConditionInfoBuilder(getTrackerFields())).build());
		return this;
	}

	public TrackerViewBuilder condition(SimpleTrackerViewCondition condition) {
		filtersView.addFiltersItem(new FilterConditionInfo()
				.field(new IdNamed().id(condition.getFieldId()))
		);
		return this;
	}

	public FiltersView build() {
		return filtersView;
	}

	private List<AbstractField> getTrackerFields() {
		try {
			return trackerApi.getTrackerSchema(trackerId.id());
		} catch (ApiException e) {
			throw new RuntimeException(e);
		}
	}
}
