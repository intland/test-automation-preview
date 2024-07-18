/*
 * Copyright by Intland Software
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Intland Software. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Intland.
 */

package com.intland.codebeamer.integration.api.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.cache.LoadingCache;
import com.intland.codebeamer.integration.api.builder.choice.ChoiceOption;
import com.intland.codebeamer.integration.api.builder.choice.MemberType;
import com.intland.codebeamer.integration.api.builder.reference.ReferenceFilter;
import com.intland.codebeamer.integration.api.builder.reference.StatusMeaning;
import com.intland.codebeamer.integration.api.builder.reference.TrackerReferenceFilterBuilder;
import com.intland.codebeamer.integration.api.builder.reference.TrackerTypeReferenceBuilder;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.role.RoleApiService;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.tracker.TrackerFieldApiService;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.util.CacheUtil;
import com.intland.swagger.client.model.ChoiceMembers;
import com.intland.swagger.client.model.ChoiceOptionReference;
import com.intland.swagger.client.model.ChoiceOptions;
import com.intland.swagger.client.model.ChoiceOptionsChoiceOption;
import com.intland.swagger.client.model.ChoiceWorkConfigItems;
import com.intland.swagger.client.model.FieldReference;
import com.intland.swagger.client.model.ReferenceFilterBasedChoiceReferenceFilter;
import com.intland.swagger.client.model.TrackerField;

public final class TrackerFieldFactory {

	private static final int STATUS_FIELD_ID = 7;

	private final List<TrackerField> newFields = new ArrayList<>();

	private final LoadingCache<TrackerId, Map<String, FieldReference>> existingFields;

	private final Map<String, ChoiceOptionReference> existingStatuses;

	private final AtomicInteger tableFieldId = new AtomicInteger(1);

	private final AtomicInteger tableColumnFieldId = new AtomicInteger(1);

	private final AtomicInteger customChoiceFieldId = new AtomicInteger(1100);

	private final AtomicInteger customFieldId = new AtomicInteger(11000);

	private final TrackerFieldApiService trackerFieldApiService;

	private final TrackerApiService trackerApiService;

	private final TrackerItemApiService trackerItemApiService;

	private final UserApiService userApiService;

	private final RoleApiService roleApiService;

	private final ProjectApiService projectApiService;

	private final Tracker tracker;

	public TrackerFieldFactory(Tracker tracker, TrackerFieldApiService trackerFieldApiService, TrackerApiService trackerApiService,
			TrackerItemApiService trackerItemApiService, UserApiService userApiService, RoleApiService roleApiService,
			ProjectApiService projectApiService) {
		this.tracker = tracker;
		this.trackerFieldApiService = trackerFieldApiService;
		this.trackerApiService = trackerApiService;
		this.trackerItemApiService = trackerItemApiService;
		this.userApiService = userApiService;
		this.roleApiService = roleApiService;
		this.projectApiService = projectApiService;
		this.existingFields = CacheUtil.build(key -> trackerFieldApiService.getTrackerFields(key).stream()
				.collect(Collectors.toMap(FieldReference::getName, Function.identity())));
		this.existingStatuses = getChoiceOptionsFor(STATUS_FIELD_ID);
	}

	public TrackerFieldFactory createTextField(String fieldName) {
		return createTextField(fieldName, Function.identity());
	}

	public TrackerFieldFactory createTextField(String fieldName, Function<TrackerFieldBuilder, TrackerFieldBuilder> builder) {
		return createField(fieldName, TrackerFieldType.TEXT, builder);
	}

	public TrackerFieldFactory createIntegerField(String fieldName) {
		return createIntegerField(fieldName, Function.identity());
	}

	public TrackerFieldFactory createIntegerField(String fieldName, Function<TrackerFieldBuilder, TrackerFieldBuilder> builder) {
		return createField(fieldName, TrackerFieldType.INTEGER, builder);
	}

	public TrackerFieldFactory createDecimalField(String fieldName) {
		return createDecimalField(fieldName, Function.identity());
	}

	public TrackerFieldFactory createDecimalField(String fieldName, Function<TrackerFieldBuilder, TrackerFieldBuilder> builder) {
		return createField(fieldName, TrackerFieldType.DECIMAL, builder);
	}

