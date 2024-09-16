package com.intland.codebeamer.integration.classic.defaults;

public enum Field {
	PARENT("Parent"),
	RELEASE("Release");

	final String name;

	Field(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}