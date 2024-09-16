/*
 * Copyright by Intland Software
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Intland Software. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Intland.
 */

package com.intland.codebeamer.integration.classic.component.field.edit.common;

import java.nio.file.Path;
import java.util.function.Function;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.builder.wiki.WikiMarkupBuilder;
import com.intland.codebeamer.integration.classic.component.FroalaComponent;
import com.intland.codebeamer.integration.classic.component.FroalaComponent.Type;
import com.intland.codebeamer.integration.classic.component.field.edit.AbstractEditFieldComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Component;

public abstract class AbstractEditWikiTextFieldComponent
		<C extends AbstractEditWikiTextFieldComponent<C, A>, A extends AbstractEditWikiTextFieldAssertions<C, A>>
		extends AbstractEditFieldComponent<C, A> {

	@Component(value = "Wikitext edit", includeInSitemap = false)
	protected FroalaComponent froala;

	public AbstractEditWikiTextFieldComponent(CodebeamerPage codebeamerPage, String frameLocator, String componentLocator) {
		super(codebeamerPage, frameLocator, componentLocator);
	}

	public C fill(String value) {
		this.froala.fill(value, Type.MARKUP);
		return (C) this;
	}

	public C fill(Function<WikiMarkupBuilder, WikiMarkupBuilder> markupBuilder) {
		String markup = markupBuilder.apply(new WikiMarkupBuilder()).build();
		return fill(markup);
	}

	public C fillAndSave(String value) {
		this.fill(value);
		this.froala.save();
		return (C) this;
	}

	public C fill(String value, Type type) {
		this.froala.fill(value, type);
		return (C) this;
	}

	public C save() {
		this.froala.save();
		return (C) this;
	}

	public C setComment(String text) {
		this.froala.fill(text);
		return (C) this;
	}

	public C addAttachment(Path attachment) {
		this.froala.addAttachment(attachment);
		return (C) this;
	}

	public C pasteFromClipboard() {
		this.froala.pasteFromClipboard();
		return (C) this;
	}

	protected FroalaComponent getFroala() {
		return froala;
	}
}
