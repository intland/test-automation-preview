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

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.sitemap.annotation.Page;

@Page("CreateReviewPage")
public class CreateReviewPage extends AbstractCreateReviewPage<CreateReviewPage> {

	public CreateReviewPage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
	}

	@Override
	protected String getPath() {
		return "/create";
	}
}
