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

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import com.google.common.cache.LoadingCache;
import com.intland.codebeamer.integration.api.builder.alloweddefault.AllowedDefaultValue;
import com.intland.codebeamer.integration.api.builder.alloweddefault.AllowedValueBuilder;
import com.intland.codebeamer.integration.api.builder.alloweddefault.DefaultValueBuilder;
import com.intland.codebeamer.integration.api.builder.alloweddefault.ValueType;
import com.intland.codebeamer.integration.api.builder.permission.FieldPermissionBuilder;
import com.intland.codebeamer.integration.api.builder.permission.SubjectType;
import com.intland.codebeamer.integration.api.builder.permission.TrackerFieldPermission;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.role.RoleApiService;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.swagger.client.model.ChoiceOptionReference;
import com.intland.swagger.client.model.FieldReference;
import com.intland.swagger.client.model.PerStatusFieldPermission;
import com.intland.swagger.client.model.SameAsFieldPermission;
import com.intland.swagger.client.model.SingleFieldPermission;
import com.intland.swagger.client.model.TrackerField;
import com.intland.swagger.client.model.TrackerFieldDateFieldSettings;
import com.intland.swagger.client.model.TrackerFieldPermissionAccessPermission;
import com.intland.swagger.client.model.TrackerFieldServiceDeskField;
import com.intland.swagger.client.model.UnrestrictedFieldPermission;

public class TrackerFieldBuilder {

	private TrackerField trackerField;

	private Tracker tracker;

	private LoadingCache<TrackerId, Map<String, FieldReference>> trackerFieldCache;

	private Map<String, ChoiceOptionReference> existingStatuses;

	private UserApiService userApiService;

	private RoleApiService roleApiService;

	private ProjectApiService projectApiService;

	private TrackerItemApiService trackerItemApiService;

	public TrackerFieldBuilder(Tracker tracker, LoadingCache<TrackerId, Map<String, FieldReference>> trackerFieldCache,
			RoleApiService roleApiService, UserApiService userApiService, ProjectApiService projectApiService,
			TrackerItemApiService trackerItemApiService, Map<String, ChoiceOptionReference> existingStatuses) {
		this.trackerField = new TrackerField();
		this.tracker = tracker;
		this.trackerFieldCache = trackerFieldCache;
		this.existingStatuses = existingStatuses;
		this.roleApiService = roleApiService;
		this.userApiService = userApiService;
		this.projectApiService = projectApiService;
		this.trackerItemApiService = trackerItemApiService;
	}

