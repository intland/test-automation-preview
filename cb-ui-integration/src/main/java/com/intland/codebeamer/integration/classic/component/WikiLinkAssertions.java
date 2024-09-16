package com.intland.codebeamer.integration.classic.component;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItem;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

import java.util.regex.Pattern;

public class WikiLinkAssertions extends AbstractCodebeamerComponentAssert<WikiLinkComponent, WikiLinkAssertions> {

	protected WikiLinkAssertions(WikiLinkComponent component) {
		super(component);
	}

	public WikiLinkAssertions hasItemSummaryText(TrackerItem item) {
		CodebeamerLocator link = getComponent().getLinkLocator();
		return assertAll("Link text should be the summary of '%s'".formatted(item.name()), () ->
				assertThat(link).hasText(
						Pattern.compile("\\s*\\[TRACK\\w+-%d]\\u00A0%s\\s*".formatted(Integer.valueOf(item.id().id()), item.name())))
		);
	}
}
