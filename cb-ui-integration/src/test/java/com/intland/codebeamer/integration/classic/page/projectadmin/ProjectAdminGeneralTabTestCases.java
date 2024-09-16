package com.intland.codebeamer.integration.classic.page.projectadmin;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.role.Role;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;

@Test(groups = "ProjectAdminGeneralTabTestCases")
public class ProjectAdminGeneralTabTestCases extends AbstractIntegrationClassicNGTests {

	private Project project;

	private User activeUser;

	private Role developer;

	@Override
	protected void generateDataBeforeClass() throws Exception {

		activeUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.addToProjectCategoryAdminGroup()
				.build();
		project = getDataManagerService().getProjectApiService(activeUser).createProjectFromTemplate();

		developer = getDataManagerService().getRoleApiService(activeUser).findRoleByName("Developer");

		switchToClassicUI(activeUser);
	}

	@Test(description = "Validating tab focus on project admin page")
	public void validateTabIsActive() {
		getClassicCodebeamerApplication(activeUser)
				.visitProjectAdminPage(project)
				.changeToProjectAdminGeneralTab()
				.projectAdminGeneralTab(formComponent -> formComponent.assertThat().isTabActive());
	}

	@Test(description = "Test case for upload image on project admin page")
	public void uploadImageOnProjectAdminGeneralTabPage() {
		getClassicCodebeamerApplication(activeUser)
				.visitProjectAdminPage(project)
				.changeToProjectAdminGeneralTab()
				.projectAdminGeneralTab(formComponent -> formComponent.uploadFile(getFilePath("projectIcon.png"))
						.assignNewMemberToRole(developer)
						.save())
				.overlayMessageBoxComponent(c -> c.assertThat().hasSuccess());
	}

	@Test(description = "Test case for upload empty file on project admin page")
	public void uploadEmptyFileOnProjectAdminGeneralTabPage() {
		getClassicCodebeamerApplication(activeUser)
				.visitProjectAdminPage(project)
				.changeToProjectAdminGeneralTab()
				.projectAdminGeneralTab(formComponent -> formComponent.uploadFile(getFilePath("empty.txt")))
				.overlayMessageBoxComponent(c -> c.assertThat().hasError());
	}

	@Test(description = "Test case for project admin page cancel feature")
	public void cancelProjectAdminGeneralTabPage() {
		getClassicCodebeamerApplication(activeUser)
				.visitProjectAdminPage(project)
				.changeToProjectAdminGeneralTab().projectAdminGeneralTab(formComponent -> formComponent.cancel()
						.redirectedToWikiPage());
	}

	@Test(description = "Validating fields on project admin page")
	public void validateFieldsOnProjectAdminGeneralTabPage() {
		getClassicCodebeamerApplication(activeUser)
				.visitProjectAdminPage(project)
				.changeToProjectAdminGeneralTab()
				.projectAdminGeneralTab(formComponent -> formComponent.assertThat().isFormFieldsReady());
	}

	@Test(description = "Validating checkbox fields on project admin page")
	public void validateCheckboxesOnProjectAdminGeneralTabPage() {
		getClassicCodebeamerApplication(activeUser)
				.visitProjectAdminPage(project)
				.changeToProjectAdminGeneralTab()
				.projectAdminGeneralTab(formComponent -> formComponent.assertThat().isCheckboxFieldsReady());
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		getDataManagerService().getProjectApiService(activeUser).deleteProject(project);
		getDataManagerService().getUserApiServiceWithConfigUser().disableUser(activeUser);
	}
}