	public TrackerFieldFactory createDateField(String fieldName) {
		return createDateField(fieldName, Function.identity());
	}

	public TrackerFieldFactory createDateField(String fieldName, Function<TrackerFieldBuilder, TrackerFieldBuilder> builder) {
		return createField(fieldName, TrackerFieldType.DATE, builder);
	}

	public TrackerFieldFactory createBooleanField(String fieldName) {
		return createBooleanField(fieldName, Function.identity());
	}

	public TrackerFieldFactory createBooleanField(String fieldName, Function<TrackerFieldBuilder, TrackerFieldBuilder> builder) {
		return createField(fieldName, TrackerFieldType.BOOLEAN, builder);
	}

	public TrackerFieldFactory createLanguageField(String fieldName) {
		return createLanguageField(fieldName, Function.identity());
	}

	public TrackerFieldFactory createLanguageField(String fieldName, Function<TrackerFieldBuilder, TrackerFieldBuilder> builder) {
		return createField(fieldName, TrackerFieldType.LANGUAGE, builder);
	}

	public TrackerFieldFactory createCountryField(String fieldName) {
		return createCountryField(fieldName, Function.identity());
	}

	public TrackerFieldFactory createCountryField(String fieldName, Function<TrackerFieldBuilder, TrackerFieldBuilder> builder) {
		return createField(fieldName, TrackerFieldType.COUNTRY, builder);
	}

	public TrackerFieldFactory createWikiTextField(String fieldName) {
		return createWikiTextField(fieldName, Function.identity());
	}

	public TrackerFieldFactory createWikiTextField(String fieldName, Function<TrackerFieldBuilder, TrackerFieldBuilder> builder) {
		return createField(fieldName, TrackerFieldType.WIKITEXT, builder);
	}

	public TrackerFieldFactory createDurationField(String fieldName) {
		return createDurationField(fieldName, Function.identity());
	}

	public TrackerFieldFactory createDurationField(String fieldName, Function<TrackerFieldBuilder, TrackerFieldBuilder> builder) {
		return createField(fieldName, TrackerFieldType.DURATION, builder);
	}

	public TrackerFieldFactory createColorField(String fieldName) {
		return createColorField(fieldName, Function.identity());
	}

	public TrackerFieldFactory createColorField(String fieldName, Function<TrackerFieldBuilder, TrackerFieldBuilder> builder) {
		return createField(fieldName, TrackerFieldType.COLOR, builder);
	}

	public TrackerFieldFactory createUrlField(String fieldName) {
		return createUrlField(fieldName, Function.identity());
	}

	public TrackerFieldFactory createUrlField(String fieldName, Function<TrackerFieldBuilder, TrackerFieldBuilder> builder) {
		return createField(fieldName, TrackerFieldType.URL, builder);
	}

	public TrackerFieldFactory createMemberField(String fieldName, List<MemberType> memberTypes) {
		return createMemberField(fieldName, memberTypes, Function.identity());
	}

	public TrackerFieldFactory createMemberField(String fieldName, List<MemberType> memberTypes,
			Function<TrackerFieldBuilder, TrackerFieldBuilder> builder) {
		TrackerField field = createFieldObject(fieldName, TrackerFieldType.PRINCIPAL, builder);

		Set<ChoiceMembers.MembersTypeDataSourceTypesEnum> memberTypesForApi = memberTypes.stream()
				.map(type -> ChoiceMembers.MembersTypeDataSourceTypesEnum.fromValue(type.name()))
				.collect(Collectors.toSet());

		field.choiceOptionSetting(new ChoiceMembers().membersTypeDataSourceTypes(memberTypesForApi).type("MEMBERS"));

		newFields.add(field);

		return this;
	}

	public TrackerFieldFactory createChoiceField(String fieldName, List<ChoiceOption> options) {
		return createChoiceField(fieldName, options, Function.identity());
	}

