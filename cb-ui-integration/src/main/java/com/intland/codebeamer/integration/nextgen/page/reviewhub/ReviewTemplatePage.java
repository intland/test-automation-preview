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

package com.intland.codebeamer.integration.nextgen.page.reviewhub;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.nextgen.page.reviewhub.component.ReviewTemplateAssertions;
import com.intland.codebeamer.integration.nextgen.page.reviewhub.component.ReviewTemplateComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;
import com.intland.codebeamer.integration.util.CBXPathUtil;

@Page("ReviewTemplatePage")
public class ReviewTemplatePage extends AbstractCodebeamerPage<ReviewTemplatePage> {

	private static final String REVIEW_TEMPLATE_LIST_PATH = CBXPathUtil.buildPath("reviewTemplateList");

	@Component("Review template list component")
	private ReviewTemplateComponent reviewTemplateComponent;

	public ReviewTemplatePage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
		this.reviewTemplateComponent = new ReviewTemplateComponent(getCodebeamerPage());
	}

	@Override
	public ReviewTemplatePage isActive() {
		assertUrl(REVIEW_TEMPLATE_LIST_PATH, "Review template page should be the active page");
		return this;
	}

	@Action("Visit")
	public ReviewTemplatePage visit() {
		navigate(REVIEW_TEMPLATE_LIST_PATH);
		return isActive();
	}

	public ReviewTemplateComponent getReviewTemplateComponent() {
		return reviewTemplateComponent;
	}

	public ReviewTemplatePage assertReviewTemplatePage(Consumer<ReviewTemplateAssertions> assertion) {
		assertion.accept(getReviewTemplateComponent().assertThat());
		return this;
	}
}
