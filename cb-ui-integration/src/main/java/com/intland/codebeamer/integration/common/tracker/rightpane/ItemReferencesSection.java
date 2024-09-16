package com.intland.codebeamer.integration.common.tracker.rightpane;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.AbstractAccordionSection;

public class ItemReferencesSection extends AbstractAccordionSection<ItemReferencesSection, ItemReferencesAssertions> {

	public ItemReferencesSection(CodebeamerPage codebeamerPage, String parentSelector) {
		super(codebeamerPage, parentSelector + " .issue-references-title");
	}

	@Override
	public ItemReferencesAssertions assertThat() {
		return new ItemReferencesAssertions(this);
	}
}
