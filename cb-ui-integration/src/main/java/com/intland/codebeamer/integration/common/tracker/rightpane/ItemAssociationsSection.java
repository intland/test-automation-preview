package com.intland.codebeamer.integration.common.tracker.rightpane;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.AbstractAccordionSection;

public class ItemAssociationsSection extends AbstractAccordionSection<ItemAssociationsSection, ItemAssociationsAssertions> {

	public ItemAssociationsSection(CodebeamerPage codebeamerPage, String parentSelector) {
		super(codebeamerPage, parentSelector + " .issue-associations-title");
	}

	@Override
	public ItemAssociationsAssertions assertThat() {
		return new ItemAssociationsAssertions(this);
	}
}
