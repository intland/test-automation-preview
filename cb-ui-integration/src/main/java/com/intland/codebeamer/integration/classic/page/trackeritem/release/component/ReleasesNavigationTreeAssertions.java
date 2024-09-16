package com.intland.codebeamer.integration.classic.page.trackeritem.release.component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponentAssert;

public class ReleasesNavigationTreeAssertions extends AbstractCodebeamerComponentAssert<ReleasesNavigationTreeComponent, ReleasesNavigationTreeAssertions> {

	private static final String STYLE_ATTR = "style";
	private static final String MARGIN_LEFT_STYLE = "margin-left: %dem;";
	private static final String DATA_TARGET_VERSION_ID = "data-target-version-id";

	protected ReleasesNavigationTreeAssertions(ReleasesNavigationTreeComponent component) {
		super(component);
	}

	public ReleasesNavigationTreeAssertions checkTreeStructure(LinkedHashMap<Integer, Integer> treeStructure) {
		return assertAll("Tree structures should match", () -> {
			int index = 0;
			List<CodebeamerLocator> treeNodes = getComponent().getTreeItems();
			for (Map.Entry<Integer, Integer> entry : treeStructure.entrySet()) {
				CodebeamerLocator treeNode = treeNodes.get(index);
				String expectedStyle = MARGIN_LEFT_STYLE.formatted(entry.getValue());
				try {
					assertThat(treeNode).hasAttribute(DATA_TARGET_VERSION_ID, String.valueOf(entry.getKey()));
					assertThat(treeNode).hasAttribute(STYLE_ATTR, expectedStyle);
				} catch (AssertionError e) {
					throw new AssertionError("Tree elements differ at index %d".formatted(index), e);
				}
				index++;
			}
		});
	}
}
