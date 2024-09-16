package com.intland.codebeamer.integration.classic.page.tracker.view.document.rightpane.properties;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.common.tracker.rightpane.AbstractRightPaneTab;
import com.intland.codebeamer.integration.common.tracker.rightpane.ItemAssociationsSection;
import com.intland.codebeamer.integration.common.tracker.rightpane.ItemCommentsSection;
import com.intland.codebeamer.integration.common.tracker.rightpane.ItemDetailsSection;
import com.intland.codebeamer.integration.common.tracker.rightpane.ItemReferencesSection;
import com.intland.codebeamer.integration.sitemap.annotation.Component;

public class PropertiesTabComponent
		extends AbstractRightPaneTab<PropertiesTabComponent, PropertiesTabAssertions> {

	@Component("Details section")
	private final ItemDetailsSection itemDetailsSection;

	@Component("References section")
	private final ItemReferencesSection itemReferencesSection;

	@Component("Associations section")
	private final ItemAssociationsSection itemAssociationsSection;

	@Component("Comments section")
	private final ItemCommentsSection itemCommentsSection;

	public PropertiesTabComponent(CodebeamerPage codebeamerPage, String parentSelector) {
		super(codebeamerPage, parentSelector, parentSelector + " #properties-tab");
		this.itemDetailsSection = new ItemDetailsSection(codebeamerPage, getSelector());
		this.itemReferencesSection = new ItemReferencesSection(codebeamerPage, getSelector());
		this.itemAssociationsSection = new ItemAssociationsSection(codebeamerPage, getSelector());
		this.itemCommentsSection = new ItemCommentsSection(codebeamerPage, getSelector());
	}

	public PropertiesTabComponent detailsSection(Consumer<ItemDetailsSection> detailsSectionConsumer) {
		detailsSectionConsumer.accept(itemDetailsSection);
		return this;
	}

	public PropertiesTabComponent referencesSection(Consumer<ItemReferencesSection> referencesSectionConsumer) {
		referencesSectionConsumer.accept(itemReferencesSection);
		return this;
	}

	public PropertiesTabComponent associationsSection(Consumer<ItemAssociationsSection> associationsSectionConsumer) {
		associationsSectionConsumer.accept(itemAssociationsSection);
		return this;
	}

	public PropertiesTabComponent commentsSection(Consumer<ItemCommentsSection> commentsSectionConsumer) {
		commentsSectionConsumer.accept(itemCommentsSection);
		return this;
	}

	@Override
	public PropertiesTabAssertions assertThat() {
		return new PropertiesTabAssertions(this);
	}

	@Override
	protected String getTabClass() {
		return "properties";
	}
}
