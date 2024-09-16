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

package com.intland.codebeamer.integration.classic.page.reviewhub;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.nextgen.page.reviewhub.component.ReviewTemplateAssertions;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.annotation.TestCase;
import com.intland.codebeamer.integration.util.HttpStatus;

@Test(groups = "ReviewTemplateTestCases")
public class ReviewTemplateTestCases extends AbstractIntegrationClassicNGTests {

	private User regularUser;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		regularUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.build();
	}

	@TestCase(link = "reviewsTemplatePageHasAllElements", expectedHttpErrors = HttpStatus.FORBIDDEN)
	@Test(description = "Reviews template contains all elements")
	public void reviewsTemplatePageHasAllElements() {
		getClassicCodebeamerApplication(regularUser)
				.visitReviewTemplatePage()
				.assertReviewTemplatePage(ReviewTemplateAssertions::isReady);
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		if (regularUser != null) {
			getDataManagerService().getUserApiServiceWithConfigUser().disableUser(regularUser);
		}
	}
}
