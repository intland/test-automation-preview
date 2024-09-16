package com.intland.codebeamer.integration.api.builder.trackerview;

public enum TrackerViewType {
	TRACKER_VIEW(1),
	USER_ISSUE_VIEW(2),
	REFERENCE_VIEW(3),
	ISSUE_CHANGES_FILTER(4);

	private final int value;

	TrackerViewType(int value) {
		this.value = value;
	}

	int getValue() {
		return value;
	}
}
