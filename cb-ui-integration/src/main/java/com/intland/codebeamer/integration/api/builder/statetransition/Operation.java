package com.intland.codebeamer.integration.api.builder.statetransition;

public enum Operation {

	CLEAR(0),
	SET(1),
	PREPEND(2),
	APPEND(3),
	INCREASE(4),
	DECREASE(5),
	ADD(6),
	REMOVE(7),
	RETAIN(8);

	private final int value;

	Operation(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
