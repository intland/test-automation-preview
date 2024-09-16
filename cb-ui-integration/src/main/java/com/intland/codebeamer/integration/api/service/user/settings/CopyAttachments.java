package com.intland.codebeamer.integration.api.service.user.settings;

public enum CopyAttachments {

	ALWAYS_ASK(0),
	AUTOMATICALLY_COPY(1),
	DO_NOT_COPY(2);

	private final int value;

	CopyAttachments(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
