package com.intland.codebeamer.integration.classic.component.tab;

public class TabId {

	private final String id;

	public TabId(String id) {
		this.id = id;
	}

	public String getTabContentId() {
		return this.id;
	}

	public String getTabId() {
		return "%s-tab".formatted(this.id);
	}
}
