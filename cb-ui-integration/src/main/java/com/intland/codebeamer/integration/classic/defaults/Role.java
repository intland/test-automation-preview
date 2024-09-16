package com.intland.codebeamer.integration.classic.defaults;

public enum Role {

	PROJECT_ADMIN("Project Admin");

	String name;

	Role(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
