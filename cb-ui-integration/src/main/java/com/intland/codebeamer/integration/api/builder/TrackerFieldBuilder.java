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

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

import com.intland.codebeamer.integration.api.builder.alloweddefault.AllowedDefaultValue;
import com.intland.codebeamer.integration.api.builder.alloweddefault.AllowedValueBuilder;
import com.intland.codebeamer.integration.api.builder.alloweddefault.DefaultValueBuilder;
import com.intland.codebeamer.integration.api.builder.alloweddefault.ValueType;
import com.intland.codebeamer.integration.api.builder.dependency.FieldDependencyBuilder;
import com.intland.codebeamer.integration.api.builder.permission.FieldPermissionBuilder;
import com.intland.codebeamer.integration.api.builder.permission.SubjectType;
import com.intland.codebeamer.integration.api.builder.permission.TrackerFieldPermission;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.role.RoleApiService;
import com.intland.codebeamer.integration.api.service.sharedfield.SharedField;
import com.intland.codebeamer.integration.api.service.sharedfield.SharedFieldApiService;
import com.intland.codebeamer.integration.api.service.sharedfield.SharedFieldId;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItem;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.swagger.client.model.ChoiceOptionReference;
import com.intland.swagger.client.model.PerStatusFieldPermission;
import com.intland.swagger.client.model.SameAsFieldPermission;
import com.intland.swagger.client.model.SingleFieldPermission;
import com.intland.swagger.client.model.TrackerField;
import com.intland.swagger.client.model.TrackerFieldDateFieldSettings;
import com.intland.swagger.client.model.TrackerFieldDependency;
import com.intland.swagger.client.model.TrackerFieldPermissionAccessPermission;
import com.intland.swagger.client.model.TrackerFieldServiceDeskField;
import com.intland.swagger.client.model.UnrestrictedFieldPermission;

public class TrackerFieldBuilder {

	private TrackerField trackerField;

	private Tracker tracker;

	private Map<String, TrackerField> existingFields;

	private Map<String, ChoiceOptionReference> existingStatuses;

	private UserApiService userApiService;

	private RoleApiService roleApiService;

	private ProjectApiService projectApiService;

	private TrackerItemApiService trackerItemApiService;

	private SharedFieldApiService sharedFieldApiService;

	public TrackerFieldBuilder(Tracker tracker, Map<String, TrackerField> existingFields,
			RoleApiService roleApiService, UserApiService userApiService, ProjectApiService projectApiService,
			TrackerItemApiService trackerItemApiService, SharedFieldApiService sharedFieldApiService, Map<String, ChoiceOptionReference> existingStatuses) {
		this(new TrackerField(), tracker, existingFields, roleApiService, userApiService, projectApiService,
				trackerItemApiService, sharedFieldApiService, existingStatuses);
	}

	public TrackerFieldBuilder(TrackerField trackerField, Tracker tracker,
			Map<String, TrackerField> existingFields, RoleApiService roleApiService,
			UserApiService userApiService, ProjectApiService projectApiService, TrackerItemApiService trackerItemApiService,
			SharedFieldApiService sharedFieldApiService, Map<String, ChoiceOptionReference> existingStatuses) {
		this.trackerField = trackerField;
		this.tracker = tracker;
		this.existingFields = existingFields;
		this.existingStatuses = existingStatuses;
		this.roleApiService = roleApiService;
		this.userApiService = userApiService;
		this.projectApiService = projectApiService;
		this.trackerItemApiService = trackerItemApiService;
		this.sharedFieldApiService = sharedFieldApiService;
	}

	public TrackerFieldBuilder label(String label) {
		trackerField.label(label);
		return this;
	}

	public TrackerFieldBuilder sharedFields(String... sharedFields) {
		trackerField.setGlobalTypeIds(Arrays.stream(sharedFields)
				.map(sharedFieldApiService::getSharedFieldByName)
				.map(SharedField::id)
				.map(SharedFieldId::id)
				.collect(toList()));
		return this;
	}

	public TrackerFieldBuilder title(String title) {
		trackerField.title(title);
		return this;
	}

	public TrackerFieldBuilder computedAs(String computedAs) {
		trackerField.computedAs(computedAs);
		return this;
	}

	public TrackerFieldBuilder description(String description) {
		trackerField.description(description);
		return this;
	}

	public TrackerFieldBuilder hidden() {
		return hidden(true);
	}

	public TrackerFieldBuilder hidden(boolean hidden) {
		trackerField.hidden(Boolean.valueOf(hidden));
		return this;
	}

