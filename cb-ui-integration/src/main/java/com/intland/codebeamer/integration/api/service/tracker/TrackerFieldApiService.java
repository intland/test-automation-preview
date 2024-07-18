package com.intland.codebeamer.integration.api.service.tracker;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.service.AbstractApiService;
import com.intland.codebeamer.integration.configuration.ApplicationConfiguration;
import com.intland.swagger.client.internal.api.TrackerApi;
import com.intland.swagger.client.model.ChoiceOptionReference;
import com.intland.swagger.client.model.FieldReference;
import com.intland.swagger.client.model.OptionChoiceField;

public class TrackerFieldApiService extends AbstractApiService {

	private final TrackerApi trackerApi;

	public TrackerFieldApiService(ApplicationConfiguration applicationConfiguration) {
		this(applicationConfiguration, applicationConfiguration.getApiUser());
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

	public int resolveChoiceOptionId(Tracker tracker, String fieldName, String optionName) {
		FieldReference field = findFieldReference(tracker, fieldName);
		return resolveChoiceOptionId(tracker, field, optionName);
	}
	
	public void deleteTracker(TrackerId trackerId) {
		try {
			trackerApi.deleteTracker(trackerId.id());
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	private Integer resolveChoiceOptionId(Tracker tracker, FieldReference field, String value) {
		List<ChoiceOptionReference> foundChoiceOptions = this.getChoiceOptionsFor(tracker.id(), field.getId()).stream()
			.filter(c -> value.equals(c.getName()))
			.toList();
		
		if (CollectionUtils.isEmpty(foundChoiceOptions)) {
			throw new IllegalStateException("No choice option is found by name '%s'".formatted(value));
		}
		
		if (CollectionUtils.size(foundChoiceOptions) > 1) {
			throw new IllegalStateException("More than one choice option is found by name '%s', Field: %s".formatted(value, foundChoiceOptions));
		}
		
		return foundChoiceOptions.get(0).getId();
	}

	private FieldReference findFieldReference(Tracker tracker, String fieldName) {
		List<FieldReference> fields = this.getTrackerFields(tracker).stream()
				.filter(f -> fieldName.equals(f.getName()))
				.toList();
		
		if (CollectionUtils.isEmpty(fields)) {
			throw new IllegalStateException("No field is found by name '%s'".formatted(fieldName));
		}
		
		if (CollectionUtils.size(fields) > 1) {
			throw new IllegalStateException("More than one field is found by name '%s', Field: %s".formatted(fieldName, fields));
		}
		return fields.get(0);
	}

}