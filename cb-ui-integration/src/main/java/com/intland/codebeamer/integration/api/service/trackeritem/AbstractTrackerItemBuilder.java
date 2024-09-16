package com.intland.codebeamer.integration.api.service.trackeritem;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.cache.LoadingCache;
import com.intland.codebeamer.integration.api.builder.table.TrackerItemTableBuilder;
import com.intland.codebeamer.integration.api.builder.wiki.WikiMarkupBuilder;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.role.Role;
import com.intland.codebeamer.integration.api.service.role.RoleApiService;
import com.intland.codebeamer.integration.api.service.role.RoleId;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerFieldApiService;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.api.service.user.UserId;
import com.intland.codebeamer.integration.classic.component.field.HtmlColor;
import com.intland.codebeamer.integration.util.CacheUtil;
import com.intland.swagger.client.internal.api.TrackerItemApi;
import com.intland.swagger.client.model.AbstractField;
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
import com.intland.swagger.client.model.TableField;
import com.intland.swagger.client.model.TextFieldValue;
import com.intland.swagger.client.model.TrackerItem;
import com.intland.swagger.client.model.TrackerItem.DescriptionFormatEnum;
import com.intland.swagger.client.model.TrackerItemReference;
import com.intland.swagger.client.model.UrlFieldValue;
import com.intland.swagger.client.model.UserReference;
import com.intland.swagger.client.model.WikiTextFieldValue;

public abstract class AbstractTrackerItemBuilder<T extends AbstractTrackerItemBuilder> {
	
	private static final int PRIORITY_FIELD_ID = 2;

	private static final int SEVERITY_FIELD_ID = 14;
	
	private static final int RESOLUTION_FIELD_ID = 15;
	
	private static final int STATUS_FIELD_ID = 7;

	private final static int CATEGORY_FIELD_ID = 13;
	
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

	public AbstractTrackerItemBuilder(TrackerItem trackerItem, TrackerFieldApiService trackerFieldApiService,
			TrackerItemApi trackerItemApi, ProjectApiService projectApiService, UserApiService userApiService,
			RoleApiService roleApiService, TrackerItemApiService trackerItemApiService) {
		this(null, trackerItem, trackerFieldApiService, trackerItemApi, projectApiService, userApiService, roleApiService, trackerItemApiService);
	}
	
	public T name(String name) {
		this.trackerItem.setName(name);
		return (T) this;
	}
	
	public T description(String description) {
		this.trackerItem.setDescription(description);
		this.trackerItem.setDescriptionFormat(DescriptionFormatEnum.WIKI);
		return (T) this;
	}

	public T description(Function<WikiMarkupBuilder, WikiMarkupBuilder> markupBuilder) {
		this.trackerItem.setDescription(markupBuilder.apply(new WikiMarkupBuilder()).build());
		this.trackerItem.setDescriptionFormat(DescriptionFormatEnum.WIKI);
		return (T) this;
	}

	public T priority(String priority) {
		this.trackerItem.setPriority(createChoiceOptionReference(PRIORITY_FIELD_ID, priority));
		return (T) this;
	}
	
	public T resolution(String resolution) {
		this.trackerItem.setResolutions(List.of(createChoiceOptionReference(RESOLUTION_FIELD_ID, resolution)));
		return (T) this;
	}
	
	public T severity(String severity) {
		this.trackerItem.setSeverities(List.of(createChoiceOptionReference(SEVERITY_FIELD_ID, severity)));
		return (T) this;
	}
	
	public T status(String status) {
		this.trackerItem.setStatus(createChoiceOptionReference(STATUS_FIELD_ID, status));
		return (T) this;
	}

	public T table(String tableName, Function<TrackerItemTableBuilder, TrackerItemTableBuilder> builder) {
		AbstractField trackerField = trackerFieldApiService.getTrackerField(trackerId, getFieldReference(tableName).getId());
		if (!(trackerField instanceof TableField tableField)) {
			throw new IllegalArgumentException("%s is not a table field".formatted(tableName));
		}

		AbstractFieldValue tableFieldValue = builder.apply(new TrackerItemTableBuilder(tableField)).build()
				.fieldId(getFieldReference(tableName).getId());
		this.trackerItem.getCustomFields().add(tableFieldValue);
		return (T) this;
	}

	public T type(String type) {
		this.trackerItem.categories(List.of(createChoiceOptionReference(CATEGORY_FIELD_ID, type)));
		return (T) this;
	}

	public T folderType() {
		return this.type("Folder");
	}

	public T informationType() {
		return this.type("Information");
	}

	public T setTextFor(String fieldName, String text) {
		this.trackerItem.getCustomFields().add(new TextFieldValue()
				.value(text)
				.fieldId(getFieldReference(fieldName).getId()));
		return (T) this;
	}

	public T setIntegerFor(String fieldName, int i) {
		return setIntegerFor(fieldName, Integer.valueOf(i));
	}

	public T setIntegerFor(String fieldName, Integer i) {
		this.trackerItem.getCustomFields().add(new IntegerFieldValue()
				.value(i)
				.fieldId(getFieldReference(fieldName).getId()));
		return (T) this;
	}

