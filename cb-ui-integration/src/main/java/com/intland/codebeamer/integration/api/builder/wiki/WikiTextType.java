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

package com.intland.codebeamer.integration.api.builder.wiki;

/**
 * @author <a href="mailto:cbalog@ptc.com">Csongor Balog</a>
 */
public enum WikiTextType {

	PLAINTEXT("PlainText"),

	HTML("Html"),

	WIKI("Wiki");

	private String value;

	WikiTextType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}