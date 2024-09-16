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

package com.intland.codebeamer.integration.api.service.filter.model;

import java.util.function.Function;

import com.intland.codebeamer.integration.api.builder.RenderingMethod;
import com.intland.codebeamer.integration.api.service.tracker.TrackerField;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.swagger.client.model.CbQLIntelligentTableViewConfiguration;
import com.intland.swagger.client.model.LevelSetting;

public class CbQLIntelligentTableViewConfigurationBuilder {

	private CbQLIntelligentTableViewConfiguration cbQLIntelligentTableViewConfiguration;

	public CbQLIntelligentTableViewConfigurationBuilder() {
		super();
	}

	public CbQLIntelligentTableViewConfigurationBuilder(
			CbQLIntelligentTableViewConfiguration cbQLIntelligentTableViewConfiguration) {
		this.cbQLIntelligentTableViewConfiguration = cbQLIntelligentTableViewConfiguration;
	}

	public CbQLIntelligentTableViewConfiguration build() {
		return this.cbQLIntelligentTableViewConfiguration;
	}

	public CbQLIntelligentTableViewConfigurationBuilder renderingMethod(RenderingMethod renderingMethod) {
		this.cbQLIntelligentTableViewConfiguration.setRenderingMethod(
				CbQLIntelligentTableViewConfiguration.RenderingMethodEnum.fromValue(renderingMethod.getValue()));
		return this;
	}

	public CbQLIntelligentTableViewConfigurationBuilder addInitialFieldIdsItem(TrackerField initialFieldId) {
		this.cbQLIntelligentTableViewConfiguration.addInitialFieldIdsItem(initialFieldId.getId());
		return this;
	}

	public CbQLIntelligentTableViewConfigurationBuilder addInitialFieldTrackerIdsItem(TrackerId initialFieldTrackerId) {
		this.cbQLIntelligentTableViewConfiguration.addInitialFieldTrackerIdsItem(initialFieldTrackerId.id());
		return this;
	}

	public CbQLIntelligentTableViewConfigurationBuilder addLevelSettingsItem(String key,
			Function<LevelSettingBuilder, LevelSettingBuilder> builder) {
		LevelSetting levelSetting = builder.apply(
				new LevelSettingBuilder(new LevelSetting())).build();
		this.cbQLIntelligentTableViewConfiguration.putLevelSettingsItem(key, levelSetting);
		return this;
	}

	public CbQLIntelligentTableViewConfigurationBuilder ignoreRedundants(boolean ignoreRedundants) {
		this.cbQLIntelligentTableViewConfiguration.setIgnoreRedundants(ignoreRedundants);
		return this;
	}

	public CbQLIntelligentTableViewConfigurationBuilder ignoreRedundants() {
		return ignoreRedundants(true);
	}

	public CbQLIntelligentTableViewConfigurationBuilder ignoreRedundantsUpdated(boolean ignoreRedundantsUpdated) {
		this.cbQLIntelligentTableViewConfiguration.setIgnoreRedundantsUpdated(ignoreRedundantsUpdated);
		return this;
	}

	public CbQLIntelligentTableViewConfigurationBuilder ignoreRedundantsUpdated() {
		return ignoreRedundantsUpdated(true);
	}

	public CbQLIntelligentTableViewConfigurationBuilder disableScmTrackers(boolean disableScmTrackers) {
		this.cbQLIntelligentTableViewConfiguration.setDisableScmTrackers(disableScmTrackers);
		return this;
	}

	public CbQLIntelligentTableViewConfigurationBuilder disableScmTrackers() {
		return disableScmTrackers(true);
	}

	public CbQLIntelligentTableViewConfigurationBuilder documentView(boolean documentView) {
		this.cbQLIntelligentTableViewConfiguration.setDocumentView(documentView);
		return this;
	}

	public CbQLIntelligentTableViewConfigurationBuilder documentView() {
		return documentView(true);
	}
}
