package com.intland.codebeamer.integration.classic.component;

import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

import java.util.regex.Pattern;

public class InterwikiLinkAssertions extends AbstractCodebeamerComponentAssert<InterwikiLinkComponent, InterwikiLinkAssertions> {

	protected InterwikiLinkAssertions(InterwikiLinkComponent component) {
		super(component);
	}

	public InterwikiLinkAssertions targetsItem(TrackerItemId taskId) {
		return assertAll("Link should target tracker item: %d".formatted(taskId.id()), () ->
				assertThat(getComponent().getLink()).hasAttribute("href", Pattern.compile("/(issue|item)/%d".formatted(taskId.id())))
		);
	}

}
