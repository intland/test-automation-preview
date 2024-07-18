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

package com.intland.codebeamer.integration.testcase.classic.trackeritem.create;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.testdata.DataManagerService;

@Test(groups = "TrackerItemCommentTestCases")
public class TrackerItemCommentTestCases extends AbstractIntegrationClassicNGTests {
	
	private User projectAdmin;
	
	private Project project;

	private TrackerItemId trackerItemId;
	
	@Override
	protected void generateDataBeforeClass() throws Exception {
		DataManagerService services = getDataManagerService();
		projectAdmin = services.getUserApiService().createUser()
				.addToRegularUserGroup()
				.build();
				
		project = services.getProjectApiService().createProject(getRandomText("MyProject"))
					.addUserAs(projectAdmin, "Project Admin")
					.build();
		
		services.getTrackerApiService(projectAdmin).createDefaultTaskTracker(project, "MyTask");
	}
	
	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		getDataManagerService().getProjectApiService().deleteProject(project);
		getDataManagerService().getUserApiService().disableUser(projectAdmin);
	}
	
	@BeforeMethod(alwaysRun = true)
	protected void generateDataBeforeMethod() throws Exception {
		TrackerItemApiService trackerItemApiService = getDataManagerService().getTrackerItemApiService(projectAdmin);
		trackerItemId = trackerItemApiService.createTrackerItem(project, "MyTask", builder -> builder
				.name(getRandomText("MyItem"))
				.description("description"));
	}
	
	@Test(description = "User is able to create comment")
	public void userIsAbleToCreateComment() {
		getClassicCodebeamerApplication(projectAdmin)
			.visitTrackerItemPage(trackerItemId)
			.commentsAndAttachmentsTabComponent()
				.addCommentsAndAttachments()
				.setComment("My Comment")
				.save()
			.commentsAndAttachmentsTabAssertion(a -> a.hasNumberOfComment(1));
	}
	
	@Test(description = "User is able to upload attachments on the create tracker item page")
	public void userIsAbleToUploadAttachmentsOnCreateTrackerItemPage() {
		getClassicCodebeamerApplication(projectAdmin)
			.visitTrackerItemPage(trackerItemId)
			.commentsAndAttachmentsTabComponent(c -> c
					.addCommentsAndAttachments()
					.addAttachment(getFilePath("test-file.txt"))
					.addAttachment(getFilePath("test-file.tar.gz"))
					.addAttachment(getFilePath("test-file.zip"))
					.save())
			.commentsAndAttachmentsTabAssertion(a -> a.hasNumberOfComment(1));
	}
	
	@Test(description = "User is able to create a comment and upload attachments on the create tracker item page")
	public void userIsAbleToCreateCommentAndUploadAttachmentsOnCreateTrackerItemPage() {
		getClassicCodebeamerApplication(projectAdmin)
			.visitTrackerItemPage(trackerItemId)
			.commentsAndAttachmentsTabComponent(a -> a
				.addCommentsAndAttachments(n -> n
					.setComment("My Comment - A")
					.addAttachment(getFilePath("test-file.txt", "test-file-a.txt"))
					.addAttachment(getFilePath("test-file.tar.gz", "test-file-a.tar.gz"))
					.addAttachment(getFilePath("test-file.zip", "test-file-a.zip"))
					.save())
				.addCommentsAndAttachments(n -> n
					.setComment("My Comment - B")
					.addAttachment(getFilePath("test-file.txt", "test-file-b.txt"))
					.addAttachment(getFilePath("test-file.tar.gz", "test-file-b.tar.gz"))
					.addAttachment(getFilePath("test-file.zip", "test-file-b.zip"))
					.save()))
			.commentsAndAttachmentsTabAssertion(a -> a.hasNumberOfComment(2))
			.commentsAndAttachmentsTabComponent(a -> a.assertComment(1, ca -> ca
					.isCreatedBy(projectAdmin)
					.containsText("My Comment - B")
					.hasAttachments("test-file-b.txt", "test-file-b.tar.gz", "test-file-b.zip")))
			.commentsAndAttachmentsTabComponent(a -> a.assertComment(2, ca -> ca
					.isCreatedBy(projectAdmin)
					.containsText("My Comment - A")
					.hasNumberOfAttachments(3)
					.hasAttachment("test-file-a.txt")
					.hasAttachment("test-file-a.tar.gz")
					.hasAttachment("test-file-a.zip")))
			.commentsAndAttachmentsTabComponent()
			.getComment(2)
			.edit()
			.commentsAndAttachmentsComponent(c -> c
					.setComment("My Comment - A - Updated")
					.addAttachment(getFilePath("test-file.txt", "test-file-a-updated.txt")))
			.save()
			.redirectedToTrackerItemPage()
			.commentsAndAttachmentsTabComponent(a -> a.assertComment(2, ca -> ca
					.isCreatedBy(projectAdmin)
					.isUpdatedBy(projectAdmin)
					.hasText("My Comment - A - Updated")
					.hasNumberOfAttachments(4)
					.hasAttachment("test-file-a-updated.txt")
					.hasAttachment("test-file-a.txt")
					.hasAttachment("test-file-a.tar.gz")
					.hasAttachment("test-file-a.zip")));
	}
}
