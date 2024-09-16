package com.intland.codebeamer.integration.common.tracker.rightpane;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.AbstractAccordionSection;
import com.intland.codebeamer.integration.classic.component.WikiLinkAssertions;
import com.intland.codebeamer.integration.classic.component.WikiLinkComponent;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemFieldFormComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Component;

public class ItemDetailsSection extends AbstractAccordionSection<ItemDetailsSection, ItemDetailsAssertions> {

	@Component("Item wiki link")
	private final WikiLinkComponent wikiLinkComponent;

	@Component("Field form")
	private final TrackerItemFieldFormComponent properties;

	public ItemDetailsSection(CodebeamerPage codebeamerPage, String parentSelector) {
		super(codebeamerPage, parentSelector + " .issue-details-title");
		this.wikiLinkComponent = new WikiLinkComponent(codebeamerPage, getSelector(".viewPropertiesSummary"));
		this.properties = new TrackerItemFieldFormComponent(codebeamerPage);
	}

	public ItemDetailsSection fieldFormComponent(Consumer<TrackerItemFieldFormComponent> consumer) {
		consumer.accept(properties);
		return this;
	}

	public ItemDetailsSection assertWikiLink(Consumer<WikiLinkAssertions> assertion) {
		assertion.accept(wikiLinkComponent.assertThat());
		return this;
	}

	@Override
	public ItemDetailsAssertions assertThat() {
		return new ItemDetailsAssertions(this);
	}
}
