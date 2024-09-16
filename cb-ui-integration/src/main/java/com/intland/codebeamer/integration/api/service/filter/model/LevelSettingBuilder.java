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

import com.intland.codebeamer.integration.api.service.baseline.BaselineId;
import com.intland.swagger.client.model.LevelSetting;

public class LevelSettingBuilder {

	private LevelSetting levelSetting;

	public LevelSettingBuilder() {
		super();
	}

	public LevelSettingBuilder(LevelSetting levelSetting) {
		this.levelSetting = levelSetting;
	}

	public LevelSetting build() {
		return levelSetting;
	}

	public LevelSettingBuilder addEntityIdsItem(Integer entityId) {
		this.levelSetting.addEntityIdsItem(entityId);
		return this;
	}

	public LevelSettingBuilder addFieldIdsItem(FieldAndTrackerId fieldIdAndTrackerId) {
		this.levelSetting.addFieldIdsItem(
				new com.intland.swagger.client.model.FieldAndTrackerId()
						.fieldId(fieldIdAndTrackerId.fieldId())
						.trackerId(fieldIdAndTrackerId.trackerId()));

		return this;
	}

	public LevelSettingBuilder upstream(boolean upstream) {
		this.levelSetting.setUpstream(upstream);
		return this;
	}

	public LevelSettingBuilder upstream() {
		return upstream(true);
	}

	public LevelSettingBuilder downstream(boolean downstream) {
		this.levelSetting.setDownstream(downstream);
		return this;
	}

	public LevelSettingBuilder downstream() {
		return downstream(true);
	}

	public LevelSettingBuilder upstreamAssociations(boolean upstreamAssociations) {
		this.levelSetting.setUpstreamAssociations(upstreamAssociations);
		return this;
	}

	public LevelSettingBuilder upstreamAssociations() {
		return upstreamAssociations(true);
	}

	public LevelSettingBuilder downstreamAssociations(boolean downstreamAssociations) {
		this.levelSetting.setDownstreamAssociations(downstreamAssociations);
		return this;
	}

	public LevelSettingBuilder downstreamAssociations() {
		return downstreamAssociations(true);
	}

	public LevelSettingBuilder foldersAndInformation(boolean foldersAndInformation) {
		this.levelSetting.setFoldersAndInformation(foldersAndInformation);
		return this;
	}

	public LevelSettingBuilder foldersAndInformation() {
		return foldersAndInformation(true);
	}

	public LevelSettingBuilder showTestStepReferences(boolean showTestStepReferences) {
		this.levelSetting.setShowTestStepReferences(showTestStepReferences);
		return this;
	}

	public LevelSettingBuilder showTestStepReferences() {
		return showTestStepReferences(true);
	}

	public LevelSettingBuilder externalScm(boolean externalScm) {
		this.levelSetting.setExternalScm(externalScm);
		return this;
	}

	public LevelSettingBuilder externalScm() {
		return externalScm(true);
	}

	public LevelSettingBuilder children(boolean children) {
		this.levelSetting.setChildren(children);
		return this;
	}

	public LevelSettingBuilder children() {
		return children(true);
	}

	public LevelSettingBuilder cbQL(String cbQL) {
		this.levelSetting.setCbQL(cbQL);
		return this;
	}

	public LevelSettingBuilder cbQLLogic(String cbQLLogic) {
		this.levelSetting.setCbQLLogic(cbQLLogic);
		return this;
	}

	public LevelSettingBuilder cbQLLogicSlices(String cbQLLogicSlices) {
		this.levelSetting.setCbQLLogicSlices(cbQLLogicSlices);
		return this;
	}

	public LevelSettingBuilder historyViewDate(String historyViewDate) {
		this.levelSetting.setHistoryViewDate(historyViewDate);
		return this;
	}

	public LevelSettingBuilder historyBaselineId(BaselineId historyBaselineId) {
		this.levelSetting.setHistoryBaselineId(historyBaselineId.id());
		return this;
	}
}
