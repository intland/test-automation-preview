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

package com.intland.codebeamer.integration.testcase.classic.uploadmodule;

import java.nio.file.Path;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserPhoto;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.CommentsAndAttachmentsTabAssertions;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.annotation.TestCase;

@Test(groups = "UploadModuleTestCases")
public class UploadModuleTestCases extends AbstractIntegrationClassicNGTests {

    private User activeUser;

    private UserApiService userApiService;

    private ProjectApiService projectApiService;

    private Project project;

    private Tracker tracker;

    private TrackerItemId trackerItemId;

    @Override
    protected void generateDataBeforeClass() throws Exception {
        userApiService = getDataManagerService().getUserApiServiceWithConfigUser();
        activeUser = userApiService
                .createUser()
                .addToRegularUserGroup()
                .build();
        projectApiService = getDataManagerService().getProjectApiService(activeUser);
        project = projectApiService
                .createProjectFromTemplate(getRandomText("My project"));
        tracker = getDataManagerService().getTrackerApiService(activeUser)
                .createDefaultTaskTracker(project, "Task tracker");
        trackerItemId = getDataManagerService().getTrackerItemApiService(activeUser)
                .createTrackerItem(project, "Task tracker", trackerItem ->
                        trackerItem.name("Test task tracker item")
                                .description("Test task tracker item description"));
    }

    @Override
    protected void cleanUpDataAfterClass() throws Exception {
        projectApiService.deleteProject(project);
    }

    @TestCase(link = "https://codebeamer.com/cb/item/13248727")
    @Test(description = "Upload multiple empty files as documents")
    public void uploadMultipleEmptyFilesAsDocuments() {
        //Given
        Path[] resources = {
                getEmptyFilePath(),
                getEmptyFilePath()
        };

        //When
        getClassicCodebeamerApplication(activeUser)
                .visitProjectDocumentsPage(project)
                .getDocumentListComponent()
                .addNewFile()
                .uploadDocumentComponent(c ->
                        c.uploadFile(resources)
                )
                //Then
                .overlayMessageBoxComponent(c -> c.assertThat().hasError());
    }

    @TestCase(link = "https://codebeamer.com/cb/item/13264778")
    @Test(description = "Upload an attachment to tracker item comment")
    public void uploadAnAttachmentToTrackerItemComment() {
        getClassicCodebeamerApplication(activeUser)
                .visitTrackerItemPage(tracker, trackerItemId)
                .commentsAndAttachmentsTabAssertion(CommentsAndAttachmentsTabAssertions::hasNoComment)
                .commentsAndAttachmentsTabComponent(tab -> tab
                        .addCommentsAndAttachments(uploadFile -> uploadFile
                                .addAttachment(getFilePath("150KiB.txt"))
                                .save()
                                .commentsAndAttachmentsTabComponent()
                                .assertComment(1, ca -> ca
                                        .isCreatedBy(activeUser)
                                        .hasAttachment("150KiB.txt"))));
    }

    @TestCase(link = "https://codebeamer.com/cb/item/13248944")
    @Test(description = "Upload a file during tracker item import and cancel the process")
    public void uploadAFileDuringTrackerItemImportAndCancelTheProcess() {

        getClassicCodebeamerApplication(activeUser)
                .visitTrackerItemImportPage(tracker)
                .importFormComponent(c -> c.addFile(getSampleCsv())
                        .cancelUpload().assertThat().removeUploadLinkElementNotVisible());
    }

	@TestCase(link = "https://codebeamer.com/cb/item/13248735")
	@Test(description = "Upload a file as document and cancel the process")
	public void uploadAnEmptyFileDuringTrackerItemImport() {
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerItemImportPage(tracker)
				.importFormComponent(f -> f
						.addFile(getEmptyFilePath()))
				.assertPage(page -> page
						.overlayMessageBoxComponent(c -> c
								.assertThat()
								.hasError()));
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13264895")
	@Test(description = "User is able to upload and delete the profile photo")
	public void uploadAProfilePhoto() {
		UserPhoto userPhoto = getClassicCodebeamerApplication(activeUser)
				.visitProfilePhotoPage()
				.getProfilePhotoSrcAttribute();

		getClassicCodebeamerApplication(activeUser)
				.visitProfilePhotoPage()
				.getProfilePhotoComponent()
				.uploadProfilePhoto(getFilePath("dummyProfilePhoto.jpg"))
				.redirectedToMyUserAccountPage()
				.getUserAccountActionBarComponent()
				.visitEditProfilePhotoPage()
				.assertProfilePhotoComponent(assertUpload -> assertUpload.hasProfilePhoto(userPhoto))
				.getProfilePhotoComponent()
				.delete()
				.redirectedToMyUserAccountPage()
				.getUserAccountActionBarComponent()
				.visitEditProfilePhotoPage()
				.assertProfilePhotoComponent(assertDelete -> assertDelete.hasNoProfilePhoto(userPhoto));
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13248967")
	@Test(description = "User is able to get a warning when uploaded an invalid file")
	public void uploadANonPictureIntoUserPhoto() {
		getClassicCodebeamerApplication(activeUser)
				.visitProfilePhotoPage()
				.getProfilePhotoComponent()
				.uploadProfilePhoto(getFilePath("testFile.txt"))
				.redirectedToProfilePhotoPage()
				.assertProfilePhotoComponent(assertFile -> assertFile.hasFileUploadError("Failed to process the uploaded image. Please check whether it is a valid JPEG, GIF or PNG image file."));
	}
}
