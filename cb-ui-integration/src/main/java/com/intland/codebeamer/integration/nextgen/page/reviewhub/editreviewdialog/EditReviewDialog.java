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

package com.intland.codebeamer.integration.nextgen.page.reviewhub.editreviewdialog;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.reviewhub.model.Review;
import com.intland.codebeamer.integration.nextgen.page.reviewhub.editreviewdialog.component.EditReviewFormComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.nextgen.dialog.AbstractCodebeamerNextGenDialog;

public class EditReviewDialog extends AbstractCodebeamerNextGenDialog {

	@Component("Edit review component")
	private EditReviewFormComponent editReviewFormComponent;

	public EditReviewDialog(CodebeamerPage codebeamerPage) {
		super(codebeamerPage, "p-dialog[data-cy='edit-review-dialog'] ", "review-save-edit", "review-cancel-edit");
		this.editReviewFormComponent = new EditReviewFormComponent(getCodebeamerPage(), this.getDialogLocator());
	}

	public EditReviewDialog editReviewDialogFormComponent(Consumer<EditReviewFormComponent> formConsumer) {
		formConsumer.accept(this.editReviewFormComponent);
		return this;
	}

	public EditReviewDialogNavigation save(Review review) {
		this.getSaveButton().click();
		return new EditReviewDialogNavigation(getCodebeamerPage(), review);
	}
}