	public TrackerFieldFactory createChoiceField(String fieldName, List<ChoiceOption> options,
			Function<TrackerFieldBuilder, TrackerFieldBuilder> builder) {
		TrackerField field = createFieldObject(fieldName, TrackerFieldType.CHOICE, builder);

		List<ChoiceOptionsChoiceOption> convertedChoiceOptions = options
				.stream()
				.map(this::convertChoiceOptions)
				.toList();

		field.choiceOptionSetting(new ChoiceOptions().choiceOptions(convertedChoiceOptions).type("CHOICE_OPTIONS"));

		newFields.add(field);

		return this;
	}

	public TrackerFieldFactory createReferenceFieldOfTrackers(String fieldName,
			Function<TrackerReferenceFilterBuilder, TrackerReferenceFilterBuilder> referenceFilter) {
		return createReferenceFieldOfTrackers(fieldName, referenceFilter, Function.identity());
	}

	public TrackerFieldFactory createReferenceFieldOfTrackers(String fieldName,
			Function<TrackerReferenceFilterBuilder, TrackerReferenceFilterBuilder> referenceFilter,
			Function<TrackerFieldBuilder, TrackerFieldBuilder> builder) {
		TrackerField field = createFieldObject(fieldName, TrackerFieldType.REFERENCE, builder);

		List<ReferenceFilter> filters = referenceFilter.apply(new TrackerReferenceFilterBuilder()).build();

		List<ReferenceFilterBasedChoiceReferenceFilter> apiReferenceFilters = filters
				.stream()
				.map(filter -> {
					return new ReferenceFilterBasedChoiceReferenceFilter()
							.domainType(ReferenceFilterBasedChoiceReferenceFilter.DomainTypeEnum.TRACKER)
							.domainId(projectApiService.findTrackerByName(filter.project(), filter.trackerName()).id())
							.filterStatusId(Optional.ofNullable(filter.status()).map(StatusMeaning::getStatusCode).orElse(null))
							.id(tracker.id().id());
				})
				.toList();

		field.choiceOptionSetting(
				new ChoiceWorkConfigItems().referenceFilters(apiReferenceFilters).type("CHOICE_WORK_CONFIG_ITEMS"));

		newFields.add(field);

		return this;
	}

	public TrackerFieldFactory createReferenceFieldOfType(String fieldName,
			Function<TrackerTypeReferenceBuilder, TrackerTypeReferenceBuilder> referenceFilter) {
		return createReferenceFieldOfType(fieldName, referenceFilter, Function.identity());
	}

	public TrackerFieldFactory createReferenceFieldOfType(String fieldName,
			Function<TrackerTypeReferenceBuilder, TrackerTypeReferenceBuilder> referenceFilter,
			Function<TrackerFieldBuilder, TrackerFieldBuilder> fieldProperties) {
		TrackerField field = createFieldObject(fieldName, TrackerFieldType.REFERENCE, fieldProperties);

		List<ReferenceFilter> filters = referenceFilter.apply(new TrackerTypeReferenceBuilder()).build();

		List<ReferenceFilterBasedChoiceReferenceFilter> apiReferenceFilters = filters
				.stream()
				.map(filter -> {
					return new ReferenceFilterBasedChoiceReferenceFilter()
							.id(tracker.id().id())
							.domainType(ReferenceFilterBasedChoiceReferenceFilter.DomainTypeEnum.PROJECT)
							.domainId(filter.project().id().id())
							.filterStatusId(Optional.ofNullable(filter.status()).map(StatusMeaning::getStatusCode).orElse(null))
							.targetIds(filter.trackerTypes().stream().map(TrackerType::getTechnicalId).toList());
				})
				.toList();

		field.choiceOptionSetting(
				new ChoiceWorkConfigItems().referenceFilters(apiReferenceFilters).type("CHOICE_WORK_CONFIG_ITEMS"));

		newFields.add(field);

		return this;
	}

	/**
	 * Creates a table field without any columns.
	 *
	 * @param fieldName name of the table field
	 */
	public TrackerFieldFactory createTableField(String fieldName) {
		return createTableField(fieldName, Function.identity(), Function.identity());
	}

