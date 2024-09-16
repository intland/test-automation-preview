package com.intland.codebeamer.integration.common.tracker.rightpane;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.AbstractAccordionSection;

public class ItemCommentsSection extends AbstractAccordionSection<ItemCommentsSection, ItemCommentsAssertions> {

	public ItemCommentsSection(CodebeamerPage codebeamerPage, String parentSelector) {
		super(codebeamerPage, parentSelector + ".issue-comments-title");
	}

	@Override
	public ItemCommentsAssertions assertThat() {
		return new ItemCommentsAssertions(this);
	}
}