	public TrackerFieldBuilder mandatory() {
		return mandatory(true);
	}

	public TrackerFieldBuilder mandatory(boolean mandatory) {
		trackerField.mandatory(Boolean.valueOf(mandatory));
		return this;
	}

	public TrackerFieldBuilder mandatoryExceptInStatuses(String... statuses) {
		trackerField.mandatoryExceptInStatus(Arrays.stream(statuses).map(this::convertStatusTextToStatusId)
				.map(Integer::valueOf)
				.toList());
		return this;
	}

	public TrackerFieldBuilder clearMandatoryExceptInStatuses() {
		trackerField.mandatoryExceptInStatus(List.of());
		return this;
	}

	public TrackerFieldBuilder omitMerge() {
		return omitMerge(true);
	}

	public TrackerFieldBuilder omitMerge(boolean omitMerge) {
		trackerField.omitMerge(Boolean.valueOf(omitMerge));
		return this;
	}

	public TrackerFieldBuilder omitSuspectedWhenChange() {
		return omitSuspectedWhenChange(true);
	}

	public TrackerFieldBuilder omitSuspectedWhenChange(boolean omitSuspectedWhenChange) {
		trackerField.omitSuspectedWhenChange(Boolean.valueOf(omitSuspectedWhenChange));
		return this;
	}

	public TrackerFieldBuilder propagateDependencies() {
		return propagateDependencies(true);
	}

	public TrackerFieldBuilder propagateDependencies(boolean propagateDependencies) {
		trackerField.propagateDependencies(Boolean.valueOf(propagateDependencies));
		return this;
	}

	public TrackerFieldBuilder propagateSuspect() {
		return propagateSuspect(true);
	}

	public TrackerFieldBuilder propagateSuspect(boolean propagateSuspect) {
		trackerField.propagateSuspect(Boolean.valueOf(propagateSuspect));
		return this;
	}

	public TrackerFieldBuilder reversedSuspect() {
		return reversedSuspect(true);
	}

	public TrackerFieldBuilder reversedSuspect(boolean reversedSuspect) {
		trackerField.reversedSuspect(Boolean.valueOf(reversedSuspect));
		return this;
	}

	public TrackerFieldBuilder listable() {
		return listable(true);
	}

	public TrackerFieldBuilder listable(boolean listable) {
		trackerField.listable(Boolean.valueOf(listable));
		return this;
	}

	public TrackerFieldBuilder multipleSelection() {
		return multipleSelection(true);
	}

	public TrackerFieldBuilder multipleSelection(boolean multipleSelection) {
		trackerField.multipleSelection(Boolean.valueOf(multipleSelection));
		return this;
	}

	public TrackerFieldBuilder newLine() {
		return newLine(true);
	}

	public TrackerFieldBuilder newLine(boolean newLine) {
		trackerField.newLine(Boolean.valueOf(newLine));
		return this;
	}

	/**
	 * Sets a field which the created field depends on.
	 * <p>
	 * Limitation: the field must already exist in the tracker.
	 */
	public TrackerFieldBuilder dependsOnField(String fieldName) {
		return dependsOnField(fieldName, Function.identity());
	}

	/**
	 * Sets a field which the created field depends on.
	 * <p>
	 * Limitation: the field must already exist in the tracker.
	 * FieldDependencyBuilder helps to create static field dependencies.
	 */
	public TrackerFieldBuilder dependsOnField(String fieldName,
			Function<FieldDependencyBuilder, FieldDependencyBuilder> builder) {
		Integer dependantFieldId = getFieldReference(fieldName).getReferenceId();
		Map<String, String> dependencies = builder.apply(new FieldDependencyBuilder()).build();

		TrackerFieldDependency dependency = createTrackerFieldDependency()
				.dependentFieldId(dependantFieldId)
				.valueCombinations(dependencies);

		trackerField.dependency(dependency);
		return this;
	}

	public TrackerFieldBuilder union() {
		return union(true);
	}

	public TrackerFieldBuilder union(boolean union) {
		trackerField.union(Boolean.valueOf(union));
		return this;
	}

	public TrackerFieldBuilder width(int width) {
		trackerField.width(Integer.valueOf(width));
		return this;
	}

	public TrackerFieldBuilder height(int height) {
		trackerField.height(Integer.valueOf(height));
		return this;
	}

	public TrackerFieldBuilder span(int span) {
		trackerField.span(Integer.valueOf(span));
		return this;
	}

