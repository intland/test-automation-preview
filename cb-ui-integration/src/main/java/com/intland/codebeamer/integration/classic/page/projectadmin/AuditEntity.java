package com.intland.codebeamer.integration.classic.page.projectadmin;

/**
 * 
 * @author akadam
 *
 */
public enum AuditEntity {

	FIELD_CHANGES(1, "Field"),
	WORKFLOW_CHANGES(2, "Workflow"),
	PERMISSION_CHANGES(3, "Permission"),
	TRACKER_CHANGES(4, "Tracker"),
	BASELINE_CHANGES(5, "Baseline");

	private final Integer id;
	private final String value;

	AuditEntity(Integer id, String value) {
		this.id = id;
		this.value = value;
	}

	public Integer getId() {
		return id;
	}

	public String getValue() {
		return value;
	}
}
