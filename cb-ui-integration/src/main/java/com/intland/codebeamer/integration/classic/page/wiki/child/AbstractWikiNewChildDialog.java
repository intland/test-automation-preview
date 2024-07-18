package com.intland.codebeamer.integration.classic.page.wiki.child;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.wiki.child.component.AbstractWikiNewChildDialogFormAssertions;
import com.intland.codebeamer.integration.classic.page.wiki.child.component.AbstractWikiNewChildDialogFormComponent;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerDialog;

public abstract class AbstractWikiNewChildDialog<C extends AbstractWikiNewChildDialogFormComponent, A extends AbstractWikiNewChildDialogFormAssertions>
		extends AbstractCodebeamerDialog {

	public AbstractWikiNewChildDialog(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "#inlinedPopupIframe[src*='createWikiPage']");
	}

	protected abstract AbstractWikiNewChildDialog wikiNewChildDialogFormComponent(
			Consumer<C> formConsumer);

	protected abstract AbstractWikiNewChildDialog assertWikiNewChildDialogFormComponent(Consumer<A> assertion);

	protected abstract AbstractWikiNewChildDialogNavigation save();

}
