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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.builder.choice.ChoiceOption;
import com.intland.codebeamer.integration.api.builder.choice.MemberType;
import com.intland.codebeamer.integration.api.builder.permission.AccessLevel;
import com.intland.codebeamer.integration.api.builder.permission.SubjectType;
import com.intland.codebeamer.integration.api.builder.permission.TrackerFieldPermission;
import com.intland.codebeamer.integration.api.builder.reference.ReferenceFilter;
import com.intland.codebeamer.integration.api.builder.reference.StatusMeaning;
import com.intland.codebeamer.integration.api.builder.reference.TrackerReferenceFilterBuilder;
import com.intland.codebeamer.integration.api.builder.reference.TrackerTypeReferenceBuilder;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.project.ProjectId;
import com.intland.codebeamer.integration.api.service.role.RoleApiService;
import com.intland.codebeamer.integration.api.service.sharedfield.SharedFieldApiService;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.tracker.TrackerFieldApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.common.tracker.TrackerLayoutLabel;
import com.intland.codebeamer.integration.test.testdata.DataManagerService;
import com.intland.swagger.client.internal.ApiException;
import com.intland.swagger.client.internal.api.TrackerApi;
import com.intland.swagger.client.model.ChoiceMembers;
import com.intland.swagger.client.model.ChoiceOptionReference;
import com.intland.swagger.client.model.ChoiceOptions;
import com.intland.swagger.client.model.ChoiceOptionsChoiceOption;
import com.intland.swagger.client.model.ChoiceWorkConfigItems;
import com.intland.swagger.client.model.ReferenceFilterBasedChoiceReferenceFilter;
import com.intland.swagger.client.model.TrackerField;

public final class TrackerFieldFactory {

	private static final int TABLE_FIELD_ID_START_IDX = 1_000_000;

	private static final int CUSTOM_FIELD_ID_START_IDX = 10_000;

	private static final int CUSTOM_CHOICE_FIELD_ID_START_IDX = 1_000;

	private static final int TABLE_COLUMN_POSITION_INCREMENTER = 10;

	private static final int CUSTOM_FIELD_ID_UPPER_LIMIT = 20_000;

	private Map<String, TrackerField> existingFields;

	private final List<TrackerField> newFields = new LinkedList<>();

	private final List<TrackerField> changedFields = new LinkedList<>();

	private final Map<String, ChoiceOptionReference> existingStatuses;

	private final AtomicInteger tableFieldId = new AtomicInteger(1);

	private final AtomicInteger customChoiceFieldId = new AtomicInteger(CUSTOM_CHOICE_FIELD_ID_START_IDX);

	private final AtomicInteger customFieldId = new AtomicInteger(CUSTOM_FIELD_ID_START_IDX);

	private final TrackerFieldApiService trackerFieldApiService;

	private final TrackerApiService trackerApiService;

	private final TrackerApi trackerApi;

	private final TrackerItemApiService trackerItemApiService;

	private final UserApiService userApiService;

	private final RoleApiService roleApiService;

	private final ProjectApiService projectApiService;

	private final DataManagerService dataManagerService;

	private final SharedFieldApiService sharedFieldApiService;

	private final Tracker tracker;

	private final ProjectId projectId;

	private final ApiUser apiUser;

