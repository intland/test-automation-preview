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

package com.intland.codebeamer.integration.classic.page.tracker.config.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.tracker.config.fields.FieldType;
import com.intland.codebeamer.integration.classic.page.tracker.config.fields.FieldOverlayMenuComponent;
import com.intland.codebeamer.integration.classic.page.tracker.config.fields.base.FieldConfigDialog;

public class TrackerConfigFieldsTab extends AbstractTrackerConfigTab<TrackerConfigFieldsTab, TrackerConfigFieldsAssertions> {

	private static final String NEW_CUSTOM_FIELD_DIALOG_TITLE = "New custom field";

	private static final String FIELD_DIALOG_TITLE_SELECTOR = "div.ui-dialog:has(span.ui-dialog-title:has-text('%s'))";

	private static final String FIELD_CONFIG_LINK_SELECTOR = "tr:has(td.property:has-text('%s')) span.fieldLabel,tr td.textData span.fieldLabel:has-text('%s')";

	private static final String FIELD_MORE_MENU_SELECTOR = "tr:has(td.property:has-text('%s')) td.layoutAndContent img.menuArrowDown,tr:has(td.textData span.fieldLabel:has-text('%s')) td.layoutAndContent img.menuArrowDown";

	public TrackerConfigFieldsTab(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#tracker-customize-field-properties");
	}

	@Override
	public TrackerConfigFieldsAssertions assertThat() {
		return new TrackerConfigFieldsAssertions(this);
	}

	/**
	 * Opens the config edit dialog of an existing field.
	 *
	 * @param fieldPropertyName it could be either the name of the field or the property name of the field. For built-in fields,
	 *                          please us the property name, as it is language independent.
	 * @return field config dialog object
	 */
	public FieldConfigDialog editFieldConfig(String fieldPropertyName) {
		return editFieldConfigDialog(fieldPropertyName);
	}

	/**
	 * Opens a new custom field dialog.
	 *
	 * <p>
	 *     If you want to change the type, then you should either call {@link FieldConfigDialog#setTypeSelector(FieldType)}
	 * </p>
	 * <pre>{@code
	 * .trackerConfigFieldsTab(c -> c.newCustomField()
	 * 						.setTypeSelector(FieldType.COLOR)
	 * 						.setLabel("New color field")
	 * 						.setListable(true)
	 * 						.clickOk()
	 * }</pre>
	 *
	 * <p>
	 *     or use any of the dedicated methods in {@link TrackerConfigFieldsTab} e.g. {@link TrackerConfigFieldsTab#newLanguageField()}
	 * </p>
	 * <pre>{@code
     * 	.trackerConfigFieldsTab(c -> c.newLanguageField()
     *				.setLabel("Lang field")
     *				.clickOk())
	 * }</pre>
	 *
	 * @return field config dialog object
	 */
	public FieldConfigDialog newCustomField() {
		return newTextField();
	}

	public FieldConfigDialog newTextField() {
		return openCustomFieldDialog();
	}

	public FieldConfigDialog newIntegerField() {
		return openCustomFieldDialog().setTypeSelector(FieldType.INTEGER);
	}

	public FieldConfigDialog newDecimalField() {
		return openCustomFieldDialog().setTypeSelector(FieldType.DECIMAL);
	}

	public FieldConfigDialog newDateField() {
		return openCustomFieldDialog().setTypeSelector(FieldType.DATE);
	}

	public FieldConfigDialog newColorField() {
		return openCustomFieldDialog().setTypeSelector(FieldType.COLOR);
	}

	public FieldConfigDialog newDurationField() {
		return openCustomFieldDialog().setTypeSelector(FieldType.DURATION);
	}

	public FieldConfigDialog newBooleanField() {
		return openCustomFieldDialog().setTypeSelector(FieldType.BOOL);
	}

	public FieldConfigDialog newLanguageField() {
		return openCustomFieldDialog().setTypeSelector(FieldType.LANGUAGE);
	}

	public FieldConfigDialog newCountryField() {
		return openCustomFieldDialog().setTypeSelector(FieldType.COUNTRY);
	}

	public FieldConfigDialog newWikiTextField() {
		return openCustomFieldDialog().setTypeSelector(FieldType.WIKITEXT);
	}

	public FieldConfigDialog newWikiLinkField() {
		return openCustomFieldDialog().setTypeSelector(FieldType.WIKILINK);
	}

	public FieldConfigDialog newChoiceField() {
		getMoreFieldsSelector().click();
		getMoreFieldsSelector().selectOption("choiceField");
		return newFieldConfigDialog("New choice field");
	}

	public FieldConfigDialog newTableField() {
		getMoreFieldsSelector().click();
		getMoreFieldsSelector().selectOption("table");
		return newFieldConfigDialog("New table");
	}

	public FieldConfigDialog addColumnToTableField(String tableFieldPropertyName) {
		getMoreButtonOfFieldElement(tableFieldPropertyName).click();
		getNewColumnButton().click();
		return newFieldConfigDialog("New column");
	}

	public CodebeamerLocator getFieldEditLink(String fieldPropertyName) {
		return this.locator(FIELD_CONFIG_LINK_SELECTOR.formatted(fieldPropertyName, fieldPropertyName));
	}

	public CodebeamerLocator getMoreButtonOfFieldElement(String fieldPropertyName) {
		return this.locator(FIELD_MORE_MENU_SELECTOR.formatted(fieldPropertyName, fieldPropertyName));
	}

	public CodebeamerLocator getNewColumnButton() {
		return new FieldOverlayMenuComponent(getCodebeamerPage()).getNewColumnButton();
	}

	public CodebeamerLocator getMoreFieldsSelector() {
		return this.locator("select.more-fields-selector");
	}

	private FieldConfigDialog openCustomFieldDialog() {
		getMoreFieldsSelector().click();
		getMoreFieldsSelector().selectOption("customField");

		return getFieldConfigDialog(NEW_CUSTOM_FIELD_DIALOG_TITLE);
	}

	private FieldConfigDialog editFieldConfigDialog(String selector) {
		getFieldEditLink(selector).click();
		return getFieldConfigDialog(selector);
	}

	private FieldConfigDialog newFieldConfigDialog(String selector) {
		return getFieldConfigDialog(selector);
	}

	private FieldConfigDialog getFieldConfigDialog(String selector) {
		return new FieldConfigDialog(getCodebeamerPage(), FIELD_DIALOG_TITLE_SELECTOR.formatted(selector));
	}

	@Override
	protected String getTabId() {
		return "#tracker-customize-field-properties-tab";
	}

	@Override
	protected String getMainFormId() {
		return "#trackerLayoutForm";
	}
}
