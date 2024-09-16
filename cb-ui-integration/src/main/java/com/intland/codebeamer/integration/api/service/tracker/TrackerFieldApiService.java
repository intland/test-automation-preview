package com.intland.codebeamer.integration.api.service.tracker;

import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.service.AbstractApiService;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.configuration.ApplicationConfiguration;
import com.intland.swagger.client.internal.api.TrackerApi;
import com.intland.swagger.client.model.AbstractField;
import com.intland.swagger.client.model.ChoiceOptionReference;
import com.intland.swagger.client.model.FieldReference;
import com.intland.swagger.client.model.OptionChoiceField;

public class TrackerFieldApiService extends AbstractApiService {

	private final TrackerApi trackerApi;

	public TrackerFieldApiService(ApplicationConfiguration applicationConfiguration) {
		this(applicationConfiguration, applicationConfiguration.getApiUser());
	}

	public TrackerFieldApiService(ApplicationConfiguration applicationConfiguration, String username) {
		this(applicationConfiguration, new ApiUser(username, UserApiService.DEFAULT_PASSWORD));
	}

	public TrackerFieldApiService(ApplicationConfiguration applicationConfiguration, String username, String password) {
		this(applicationConfiguration, new ApiUser(username, password));
	}

	public TrackerFieldApiService(ApplicationConfiguration applicationConfiguration, ApiUser apiUser) {
		super(applicationConfiguration, apiUser);
		this.trackerApi = new TrackerApi(getUserApiClient());
	}

	// TODO We should not let anybody to access this method
	public List<ChoiceOptionReference> getChoiceOptionsFor(TrackerId tracker, int fieldId) {
		try {
			return ((OptionChoiceField) trackerApi.getTrackerField(tracker.id(), fieldId)).getOptions();
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	// TODO We should not let anybody to access this method
	public List<FieldReference> getTrackerFields(Tracker tracker) {
		return getTrackerFields(tracker.id());
	}
	
	// TODO We should not let anybody to access this method
	public List<FieldReference> getTrackerFields(TrackerId tracker) {
		try {
			return trackerApi.getTrackerFields(tracker.id());
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public AbstractField getTrackerField(TrackerId tracker, Integer fieldId) {
		try {
			return trackerApi.getTrackerField(tracker.id(), fieldId);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public int resolveChoiceOptionId(Tracker tracker, String fieldName, String optionName) {
		return resolveChoiceOptionId(tracker.id(), fieldName, optionName);
	}
	
	public int resolveChoiceOptionId(TrackerId trackerId, String fieldName, String optionName) {
		TrackerField field = findFieldReference(trackerId, fieldName);
		return resolveChoiceOptionId(trackerId, field, optionName);
	}
	
	public void deleteTracker(TrackerId trackerId) {
		try {
			trackerApi.deleteTracker(trackerId.id());
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public TrackerField findFieldReference(TrackerId trackerId, String fieldName) {
		List<TrackerField> fields = this.getTrackerFields(trackerId).stream()
				.filter(f -> fieldName.equals(f.getName()))
				.map(TrackerField::new)
				.toList();

		if (CollectionUtils.isEmpty(fields)) {
			throw new IllegalStateException("No field is found by name '%s'".formatted(fieldName));
		}

		if (CollectionUtils.size(fields) > 1) {
			throw new IllegalStateException("More than one field is found by name '%s', Field: %s".formatted(fieldName, fields));
		}
		return fields.getFirst();
	}

	private int resolveChoiceOptionId(TrackerId trackerId, TrackerField field, String value) {
		List<ChoiceOptionReference> foundChoiceOptions = this.getChoiceOptionsFor(trackerId, field.getId()).stream()
			.filter(c -> value.equals(c.getName()))
			.toList();
		
		if (CollectionUtils.isEmpty(foundChoiceOptions)) {
			throw new IllegalStateException("No choice option is found by name '%s'".formatted(value));
		}
		
		if (CollectionUtils.size(foundChoiceOptions) > 1) {
			throw new IllegalStateException("More than one choice option is found by name '%s', Field: %s".formatted(value, foundChoiceOptions));
		}

		return Objects.requireNonNull(foundChoiceOptions.getFirst().getId()).intValue();
	}

}