package com.intland.codebeamer.integration.classic.page.wiki.component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.builder.wiki.WikiMarkupBuilder;
import com.intland.codebeamer.integration.classic.InterwikiLinkNavigation;
import com.intland.codebeamer.integration.classic.component.FroalaComponent;
import com.intland.codebeamer.integration.classic.component.InterwikiLinkComponent;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;

public class WikiContentComponent extends AbstractCodebeamerComponent<WikiContentComponent, WikiPageContentAssertions> {

	public WikiContentComponent(CodebeamerPage codebeamerPage, String parentSelector) {
		super(codebeamerPage, parentSelector);
	}

	public InterwikiLinkComponent getInterwikiLink(String linkText) {
		return new InterwikiLinkComponent(getCodebeamerPage(), getSelector(), linkText);
	}

	public InterwikiLinkNavigation clickOnInterwikiLink(String linkText) {
		return getInterwikiLink(linkText).click();
	}

	public CodebeamerLocator getSection(String sectionTitle) {
		String sectionId = "section-%s".formatted(
				URLEncoder.encode(StringUtils.capitalize(sectionTitle), StandardCharsets.UTF_8));
		return this.locator("div.sectionEditable:has(> [id*='%s'])".formatted(sectionId));
	}

	public WikiContentComponent inlineEditSection(String sectionTitle, String content) {
		this.startInlineEditingSection(sectionTitle);
		new FroalaComponent(getCodebeamerPage(), "")
				.fill(content, FroalaComponent.Type.RICH_TEXT)
				.getSaveButton().click();
		return this;
	}

	public WikiContentComponent inlineEditSection(String sectionTitle, Function<WikiMarkupBuilder, WikiMarkupBuilder> markupBuilder) {
		this.startInlineEditingSection(sectionTitle);
		new FroalaComponent(getCodebeamerPage(), "")
				.fill(markupBuilder.apply(new WikiMarkupBuilder()).build())
				.getSaveButton().click();
		return this;
	}

	public CodebeamerLocator getLocator() {
		return selfLocator();
	}

	@Override
	public WikiPageContentAssertions assertThat() {
		return new WikiPageContentAssertions(this);
	}

	private void startInlineEditingSection(String sectionTitle) {
		getSection(sectionTitle)
				.hover()
				.concat(".inplaceEditableIcon")
				.click();
	}
}
