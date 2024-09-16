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

package com.intland.codebeamer.integration.api.builder.dependency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.intland.codebeamer.integration.api.service.artifact.ArtifactType;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;

public class FieldDependencyBuilder {

	private static final String ANY = "*";

	private static final String NULL = "NULL";

	private Map<String, String> dependencies = new HashMap<>();

	public FieldDependencyBuilder() {
	}

	/**
	 * Setting static dependencies for a field.
	 * <p>
	 * If severity dependsOn status:
	 * New || Blocker, Critical
	 *
	 * @param dependantFieldOptions options of dependant field, use option id
	 * @param currentFieldOptions options of current field, use option id
	 * @return field dependency builder
	 */
	public FieldDependencyBuilder addDependencyForOptions(List<String> dependantFieldOptions, List<String> currentFieldOptions) {
		dependencies.put(generateValue(dependantFieldOptions), generateValue(currentFieldOptions));
		return this;
	}

	/**
	 * Setting static dependencies for a field.
	 * <p>
	 * If severity dependsOn status:
	 * Any || Blocker, Critical
	 *
	 * @param currentFieldOptions options of current field, use option id
	 * @return field dependency builder
	 */
	public FieldDependencyBuilder addDependencyForAnyDependant(String... currentFieldOptions) {
		dependencies.put(ANY, generateValue(List.of(currentFieldOptions)));
		return this;
	}

	/**
	 * Setting static dependencies for a field.
	 * <p>
	 * If severity dependsOn status:
	 * New || Any
	 *
	 * @param dependantFieldOptions options of dependant field, use option id
	 * @return field dependency builder
	 */
	public FieldDependencyBuilder addDependencyForAnyCurrentField(String... dependantFieldOptions) {
		dependencies.put(generateValue(List.of(dependantFieldOptions)), ANY);
		return this;
	}

	/**
	 * Setting static dependencies for a field.
	 * <p>
	 * If severity dependsOn status:
	 * -- || Blocker, Critical
	 *
	 * @param currentFieldOptions options of current field, use option id
	 * @return field dependency builder
	 */
	public FieldDependencyBuilder addDependencyForEmptyDependant(String... currentFieldOptions) {
		dependencies.put(NULL, generateValue(List.of(currentFieldOptions)));
		return this;
	}

	/**
	 * Setting static dependencies for a field.
	 * <p>
	 * If severity dependsOn status:
	 * New || --
	 *
	 * @param dependantFieldOptions options of dependant field, use option id
	 * @return field dependency builder
	 */
	public FieldDependencyBuilder addDependencyForEmptyCurrentField(String... dependantFieldOptions) {
		dependencies.put(generateValue(List.of(dependantFieldOptions)), NULL);
		return this;
	}

	//

	/**
	 * Setting static dependencies for a field.
	 *
	 * @param dependantFieldOptions items of dependant field
	 * @param currentFieldOptions items of current field
	 * @return field dependency builder
	 */
	public FieldDependencyBuilder addDependencyForReferences(List<TrackerItemId> dependantFieldOptions,
			List<TrackerItemId> currentFieldOptions) {
		dependencies.put(generateValue(convertReference(dependantFieldOptions)),
				generateValue(convertReference(currentFieldOptions)));
		return this;
	}

	/**
	 * Setting static dependencies for a field.
	 *
	 * @param currentFieldOptions items of current field
	 * @return field dependency builder
	 */
	public FieldDependencyBuilder addDependencyForAnyDependantReference(TrackerItemId... currentFieldOptions) {
		dependencies.put(ANY, generateValue(convertReference(List.of(currentFieldOptions))));
		return this;
	}

	/**
	 * Setting static dependencies for a field.
	 *
	 * @param dependantFieldOptions items of dependant field
	 * @return field dependency builder
	 */
	public FieldDependencyBuilder addDependencyForAnyCurrentFieldReference(TrackerItemId... dependantFieldOptions) {
		dependencies.put(generateValue(convertReference(List.of(dependantFieldOptions))), ANY);
		return this;
	}

	/**
	 * Setting static dependencies for a field.
	 *
	 * @param currentFieldOptions items of current field
	 * @return field dependency builder
	 */
	public FieldDependencyBuilder addDependencyForEmptyDependantReference(TrackerItemId... currentFieldOptions) {
		dependencies.put(NULL, generateValue(convertReference(List.of(currentFieldOptions))));
		return this;
	}

	/**
	 * Setting static dependencies for a field.
	 *
	 * @param dependantFieldOptions items of dependant field
	 * @return field dependency builder
	 */
	public FieldDependencyBuilder addDependencyForEmptyCurrentFieldReference(TrackerItemId... dependantFieldOptions) {
		dependencies.put(generateValue(convertReference(List.of(dependantFieldOptions))), NULL);
		return this;
	}

	/**
	 * Setting static dependencies for a field.
	 *
	 * @param dependantFieldOptions options of dependant field, use option id
	 * @param currentFieldOptions items of current field
	 * @return field dependency builder
	 */
	public FieldDependencyBuilder addDependencyBetweenOptionAndReference(List<String> dependantFieldOptions,
			List<TrackerItemId> currentFieldOptions) {
		dependencies.put(generateValue(dependantFieldOptions), generateValue(convertReference(currentFieldOptions)));
		return this;
	}

	/**
	 * Setting static dependencies for a field.
	 *
	 * @param dependantFieldOptions items of dependant field
	 * @param currentFieldOptions options of current field, use option id
	 * @return field dependency builder
	 */
	public FieldDependencyBuilder addDependencyBetweenReferenceAndOptions(List<TrackerItemId> dependantFieldOptions,
			List<String> currentFieldOptions) {
		dependencies.put(generateValue(convertReference(dependantFieldOptions)), generateValue(currentFieldOptions));
		return this;
	}

	public Map<String, String> build() {
		return dependencies;
	}

	private List<String> convertReference(List<TrackerItemId> list) {
		return list.stream()
				.map(TrackerItemId::id)
				.map(id -> "%s-%s".formatted(ArtifactType.TRACKER_ITEM.getValue(), id))
				.toList();
	}

	private String generateValue(List<String> list) {
		return String.join(",", list);
	}
}
