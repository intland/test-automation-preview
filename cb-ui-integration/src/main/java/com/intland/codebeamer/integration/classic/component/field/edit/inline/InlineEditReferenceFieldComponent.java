package com.intland.codebeamer.integration.classic.component.field.edit.inline;

import java.util.Arrays;

import org.testng.Assert;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.field.edit.common.AbstractEditReferenceFieldComponent;
import com.microsoft.playwright.Locator;

public class InlineEditReferenceFieldComponent extends
		AbstractEditReferenceFieldComponent<InlineEditReferenceFieldComponent, InlineEditReferenceFieldAssertions> {

	public InlineEditReferenceFieldComponent(CodebeamerPage codebeamerPage, String fieldLocator, String frameLocator) {
		super(codebeamerPage, frameLocator, fieldLocator);
	}

	public InlineEditReferenceFieldComponent select(String... referenceName) {
		waitForReferenceInlineEditStart();

		Arrays.stream(referenceName).forEach(this::selectReference);

		stopInlineEdit();
		return this;
	}

	public InlineEditReferenceFieldComponent remove(String... referenceName) {
		waitForReferenceInlineEditStart();

		Arrays.stream(referenceName).forEach(this::removeReference);

		stopInlineEdit();
		return this;
	}

	public InlineEditReferenceFieldComponent removeAll() {
		waitForReferenceInlineEditStart();

		getSelectedReferences().forEach(this::removeReference);

		stopInlineEdit();
		return this;
	}

	public InlineEditReferenceFieldComponent select(String referenceName, int position) {
		waitForReferenceInlineEditStart();

		int selectedItemsCount = getSelectedValues().count();
		Assert.assertTrue((selectedItemsCount >= position), "Selected position should be lower than the list length");

		getValueField()
				.pressControlLeft((selectedItemsCount - position) * 2)
				.type(referenceName);

		this.editReferenceDialogComponent.select(referenceName);

		stopInlineEdit();
		return this;
	}

	@Override
	public InlineEditReferenceFieldAssertions assertThat() {
		return new InlineEditReferenceFieldAssertions(this);
	}

	@Override
	protected void stopInlineEdit() {
		getLocator().click(new Locator.ClickOptions().setPosition(2, 2).setDelay(500));
		waitForReferenceInlineEditEnd();
	}

	private void selectReference(String referenceName) {
		getValueField().type(referenceName);

		this.editReferenceDialogComponent.select(referenceName);

		getElement(referenceName).waitForAttached();
	}

	private void removeReference(String referenceName) {
		CodebeamerLocator element = getElement(referenceName);
		element.waitForAttached();

		getRemoveElement(referenceName).click();

		element.waitForDetached();
	}
}