	public TrackerFieldBuilder label(String label) {
		trackerField.label(label);
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

	public TrackerFieldBuilder hidden(boolean hidden) {
		trackerField.hidden(Boolean.valueOf(hidden));
		return this;
	}

	public TrackerFieldBuilder mandatory(boolean mandatory) {
		trackerField.mandatory(Boolean.valueOf(mandatory));
		return this;
	}

	public TrackerFieldBuilder omitMerge(boolean omitMerge) {
		trackerField.omitMerge(Boolean.valueOf(omitMerge));
		return this;
	}

	public TrackerFieldBuilder omitSuspectedWhenChange(boolean omitSuspectedWhenChange) {
		trackerField.omitSuspectedWhenChange(Boolean.valueOf(omitSuspectedWhenChange));
		return this;
	}

	public TrackerFieldBuilder propagateDependencies(boolean propagateDependencies) {
		trackerField.propagateDependencies(Boolean.valueOf(propagateDependencies));
		return this;
	}

	public TrackerFieldBuilder propagateSuspect(boolean propagateSuspect) {
		trackerField.propagateSuspect(Boolean.valueOf(propagateSuspect));
		return this;
	}

	public TrackerFieldBuilder reversedSuspect(boolean reversedSuspect) {
		trackerField.reversedSuspect(Boolean.valueOf(reversedSuspect));
		return this;
	}

	public TrackerFieldBuilder listable(boolean listable) {
		trackerField.listable(Boolean.valueOf(listable));
		return this;
	}

	public TrackerFieldBuilder multipleSelection(boolean multipleSelection) {
		trackerField.multipleSelection(Boolean.valueOf(multipleSelection));
		return this;
	}

	public TrackerFieldBuilder newLine(boolean newLine) {
		trackerField.newLine(Boolean.valueOf(newLine));
		return this;
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
		trackerField.hideIfFormulaSameAsFieldId(getFieldReference(fieldName).getId());
		return this;
	}

	public TrackerFieldBuilder mandatoryIfFormula(String mandatoryIfFormula) {
		trackerField.mandatoryIfFormula(mandatoryIfFormula);
		return this;
	}

	public TrackerFieldBuilder mandatoryIfFormulaSameAsField(String fieldName) {
		trackerField.mandatoryIfFormulaSameAsFieldId(getFieldReference(fieldName).getId());
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

	public TrackerFieldBuilder displayYear(boolean displayYear) {
		trackerField.dateFieldSettings(createTrackerFieldDateFieldSettings().displayYear(Boolean.valueOf(displayYear)));
		return this;
	}

	public TrackerFieldBuilder displayMonth(boolean displayMonth) {
		trackerField.dateFieldSettings(createTrackerFieldDateFieldSettings().displayMonth(Boolean.valueOf(displayMonth)));
		return this;
	}

	public TrackerFieldBuilder displayDay(boolean displayDay) {
		trackerField.dateFieldSettings(createTrackerFieldDateFieldSettings().displayDay(Boolean.valueOf(displayDay)));
		return this;
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

	public TrackerItemId getTrackerItem(Project project, String trackerName, String trackerItemName) {
		TrackerId tracker = projectApiService.findTrackerByName(project, trackerName);
		TrackerItemId trackerItem = trackerItemApiService.findTrackerItemByName(tracker, trackerItemName);

		return trackerItem;
	}

	public TrackerFieldBuilder withPermissionUnrestricted() {
		trackerField.setPermission(new UnrestrictedFieldPermission().type("UNRESTRICTED"));
		return this;
	}

	public TrackerFieldBuilder withPermissionSameAsField(String fieldName) {
		FieldReference fieldReference = getFieldReference(fieldName);
		trackerField.setPermission(new SameAsFieldPermission().sameAsId(fieldReference.getId()).type("SAME_AS"));
		return this;
	}

	public TrackerFieldBuilder withPermissionSingle(Function<FieldPermissionBuilder, FieldPermissionBuilder> permBuilder) {
		List<TrackerFieldPermission> permissions = permBuilder.apply(new FieldPermissionBuilder()).build();

		List<TrackerFieldPermissionAccessPermission> convertedPermissions = permissions.stream()
				.filter(p -> p.status() == null)
				.map(this::mapPermissionObject)
				.toList();

		trackerField.setPermission(new SingleFieldPermission().accessPermissions(convertedPermissions).type("SINGLE"));
		return this;
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

	private TrackerFieldServiceDeskField createTrackerFieldServiceDeskField() {
		return Optional.ofNullable(trackerField.getServiceDeskField())
				.orElse(new TrackerFieldServiceDeskField());
	}

	private TrackerFieldDateFieldSettings createTrackerFieldDateFieldSettings() {
		return Optional.ofNullable(trackerField.getDateFieldSettings())
				.orElse(new TrackerFieldDateFieldSettings());
	}

	private FieldReference getFieldReference(String fieldName) {
		Objects.requireNonNull(fieldName, "Field name cannot be null");
		try {
			return Objects.requireNonNull(trackerFieldCache.get(this.tracker.id()).get(fieldName),
					"Field('%s') cannot be found in tracker(%s)".formatted(fieldName, this.tracker.id()));
		} catch (ExecutionException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	private TrackerFieldPermissionAccessPermission mapPermissionObject(TrackerFieldPermission perm) {
		TrackerFieldPermissionAccessPermission apiPermission = new TrackerFieldPermissionAccessPermission();

		apiPermission.setAccess(TrackerFieldPermissionAccessPermission.AccessEnum.fromValue(perm.accessLevel().name()));
		apiPermission.setSubjectType(TrackerFieldPermissionAccessPermission.SubjectTypeEnum.fromValue(perm.subjectType().name()));

		if (SubjectType.ROLE.equals(perm.subjectType())) {
			apiPermission.setSubjectId(this.roleApiService.findRoleByName(perm.subjectName()).getId());
		} else {
			apiPermission.setSubjectId(getFieldReference(perm.subjectName()).getId());
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
			case ValueType.TRACKER_ITEM_ID -> formatReferenceText(valueType, defaultValue);
			case ValueType.TRACKER_ITEM_NAME -> formatReferenceText(
					valueType, String.valueOf(getTrackerItem(value.project(), value.trackerName(), defaultValue).id()));
			case ValueType.USER -> formatReferenceText(valueType,
					String.valueOf(userApiService.findUserByName(defaultValue).getId()));
			case ValueType.ROLE -> formatReferenceText(valueType,
					String.valueOf(roleApiService.findRoleByName(defaultValue).getId()));
			default -> defaultValue;
		};
	}

	private String formatReferenceText(ValueType type, String value) {
		return "%s-%s".formatted(type.getReferenceId(), value);
	}
}