	public T setDecimalFor(String fieldName, double d) {
		return setDecimalFor(fieldName, Double.valueOf(d));
	}

	public T setDecimalFor(String fieldName, Double d) {
		this.trackerItem.getCustomFields().add(new DecimalFieldValue()
				.value(d)
				.fieldId(getFieldReference(fieldName).getId()));
		return (T) this;
	}
		
	public T setDateFor(String fieldName, int year, int month, int day, int hour, int minute) {
		return setDateFor(fieldName, createDateTime(year, month, day, hour, minute));
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
	
	public T setLanguageFor(String fieldName, Language... language) {
		AbstractFieldValue languageFieldValue = new LanguageFieldValue()
				.values(Arrays.stream(language)
						.map(Language::toString)
						.collect(Collectors.toCollection(LinkedHashSet::new)))
				.fieldId(getFieldReference(fieldName).getId());
		
		this.trackerItem.getCustomFields().add(languageFieldValue);
		return (T) this;
	}
	
	public T setCountryFor(String fieldName, Country... country) {
		AbstractFieldValue countryFieldValue = new CountryFieldValue()
				.values(Arrays.stream(country)
						.map(Country::toString)
						.collect(Collectors.toCollection(LinkedHashSet::new)))
				.fieldId(getFieldReference(fieldName).getId());
		
		this.trackerItem.getCustomFields().add(countryFieldValue);
		return (T) this;
	}
	
	public T setWikiTextFor(String fieldName, String wikiText) {
		this.trackerItem.getCustomFields().add(new WikiTextFieldValue()
				.value(wikiText)
				.fieldId(getFieldReference(fieldName).getId()));
		return (T) this;
	}
	
	public T setColorFor(String fieldName, HtmlColor color) {
		return setColorFor(fieldName, color.getHexCode());
	}
	
	public T setColorFor(String fieldName, String color) {
		this.trackerItem.getCustomFields().add(new ColorFieldValue()
				.value(color)
				.fieldId(getFieldReference(fieldName).getId()));
		return (T) this;
	}
	
	public T setUrlFor(String fieldName, String url) {
		this.trackerItem.getCustomFields().add(new UrlFieldValue()
				.value("[%s]".formatted(url))
				.fieldId(getFieldReference(fieldName).getId()));
		return (T) this;
	}
	
	public T setUrlFor(String fieldName, String alias, String url) {
		this.trackerItem.getCustomFields().add(new UrlFieldValue()
				.value("[%s|%s]".formatted(alias, url))
				.fieldId(getFieldReference(fieldName).getId()));
		return (T) this;
	}

	public T setWikiLinkFor(String fieldName, TrackerItemId trackerItemId) {
		this.trackerItem.getCustomFields().add(new UrlFieldValue()
				.value("[ISSUE:%d]".formatted(trackerItemId.id()))
				.fieldId(getFieldReference(fieldName).getId()));
		return (T) this;
	}
	
	public T setDurationFor(String fieldName, Duration duration) {
		this.trackerItem.getCustomFields().add(new DurationFieldValue()
				.value(duration.toMillis())
				.fieldId(getFieldReference(fieldName).getId()));
		return (T) this;
	}

	public T setChoiceOptionFor(String fieldName, String option) {
		int fieldId = getFieldReference(fieldName).getId().intValue();
		this.trackerItem.getCustomFields().add(
				new ChoiceFieldValue()
						.values(List.of(createChoiceOptionReference(fieldId, option)))
						.fieldId(fieldId));
		return (T) this;
	}
	
	public T setTrackerItemFor(String fieldName, Project project, String trackerName, String... trackerItemNames) {
		return setTrackerItemFor(fieldName, projectApiService.findTrackerByName(project, trackerName).id(), trackerItemNames);
	}
	
	public T setTrackerItemFor(String fieldName, TrackerId trackerId, String... trackerItemNames) {
		List<TrackerItemId> trackerItems = trackerItemApiService.findTrackerItemByNames(trackerId, trackerItemNames).stream()
				.map(t -> t.id()).toList();
		return setTrackerItemFor(fieldName, trackerItems);
	}
	
	public T setTrackerItemFor(String fieldName, com.intland.codebeamer.integration.api.service.trackeritem.TrackerItem... trackerItems) {
		return setTrackerItemFor(fieldName, Arrays.stream(trackerItems).map(t -> t.id()).toList());
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

	public T trackerId(TrackerId trackerId) {
		this.trackerId = trackerId;
		return (T) this;
	}

	public T trackerId(int trackerId) {
		this.trackerId = new TrackerId(trackerId);
		return (T) this;
	}

	public T tracker(Tracker tracker) {
		this.trackerId = tracker.id();
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
	
	private AbstractReference createChoiceOptionReference(int fieldId, String option) {
		return new ChoiceOptionReference()
				.id(getChoiceOptionsFor(fieldId).get(option.toLowerCase()).getId());
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
				.map(item -> new TrackerItemReference().id(item.id()))
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
	
	private Date createDateTime(int year, int month, int day, int hour, int minute) {
		return Date.from(LocalDateTime
				.of(year, month, day, hour, minute)
				.atZone(ZoneId.systemDefault()).toInstant());
	}
	
}