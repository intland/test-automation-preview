package com.intland.codebeamer.integration.api.builder.trackerview;

import java.util.Arrays;
import java.util.List;

import com.intland.swagger.client.model.AbstractField;
import com.intland.swagger.client.model.FilterConditionInfo;
import com.intland.swagger.client.model.IdNamed;
import com.intland.swagger.client.model.ValueLabel;

public class TrackerViewConditionInfoBuilder {

	private final FilterConditionInfo conditionInfo;
	private final List<AbstractField> fields;

	public TrackerViewConditionInfoBuilder(List<AbstractField> fields) {
		this.fields = fields;
		this.conditionInfo = new FilterConditionInfo();
	}

	public TrackerViewConditionInfoBuilder field(String name) {
		conditionInfo.field(new IdNamed()
				.id(getFieldId(name).getId())
				.name(name)
		);
		return this;
	}

	public TrackerViewConditionInfoBuilder is(String... value) {
		conditionInfo.value(Arrays.asList(value));
		return this;
	}

	public TrackerViewConditionInfoBuilder changed() {
		conditionInfo.op(new ValueLabel()
				.value("d")
				.label("changed")
		);
		return this;
	}

	public TrackerViewConditionInfoBuilder not(boolean not) {
		conditionInfo.not(not);
		return this;
	}

	public FilterConditionInfo build() {
		return conditionInfo;
	}

	private AbstractField getFieldId(final String fieldName) {
		return fields.stream()
				.filter(it -> it.getName().equals(fieldName))
				.findFirst()
				.orElseThrow();
	}

}
