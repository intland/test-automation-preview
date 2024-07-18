/*
 * Copyright by Intland Software
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Intland Software. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Intland.
 */

package com.intland.codebeamer.integration.api.builder.reference;

public enum StatusMeaning {

	OPEN(1, "Open"),
	CLOSED(2, "Closed"),
	RESOLVED(3, "Resolved"),
	SUCCESSFUL(4, "Successful"),
	UNRESOLVED(5, "Unresolved"),
	UNSUCCESSFUL(6, "Unsuccessful"),
	INPROGRESS(7, "InProgress"),
	PENDING(8, "Pending");

	private Integer statusCode;

	private String statusName;

	StatusMeaning(Integer statusCode, String statusName) {
		this.statusCode = statusCode;
		this.statusName = statusName;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public String getStatusName() {
		return statusName;
	}
}
