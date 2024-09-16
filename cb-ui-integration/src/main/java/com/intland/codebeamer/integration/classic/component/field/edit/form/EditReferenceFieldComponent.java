package com.intland.codebeamer.integration.classic.component.field.edit.form;

import org.testng.Assert;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.component.field.edit.common.AbstractEditReferenceFieldComponent;

public class EditReferenceFieldComponent extends
		AbstractEditReferenceFieldComponent<EditReferenceFieldComponent, EditReferenceFieldAssertions> {

	public EditReferenceFieldComponent(CodebeamerPage codebeamerPage, String fieldLocator, String frameLocator) {
		super(codebeamerPage, frameLocator, fieldLocator);
	}

	public EditReferenceFieldComponent removeAll() {
		getValueField().click();
		getSelectedReferences().forEach(c -> getRemoveElement(c).click());
		return this;
	}
	
	public EditReferenceFieldComponent remove(String referenceName) {
		getValueField().click();
		getRemoveElement(referenceName).click();
		return this;
	}
	
	public EditReferenceFieldComponent select(String referenceName) {
		getValueField().click().type(referenceName);
		this.editReferenceDialogComponent.select(referenceName);
		return this;
	}

	public EditReferenceFieldComponent select(String referenceName, int position) {
		int selectedItemsCount = getSelectedValues().count();
		Assert.assertTrue((selectedItemsCount >= position), "Selected position should be lower than "
				+ "or equals to the current number of items");
		
		getValueField()
			.click()
			.pressControlLeft((selectedItemsCount - position) * 2)
			.type(referenceName);
		
		this.editReferenceDialogComponent.select(referenceName);
		return this;
	}

	@Override
	public CodebeamerLocator getTitleElement() {
		return this.locator("table");
	}

	@Override
	public EditReferenceFieldAssertions assertThat() {
		return new EditReferenceFieldAssertions(this);
	}
}



