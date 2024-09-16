package com.intland.codebeamer.integration.classic.component.field.edit.common;

import java.util.List;
import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerLocator;
import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.artifact.ArtifactType;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItem;
import com.intland.codebeamer.integration.classic.component.field.edit.AbstractEditFieldComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.common.dialog.EditReferenceDialogComponent;
import com.intland.codebeamer.integration.classic.component.field.edit.common.modal.EditReferenceModalComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Component;

public abstract class AbstractEditReferenceFieldComponent<C extends AbstractEditReferenceFieldComponent<C, A>, A extends AbstractEditReferenceFieldAssertions<C, A>>
		extends AbstractEditFieldComponent<C, A> {

	@Component(value = "Edit reference type dialog", includeInSitemap = false)
	protected EditReferenceDialogComponent editReferenceDialogComponent;

	@Component(value = "Edit reference type modal", details = "Opens a modal popup where user can filter references",
			includeInSitemap = false)
	protected EditReferenceModalComponent editReferenceModalComponent;

	public AbstractEditReferenceFieldComponent(CodebeamerPage codebeamerPage, String frameLocator, String componentLocator) {
		super(codebeamerPage, frameLocator, componentLocator);
		this.editReferenceDialogComponent = new EditReferenceDialogComponent(codebeamerPage);
		this.editReferenceModalComponent = new EditReferenceModalComponent(codebeamerPage);
	}

	public EditReferenceModalComponent openReferenceModal() {
		clickOnPencilIcon();
		return editReferenceModalComponent;
	}

	public C referenceModal(Consumer<EditReferenceModalComponent> modal) {
		modal.accept(editReferenceModalComponent);
		return (C) this;
	}

	private void clickOnPencilIcon() {
		getPencilIconElement().click();
	}

	public List<String> getSelectedReferences() {
		getValueContainerElement().waitForAttached();
		return getValueContainerElement().concat("li p.name").all().stream()
				.map(CodebeamerLocator::text)
				.toList();
	}

	public CodebeamerLocator getElement(String referenceName) {
		getValueContainerElement().waitForAttached();
		return getValueContainerElement().concat("li:has(p:text-is('%s'))".formatted(referenceName));
	}

	public CodebeamerLocator getRemoveElement(String referenceName) {
		return getElement(referenceName).concat("span.token-input-delete-token-facebook");
	}
	public CodebeamerLocator getValueContainerElement() {
		return this.locator(" ul.token-input-list-facebook");
	}

	public CodebeamerLocator getSelectedValues() {
		return getValueContainerElement().concat("li:has(p.name)");
	}

	public CodebeamerLocator getValueField() {
		return this.locator(" ul li input[type=text]");
	}

	public CodebeamerLocator getFieldReferenceHiddenInput() {
		return this.locator(" [id^='dynamicChoice_references']:not([id^='dynamicChoice_references_autocomplete_editor'])");
	}

	public CodebeamerLocator getPencilIconElement() {
		return this.locator(".popupButtonWrapper");
	}

	public String getFieldReferenceHiddenInputValue() {
		return this.getFieldReferenceHiddenInput().getAttribute("value");
	}

	protected String createTrackerItemAssertionRegex(List<TrackerItem> trackerItems) {
		StringBuilder assertString = new StringBuilder();
		for (int i = 0; i < trackerItems.size(); ++i) {
			assertString.append(ArtifactType.TRACKER_ITEM.getValue()).append("-").append(trackerItems.get(i).id().id());
			assertString.append((i < trackerItems.size() - 1) ? ".*," : ".*");
		}
		return assertString.toString();
	}
}



