package com.intland.codebeamer.integration.api.service.trackeritem;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.cache.LoadingCache;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.role.Role;
import com.intland.codebeamer.integration.api.service.role.RoleApiService;
import com.intland.codebeamer.integration.api.service.role.RoleId;
import com.intland.codebeamer.integration.api.service.tracker.TrackerFieldApiService;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.api.service.user.UserId;
import com.intland.codebeamer.integration.util.CacheUtil;
import com.intland.swagger.client.internal.api.TrackerItemApi;
import com.intland.swagger.client.model.AbstractFieldValue;
import com.intland.swagger.client.model.AbstractReference;
import com.intland.swagger.client.model.BoolFieldValue;
import com.intland.swagger.client.model.ChoiceFieldValue;
import com.intland.swagger.client.model.ChoiceOptionReference;
import com.intland.swagger.client.model.ColorFieldValue;
import com.intland.swagger.client.model.CountryFieldValue;
import com.intland.swagger.client.model.DateFieldValue;
import com.intland.swagger.client.model.DecimalFieldValue;
import com.intland.swagger.client.model.DurationFieldValue;
import com.intland.swagger.client.model.FieldReference;
import com.intland.swagger.client.model.IntegerFieldValue;
import com.intland.swagger.client.model.LanguageFieldValue;
import com.intland.swagger.client.model.RoleReference;
import com.intland.swagger.client.model.TextFieldValue;
import com.intland.swagger.client.model.TrackerItem;
import com.intland.swagger.client.model.TrackerItem.DescriptionFormatEnum;
import com.intland.swagger.client.model.TrackerItemReference;
import com.intland.swagger.client.model.UrlFieldValue;
import com.intland.swagger.client.model.UserReference;
import com.intland.swagger.client.model.WikiTextFieldValue;

public abstract class AbstractTrackerItemBuilder<T extends AbstractTrackerItemBuilder> {
	
	private static final int PRIORITY_FIELD_ID = 2;

	private static final int STATUS_FIELD_ID = 7;
	
	private TrackerFieldApiService trackerFieldApiService;

	private ProjectApiService projectApiService;
		
	private UserApiService userApiService;

	private RoleApiService roleApiService;

	private TrackerItemApiService trackerItemApiService;

	private LoadingCache<TrackerId, Map<String, FieldReference>> trackerFieldCache;

	protected TrackerId trackerId;

	protected TrackerItemApi trackerItemApi;

	protected TrackerItem trackerItem;
	
	public AbstractTrackerItemBuilder(TrackerId trackerId, TrackerItem trackerItem, TrackerFieldApiService trackerFieldApiService,
			TrackerItemApi trackerItemApi, ProjectApiService projectApiService, UserApiService userApiService,
			RoleApiService roleApiService, TrackerItemApiService trackerItemApiService) {
		super();
		this.trackerId = trackerId;
		this.trackerFieldApiService = trackerFieldApiService;
		this.trackerItemApi = trackerItemApi;
		this.projectApiService = projectApiService;
		this.userApiService = userApiService;
		this.roleApiService = roleApiService;
		this.trackerItemApiService = trackerItemApiService;
		
		this.trackerItem = trackerItem;
		this.trackerItem.setDescriptionFormat(DescriptionFormatEnum.PLAINTEXT);
		this.trackerItem.setCustomFields(new LinkedList<AbstractFieldValue>());
				
		this.trackerFieldCache = CacheUtil.build(key -> trackerFieldApiService.getTrackerFields(key).stream()
						.collect(Collectors.toMap(FieldReference::getName, Function.identity())));	
	}
	
	public T name(String name) {
		this.trackerItem.setName(name);
		return (T) this;
	}
	
	public T description(String description) {
		this.trackerItem.setDescription(description);
		return (T) this;
	}

	public T priority(String priority) {
		this.trackerItem.setPriority(createChoiceOptionReference(PRIORITY_FIELD_ID, priority));
		return (T) this;
	}
	
	public T status(String status) {
		this.trackerItem.setStatus(createChoiceOptionReference(STATUS_FIELD_ID, status));
		return (T) this;
	}

	public T setTextFor(String fieldName, String text) {
		this.trackerItem.getCustomFields().add(new TextFieldValue()
				.value(text)
				.fieldId(getFieldReference(fieldName).getId()));
		return (T) this;
	}
	
	public T setIntegerFor(String fieldName, Integer i) {
		this.trackerItem.getCustomFields().add(new IntegerFieldValue()
				.value(i)
				.fieldId(getFieldReference(fieldName).getId()));
		return (T) this;
	}
	
	public T setDecimalFor(String fieldName, Double d) {
		this.trackerItem.getCustomFields().add(new DecimalFieldValue()
				.value(d)
				.fieldId(getFieldReference(fieldName).getId()));
		return (T) this;
	}
	
	public T setDateFor(String fieldName, Date date) {
		this.trackerItem.getCustomFields().add(new DateFieldValue()
				.value(date)
				.fieldId(getFieldReference(fieldName).getId()));
		return (T) this;
	}
	
	public T setBooleanFor(String fieldName, Boolean bool) {
		this.trackerItem.getCustomFields().add(new BoolFieldValue()
				.value(bool)
				.fieldId(getFieldReference(fieldName).getId()));
		return (T) this;
	}
	
	public T setLanguageFor(String fieldName, Language language) {
		this.trackerItem.getCustomFields().add(new LanguageFieldValue()
				.addValuesItem(language.toString())
				.fieldId(getFieldReference(fieldName).getId()));
		return (T) this;
	}
	