	/**
	 * Creates a table field with columns.
	 *
	 * @param fieldName name of the table field
	 * @param tableColumnsFactory a factory function for adding columns (fields) to the table
	 */
	public TrackerFieldFactory createTableField(String fieldName,
			Function<TrackerFieldFactory, TrackerFieldFactory> tableColumnsFactory) {
		return createTableField(fieldName, Function.identity(), tableColumnsFactory);
	}

	/**
	 * Creates a table field with columns.
	 * The table field's parameters might also be altered.
	 *
	 * @param fieldName name of the table field
	 * @param tableFieldProperty a builder function for altering the properties of the table field
	 * @param tableColumnsFactory a factory function for adding columns (fields) to the table
	 */
	public TrackerFieldFactory createTableField(String fieldName, Function<TrackerFieldBuilder, TrackerFieldBuilder> tableFieldProperty,
			Function<TrackerFieldFactory, TrackerFieldFactory> tableColumnsFactory) {
		TrackerField tableField = createFieldObject(fieldName, TrackerFieldType.TABLE, tableFieldProperty);

		newFields.add(tableField);

		List<TrackerField> columns = tableColumnsFactory.apply(
				new TrackerFieldFactory(tracker, trackerFieldApiService, trackerApiService, trackerItemApiService,
						userApiService, roleApiService, projectApiService))
				.getFieldList();

		columns.stream().forEach(a ->  {
			int columnId = tableField.getReferenceId() + tableColumnFieldId.getAndIncrement();
			a.setReferenceId(columnId);
		});

		newFields.addAll(columns);

		return this;
	}

	public List<TrackerField> getFieldList() {
		return newFields;
	}

	public Tracker buildAndAdd() {
		trackerApiService.addFieldsToTracker(tracker.id(), newFields);
		return tracker;
	}

	private TrackerFieldFactory createField(String fieldName, TrackerFieldType fieldType,
			Function<TrackerFieldBuilder, TrackerFieldBuilder> builder) {
		TrackerField trackerField = createFieldObject(fieldName, fieldType, builder);

		newFields.add(trackerField);

		return this;
	}

	private TrackerField createFieldObject(String fieldName, TrackerFieldType fieldType,
			Function<TrackerFieldBuilder, TrackerFieldBuilder> builder) {
		TrackerFieldBuilder defaultBuilder = new TrackerFieldBuilder(tracker, existingFields, roleApiService, userApiService,
				projectApiService, trackerItemApiService, existingStatuses).withPermissionUnrestricted();

		return builder.apply(defaultBuilder)
				.label(fieldName)
				.fieldType(fieldType)
				.referenceId(calculateReferenceId(fieldType))
				.build();
	}

	private int calculateReferenceId(TrackerFieldType fieldType) {
		Integer typeId = Integer.valueOf(fieldType.getTypeId());

		if (List.of(TrackerFieldType.REFERENCE.getTypeId(), TrackerFieldType.CHOICE.getTypeId(),
				TrackerFieldType.PRINCIPAL.getTypeId()).contains(typeId)) {
			return customChoiceFieldId.getAndIncrement();
		} else if (typeId.equals(TrackerFieldType.TABLE.getTypeId())) {
			tableColumnFieldId.set(1);
			return tableFieldId.getAndIncrement() * 1_000_000;
		}

		return customFieldId.getAndIncrement();
	}

	private Map<String, ChoiceOptionReference> getChoiceOptionsFor(int fieldId) {
		try {
			return trackerFieldApiService.getChoiceOptionsFor(tracker.id(), fieldId).stream()
					.collect(Collectors.toMap(r -> r.getName().toLowerCase(), Function.identity()));
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	private ChoiceOptionsChoiceOption convertChoiceOptions(ChoiceOption option) {
		List<Integer> convertedDefaultInStatuses = convertStatusList(option.getDefaultInStatuses());
		List<Integer> convertedRestrictedToStatuses = convertStatusList(option.getRestrictedToStatuses());

		return option.toApiChoiceOption(convertedDefaultInStatuses, convertedRestrictedToStatuses);
	}

	private List<Integer> convertStatusList(List<String> statusList) {
		if (CollectionUtils.isEmpty(statusList)) {
			return List.of();
		}

		return statusList.stream()
				.map(s -> existingStatuses.get(s.toLowerCase()).getId())
				.toList();
	}
}
