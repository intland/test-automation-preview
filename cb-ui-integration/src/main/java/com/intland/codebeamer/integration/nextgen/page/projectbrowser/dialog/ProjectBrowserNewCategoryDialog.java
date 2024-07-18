package com.intland.codebeamer.integration.nextgen.page.projectbrowser.dialog;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.nextgen.page.projectbrowser.dialog.component.ProjectBrowserNewCategoryFormComponent;
import com.intland.codebeamer.integration.ui.nextgen.component.ToastAssertions;
import com.intland.codebeamer.integration.ui.nextgen.component.ToastComponent;
import com.intland.codebeamer.integration.ui.nextgen.dialog.AbstractCodebeamerNextGenDialog;

public class ProjectBrowserNewCategoryDialog extends AbstractCodebeamerNextGenDialog {

	private ProjectBrowserNewCategoryFormComponent projectBrowserNewCategoryFormComponent;
	
	private ToastComponent toastComponent;
	
	public ProjectBrowserNewCategoryDialog(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "p-dialog[data-cy='ws-category-dialog']", "ws-category-dialog-save-btn", "ws-category-dialog-cancel-btn");
		this.projectBrowserNewCategoryFormComponent = new ProjectBrowserNewCategoryFormComponent(getCodebeamerPage(), this.getDialogLocator());
		this.toastComponent = new ToastComponent(getCodebeamerPage());
	}

	public ProjectBrowserNewCategoryDialog projectBrowserNewCategoryFormComponent(Consumer<ProjectBrowserNewCategoryFormComponent> formConsumer) {
		formConsumer.accept(this.projectBrowserNewCategoryFormComponent);
		return this;
	}
	
	public ProjectBrowserNewCategoryDialog assertToastComponent(Consumer<ToastAssertions> assertion) {
		assertion.accept(toastComponent.assertThat());
		return this;
	}
	
	public ProjectBrowserNewCategoryDialogNavigation save() {
		this.getSaveButton().click();
		return new ProjectBrowserNewCategoryDialogNavigation(getCodebeamerPage());
	}
	
}
