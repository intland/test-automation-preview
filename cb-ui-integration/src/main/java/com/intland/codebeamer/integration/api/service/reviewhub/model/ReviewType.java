package com.intland.codebeamer.integration.api.service.reviewhub.model;

public enum ReviewType {

	RELEASE_REVIEW("releaseReview"),

	REPORT_REVIEW("reportReview"),

	TRACKER_REVIEW("trackerReview"),

	TRACKER_ITEM_REVIEW("trackerItemReview");

	private String value;

	ReviewType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