	public TrackerFieldFactory(Tracker tracker, ProjectId projectId, DataManagerService dataManagerService,
			TrackerApi trackerApi, ApiUser apiUser) {
		this.tracker = tracker;
		this.projectId = projectId;
		this.trackerApi = trackerApi;
		this.trackerFieldApiService = dataManagerService.getTrackerFieldApiService(apiUser);
		this.trackerApiService = dataManagerService.getTrackerApiService(apiUser);
		this.trackerItemApiService = dataManagerService.getTrackerItemApiService(apiUser);
		this.userApiService = dataManagerService.getUserApiServiceWithConfigUser();
		this.roleApiService = dataManagerService.getRoleApiService(apiUser);
		this.projectApiService = dataManagerService.getProjectApiService(apiUser);
		this.sharedFieldApiService = dataManagerService.getSharedFieldApiService();
		this.dataManagerService = dataManagerService;
		this.apiUser = apiUser;
		this.existingStatuses = getChoiceOptionsFor(TrackerLayoutLabel.STATUS.getFieldId());
		setupExistingFieldParameters();
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

	public TrackerFieldFactory createWikiLinkUrlField(String fieldName) {
		return createWikiLinkUrlField(fieldName, Function.identity());
	}

	public TrackerFieldFactory createWikiLinkUrlField(String fieldName,
			Function<TrackerFieldBuilder, TrackerFieldBuilder> builder) {
		return createField(fieldName, TrackerFieldType.URL, builder);
	}

	public TrackerFieldFactory createMemberField(String fieldName, List<MemberType> memberTypes) {
		return createMemberField(fieldName, memberTypes, Function.identity());
	}

	public TrackerFieldFactory createMemberField(String fieldName, List<MemberType> memberTypes,
			Function<TrackerFieldBuilder, TrackerFieldBuilder> builder) {
		TrackerField field = newFieldObject(fieldName, TrackerFieldType.PRINCIPAL, builder);

		constructMemberChoices(memberTypes, field);

		newFields.add(field);

		return this;
	}

	public TrackerFieldFactory createChoiceField(String fieldName) {
		return createChoiceField(fieldName, List.of());
	}

	public TrackerFieldFactory createChoiceField(String fieldName, List<ChoiceOption> options) {
		return createChoiceField(fieldName, options, Function.identity());
	}

	public TrackerFieldFactory createChoiceField(String fieldName, List<ChoiceOption> options,
			Function<TrackerFieldBuilder, TrackerFieldBuilder> builder) {
		TrackerField field = newFieldObject(fieldName, TrackerFieldType.CHOICE, builder);

		constructChoiceOptions(options, field);

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
		TrackerField field = newFieldObject(fieldName, TrackerFieldType.REFERENCE, builder);

		constructReferenceFiltersOfTrackers(referenceFilter, field);

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
		TrackerField field = newFieldObject(fieldName, TrackerFieldType.REFERENCE, fieldProperties);

		constructReferenceFiltersOfType(referenceFilter, field);

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
	public TrackerFieldFactory createTableField(String fieldName,
			Function<TrackerFieldBuilder, TrackerFieldBuilder> tableFieldProperty,
			Function<TrackerFieldFactory, TrackerFieldFactory> tableColumnsFactory) {
		TrackerField tableField = newFieldObject(fieldName, TrackerFieldType.TABLE, tableFieldProperty);

		newFields.add(tableField);

		List<TrackerField> newColumns = tableColumnsFactory.apply(
						new TrackerFieldFactory(tracker, projectId, dataManagerService, trackerApi, apiUser))
				.getNewFields();

		int position = 0;
		int tableColumnFieldId = 1;
		for (TrackerField column : newColumns) {
			int columnId = Objects.requireNonNull(tableField.getReferenceId()).intValue() + tableColumnFieldId++;
			column.setReferenceId(columnId);

			position += TABLE_COLUMN_POSITION_INCREMENTER;
			column.setPosition(position);

			newFields.add(column);
		}

		return this;
	}

	/**
	 * Updates the properties of an existing field.
	 *
	 * @param fieldName name of the field to be modified
	 * @param builder for updating field config parameters
	 */
	public TrackerFieldFactory updateField(String fieldName, Function<TrackerFieldBuilder, TrackerFieldBuilder> builder) {
		TrackerField trackerField = updateFieldObject(fieldName, builder);

		changedFields.add(trackerField);

		return this;
	}

	/**
	 * Sets the member types of an existing member field.
	 * Note: it will overwrite the setting of the existing field.
	 *
	 *
	 * @param fieldName name of the field to be modified
	 * @param memberTypes type of member that could selected in this field
	 */
	public TrackerFieldFactory updateMemberField(String fieldName, List<MemberType> memberTypes) {
		return updateMemberField(fieldName, memberTypes, Function.identity());
	}

	/**
	 * Updates the properties and the member types of an existing member field.
	 *
	 * @param fieldName name of the field to be modified
	 * @param memberTypes type of member that could selected in this field
	 * @param builder for updating field config parameters
	 */
	public TrackerFieldFactory updateMemberField(String fieldName, List<MemberType> memberTypes,
			Function<TrackerFieldBuilder, TrackerFieldBuilder> builder) {
		TrackerField field = updateFieldObject(fieldName, builder);

		constructMemberChoices(memberTypes, field);

		changedFields.add(field);

		return this;
	}

	public TrackerFieldFactory updateChoiceField(String fieldName, List<ChoiceOption> options) {
		return updateChoiceField(fieldName, options, Function.identity());
	}

	public TrackerFieldFactory updateChoiceField(String fieldName, List<ChoiceOption> options,
			Function<TrackerFieldBuilder, TrackerFieldBuilder> builder) {
		TrackerField field = updateFieldObject(fieldName, builder);

		constructChoiceOptions(options, field);

		changedFields.add(field);

		return this;
	}

	public TrackerFieldFactory updateReferenceFieldOfTrackers(String fieldName,
			Function<TrackerReferenceFilterBuilder, TrackerReferenceFilterBuilder> referenceFilter) {
		return updateReferenceFieldOfTrackers(fieldName, referenceFilter, Function.identity());
	}

	public TrackerFieldFactory updateReferenceFieldOfTrackers(String fieldName,
			Function<TrackerReferenceFilterBuilder, TrackerReferenceFilterBuilder> referenceFilter,
			Function<TrackerFieldBuilder, TrackerFieldBuilder> builder) {
		TrackerField field = updateFieldObject(fieldName, builder);

		constructReferenceFiltersOfTrackers(referenceFilter, field);

		changedFields.add(field);

		return this;
	}

	public TrackerFieldFactory updateReferenceFieldOfType(String fieldName,
			Function<TrackerTypeReferenceBuilder, TrackerTypeReferenceBuilder> referenceFilter) {
		return updateReferenceFieldOfType(fieldName, referenceFilter, Function.identity());
	}

	public TrackerFieldFactory updateReferenceFieldOfType(String fieldName,
			Function<TrackerTypeReferenceBuilder, TrackerTypeReferenceBuilder> referenceFilter,
			Function<TrackerFieldBuilder, TrackerFieldBuilder> fieldProperties) {
		TrackerField trackerField = updateFieldObject(fieldName, fieldProperties);

		constructReferenceFiltersOfType(referenceFilter, trackerField);

		changedFields.add(trackerField);

		return this;
	}

	/**
	 * Updates a table field.
	 *
	 * @param fieldName name of the table field
	 * @param tableColumnsFactory a factory function for adding or modifying columns (fields) of the table
	 */
	public TrackerFieldFactory updateTableField(String fieldName,
			Function<TrackerFieldFactory, TrackerFieldFactory> tableColumnsFactory) {
		return updateTableField(fieldName, Function.identity(), tableColumnsFactory);
	}

	/**
	 * Updates a table field.
	 * The table field's parameters might also be altered.
	 *
	 * @param fieldName name of the table field
	 * @param tableFieldProperty a builder function for altering the properties of the table field
	 * @param tableColumnsFactory a factory function for adding or modifying columns (fields) of the table
	 */
	public TrackerFieldFactory updateTableField(String fieldName,
			Function<TrackerFieldBuilder, TrackerFieldBuilder> tableFieldProperty,
			Function<TrackerFieldFactory, TrackerFieldFactory> tableColumnsFactory) {
		TrackerField tableField = updateFieldObject(fieldName, tableFieldProperty);

		changedFields.add(tableField);

		TrackerFieldFactory columns = tableColumnsFactory.apply(
				new TrackerFieldFactory(tracker, projectId, dataManagerService, trackerApi, apiUser));

		int columnId = getMaxColumnReferenceIdInTable(fieldName);
		int position = getMaxColumnPositionInTable(fieldName);
		for (TrackerField column : columns.getNewFields()) {
			column.setReferenceId(++columnId);

			position += TABLE_COLUMN_POSITION_INCREMENTER;
			column.setPosition(position);

			newFields.add(column);
		}

		changedFields.addAll(columns.getChangedFields());

		return this;
	}

	private int getMaxColumnReferenceIdInTable(String tableName) {
		return getMaxIdInTable(tableName, TrackerField::getReferenceId);
	}

	private int getMaxColumnPositionInTable(String tableName) {
		return getMaxIdInTable(tableName, TrackerField::getPosition);
	}

	private int getMaxIdInTable(String tableName, Function<TrackerField, Integer> getField) {
		Integer tableReferenceId = Objects.requireNonNull(existingFields.get(tableName).getReferenceId());
		int nextTableReferenceId = Objects.requireNonNull(tableReferenceId).intValue() + TABLE_FIELD_ID_START_IDX;

		return existingFields.values()
				.stream()
				.filter(f -> Objects.nonNull(f.getReferenceId()))
				.filter(f -> f.getReferenceId().intValue() > tableReferenceId.intValue())
				.filter(f -> f.getReferenceId().intValue() < nextTableReferenceId)
				.map(getField)
				.filter(Objects::nonNull)
				.max(Integer::compare)
				.orElseGet(() -> tableReferenceId.intValue() + 100).intValue();
	}

	public List<TrackerField> getNewFields() {
		return newFields;
	}

	public List<TrackerField> getChangedFields() {
		return changedFields;
	}

	public Tracker buildAndAdd() {
		trackerApiService.addFieldsToTracker(tracker.id(), newFields, changedFields);
		return tracker;
	}

	private TrackerFieldFactory createField(String fieldName, TrackerFieldType fieldType,
			Function<TrackerFieldBuilder, TrackerFieldBuilder> builder) {
		TrackerField trackerField = newFieldObject(fieldName, fieldType, builder);

		newFields.add(trackerField);

		return this;
	}

	private TrackerField newFieldObject(String fieldName, TrackerFieldType fieldType,
			Function<TrackerFieldBuilder, TrackerFieldBuilder> builder) {
		TrackerFieldBuilder defaultBuilder = new TrackerFieldBuilder(tracker, existingFields, roleApiService, userApiService,
				projectApiService, trackerItemApiService, sharedFieldApiService, existingStatuses)
				.withPermissionSingle(getDefaultSinglePermissions());

		return builder.apply(defaultBuilder)
				.label(fieldName)
				.fieldType(fieldType)
				.referenceId(calculateReferenceId(fieldType))
				.build();
	}

	private TrackerField updateFieldObject(String fieldName, Function<TrackerFieldBuilder, TrackerFieldBuilder> builder) {
		TrackerField trackerField = Optional.ofNullable(existingFields.get(fieldName))
				.orElseThrow(() -> new IllegalStateException("Field to be updated should exist on tracker: %s"
						.formatted(fieldName)));

		TrackerFieldBuilder defaultBuilder = new TrackerFieldBuilder(trackerField, tracker, existingFields, roleApiService,
				userApiService, projectApiService, trackerItemApiService, sharedFieldApiService, existingStatuses);

		return builder.apply(defaultBuilder)
				.build();
	}

	/**
	 * <p>Creates the single permission for a field which is the default when someone adds a new field on the UI.</p>
	 * Every role in the project is granted access to the field with WRITE permission.
	 */
	private List<TrackerFieldPermission> getDefaultSinglePermissions() {
		return projectApiService.getRoles(projectId).stream()
				.map(role -> new TrackerFieldPermission(role.getName(), SubjectType.ROLE, AccessLevel.READ_WRITE, null))
				.toList();
	}

	private int calculateReferenceId(TrackerFieldType fieldType) {
		Integer typeId = Integer.valueOf(fieldType.getTypeId());

		if (isCustomChoiceField(typeId)) {
			return customChoiceFieldId.getAndIncrement();
		}

		if (isTableField(typeId)) {
			return tableFieldId.getAndIncrement() * TABLE_FIELD_ID_START_IDX;
		}

		return customFieldId.getAndIncrement();
	}

	private boolean isCustomChoiceField(Integer typeId) {
		return List.of(TrackerFieldType.REFERENCE.getTypeId(), TrackerFieldType.CHOICE.getTypeId(),
				TrackerFieldType.PRINCIPAL.getTypeId()).contains(typeId);
	}

	private boolean isTableField(Integer typeId) {
		return typeId.equals(TrackerFieldType.TABLE.getTypeId());
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

	private void constructReferenceFiltersOfTrackers(
			Function<TrackerReferenceFilterBuilder, TrackerReferenceFilterBuilder> referenceFilter,
			TrackerField field) {
		List<ReferenceFilter> filters = referenceFilter.apply(new TrackerReferenceFilterBuilder()).build();

		List<ReferenceFilterBasedChoiceReferenceFilter> apiReferenceFilters = filters
				.stream()
				.map(filter -> new ReferenceFilterBasedChoiceReferenceFilter()
						.domainType(ReferenceFilterBasedChoiceReferenceFilter.DomainTypeEnum.TRACKER)
						.domainId(projectApiService.findTrackerByName(filter.project(), filter.trackerName()).id().id())
						.filterStatusId(Optional.ofNullable(filter.status()).map(StatusMeaning::getStatusCode).orElse(null))
						.id(tracker.id().id())
				)
				.toList();

		field.choiceOptionSetting(
				new ChoiceWorkConfigItems().referenceFilters(apiReferenceFilters).type("CHOICE_WORK_CONFIG_ITEMS"));
	}

	private void constructReferenceFiltersOfType(
			Function<TrackerTypeReferenceBuilder, TrackerTypeReferenceBuilder> referenceFilter, TrackerField field) {
		List<ReferenceFilter> filters = referenceFilter.apply(new TrackerTypeReferenceBuilder()).build();

		List<ReferenceFilterBasedChoiceReferenceFilter> apiReferenceFilters = filters
				.stream()
				.map(filter -> new ReferenceFilterBasedChoiceReferenceFilter()
						.id(tracker.id().id())
						.domainType(ReferenceFilterBasedChoiceReferenceFilter.DomainTypeEnum.PROJECT)
						.domainId(filter.project().id().id())
						.filterStatusId(Optional.ofNullable(filter.status()).map(StatusMeaning::getStatusCode).orElse(null))
						.targetIds(filter.trackerTypes().stream().map(TrackerType::getTechnicalId).toList())
				)
				.toList();

		field.choiceOptionSetting(
				new ChoiceWorkConfigItems().referenceFilters(apiReferenceFilters).type("CHOICE_WORK_CONFIG_ITEMS"));
	}

	private void constructChoiceOptions(List<ChoiceOption> options, TrackerField field) {
		List<ChoiceOptionsChoiceOption> convertedChoiceOptions = options
				.stream()
				.map(this::convertChoiceOptions)
				.toList();

		field.choiceOptionSetting(new ChoiceOptions().choiceOptions(convertedChoiceOptions).type("CHOICE_OPTIONS"));
	}

	private void constructMemberChoices(List<MemberType> memberTypes, TrackerField field) {
		Set<ChoiceMembers.MembersTypeDataSourceTypesEnum> memberTypesForApi = memberTypes.stream()
				.map(type -> ChoiceMembers.MembersTypeDataSourceTypesEnum.fromValue(type.name()))
				.collect(Collectors.toSet());

		field.choiceOptionSetting(new ChoiceMembers().membersTypeDataSourceTypes(memberTypesForApi).type("MEMBERS"));
	}

	private void setupExistingFieldParameters() {
		try {
			this.existingFields = trackerApi.getTrackerConfiguration(tracker.id().id())
					.getFields()
					.stream()
					.collect(Collectors.toMap(TrackerField::getLabel, Function.identity()));

			int numberOfTableFields = Long.valueOf(existingFields.values()
					.stream()
					.filter(field -> isTableField(field.getTypeId()))
					.count()).intValue();

			int nextChoiceFieldReferenceId = getMaxFieldReferenceId(CUSTOM_CHOICE_FIELD_ID_START_IDX, CUSTOM_FIELD_ID_START_IDX);
			int nextCustomFieldReferenceId = getMaxFieldReferenceId(CUSTOM_FIELD_ID_START_IDX, CUSTOM_FIELD_ID_UPPER_LIMIT);

			tableFieldId.set(numberOfTableFields + 1);
			customChoiceFieldId.set(nextChoiceFieldReferenceId);
			customFieldId.set(nextCustomFieldReferenceId);

		} catch (ApiException e) {
			throw new RuntimeException(e);
		}
	}

	private int getMaxFieldReferenceId(int startReferenceId, int upperLimit) {
		return existingFields.values()
				.stream()
				.map(TrackerField::getReferenceId)
				.filter(Objects::nonNull)
				.filter(referenceId -> referenceId.intValue() >= startReferenceId)
				.filter(referenceId -> referenceId.intValue() < upperLimit)
				.max(Integer::compareTo)
				.map(maxValue -> maxValue.intValue() + 1)
				.orElse(startReferenceId).intValue();
	}
}