	public TrackerFieldBuilder position(int position) {
		trackerField.position(Integer.valueOf(position));
		return this;
	}

	public TrackerFieldBuilder minValue(String minValue) {
		trackerField.minValue(minValue);
		return this;
	}

	public TrackerFieldBuilder maxValue(String maxValue) {
		trackerField.maxValue(maxValue);
		return this;
	}

	public TrackerFieldBuilder digits(int digits) {
		trackerField.digits(Integer.valueOf(digits));
		return this;
	}

	public TrackerFieldBuilder hideIfFormula(String hideIfFormula) {
		trackerField.hideIfFormula(hideIfFormula);
		return this;
	}

	public TrackerFieldBuilder hideIfFormulaSameAsField(String fieldName) {
		trackerField.hideIfFormulaSameAsFieldId(getFieldReference(fieldName).getReferenceId());
		return this;
	}

	public TrackerFieldBuilder mandatoryIfFormula(String mandatoryIfFormula) {
		trackerField.mandatoryIfFormula(mandatoryIfFormula);
		return this;
	}

	public TrackerFieldBuilder mandatoryIfFormulaSameAsField(String fieldName) {
		trackerField.mandatoryIfFormulaSameAsFieldId(getFieldReference(fieldName).getReferenceId());
		return this;
	}

	public TrackerFieldBuilder serviceDeskLabel(String serviceDeskLabel) {
		trackerField.serviceDeskField(createTrackerFieldServiceDeskField().label(serviceDeskLabel));
		return this;
	}

	public TrackerFieldBuilder serviceDeskDescription(String serviceDeskDescription) {
		trackerField.serviceDeskField(createTrackerFieldServiceDeskField().description(serviceDeskDescription));
		return this;
	}

	public TrackerFieldBuilder displayYear() {
		return displayYear(true);
	}

	public TrackerFieldBuilder displayYear(boolean displayYear) {
		trackerField.dateFieldSettings(createTrackerFieldDateFieldSettings().displayYear(Boolean.valueOf(displayYear)));
		return this;
	}

	public TrackerFieldBuilder displayMonth() {
		return displayMonth(true);
	}

	public TrackerFieldBuilder displayMonth(boolean displayMonth) {
		trackerField.dateFieldSettings(createTrackerFieldDateFieldSettings().displayMonth(Boolean.valueOf(displayMonth)));
		return this;
	}

	public TrackerFieldBuilder displayDay() {
		return displayDay(true);
	}

	public TrackerFieldBuilder displayDay(boolean displayDay) {
		trackerField.dateFieldSettings(createTrackerFieldDateFieldSettings().displayDay(Boolean.valueOf(displayDay)));
		return this;
	}

	public TrackerFieldBuilder displayTime() {
		return displayTime(true);
	}

	public TrackerFieldBuilder displayTime(boolean displayTime) {
		trackerField.dateFieldSettings(createTrackerFieldDateFieldSettings().displayTime(Boolean.valueOf(displayTime)));
		return this;
	}

	public TrackerFieldBuilder displayDateSettings(boolean displayYear, boolean displayMonth, boolean displayDay, boolean displayTime) {
		trackerField.dateFieldSettings(createTrackerFieldDateFieldSettings()
				.displayYear(Boolean.valueOf(displayYear))
				.displayMonth(Boolean.valueOf(displayMonth))
				.displayDay(Boolean.valueOf(displayDay))
				.displayTime(Boolean.valueOf(displayTime)));
		return this;
	}

	public TrackerFieldBuilder defaultValuesInStatuses(Function<DefaultValueBuilder, DefaultValueBuilder> defaultValueBuilder) {
		Map<String, List<AllowedDefaultValue>> defaultValuesInStatuses = defaultValueBuilder
				.apply(new DefaultValueBuilder()).build();

		processAllowedDefaultValues(TrackerField::setDefaultValuesInStatuses, defaultValuesInStatuses);

		return this;
	}

	public TrackerFieldBuilder allowedValuesInStatuses(Function<AllowedValueBuilder, AllowedValueBuilder> allowedValueBuilder) {
		Map<String, List<AllowedDefaultValue>> allowedValuesInStatuses = allowedValueBuilder
				.apply(new AllowedValueBuilder()).build();

		processAllowedDefaultValues(TrackerField::setAllowedValuesInStatuses, allowedValuesInStatuses);

		return this;
	}

