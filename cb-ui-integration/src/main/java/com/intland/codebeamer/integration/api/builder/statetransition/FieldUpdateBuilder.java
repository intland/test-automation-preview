package com.intland.codebeamer.integration.api.builder.statetransition;

import java.util.List;

import com.intland.codebeamer.integration.api.service.Member;
import com.intland.codebeamer.integration.api.service.role.Role;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.swagger.client.model.AbstractField;
import com.intland.swagger.client.model.AbstractFieldValue;
import com.intland.swagger.client.model.AbstractReference;
import com.intland.swagger.client.model.ChoiceFieldMultiValue;
import com.intland.swagger.client.model.ChoiceFieldValue;
import com.intland.swagger.client.model.FieldValueInfo;
import com.intland.swagger.client.model.RoleReference;
import com.intland.swagger.client.model.TextFieldValue;
import com.intland.swagger.client.model.TrackerItemReference;
import com.intland.swagger.client.model.UserReference;

public class FieldUpdateBuilder {

	private final FieldValueInfo fieldValueInfo = new FieldValueInfo();
	private final List<AbstractField> fields;

	public FieldUpdateBuilder(List<AbstractField> fields) {
		this.fields = fields;
	}

	public FieldUpdateBuilder field(String fieldName) {
		fieldValueInfo.fieldId(findFieldByName(fieldName).getId());
		return this;
	}

	public FieldUpdateBuilder memberValue(Member member) {
		AbstractFieldValue fieldValue = new ChoiceFieldMultiValue().values(List.of(getMemberReference(member)));
		fieldValueInfo.setFieldValues(List.of(createFieldValueInfo(fieldValue)));
		return this;
	}

	public FieldUpdateBuilder textValue(String text) {
		AbstractFieldValue fieldValue = new TextFieldValue().value(text);
		fieldValueInfo.setFieldValues(List.of(createFieldValueInfo(fieldValue)));
		return this;
	}

	public FieldUpdateBuilder itemReference(TrackerItemId itemId) {
		AbstractFieldValue fieldValue = new ChoiceFieldValue()
				.values(List.of(new TrackerItemReference().id(itemId.id())));
		fieldValueInfo.setFieldValues(List.of(createFieldValueInfo(fieldValue)));
		return this;
	}

	public FieldValueInfo build() {
		return fieldValueInfo;
	}

	private AbstractField findFieldByName(final String fieldName) {
		return fields.stream()
				.filter(it -> it.getName().equals(fieldName))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("Could not find field: " + fieldName));
	}

	private FieldValueInfo createFieldValueInfo(AbstractFieldValue fieldValue) {
		FieldValueInfo innerFieldValueInfo = new FieldValueInfo();
		innerFieldValueInfo.setOp(Operation.SET.getValue());
		innerFieldValueInfo.setRequired(Boolean.TRUE);
		innerFieldValueInfo.setFieldValue(fieldValue);
		return innerFieldValueInfo;
	}

	private AbstractReference getMemberReference(Member member) {
		return switch (member) {
			case User user -> new UserReference().id(user.getId());
			case Role role -> new RoleReference().id(role.getId());
			default -> throw new IllegalStateException("Unsupported member " + member);
		};
	}

}
