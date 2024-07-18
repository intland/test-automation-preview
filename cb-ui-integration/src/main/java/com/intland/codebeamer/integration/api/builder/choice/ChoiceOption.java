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

package com.intland.codebeamer.integration.api.builder.choice;

import java.util.List;

import com.intland.swagger.client.model.ChoiceOptionsChoiceOption;

public class ChoiceOption {

	private Integer id;
	private String name;
	private String description;
	private List<String> restrictedToStatuses;
	private List<String> defaultInStatuses;

	public ChoiceOption(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public ChoiceOption description(String description) {
		this.description = description;
		return this;
	}

	public List<String> getRestrictedToStatuses() {
		return restrictedToStatuses;
	}

	public ChoiceOption restrictedToStatuses(List<String> restrictedToStatuses) {
		this.restrictedToStatuses = restrictedToStatuses;
		return this;
	}

	public List<String> getDefaultInStatuses() {
		return defaultInStatuses;
	}

	public ChoiceOption defaultInStatuses(List<String> defaultInStatuses) {
		this.defaultInStatuses = defaultInStatuses;
		return this;
	}

	public ChoiceOptionsChoiceOption toApiChoiceOption(List<Integer> convertedDefaultInStatuses,
			List<Integer> convertedRestrictedToStatuses) {
		return new ChoiceOptionsChoiceOption()
				.id(id)
				.name(name)
				.description(description)
				.defaultInStatuses(convertedDefaultInStatuses)
				.restrictedToStatuses(convertedRestrictedToStatuses);
	}
}