	public TrackerFieldBuilder withPermissionUnrestricted() {
		trackerField.setPermission(new UnrestrictedFieldPermission().type("UNRESTRICTED"));
		return this;
	}

	public TrackerFieldBuilder withPermissionSameAsField(String fieldName) {
		TrackerField field = getFieldReference(fieldName);
		trackerField.setPermission(new SameAsFieldPermission().sameAsId(field.getReferenceId()).type("SAME_AS"));
		return this;
	}

	public TrackerFieldBuilder withPermissionSingle(Function<FieldPermissionBuilder, FieldPermissionBuilder> permBuilder) {
		List<TrackerFieldPermission> permissions = permBuilder.apply(new FieldPermissionBuilder()).build();

		return withPermissionSingle(permissions);
	}

	public TrackerFieldBuilder withPermissionPerStatus(Function<FieldPermissionBuilder, FieldPermissionBuilder> permBuilder) {
		List<TrackerFieldPermission> permissions = permBuilder.apply(new FieldPermissionBuilder()).build();

		Map<String, List<TrackerFieldPermissionAccessPermission>> convertedPermissions = permissions
				.stream()
				.filter(p -> p.status() != null)
				.collect(groupingBy(perm -> convertStatusTextToStatusId(perm.status()),
						mapping(this::mapPermissionObject, toList())));

		trackerField.setPermission(
				new PerStatusFieldPermission().accessPermissionsPerStatusMap(convertedPermissions).type("PER_STATUS"));
		return this;
	}

	public TrackerFieldBuilder withAggregationRuleMinimum() {
		trackerField.setAggregationRule(TrackerField.AggregationRuleEnum.MINIMUM);
		return this;
	}

	public TrackerFieldBuilder withAggregationRuleMaximum() {
		trackerField.setAggregationRule(TrackerField.AggregationRuleEnum.MAXIMUM);
		return this;
	}

	public TrackerFieldBuilder withAggregationRuleUnion() {
		trackerField.setAggregationRule(TrackerField.AggregationRuleEnum.UNION);
		return this;
	}

	public TrackerFieldBuilder withAggregationRuleIntersection() {
		trackerField.setAggregationRule(TrackerField.AggregationRuleEnum.INTERSECTION);
		return this;
	}

	public TrackerFieldBuilder withAggregationRuleSumTotal() {
		trackerField.setAggregationRule(TrackerField.AggregationRuleEnum.SUM_TOTAL);
		return this;
	}

	public TrackerFieldBuilder withAggregationRuleAverage() {
		trackerField.setAggregationRule(TrackerField.AggregationRuleEnum.AVERAGE);
		return this;
	}

	public TrackerFieldBuilder clearAggregationRule() {
		trackerField.aggregationRule(null);
		return this;
	}

	public TrackerFieldBuilder withDistributionRuleSet() {
		trackerField.setDistributionRule(TrackerField.DistributionRuleEnum.SET);
		return this;
	}

	public TrackerFieldBuilder withDistributionRuleDefault() {
		trackerField.setDistributionRule(TrackerField.DistributionRuleEnum.DEFAULT);
		return this;
	}

	public TrackerFieldBuilder withDistributionRuleLeast() {
		trackerField.setDistributionRule(TrackerField.DistributionRuleEnum.LEAST);
		return this;
	}

	public TrackerFieldBuilder withDistributionRuleGreatest() {
		trackerField.setDistributionRule(TrackerField.DistributionRuleEnum.GREATEST);
		return this;
	}

	public TrackerFieldBuilder withDistributionRuleSubset() {
		trackerField.setDistributionRule(TrackerField.DistributionRuleEnum.SUBSET);
		return this;
	}

	public TrackerFieldBuilder withDistributionRuleSuperSet() {
		trackerField.setDistributionRule(TrackerField.DistributionRuleEnum.SUPERSET);
		return this;
	}

	public TrackerFieldBuilder withDistributionRuleCloseRecursively() {
		trackerField.setDistributionRule(TrackerField.DistributionRuleEnum.CLOSE_RECURSIVELY);
		return this;
	}

	public TrackerFieldBuilder withDistributionRuleCloseRestricted() {
		trackerField.setDistributionRule(TrackerField.DistributionRuleEnum.CLOSE_RESTRICTED);
		return this;
	}

	public TrackerFieldBuilder clearDistributionRule() {
		trackerField.setDistributionRule(null);
		return this;
	}

	public TrackerField build() {
		return trackerField;
	}

