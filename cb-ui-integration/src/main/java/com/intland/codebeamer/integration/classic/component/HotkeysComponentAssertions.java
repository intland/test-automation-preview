package com.intland.codebeamer.integration.classic.component;

import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.testng.Assert;

import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class HotkeysComponentAssertions extends AbstractCodebeamerComponentAssert<HotkeysComponent, HotkeysComponentAssertions> {

	HotkeysComponentAssertions(HotkeysComponent component) {
		super(component);
	}

	public HotkeysComponentAssertions hasCopyWorkItemHint() {
		return hasHint("Alt + W", "Copy Work Item Link");
	}

	public HotkeysComponentAssertions hasNoCopyWorkItemHint() {
		return hasNoHint("Alt + W");
	}

	private HotkeysComponentAssertions hasNoHint(String key) {
		return assertAll("Should not have an entry for '%s'".formatted(key), () -> {
			List<HotkeysComponent.HotKeyHint> hotkeys = getComponent().getHotkeys();

			Assert.assertFalse(hotkeys.stream().anyMatch(h -> h.hotKey().equals(key)));
		});
	}

	private HotkeysComponentAssertions hasHint(String key, String action) {
		return assertAll("Should have an entry for '%s' with action: %s".formatted(key, action), () ->
				MatcherAssert.assertThat(getComponent().getHotkeys(), Matchers.hasItem(new HotkeysComponent.HotKeyHint(key, action)))
		);
	}
}
