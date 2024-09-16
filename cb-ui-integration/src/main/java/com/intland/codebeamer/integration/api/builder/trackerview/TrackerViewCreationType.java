package com.intland.codebeamer.integration.api.builder.trackerview;

public enum TrackerViewCreationType {

	SYSTEM("system"),
	GUARD("guard"),
	USER("user"),
	CHANGES("changes");

	private final String value;

	TrackerViewCreationType(String value) {
		this.value = value;
	}

	String getValue() {
		return value;
	}
}