	protected TrackerFieldBuilder referenceId(int referenceId) {
		trackerField.referenceId(referenceId);
		return this;
	}

	protected TrackerFieldBuilder fieldType(TrackerFieldType fieldType) {
		trackerField.typeId(fieldType.getTypeId());
		return this;
	}

	protected TrackerFieldBuilder withPermissionSingle(List<TrackerFieldPermission> permissions) {
		List<TrackerFieldPermissionAccessPermission> convertedPermissions = permissions.stream()
				.filter(p -> p.status() == null)
				.map(this::mapPermissionObject)
				.toList();

		trackerField.setPermission(new SingleFieldPermission().accessPermissions(convertedPermissions).type("SINGLE"));
		return this;
	}

	private TrackerFieldDependency createTrackerFieldDependency() {
		return Optional.ofNullable(trackerField.getDependency())
				.orElse(new TrackerFieldDependency());
	}

	private TrackerFieldServiceDeskField createTrackerFieldServiceDeskField() {
		return Optional.ofNullable(trackerField.getServiceDeskField())
				.orElse(new TrackerFieldServiceDeskField());
	}

	private TrackerFieldDateFieldSettings createTrackerFieldDateFieldSettings() {
		return Optional.ofNullable(trackerField.getDateFieldSettings())
				.orElse(new TrackerFieldDateFieldSettings());
	}

	private TrackerField getFieldReference(String fieldName) {
		Objects.requireNonNull(fieldName, "Field name cannot be null");
		return Objects.requireNonNull(existingFields.get(fieldName),
					"Field('%s') cannot be found in tracker(%s)".formatted(fieldName, this.tracker.id()));
	}

	private TrackerFieldPermissionAccessPermission mapPermissionObject(TrackerFieldPermission perm) {
		TrackerFieldPermissionAccessPermission apiPermission = new TrackerFieldPermissionAccessPermission();

		apiPermission.setAccess(TrackerFieldPermissionAccessPermission.AccessEnum.fromValue(perm.accessLevel().name()));
		apiPermission.setSubjectType(TrackerFieldPermissionAccessPermission.SubjectTypeEnum.fromValue(perm.subjectType().name()));

		if (SubjectType.ROLE.equals(perm.subjectType())) {
			apiPermission.setSubjectId(this.roleApiService.findRoleByName(perm.subjectName()).getId());
		} else {
			apiPermission.setSubjectId(getFieldReference(perm.subjectName()).getReferenceId());
		}

		return apiPermission;
	}

	private String convertStatusTextToStatusId(String statusName) {
		return String.valueOf(existingStatuses.get(statusName.toLowerCase()).getId());
	}

	private void processAllowedDefaultValues(BiConsumer<TrackerField, Map<String, String>> trackerFieldConsumer,
			Map<String, List<AllowedDefaultValue>> valuesInStatuses) {
		Map<String, String> convertedValues = valuesInStatuses.entrySet()
				.stream()
				.collect(toMap(entry -> convertStatusTextToStatusId(entry.getKey()),
						entry -> convertAllowedDefaultValues(entry.getValue())));

		trackerFieldConsumer.accept(trackerField, convertedValues);
	}

	private String convertAllowedDefaultValues(List<AllowedDefaultValue> defaultValues) {
		return defaultValues.stream()
				.map(this::mapAllowedDefaultValue)
				.collect(joining(","));
	}

	private String mapAllowedDefaultValue(AllowedDefaultValue value) {
		ValueType valueType = value.valueType();
		String defaultValue = value.value();

		return switch (valueType) {
			case TRACKER_ITEM_ID -> formatReferenceText(valueType, defaultValue);
			case TRACKER_ITEM_NAME -> formatReferenceText(
					valueType, String.valueOf(getTrackerItem(value.project(), value.trackerName(), defaultValue).id().id()));
			case USER -> formatReferenceText(valueType,
					String.valueOf(userApiService.findUserByName(defaultValue).getId()));
			case ROLE -> formatReferenceText(valueType,
					String.valueOf(roleApiService.findRoleByName(defaultValue).getId()));
			default -> defaultValue;
		};
	}

	private String formatReferenceText(ValueType type, String value) {
		return "%s-%s".formatted(type.getReferenceId(), value);
	}

	private TrackerItem getTrackerItem(Project project, String trackerName, String trackerItemName) {
		Tracker foundTracker = projectApiService.findTrackerByName(project, trackerName);

		return trackerItemApiService.findTrackerItemByName(foundTracker, trackerItemName);
	}
}
