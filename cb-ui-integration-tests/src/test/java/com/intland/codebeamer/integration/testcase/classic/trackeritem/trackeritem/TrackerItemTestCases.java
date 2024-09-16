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

package com.intland.codebeamer.integration.testcase.classic.trackeritem.trackeritem;

import java.util.List;

import org.testng.annotations.Test;

import com.intland.codebeamer.integration.api.builder.choice.ChoiceOption;
import com.intland.codebeamer.integration.api.service.baseline.Baseline;
import com.intland.codebeamer.integration.api.service.baseline.BaselineApiService;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.project.ProjectTemplate;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItem;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.trackeritem.template.TrackerItemTemplate;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.application.ClassicCodebeamerApplication;
import com.intland.codebeamer.integration.classic.component.HotkeysComponentAssertions;
import com.intland.codebeamer.integration.classic.page.tracker.config.component.AbstractTrackerConfigTab;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemActionBarAssertions;
import com.intland.codebeamer.integration.classic.component.actionmenubar.ActionMenuBarAssertions;
import com.intland.codebeamer.integration.classic.page.trackeritem.component.TrackerItemHistoryComponent;
import com.intland.codebeamer.integration.test.AbstractIntegrationClassicNGTests;
import com.intland.codebeamer.integration.test.annotation.TestCase;

public class TrackerItemTestCases extends AbstractIntegrationClassicNGTests {

	private User activeUser;

	private Project project;

	private Project agileProject;

	private ProjectApiService projectApiService;

	private TrackerApiService trackerApiService;

	private TrackerItemApiService trackerItemApiService;

	private BaselineApiService baselineApiService;

	@Override
	protected void generateDataBeforeClass() throws Exception {
		activeUser = getDataManagerService().getUserApiServiceWithConfigUser().createUser()
				.addToRegularUserGroup()
				.build();

		projectApiService = getDataManagerService().getProjectApiService(activeUser);
		trackerApiService = getDataManagerService().getTrackerApiService(activeUser);
		trackerItemApiService = getDataManagerService().getTrackerItemApiService(activeUser);
		baselineApiService = getDataManagerService().getBaselineApiService(activeUser);
		project = projectApiService.createProjectFromTemplate();
		agileProject = projectApiService.createProjectFromTemplate(getRandomText("Agile Project"), ProjectTemplate.AGILE_SCRUM);

		switchToClassicUI(activeUser);
	}

	@Override
	protected void cleanUpDataAfterClass() throws Exception {
		projectApiService.deleteProject(project);
		projectApiService.deleteProject(agileProject);
		getDataManagerService().getUserApiServiceWithConfigUser().disableUser(activeUser);
	}

	@TestCase(link = "https://codebeamer.com/cb/issue/13410455")
	@Test(description = "User is able to create Tracker Item Template from Active Status Area")
	public void createTrackerItemTemplateFromActiveStatusArea() {
		// GIVEN
		final String trackerName = getRandomText("Area");
		final String trackerItemName = "Default Area Tracker Item";
		final String trackerItemDescription = "Default Area Tracker Item Description";
		final String templateName = "Custom Tracker Item Template";
		Tracker tracker = trackerApiService.createDefaultAreaTracker(project, trackerName);
		TrackerItemId trackerItem = trackerItemApiService.createTrackerItem(project, trackerName, builder -> builder
				.name(trackerItemName)
				.status("Active")
				.description(trackerItemDescription));

		// WHEN
		ClassicCodebeamerApplication application = getClassicCodebeamerApplication(activeUser);

		application
				.visitTrackerItemPage(tracker, trackerItem)
				.actionBarComponent()
				.openMoreActionMenu()
				.selectSaveItemAsTemplate()
				.saveTrackerItemAsTemplateComponent(f -> f.templateName(templateName))
				.save();

		// THEN
		application
				.visitTrackerTableViewPage(tracker)
				.actionBarComponent()
				.openMoreActionMenu()
				.selectManageItemTemplates()
				.editTrackerItemTemplate(templateName)
				.redirectedToEditItemTemplatesDialog()
				.editItemTemplateComponent(f -> f.assertThat()
						.textField("Template Name", v -> v.is(templateName))
						.textAreaField("Template Description", v -> v.is(""))
						.referenceField("Parent", v -> v.isEmpty())
						.choiceField("Status", v -> v.is("Active"))
						.textField("Name", v -> v.is(trackerItemName))
						.wikiTextField("Description", v -> v.is(trackerItemDescription)));
	}

