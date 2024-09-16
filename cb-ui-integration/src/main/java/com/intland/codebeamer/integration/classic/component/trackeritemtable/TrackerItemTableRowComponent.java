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

package com.intland.codebeamer.integration.classic.component.trackeritemtable;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.FieldLocators;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemFieldFormComponent;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class TrackerItemTableRowComponent
		extends AbstractCodebeamerComponent<TrackerItemTableRowComponent, TrackerItemTableRowAssertions> {

	protected static final String DATA_TT_ID = "data-tt-id";

	private static final String SELECTOR = "#trackerItems tr[data-tt-id=%s]";

	private static final String GROUPPING_LEVEL = "groupping-level-";

	private final TrackerItemId trackerItemId;

	public TrackerItemTableRowComponent(CodebeamerPage codebeamerPage, TrackerItemId trackerItemId) {
		this(codebeamerPage, null, trackerItemId);
	}

	public TrackerItemTableRowComponent(CodebeamerPage codebeamerPage, String frameLocator, TrackerItemId trackerItemId) {
		super(codebeamerPage, frameLocator, SELECTOR.formatted(trackerItemId));
		this.trackerItemId = trackerItemId;
	}

	public TrackerItemTableRowComponent(CodebeamerPage codebeamerPage, String frameLocator, int rowIndex) {
		super(codebeamerPage, frameLocator, "#trackerItems tbody tr:nth-of-type(%d)".formatted(rowIndex));
		this.trackerItemId = getTrackerItemId();
	}

	@Override
	public TrackerItemTableRowAssertions assertThat() {
		return new TrackerItemTableRowAssertions(this);
	}

	public TrackerItemId getTrackerItemId() {
		String dataTtId = selfLocator().getAttribute(DATA_TT_ID);
		if (dataTtId == null) {
			return null;
		}

		return new TrackerItemId(Integer.valueOf(dataTtId));
	}

	public CodebeamerLocator getSummaryElement() {
		return this.locator("td.referenceSelectName .itemUrl");
	}

	public String getGroupingLevelClass() {
		return this.locator("td:first-of-type").classes()
				.stream()
				.filter(c -> c.startsWith(GROUPPING_LEVEL))
				.findFirst()
				.orElse("");
	}

	public int getIndentLevel() {
		String indentLevel = this.locator("td:first-of-type").classes().stream()
				.filter(c -> c.startsWith(GROUPPING_LEVEL) && !c.contains("footer")).findFirst().orElse("");

		if (indentLevel.isEmpty()) {
			return -1;
		}

		return Integer.parseInt(indentLevel.split(GROUPPING_LEVEL)[1]);
	}

	public TrackerItemTableHeaderComponent getTableHeaderComponent() {
		return new TrackerItemTableHeaderComponent(getCodebeamerPage(), getFrameLocator());
	}

	public TrackerItemTableRowComponent fieldsComponent(Consumer<TrackerItemFieldFormComponent> consumer) {
		consumer.accept(createFieldsComponent());
		return this;
	}

	public TrackerItemFieldFormComponent getFieldsComponent() {
		return createFieldsComponent();
	}

	private TrackerItemFieldFormComponent createFieldsComponent() {
		return new TrackerItemFieldFormComponent(getCodebeamerPage(), getFrameLocator(),
				SELECTOR.formatted(trackerItemId.id()), new FieldLocators(
				(fieldName) -> "td.fieldId_%s"
						.formatted(getTableHeaderComponent().getHeaderIndexByFieldName(fieldName)),
				null,
				(fieldName) -> "td.fieldId_%s"
						.formatted(getTableHeaderComponent().getHeaderIndexByFieldName(fieldName))));
	}
}