	public T setCountryFor(String fieldName, Country country) {
		this.trackerItem.getCustomFields().add(new CountryFieldValue()
				.addValuesItem(country.toString())
				.fieldId(getFieldReference(fieldName).getId()));
		return (T) this;
	}
	
	public T setWikiTextFor(String fieldName, String wikiText) {
		this.trackerItem.getCustomFields().add(new WikiTextFieldValue()
				.value(wikiText)
				.fieldId(getFieldReference(fieldName).getId()));
		return (T) this;
	}
	
	public T setColorFor(String fieldName, String color) {
		this.trackerItem.getCustomFields().add(new ColorFieldValue()
				.value(color)
				.fieldId(getFieldReference(fieldName).getId()));
		return (T) this;
	}
	
	public T setUrlFor(String fieldName, String url) {
		this.trackerItem.getCustomFields().add(new UrlFieldValue()
				.value(url)
				.fieldId(getFieldReference(fieldName).getId()));
		return (T) this;
	}
	
	public T setDurationFor(String fieldName, Duration duration) {
		this.trackerItem.getCustomFields().add(new DurationFieldValue()
				.value(duration.toMillis())
				.fieldId(getFieldReference(fieldName).getId()));
		return (T) this;
	}
	
	public T setTrackerItemFor(String fieldName, Project project, String trackerName, String... trackerItemNames) {
		return setTrackerItemFor(fieldName, projectApiService.findTrackerByName(project, trackerName), trackerItemNames);
	}
	
	public T setTrackerItemFor(String fieldName, TrackerId trackerId, String... trackerItemNames) {
		List<TrackerItemId> trackerItems = trackerItemApiService.findTrackerItemByNames(trackerId, trackerItemNames);
		return setTrackerItemFor(fieldName, trackerItems);
	}
	
	public T setTrackerItemFor(String fieldName, TrackerItemId... trackerItems) {
		return setTrackerItemFor(fieldName, Arrays.asList(trackerItems));
	}
	
	public T setTrackerItemFor(String fieldName, List<TrackerItemId> trackerItems) {
		this.trackerItem.getCustomFields().add(createChoiceFieldValue(fieldName, getTrackerItemReferences(trackerItems)));
		return (T) this;
	}

	public T setUserFor(String fieldName, String... usernames) {
		List<UserId> users = Arrays.stream(usernames)
				.map(name -> this.userApiService.findUserByName(name))
				.map(User::getUserId)
				.toList();
		return setUserFor(fieldName, users);
	}
	
	public T setUserFor(String fieldName, UserId... users) {
		return setUserFor(fieldName, Arrays.asList(users));
	}
	
	public T setUserFor(String fieldName, List<UserId> users) {
		this.trackerItem.getCustomFields().add(createChoiceFieldValue(fieldName, getUserReferences(users)));
		return (T) this;
	}

	public T setRoleFor(String fieldName, String... roleNames) {
		List<RoleId> roles = Arrays.stream(roleNames)
				.map(name -> this.roleApiService.findRoleByName(name))
				.map(Role::getRoleId)
				.toList();
		return setRoleFor(fieldName, roles);
	}
	
	public T setRoleFor(String fieldName, RoleId... roles) {
		return setRoleFor(fieldName, Arrays.asList(roles));
	}
	
	public T setRoleFor(String fieldName, List<RoleId> roles) {
		this.trackerItem.getCustomFields().add(createChoiceFieldValue(fieldName, getRoleReferences(roles)));
		return (T) this;
	}
	
	private Map<String, ChoiceOptionReference> getChoiceOptionsFor(int fieldId) {
		try {
			return trackerFieldApiService.getChoiceOptionsFor(trackerId, fieldId).stream()
					.collect(Collectors.toMap(r -> r.getName().toLowerCase(), Function.identity()));
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
	
	private AbstractReference createChoiceOptionReference(int fieldId, String priority) {
		return new ChoiceOptionReference()
				.id(getChoiceOptionsFor(fieldId).get(priority.toLowerCase()).getId());
	}
	
	private AbstractFieldValue createChoiceFieldValue(String fieldName, List<AbstractReference> abstractReference) {
		return new ChoiceFieldValue()
				.values(abstractReference)
				.fieldId(getFieldReference(fieldName).getId());
	}
	
	private List<AbstractReference> getRoleReferences(List<RoleId> roles) {
		if (CollectionUtils.isEmpty(roles)) {
			return Collections.emptyList();
		}
		
		return roles.stream()
				.map(role -> new RoleReference().id(role.id()))
				.toList();
	}
	
	private List<AbstractReference> getUserReferences(List<UserId> users) {
		if (CollectionUtils.isEmpty(users)) {
			return Collections.emptyList();
		}
		
		return users.stream()
				.map(user -> new UserReference().id(user.id()))
				.toList();
	}
	
	private List<AbstractReference> getTrackerItemReferences(List<TrackerItemId> trackerItems) {
		if (CollectionUtils.isEmpty(trackerItems)) {
			return Collections.emptyList();
		}
		
		return trackerItems.stream()
				.map(trackerItem -> new TrackerItemReference().id(trackerItem.id()))
				.toList();
	}

	private FieldReference getFieldReference(String fieldName) {
		Objects.requireNonNull(fieldName, "Field name cannot be null");
		try {
			return Objects.requireNonNull(trackerFieldCache.get(this.trackerId).get(fieldName),
					"Field('%s') cannot be found in tracker(%s)".formatted(fieldName, this.trackerId));
		} catch (ExecutionException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
	
}