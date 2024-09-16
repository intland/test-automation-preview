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

package com.intland.codebeamer.integration.classic.page.documents;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = "ProjectDocumentsPage")
public class ProjectDocumentsPageTest extends AbstractIntegrationClassicNGTests {

	@Test(description = "User is able to upload a project document")
	public void testUploadDocumentToAProject() {
		// GIVEN
		UserApiService userService = new UserApiService(getApplicationConfiguration());

		User activeUser = userService.createUser()
				.addToRegularUserGroup()
				.build();

		ProjectApiService projectApiService = new ProjectApiService(getApplicationConfiguration(), activeUser.getName());
		Project project = projectApiService.createProjectFromTemplate();

		// WHEN THEN
		try {
			getClassicCodebeamerApplication(activeUser)
					.visitProjectDocumentsPage(project)
					.getDocumentListComponent()
					.addNewFile()
					.uploadDocumentComponent(c -> c.uploadFile(getFilePath("documents_upload.csv"))
							.save());
		} finally {
			projectApiService.deleteProject(project);
		}
	}

}