	@TestCase(link = "https://codebeamer.com/cb/issue/13410458")
	@Test(description = "User is able to create Tracker Item Template from Obsolete Status Area")
	public void createTrackerItemTemplateFromObsoleteStatusArea() {
		// GIVEN
		final String trackerName = getRandomText("Area");
		final String trackerItemName = "Default Area Tracker Item";
		final String trackerItemDescription = "Default Area Tracker Item Description";
		final String templateName = "Custom Tracker Item Template";
		Tracker tracker = trackerApiService.createDefaultAreaTracker(project, trackerName);
		TrackerItemId trackerItem = trackerItemApiService.createTrackerItem(project, trackerName, builder -> builder
				.name(trackerItemName)
				.status("Obsolete")
				.description(trackerItemDescription));

		// WHEN
		ClassicCodebeamerApplication application = getClassicCodebeamerApplication(activeUser);

		application
				.visitTrackerItemPage(tracker, trackerItem)
				.actionBarComponent()
				.openMoreActionMenu()
				.selectSaveItemAsTemplate()
				.saveTrackerItemAsTemplateComponent(f -> f.templateName(templateName))
				.save();

		// THEN
		application
				.visitTrackerTableViewPage(tracker)
				.actionBarComponent()
				.openMoreActionMenu()
				.selectManageItemTemplates()
				.editTrackerItemTemplate(templateName)
				.redirectedToEditItemTemplatesDialog()
				.editItemTemplateComponent(f -> f.assertThat()
						.textField("Template Name", v -> v.is(templateName))
						.textAreaField("Template Description", v -> v.is(""))
						.referenceField("Parent", v -> v.isEmpty())
						.choiceField("Status", v -> v.is("Obsolete"))
						.textField("Name", v -> v.is(trackerItemName))
						.wikiTextField("Description", v -> v.is(trackerItemDescription)));
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13350949")
	@Test(description = "User is able to copy an item comment link and paste it to a wiki page")
	public void copyPasteWorkItemLinkToWikiPage() {
		// GIVEN
		final String trackerName = getRandomText("Tasks");
		final String trackerItemName = "My task";
		final Tracker tracker = trackerApiService.createDefaultTaskTracker(project, trackerName);
		final TrackerItemId trackerItemId = trackerItemApiService.createTrackerItem(project, trackerName, builder -> builder
				.name(trackerItemName)
				.description("description"));
		trackerItemApiService.createTrackerItemComment(trackerItemId, builder -> builder
				.plainText()
				.comment(getRandomString()));

		ClassicCodebeamerApplication application = getClassicCodebeamerApplication(activeUser);

		int commentId = application
				.visitTrackerItemPage(tracker, trackerItemId)
				.commentsAndAttachmentsTabComponent(commentsTab -> commentsTab
						.getComment(1)
						.copyCommentLink())
				.overlayMessageBoxComponent(c -> c.assertThat().hasSuccess())
				.commentsAndAttachmentsTabComponent()
				.getComment(1)
				.getCommentId();

		// WHEN THEN
		String pageName = getRandomString();
		application
				.visitUserMyWikiPage()
				.getUserDashboardActionbarComponent()
				.createNewWiki()
				.wikiNewChildDialogFormComponent(form -> form
						.pageName(pageName)
						.withWikiEditor(editor -> {
							editor.pasteFromClipboard();
							editor.assertThat().containsText(trackerItemName);
						}))
				.save()
				.redirectedToUserWikiPage()
				.applyUserTreeComponent(tree -> tree.selectTreeItemByName(pageName))
				.wikiPageContent()
				.clickOnInterwikiLink(trackerItemName)
				.redirectedToTrackerItemPage(tracker.id(), trackerItemId).assertPage(page -> {
					page.isActive();
					page.hasCommentIdInUrl(commentId);
				});
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13350948")
	@Test(description = "User is able to copy an item comment link and paste it to another item's description")
	public void copyPasteWorkItemLinkToAnotherWorkItemDescription() {
		// GIVEN
		final String trackerName = getRandomText("Tasks");
		final Tracker tracker = trackerApiService.createDefaultTaskTracker(project, trackerName);
		final String sourceItemName = "SourceItem";
		final TrackerItemId sourceItemId = trackerItemApiService.createTrackerItem(project, trackerName, builder -> builder
				.name(sourceItemName)
				.description("description"));
		trackerItemApiService.createTrackerItemComment(sourceItemId, builder -> builder
				.plainText()
				.comment(getRandomString()));
		TrackerItemId targetItemId = trackerItemApiService.createTrackerItem(project, tracker.name(), builder -> builder
				.name(getRandomText("TargetItem"))
				.description("description"));

		ClassicCodebeamerApplication application = getClassicCodebeamerApplication(activeUser);

		// WHEN THEN
		int commentId = application
				.visitTrackerItemPage(tracker, sourceItemId)
				.commentsAndAttachmentsTabComponent(commentsTab -> commentsTab
						.getComment(1)
						.copyCommentLink())
				.overlayMessageBoxComponent(c -> c.assertThat().hasSuccess())
				.commentsAndAttachmentsTabComponent()
				.getComment(1)
				.getCommentId();

		application
				.visitTrackerItemPage(tracker, targetItemId)
				.fieldFormComponent(fieldForm -> fieldForm
						.editDescription(descriptionField -> {
							descriptionField.pasteFromClipboard();

							descriptionField.assertThat().contains(sourceItemName);
							descriptionField.save();
						})
				)
				.fieldFormComponent()
				.descriptionField()
				.getInterwikiLink(sourceItemName)
				.click()
				.redirectedToTrackerItemPage(tracker.id(), sourceItemId)
				.assertPage(page -> {
					page.isActive();
					page.hasCommentIdInUrl(commentId);
				});
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13559919")
	@Test(description = "When the user copies an item's link using hotkey, a successful popup message is displayed")
	public void verifyPopupSuccessDisplayWhenBugTrackerItemIsHotkeyCopied() {
		// GIVEN
		final String trackerName = "Bugs";
		final String trackerItemName = "Bug 3 - °TEST_LONG?;TO_TEST_LINK_WITH_A_VERY_LONG_NAME_CHECK_WIKI_LINK_FEATURE_12345678901234567890";
		Tracker bugsTracker = projectApiService.findTrackerByName(project, trackerName);
		TrackerItemId trackerItemId = trackerItemApiService.createTrackerItem(project, trackerName, builder -> builder
				.name(trackerItemName)
				.description("description"));
		Baseline baseline = baselineApiService.createProjectBaseline("Project_baseline", project);

		// WHEN THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerItemPage(bugsTracker, trackerItemId, baseline)
				.pressKey("Alt+w")
				.overlayMessageBoxComponent(c -> c.assertThat().hasSuccess("Link has been copied to clipboard."));
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13559906")
	@Test(description = "When the user copies an item's link using hotkey, a successful popup message is displayed")
	public void verifyPopupSuccessDisplayWhenTaskTrackerItemIsHotkeyCopied() {
		// GIVEN
		final String trackerName = "Tasks";
		final String trackerItemName = "Task 2 - <>#&@{}äđĐ\\|Ä€Í÷$ßí;>*¤×¨";
		Tracker tasksTracker = projectApiService.findTrackerByName(project, trackerName);
		TrackerItemId trackerItemId = trackerItemApiService.createTrackerItem(project, trackerName, builder -> builder
				.name(trackerItemName)
				.description("description"));
		Baseline baseline = baselineApiService.createTrackerBaseline(getRandomText("Tracker_baseline"), project, tasksTracker);

		// WHEN THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerItemPage(tasksTracker, trackerItemId, baseline)
				.pressKey("Alt+w")
				.overlayMessageBoxComponent(c -> c.assertThat().hasSuccess("Link has been copied to clipboard."));
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13559907")
	@Test(description = "User is able to copy an item's link using hotkey then paste it into another item's description")
	public void verifyTaskTrackerItemNavigationUsingPreviouslyCopiedPastedLink() {
		// GIVEN
		final String trackerName = "Tasks";
		final String task1Name = "Task 1 - aáeéiíoóöőuúüű/1234567890!\"\\'=+-*/\\()";
		final String task2Name = "Task 2 - <>#&@{}äđĐ\\|Ä€Í÷$ßí;>*¤×¨";
		Tracker tracker = projectApiService.findTrackerByName(project, trackerName);
		TrackerItemId task1Id = trackerItemApiService.createTrackerItem(project, trackerName, builder -> builder
				.name(task1Name)
				.description(getRandomString()));
		TrackerItemId task2Id = trackerItemApiService.createTrackerItem(project, trackerName, builder -> builder
				.name(task2Name)
				.description(getRandomString()));
		Baseline baseline = baselineApiService.createTrackerBaseline(getRandomText("Tracker_baseline"), project, tracker);

		// WHEN THEN
		ClassicCodebeamerApplication application = getClassicCodebeamerApplication(activeUser);

		application
				.visitTrackerItemPage(tracker, task2Id, baseline)
				.pressKey("Alt+w")
				.overlayMessageBoxComponent(c -> c.assertThat().hasSuccess("Link has been copied to clipboard."));

		application
				.visitTrackerItemPage(tracker, task1Id)
				.fieldFormComponent(form -> form
					.editDescription(descriptionComponent -> descriptionComponent
							.pasteFromClipboard()
							.save()
					)
				)
				.fieldFormComponent()
				.descriptionField()
				.clickOnLink("[TASK-%d] %s".formatted(task2Id.id(), task2Name))
				.redirectedToTrackerItemPage(tracker.id(), task2Id);
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13264761")
	@Test(description = "Check Tooltip With Two Quotation Marks Not Separated")
	public void checkTooltipWithTwoQuotationMarksNotSeparated() {
		// GIVEN
		final String fieldPropertyName = "Modified by";
		final String description = "'\"two pairs\"\"next to each other\"'";
		ClassicCodebeamerApplication classicCbApp = getClassicCodebeamerApplication(activeUser);
		Tracker tracker = trackerApiService.createDefaultTestCaseTracker(project, "Tracker Item Test Cases Tracker");
		TrackerItemId trackerItemId = trackerItemApiService.createTrackerItem(project, "Tracker Item Test Cases Tracker",
				item -> item.name("Tracker Item Test Cases Test case")
						.description("Tracker Item Test Cases Description"));

		classicCbApp.visitTrackerConfigPage(tracker)
				.changeToTrackerConfigFieldsTab()
				.trackerConfigFieldsTab(tab -> tab.editFieldConfig(fieldPropertyName)
						.setDescription(description)
						.save())
				.trackerConfigFieldsTab(AbstractTrackerConfigTab::save);

		// WHEN THEN
		classicCbApp.visitTrackerItemPage(tracker, trackerItemId)
				.assertFieldFormComponent(f -> f
						.fieldLabel(fieldPropertyName, a -> a
								.hasTitle(description)));
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13264774")
	@Test(description = "Check Tooltip With Tw Quotation Marks Space Separated")
	public void checkTooltipWithTwoQuotationMarksSpaceSeparated() {
		// GIVEN
		final String fieldPropertyName = "Resolution";
		final String description = "'\"two pairs\" \"with a space between them\"'";
		ClassicCodebeamerApplication classicCbApp = getClassicCodebeamerApplication(activeUser);
		Tracker tracker = trackerApiService.createDefaultUserStoryTracker(project, "Tracker Item User Stories Tracker");
		TrackerItemId trackerItemId = trackerItemApiService.createTrackerItem(project, "Tracker Item User Stories Tracker",
				item -> item.name("Tracker Item User Stories Test case")
						.description("Tracker Item User Stories Description"));

		classicCbApp.visitTrackerConfigPage(tracker)
				.changeToTrackerConfigFieldsTab()
				.trackerConfigFieldsTab(tab -> tab.editFieldConfig(fieldPropertyName)
						.setDescription(description)
						.save())
				.trackerConfigFieldsTab(AbstractTrackerConfigTab::save);

		// WHEN THEN
		classicCbApp.visitTrackerItemPage(tracker, trackerItemId)
				.assertFieldFormComponent(f -> f
						.fieldLabel(fieldPropertyName, a -> a
								.hasTitle(description)));
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13264725")
	@Test(description = "Check tooltip with quotation marks")
	public void checkTooltipWithQuotationMarks() {
		// GIVEN
		final String trackerName = "Requirements";
		final String trackerItemName = "Change 'Custom field' tooltip";
		final String trackerItemDescription = "Tracker item description";
		final String customFieldLabel = "Custom field";
		final String customFieldDescription = "\"a pair of quotation marks\"";
		Tracker tracker = trackerApiService.createRequirementTracker(project, trackerName)
				.buildAndAdd();
		TrackerItemId trackerItemId = trackerItemApiService.createTrackerItem(project, trackerName, item -> item
				.name(trackerItemName)
				.description(trackerItemDescription));

		// WHEN
		ClassicCodebeamerApplication application = getClassicCodebeamerApplication(activeUser);

		application
				.visitTrackerConfigPage(tracker)
				.changeToTrackerConfigFieldsTab()
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.newCustomField()
						.setLabel(customFieldLabel)
						.save())
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.save())
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.editFieldConfig(customFieldLabel)
						.setDescription(customFieldDescription)
						.save())
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.save());

		// THEN
		application
				.visitTrackerItemPage(tracker, trackerItemId)
				.assertFieldFormComponent(f -> f
						.fieldLabel(customFieldLabel, a -> a.hasTitle(customFieldDescription)));
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13264753")
	@Test(description = "Check tooltip with quotation marks separated by a string")
	public void checkTooltipWithTwoQuotationMarksStringSeparated() {
		// GIVEN
		final String trackerName = getRandomText("Tasks");
		final String trackerItemName = "Change 'Submitted by' tooltip";
		final String trackerItemDescription = "Tracker item description";
		final String customFieldLabel = "Submitted by";
		final String customFieldDescription = "\"one pair\" and \"another pair\"";

		Tracker tracker = trackerApiService.createTaskTracker(project, trackerName)
				.buildAndAdd();

		TrackerItemId trackerItemId = trackerItemApiService.createTrackerItem(project, trackerName, item -> item
				.name(trackerItemName)
				.description(trackerItemDescription));

		ClassicCodebeamerApplication application = getClassicCodebeamerApplication(activeUser);

		// WHEN
		application
				.visitTrackerConfigPage(tracker)
				.changeToTrackerConfigFieldsTab()
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.editFieldConfig(customFieldLabel)
						.setDescription(customFieldDescription)
						.save())
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.save());

		// THEN
		application
				.visitTrackerItemPage(tracker, trackerItemId)
				.assertFieldFormComponent(f -> f
						.fieldLabel(customFieldLabel, a -> a.hasTitle(customFieldDescription)));
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13264759")
	@Test(description = "Check tooltip with quotation marks separated by a comma")
	public void checkTooltipWithTwoQuotationMarksCommaSeparated() {
		// GIVEN
		final String trackerName = "Test sets";
		final String trackerItemName = "Change 'Possible Run Configurations' tooltip";
		final String trackerItemDescription = "Tracker item description";
		final String customFieldLabel = "Possible Run Configurations";
		final String customFieldDescription = "\"one pair\",\"another pair\"";

		Tracker tracker = trackerApiService.createTestSetTracker(project, trackerName)
				.buildAndAdd();

		TrackerItemId trackerItemId = trackerItemApiService.createTrackerItem(project, trackerName, item -> item
				.name(trackerItemName)
				.description(trackerItemDescription));

		ClassicCodebeamerApplication application = getClassicCodebeamerApplication(activeUser);

		// WHEN
		application
				.visitTrackerConfigPage(tracker)
				.changeToTrackerConfigFieldsTab()
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.editFieldConfig(customFieldLabel)
						.setDescription(customFieldDescription)
						.save())
				.trackerConfigFieldsTab(configFieldsTab -> configFieldsTab.save());

		// THEN
		application
				.visitTrackerItemPage(tracker, trackerItemId)
				.assertFieldFormComponent(f -> f
						.fieldLabel(customFieldLabel, a -> a.hasTitle(customFieldDescription)));
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13410606")
	@Test(description = "If user creates tracker item from template with 'Obsolete' status, the status of a newly created item will be 'Obsolete' as well")
	public void createAreaTrackerItemFromActiveStatusTemplateChangedToObsolateStatusTemplate() {
		// GIVEN
		final String trackerName = getRandomText("Area");
		final String trackerItemName = "My area item";
		final String trackerItemTemplateName = "My area item template";
		final String description = "description";
		final String newItemName = "New item name";
		final Tracker tracker = trackerApiService.createDefaultAreaTracker(project, trackerName);
		final TrackerItemId trackerItemId = trackerItemApiService.createTrackerItem(project, trackerName, builder -> builder
				.name(trackerItemName)
				.description(description));
		final TrackerItemTemplate trackerItemTemplate = trackerItemApiService.createTrackerItemTemplate(trackerItemId,
				trackerItemTemplateName, description, false);

		// WHEN THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerTableViewPage(tracker)
				.actionBarComponent()
				.openMoreActionMenu()
				.selectManageItemTemplates()
				.editTrackerItemTemplate(trackerItemTemplate)
				.redirectedToEditItemTemplatesDialog()
				.editItemTemplateComponent(f -> {
					f.assertThat().choiceField("Status", s -> s.is("Active"));
					f.setChoiceField("Status", "Obsolete");
				})
				.save()
				.redirectedToManageItemTemplatesDialog()
				.close()
				.redirectedToTrackerTableViewPage()
				.actionBarComponent()
				.createTrackerItemFromTemplate(trackerItemTemplateName)
				.fieldFormComponent(f -> f.setTextField("Name", newItemName))
				.save()
				.redirectedToTrackerItemPage()
				.assertFieldFormComponent(f -> f.choiceField("Status", s -> s.is("Obsolete")));
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13410604")
	@Test(description = "If user creates tracker item from template with 'Active' status, the status of a newly created item will be 'Active' as well")
	public void createAreaTrackerItemFromObsolateStatusTemplateChangedToActiveStatusTemplate() {
		// GIVEN
		final String trackerName = getRandomText("Area");
		final String trackerItemName = "My area item";
		final String trackerItemTemplateName = "My area item template";
		final String description = "description";
		final String newItemName = "New item name";
		final Tracker tracker = trackerApiService.createDefaultAreaTracker(project, trackerName);
		final TrackerItemId trackerItemId = trackerItemApiService.createTrackerItem(project, trackerName, builder -> builder
				.name(trackerItemName)
				.description(description)
				.status("Obsolete"));
		final TrackerItemTemplate trackerItemTemplate = trackerItemApiService.createTrackerItemTemplate(trackerItemId,
				trackerItemTemplateName, description, false);

		// WHEN THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerTableViewPage(tracker)
				.actionBarComponent()
				.openMoreActionMenu()
				.selectManageItemTemplates()
				.editTrackerItemTemplate(trackerItemTemplate)
				.redirectedToEditItemTemplatesDialog()
				.editItemTemplateComponent(f -> {
					f.assertThat().choiceField("Status", s -> s.is("Obsolete"));
					f.setChoiceField("Status", "Active");
				})
				.save()
				.redirectedToManageItemTemplatesDialog()
				.close()
				.redirectedToTrackerTableViewPage()
				.actionBarComponent()
				.createTrackerItemFromTemplate(trackerItemTemplateName)
				.fieldFormComponent(f -> f.setTextField("Name", newItemName))
				.save()
				.redirectedToTrackerItemPage()
				.assertFieldFormComponent(f -> f.choiceField("Status", s -> s.is("Active")));
	}

    @TestCase(link = "https://codebeamer.com/cb/item/13410527")
    @Test(description = "Create area tracker item from Active status template")
    public void createAreaTrackerItemFromActiveStatusTemplate() {
        Tracker areaTracker = trackerApiService.createAreaTracker(project, getRandomString()).buildAndAdd();

        TrackerItemId trackerItemId = trackerItemApiService.createTrackerItem(project, areaTracker.name(),
                builder -> builder.name("Active Tracker Item").status("Active"));

        TrackerItemTemplate activeAreaTrackerItemTemplate = trackerItemApiService.createTrackerItemTemplate(trackerItemId,
                "Active item template", "Template description", true);

        getClassicCodebeamerApplication(activeUser)
                .visitTrackerTableViewPage(areaTracker)
                .actionBarComponent()
                .openMoreActionMenu()
                .selectManageItemTemplates()
                .editTrackerItemTemplate(activeAreaTrackerItemTemplate)
                .redirectedToEditItemTemplatesDialog()
                .editItemTemplateComponent(f -> f.assertThat().choiceField("Status", s -> s.is("Active")))
                .save()
                .redirectedToManageItemTemplatesDialog()
                .close()
                .redirectedToTrackerTableViewPage()
                .actionBarComponent()
                .createTrackerItemFromTemplate(activeAreaTrackerItemTemplate.title())
                .assertFieldFormComponent(f -> f.choiceField("Status", s -> s.is("Active")))
                .save()
                .redirectedToTrackerItemPage()
                .assertPage(c -> c.isActive());
    }

    @TestCase(link = "https://codebeamer.com/cb/item/13410528")
    @Test(description = "Create area tracker item from Obsolete status template")
    public void createAreaTrackerItemFromObsoleteStatusTemplate() {
        Tracker areaTracker = trackerApiService.createAreaTracker(project, getRandomString()).buildAndAdd();

        TrackerItemId trackerItemId = trackerItemApiService.createTrackerItem(project, areaTracker.name(),
                builder -> builder.name("Obsolete Tracker Item").status("Obsolete"));

        TrackerItemTemplate activeAreaTrackerItemTemplate = trackerItemApiService.createTrackerItemTemplate(trackerItemId,
                "Obsolete item template", "Template description", true);

        getClassicCodebeamerApplication(activeUser)
                .visitTrackerTableViewPage(areaTracker)
                .actionBarComponent()
                .openMoreActionMenu()
                .selectManageItemTemplates()
                .editTrackerItemTemplate(activeAreaTrackerItemTemplate)
                .redirectedToEditItemTemplatesDialog()
                .editItemTemplateComponent(f -> f.assertThat().choiceField("Status", s -> s.is("Obsolete")))
                .save()
                .redirectedToManageItemTemplatesDialog()
                .close()
                .redirectedToTrackerTableViewPage()
                .actionBarComponent()
                .createTrackerItemFromTemplate(activeAreaTrackerItemTemplate.title())
                .assertFieldFormComponent(f -> f.choiceField("Status", s -> s.is("Obsolete")))
                .save()
                .redirectedToTrackerItemPage()
                .assertPage(c -> c.isActive());
    }

	@TestCase(link = "https://codebeamer.com/cb/item/13264435")
	@Test(description = "A referenced item is not displayed in the reference field after it's been deleted.")
	public void removeAReferenceItemFromAReferenceField() {
		// GIVEN
		final String usTrackerName = getRandomText("User Stories");
		final String taskTrackerName = getRandomText("Tasks");
		final String referenceFieldName = "MyUserStory";
		Tracker usTracker = trackerApiService.createDefaultUserStoryTracker(project, usTrackerName);
		trackerApiService.createDefaultTaskTracker(project, taskTrackerName);
		trackerApiService.updateTracker(project, taskTrackerName)
				.createReferenceFieldOfTrackers(referenceFieldName,
						referenceFilter -> referenceFilter.addTrackerFilter(project, usTracker.name()),
						fieldProp -> fieldProp.multipleSelection(true))
				.buildAndAdd();

		String userStory1Name = "UserStory1";
		TrackerItemId userStory1Id = trackerItemApiService.createTrackerItem(project, usTrackerName, builder -> builder
				.name(userStory1Name)
				.description(getRandomString()));
		String userStory2Name = "UserStory2";
		TrackerItemId userStory2Id = trackerItemApiService.createTrackerItem(project, usTrackerName, builder -> builder
				.name(userStory2Name)
				.description(getRandomString()));
		TrackerItemId taskId = trackerItemApiService.createTrackerItem(project, taskTrackerName, builder -> builder
				.name("Task1")
				.setTrackerItemFor(referenceFieldName, userStory1Id, userStory2Id)
				.description(getRandomString()));
		trackerItemApiService.deleteTrackerItem(userStory2Id);

		// WHEN THEN
		ClassicCodebeamerApplication application = getClassicCodebeamerApplication(activeUser);

		application
				.visitTrackerItemPage(usTracker, taskId)
				.assertPage(page -> page
					.assertFieldFormComponent(a -> a.referenceField(referenceFieldName, ref -> ref.containsOnly(new TrackerItem(userStory1Id, userStory1Name))))
				)
				.changeToHistoryTab()
				.historyTabComponent(c -> {
					c.assertThat().hasNumberOfHistoryItemsShown(1);

					TrackerItemHistoryComponent trackerItemHistoryComponent = c.getHistoryList().getFirst();
					trackerItemHistoryComponent.assertThat()
							.actionEqualsTo("Submit")
							.versionEqualsTo(Integer.valueOf(1))
							.isSubmittedBy(activeUser.getUserId())
							.isSubmittedBy(activeUser.getName())
							.hasNumberOfFieldChanges(0);
				});
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13264461")
	@Test(description = "A referenced then deleted item is displayed in the reference field in baseline")
	public void removeAReferenceItemFromAReferenceFieldVerifyInBaseline() {
		// GIVEN
		final String usTrackerName = getRandomText("User Stories");
		final String taskTrackerName = getRandomText("Tasks");
		final String referenceFieldName = "MyUserStory";
		Tracker usTracker = trackerApiService.createDefaultUserStoryTracker(project, usTrackerName);
		trackerApiService.createDefaultTaskTracker(project, taskTrackerName);
		trackerApiService.updateTracker(project, taskTrackerName)
				.createReferenceFieldOfTrackers(referenceFieldName,
						referenceFilter -> referenceFilter.addTrackerFilter(project, usTracker.name()),
						fieldProp -> fieldProp.multipleSelection(true))
				.buildAndAdd();

		String userStory1Name = "UserStory1";
		TrackerItemId userStory1Id = trackerItemApiService.createTrackerItem(project, usTrackerName, builder -> builder
				.name(userStory1Name)
				.description(getRandomString()));
		String userStory2Name = "UserStory2";
		TrackerItemId userStory2Id = trackerItemApiService.createTrackerItem(project, usTrackerName, builder -> builder
				.name(userStory2Name)
				.description(getRandomString()));
		TrackerItemId taskId = trackerItemApiService.createTrackerItem(project, taskTrackerName, builder -> builder
				.name("Task1")
				.setTrackerItemFor(referenceFieldName, userStory1Id, userStory2Id)
				.description(getRandomString()));

		String baselineName = getRandomText("Project baseline");
		Baseline projectBaseline = baselineApiService.createProjectBaseline(baselineName, project);

		trackerItemApiService.deleteTrackerItem(userStory2Id);

		// WHEN THEN
		ClassicCodebeamerApplication application = getClassicCodebeamerApplication(activeUser);

		application
				.visitTrackerItemPage(usTracker, taskId, projectBaseline)
				.assertPage(page -> page
					.assertFieldFormComponent(a -> a.referenceField(referenceFieldName, ref -> ref.containsOnly(
						new TrackerItem(userStory1Id, userStory1Name),
						new TrackerItem(userStory2Id, userStory2Name))
					))
				)
				.changeToHistoryTab()
				.historyTabComponent(c -> {
					c.assertThat().hasNumberOfHistoryItemsShown(2);

					TrackerItemHistoryComponent baselineCreatedHistoryItem = c.getHistoryList().getFirst();
					baselineCreatedHistoryItem.assertThat().isBaselineCreationIndicator(baselineName);

					TrackerItemHistoryComponent submittedHistoryItem = c.getHistoryList().get(1);
					submittedHistoryItem.assertThat()
							.actionEqualsTo("Submit")
							.versionEqualsTo(Integer.valueOf(1))
							.isSubmittedBy(activeUser.getUserId())
							.isSubmittedBy(activeUser.getName())
							.hasNumberOfFieldChanges(0);
				});
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13559903")
	@Test(description = "Check the 'Copy Work Item' link and the related hotkey hint")
	public void verifyCopyTrackerItemElementsOnTaskTrackerItemDetails() {
		// GIVEN
		Tracker tasksTracker = projectApiService.findTrackerByName(project, "Tasks");
		TrackerItemId taskId = trackerItemApiService.createTrackerItem(project, "Tasks", builder -> builder
				.name("Task 2 - <>#&@{}äđĐ\\|Ä€Í÷$ßí;>*¤×¨")
				.description(getRandomString()));
		Baseline trackerBaseline = baselineApiService.createTrackerBaseline(getRandomText("Tracker_baseline"), project, tasksTracker);

		// WHEN THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerItemPage(tasksTracker, taskId, trackerBaseline)
				.actionMenuBarComponent(actionMenuBar -> {
					actionMenuBar.assertThat().hasCopyItemLink();

					actionMenuBar.hoverOverCopyItemLink();
					actionMenuBar.assertThat().copyItemLinkIsBlue();
				})
				.footerComponent(footer -> {
					footer.openHotkeysTable();
					footer.assertHotkeysTable(HotkeysComponentAssertions::hasCopyWorkItemHint);
				})
				.actionBarComponentAssertion(TrackerItemActionBarAssertions::isEmpty);
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13559917")
	@Test(description = "Check the 'Copy Work Item' link and the related hotkey hint of a bug tracker item")
	public void verifyCopyTrackerItemElementsOnBugTrackerItemDetails() {
		// GIVEN
		final String trackerName = getRandomText("Bugs");
		final String trackerItemName = "Bug 3";
		Tracker tracker = trackerApiService.createDefaultBugTracker(project, trackerName);
		TrackerItemId trackerItemId = trackerItemApiService.createTrackerItem(project, trackerName, builder -> builder
				.name(trackerItemName)
				.description(
						"Bug 3 - °TEST_LONG?;TO_TEST_LINK_WITH_A_VERY_LONG_NAME_CHECK_WIKI_LINK_FEATURE_12345678901234567890"));
		Baseline baseline = baselineApiService.createProjectBaseline("Project_baseline", project);

		// WHEN THEN
		ClassicCodebeamerApplication application = getClassicCodebeamerApplication(activeUser);

		application
				.visitTrackerItemPage(tracker, trackerItemId, baseline)
				.actionMenuBarComponent(actionMenuBar -> {
					actionMenuBar.assertThat().hasCopyLinkIcon();
					actionMenuBar.hoverOverCopyItemLink();
					actionMenuBar.assertThat()
							.hasCopyItemLink()
							.copyItemLinkIsBlue()
							.hasTooltip("Copy Work Item Link (Alt + W)");
				})
				.footerComponent(footer -> {
					footer.openHotkeysTable();
					footer.assertHotkeysTable(HotkeysComponentAssertions::hasCopyWorkItemHint);
				})
				.actionBarComponentAssertion(TrackerItemActionBarAssertions::isEmpty);
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13604152")
	@Test(description = "Check the 'Copy Work Item' link, the related menu item and hotkey hint")
	public void verifyCopyTrackerItemElementsOnSystemRequirementsSpecificationTrackerItemDetails() {
		// GIVEN
		Tracker tracker = projectApiService.findTrackerByName(project, "System Requirement Specifications");
		TrackerItemId item = trackerItemApiService.createTrackerItem(project, "System Requirement Specifications", builder -> builder
				.name("SRS1")
				.description(getRandomString()));

		// WHEN THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerItemPage(tracker, item)
				.actionMenuBarComponent(actionMenuBar -> {
					actionMenuBar.assertThat().hasCopyItemLink();

					actionMenuBar.hoverOverCopyItemLink();
					actionMenuBar.assertThat().copyItemLinkIsBlue();
				})
				.footerComponent(footer -> {
					footer.openHotkeysTable();
					footer.assertHotkeysTable(HotkeysComponentAssertions::hasCopyWorkItemHint);
				})
				.actionBarComponent()
				.openMoreActionMenu()
				.assertThat()
				.hasCopyWorkItemButton();
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13604299")
	@Test(description = "Check the absence of the 'Copy Work Item' link and the related hotkey hint on Item edit page")
	public void verifyLinkIconAndHotkeyListOnTrackerItemPropertiesPage() {
		// GIVEN
		Tracker tracker = trackerApiService.createDefaultDocumentTracker(project.id(), "Documents");
		TrackerItemId itemId = trackerItemApiService.createDocumentItem(tracker.id(), getFilePath("test.txt").toFile());

		// WHEN THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerItemPage(tracker, itemId)
				.edit()
				.assertActionMenuBarComponent(ActionMenuBarAssertions::hasNoCopyItemLink)
				.footerComponent(footer -> footer
					.openHotkeysTable()
					.assertHotkeysTable(HotkeysComponentAssertions::hasNoCopyWorkItemHint)
				);
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13604289")
	@Test(description = "Check if the 'Copy Work Item' link and the related hotkey is missing during edit")
	public void verifyLinkIconAndHotkeyListInTrackerItemDetailsWhenEditing() {
		// GIVEN
		Tracker bugsTracker = projectApiService.findTrackerByName(project, "Bugs");
		TrackerItemId bugId = trackerItemApiService.createTrackerItem(project, "Bugs", builder -> builder
				.name("Bug 1")
				.description("description"));

		// WHEN THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerItemPage(bugsTracker, bugId)
				.edit()
				.assertActionMenuBarComponent(ActionMenuBarAssertions::hasNoCopyItemLink)
				.footerComponent(footer -> footer
						.openHotkeysTable()
						.assertHotkeysTable(HotkeysComponentAssertions::hasNoCopyWorkItemHint)
				);
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13604291")
	@Test(description = "Verify that the 'Copy Work Item' link and the related hotkey is missing during status change")
	public void verifyLinkIconAndHotkeyListInTrackerItemDetailsWhenStatusChangeEditing() {
		// GIVEN
		Tracker tasksTracker = projectApiService.findTrackerByName(project, "Tasks");
		TrackerItemId taskId = trackerItemApiService.createTrackerItem(project, "Tasks", builder -> builder
				.name("Task 1")
				.description("description"));

		// WHEN THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerItemPage(tasksTracker, taskId)
				.actionMenuBarComponent(actionMenuBar -> {
					actionMenuBar.assertThat().hasCopyItemLink();
					actionMenuBar.hoverOverCopyItemLink();
					actionMenuBar.assertThat().copyItemLinkIsBlue();
				})
				.footerComponent(footer -> {
					footer.openHotkeysTable();
					footer.assertHotkeysTable(HotkeysComponentAssertions::hasCopyWorkItemHint);
				})
				.actionBarComponent()
				.setStatus("Start")
				.assertActionMenuBarComponent(ActionMenuBarAssertions::hasNoCopyItemLink)
				.footerComponent(footer -> footer
						.openHotkeysTable()
						.assertHotkeysTable(HotkeysComponentAssertions::hasNoCopyWorkItemHint)
				);
	}


	@TestCase(link = "https://codebeamer.com/cb/item/13410622")
	@Test(description = "Check if created choice field still shows value after deleting it.")
	public void verifySelectedChoiceOptionOnTrackerItemAfterChoiceOptionIsRemoved() {
		// GIVEN
		String customChoiceFieldName = "myChoiceField1";
		Tracker tasks = projectApiService.findTrackerByName(project, "Tasks");

		trackerApiService.updateTracker(project, tasks.name())
				.createChoiceField(customChoiceFieldName, List.of(new ChoiceOption(1, "value1"),
						new ChoiceOption(2, "value2"),
						new ChoiceOption(3, "value3")))
				.buildAndAdd();

		TrackerItemId taskItemID = trackerItemApiService.createTrackerItem(project, tasks.name(), builder -> builder
				.name("Task 1")
				.description("description")
				.setChoiceOptionFor(customChoiceFieldName, "value1"));

		trackerApiService.updateTracker(project, tasks.name())
				.updateChoiceField(customChoiceFieldName, List.of(
						new ChoiceOption(2, "value2"),
						new ChoiceOption(3, "value3")))
				.buildAndAdd();

		// WHEN THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerItemPage(tasks, taskItemID)
				.initiateOverlayStateTransition("Start")
				.assertFieldFormComponent(a -> a
						.choiceField("Status", c -> c.is("In progress"))
				).save()
				.redirectedToTrackerItemPage()
				.assertFieldFormComponent(a -> a
						.choiceField("Status", c -> c.is("In progress"))
						.choiceField(customChoiceFieldName, customField -> customField.is("value1")));
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13559922")
	@Test(description = "Check navigation to the item details page using a pasted link")
	public void verifyBugTrackerItemNavigationUsingPreviouslyCopiedPastedLink() {
		// GIVEN
		Tracker bugTracker = trackerApiService.createBugTracker(project.id(), getRandomText("Bugs"), builder ->
			builder.key("BG1")
		).buildAndAdd();

		String bug1Name = "Bug 1 - aáeéiíoóöőuúüű/1234567890!\"\\'=+-*/\\()";
		TrackerItemId bug1Id = trackerItemApiService.createTrackerItem(bugTracker, builder -> builder
				.name(bug1Name)
				.description(getRandomString()));
		String bug2Name = "Bug 3 - °TEST_LONG?;TO_TEST_LINK_WITH_A_VERY_LONG_NAME_CHECK_WIKI_LINK_FEATURE_12345678901234567890";
		TrackerItemId bug2Id = trackerItemApiService.createTrackerItem(bugTracker, builder -> builder
				.name(bug2Name)
				.description(getRandomString()));

		Baseline projectBaseline = baselineApiService.createProjectBaseline("Project baseline", project);
		ClassicCodebeamerApplication application = getClassicCodebeamerApplication(activeUser);

		// WHEN THEN
		application
				.visitTrackerItemPage(bugTracker, bug2Id, projectBaseline)
				.pressKey("Alt+W");

		application
				.visitTrackerItemPage(bugTracker, bug1Id)
				.fieldFormComponent(formComponent -> formComponent
						.editDescription(editor -> editor
								.pasteFromClipboard()
								.save()
						)
				)
				.fieldFormComponent()
				.descriptionField()
				.clickOnLink("[BG1-%d] %s".formatted(bug2Id.id(), bug2Name))
				.redirectedToTrackerItemPage(bugTracker.id(), bug2Id);
	}

	@TestCase(link = "https://codebeamer.com/cb/item/13625841")
	@Test(description = "User is able to open Bugs Tracker in Tracker Baseline revision")
	public void visitTrackerInTrackerBaselineRevision() {
		// GIVEN
		Tracker bugsTracker = projectApiService.findTrackerByName(agileProject, "Bugs");
		trackerItemApiService.createTrackerItem(agileProject, bugsTracker.name(), builder -> builder
				.name(getRandomText("Bug"))
				.description(getRandomString()));
		Baseline trackerBaseline = baselineApiService.createTrackerBaseline(getRandomText("Tracker_baseline"), agileProject,
				bugsTracker);

		// WHEN THEN
		getClassicCodebeamerApplication(activeUser)
				.visitTrackerTableViewPage(bugsTracker, trackerBaseline);
	}
}