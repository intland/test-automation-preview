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

package com.intland.codebeamer.integration.api.service.sharedfield;

import com.intland.codebeamer.integration.api.service.artifact.ArtifactType;
import com.intland.codebeamer.integration.classic.page.tracker.config.fields.FieldType;
import com.intland.codebeamer.integration.classic.page.tracker.config.fields.MemberType;
import com.intland.swagger.client.model.SharedField;
import com.intland.swagger.client.model.SharedFieldType;

public class SharedFieldBuilder {

	private final SharedField sharedField;

	public SharedFieldBuilder(String name) {
		sharedField = new SharedField();
		sharedField.setName(name);
	}

	public SharedFieldBuilder displayName(String displayName) {
		sharedField.displayName(displayName);
		return this;
	}

	public SharedFieldBuilder description(String description) {
		sharedField.description(description);
		return this;
	}

	public SharedFieldBuilder textField() {
		this.fieldType(FieldType.TEXT);
		return this;
	}

	public SharedFieldBuilder integerField() {
		this.fieldType(FieldType.INTEGER);
		return this;
	}

	public SharedFieldBuilder decimalField() {
		this.fieldType(FieldType.DECIMAL);
		return this;
	}

	public SharedFieldBuilder dateField() {
		this.fieldType(FieldType.DATE);
		return this;
	}

	public SharedFieldBuilder colorField() {
		this.fieldType(FieldType.COLOR);
		return this;
	}

	public SharedFieldBuilder durationField() {
		this.fieldType(FieldType.DURATION);
		return this;
	}

	public SharedFieldBuilder booleanField() {
		this.fieldType(FieldType.BOOL);
		return this;
	}

	public SharedFieldBuilder languageField() {
		this.fieldType(FieldType.LANGUAGE);
		return this;
	}

	public SharedFieldBuilder countryField() {
		this.fieldType(FieldType.COUNTRY);
		return this;
	}

	public SharedFieldBuilder wikitextField() {
		this.fieldType(FieldType.WIKITEXT);
		return this;
	}

	public SharedFieldBuilder urlField() {
		this.fieldType(FieldType.WIKILINK);
		return this;
	}

	public SharedFieldBuilder choiceField() {
		this.fieldType(FieldType.CHOICE);
		return this;
	}

	public SharedFieldBuilder memberFieldWithUserType() {
		this.fieldType(FieldType.MEMBER);
		this.memberType(MemberType.USERS);
		return this;
	}

	public SharedFieldBuilder memberFieldWithGroupType() {
		this.fieldType(FieldType.MEMBER);
		this.memberType(MemberType.GROUPS);
		return this;
	}

	public SharedFieldBuilder memberFieldWithRoleType() {
		this.fieldType(FieldType.MEMBER);
		this.memberType(MemberType.ROLES);
		return this;
	}

	public SharedFieldBuilder referenceFieldWithProjectType() {
		this.fieldType(FieldType.REFERENCE);
		this.referenceType(ArtifactType.PROJECT);
		return this;
	}

	public SharedFieldBuilder referenceFieldWithRepositoryType() {
		this.fieldType(FieldType.REFERENCE);
		this.referenceType(ArtifactType.SCM_REPOSITORY);
		return this;
	}

	public SharedFieldBuilder referenceFieldWithTrackerType() {
		this.fieldType(FieldType.REFERENCE);
		this.referenceType(ArtifactType.TRACKER);
		return this;
	}

	public SharedFieldBuilder referenceFieldWithTrackerItemType() {
		this.fieldType(FieldType.REFERENCE);
		this.referenceType(ArtifactType.TRACKER_ITEM);
		return this;
	}

	public SharedFieldBuilder referenceFieldWithUserType() {
		this.fieldType(FieldType.REFERENCE);
		this.referenceType(ArtifactType.USER_ACCOUNT);
		return this;
	}

	public SharedField build() {
		if (sharedField.getName() == null) {
			throw new IllegalArgumentException("Name is required");
		}

		if (sharedField.getFieldType() == null) {
			throw new IllegalArgumentException("Field type is required");
		}
		return sharedField;
	}

	private SharedFieldBuilder fieldType(FieldType fieldType) {
		sharedField.fieldType(new SharedFieldType().key(fieldType.getTypeId()));
		return this;
	}

	private SharedFieldBuilder memberType(MemberType memberType) {
		sharedField.memberType(new SharedFieldType().key(memberType.getTypeId()));
		return this;
	}

	private SharedFieldBuilder referenceType(ArtifactType referenceType) {
		sharedField.referenceType(new SharedFieldType().key(Integer.valueOf(referenceType.getValue())));
		return this;
	}

}
