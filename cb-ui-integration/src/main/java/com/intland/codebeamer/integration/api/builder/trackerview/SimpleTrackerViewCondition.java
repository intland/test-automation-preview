package com.intland.codebeamer.integration.api.builder.trackerview;

public enum SimpleTrackerViewCondition {
	HAS_REVIEW(101);

	private final int fieldId;

	SimpleTrackerViewCondition(int fieldId) {
		this.fieldId = fieldId;
	}

	public int getFieldId() {
		return fieldId;
	}
}
