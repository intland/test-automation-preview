package com.intland.codebeamer.integration.classic.page.trackeritem.component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.user.UserId;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class TrackerItemHistoryComponent extends AbstractCodebeamerComponent<TrackerItemHistoryComponent, TrackerItemHistoryAssertions> {

	private static final String USER_PATTERN = "\\[USER:(\\d+)\\]";

	public TrackerItemHistoryComponent(final CodebeamerPage codebeamerPage, final String selector) {
		super(codebeamerPage, selector);
	}

	public UserId getSubmitter() {
		// UI-AUTOMATION: add an extra selector for submitter
		String userWikiLink = locator(".submittedByColumn td:nth-child(2) a").getAttribute("data-wikilink");
		Pattern pattern = Pattern.compile(USER_PATTERN);
		Matcher matcher = pattern.matcher(userWikiLink);

		if (!matcher.find()) {
			throw new IllegalStateException("There should be a submitter in a history row");
		}
		return new UserId(Integer.valueOf(matcher.group(1)));
	}

	public CodebeamerLocator getAction() {
		return locator(".actionDescriptionColumn");
	}

	public CodebeamerLocator getVersion() {
		return locator(".versionColumn a");
	}

	public CodebeamerLocator getVersionIndicatorText() {
		return locator(".version-indicator-content .version-label-wrapper");
	}

	public Map<String, FieldChange> getFieldChanges() {
		Map<String, FieldChange> fieldChanges = new HashMap<>();
		getFieldChangesLocator()
				.all()
				.stream()
				.forEach(tr -> {
					String fieldName = tr.concat(".fieldNameColumn").text().trim();
					CodebeamerLocator newValue = tr.concat(".newValueColumn");
					CodebeamerLocator oldValue = tr.concat(".oldValueColumn");
					fieldChanges.put(fieldName, new FieldChange(oldValue, newValue));
				});
		return fieldChanges;
	}

	public CodebeamerLocator getFieldChangesLocator() {
		return locator(".fieldChangesColumn > table.itemChangesTable > tbody > tr");
	}

	@Override
	public TrackerItemHistoryAssertions assertThat() {
		return new TrackerItemHistoryAssertions(this);
	}

}