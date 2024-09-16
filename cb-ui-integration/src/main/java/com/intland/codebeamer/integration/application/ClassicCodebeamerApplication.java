package com.intland.codebeamer.integration.application;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.baseline.Baseline;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.reports.model.Report;
import com.intland.codebeamer.integration.api.service.reviewhub.model.Review;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItem;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemId;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.classic.page.baseline.BaselineLandingPage;
import com.intland.codebeamer.integration.classic.page.documents.ProjectDocumentsLandingPage;
import com.intland.codebeamer.integration.classic.page.login.LoginPage;
import com.intland.codebeamer.integration.classic.page.project.ProjectLandingPage;
import com.intland.codebeamer.integration.classic.page.project.projectadmin.AddRolePage;
import com.intland.codebeamer.integration.classic.page.project.projectadmin.ProjectMembersPage;
import com.intland.codebeamer.integration.classic.page.projectadmin.ProjectAdminPage;
import com.intland.codebeamer.integration.classic.page.projectbrowser.ProjectBrowserPage;
import com.intland.codebeamer.integration.classic.page.projectcreate.ProjectCreatePage;
import com.intland.codebeamer.integration.classic.page.reports.EditReportPage;
import com.intland.codebeamer.integration.classic.page.reports.NewReportPage;
import com.intland.codebeamer.integration.classic.page.reports.ViewReportPage;
import com.intland.codebeamer.integration.classic.page.reports.findreports.FindReportsPage;
import com.intland.codebeamer.integration.classic.page.reports.traceabilityreport.NewTraceabilityReportPage;
import com.intland.codebeamer.integration.classic.page.reviewhub.AllReviewPage;
import com.intland.codebeamer.integration.classic.page.reviewhub.MergeRequestsPage;
import com.intland.codebeamer.integration.classic.page.scmrepository.ScmRepositoryMainPage;
import com.intland.codebeamer.integration.classic.page.systemadmin.SystemAdminCreateNewGroupPage;
import com.intland.codebeamer.integration.classic.page.systemadmin.SystemAdminDashboardPage;
import com.intland.codebeamer.integration.classic.page.systemadmin.SystemAdminUserGroupsPage;
import com.intland.codebeamer.integration.classic.page.testrun.TestRunnerPage;
import com.intland.codebeamer.integration.classic.page.tracker.config.TrackerConfigurationPage;
import com.intland.codebeamer.integration.classic.page.tracker.main.TrackersPage;
import com.intland.codebeamer.integration.classic.page.tracker.view.document.TrackerDocumentViewPage;
import com.intland.codebeamer.integration.classic.page.tracker.view.table.TrackerTableViewPage;
import com.intland.codebeamer.integration.classic.page.trackeritem.TrackerItemPage;
import com.intland.codebeamer.integration.classic.page.trackeritem.create.TrackerItemCreatePage;
import com.intland.codebeamer.integration.classic.page.trackeritem.edit.TrackerItemEditPage;
import com.intland.codebeamer.integration.classic.page.trackeritem.importer.TrackerItemImportPage;
import com.intland.codebeamer.integration.classic.page.trackeritem.release.ReleasePage;
import com.intland.codebeamer.integration.classic.page.trackeritem.release.ReleaseTrackerPage;
import com.intland.codebeamer.integration.classic.page.trackeritem.release.kanban.ReleaseKanbanBoardPage;
import com.intland.codebeamer.integration.classic.page.trackeritem.release.planner.ReleasePlannerPage;
import com.intland.codebeamer.integration.classic.page.user.ProfilePhotoPage;
import com.intland.codebeamer.integration.classic.page.user.UserChangePasswordPage;
import com.intland.codebeamer.integration.classic.page.user.UserMyWikiPage;
import com.intland.codebeamer.integration.classic.page.user.usereditaccount.UserAccountEditPage;
import com.intland.codebeamer.integration.classic.page.useraccount.MyUserAccountPage;
import com.intland.codebeamer.integration.classic.page.useraccount.UserAccountPage;
import com.intland.codebeamer.integration.nextgen.page.reviewhub.CreateReviewPage;
import com.intland.codebeamer.integration.nextgen.page.reviewhub.CreateReviewFromReportsPage;
import com.intland.codebeamer.integration.nextgen.page.reviewhub.ReviewAdminPage;
import com.intland.codebeamer.integration.nextgen.page.reviewhub.ReviewItemPage;
import com.intland.codebeamer.integration.nextgen.page.reviewhub.ReviewStatisticsPage;
import com.intland.codebeamer.integration.nextgen.page.reviewhub.ReviewTemplatePage;
import com.intland.codebeamer.integration.sitemap.annotation.Application;

@Application("Codebeamer")
public class ClassicCodebeamerApplication {

	private CodebeamerPage codebeamerPage;

	private User user;

	public ClassicCodebeamerApplication(CodebeamerPage codebeamerPage) {
		this.codebeamerPage = codebeamerPage;
	}

	public ClassicCodebeamerApplication(CodebeamerPage codebeamerPage, User user) {
		this.codebeamerPage = codebeamerPage;
		this.user = user;
	}

	public LoginPage visitLoginPage() {
		return new LoginPage(codebeamerPage).visit();
	}
	
	public ProjectBrowserPage visitProjectBrowserPage() {
		return new ProjectBrowserPage(codebeamerPage).visit();
	}

	public ScmRepositoryMainPage visitScmRepositoryMainPage(Project project) {
		return new ScmRepositoryMainPage(codebeamerPage, project).visit();
	}

	public ProjectCreatePage visitProjectCreatePage() {
		return new ProjectCreatePage(codebeamerPage).visit();
	}

	public UserMyWikiPage visitUserMyWikiPage() {
		return new UserMyWikiPage(codebeamerPage).visit();
	}

	public ProjectLandingPage visitProjectLandingPage(Project project) {
		return new ProjectLandingPage(codebeamerPage, project).visit();
	}

	public ProjectDocumentsLandingPage visitProjectDocumentsPage(Project project) {
		return new ProjectDocumentsLandingPage(codebeamerPage, project).visit();
	}

	public ProjectMembersPage visitProjectMembersPage(Project project) {
		return new ProjectMembersPage(codebeamerPage, project).visit();
	}

	public AddRolePage visitAddRolePage(Project project) {
		return new AddRolePage(codebeamerPage, project).visit();
	}

	public TrackersPage visitTrackersPage(Project project) {
		return new TrackersPage(codebeamerPage, project).visit();
	}

	public TrackerTableViewPage visitTrackerTableViewPage(Tracker tracker) {
		return new TrackerTableViewPage(codebeamerPage, tracker).visit();
	}

	public TrackerTableViewPage visitTrackerTableViewPage(Tracker tracker, Baseline baseline) {
		return new TrackerTableViewPage(codebeamerPage, tracker, baseline).visit();
	}

	public TrackerDocumentViewPage visitTrackerDocumentViewPage(Tracker tracker) {
		return new TrackerDocumentViewPage(codebeamerPage, tracker).visit();
	}

	public TrackerItemPage visitTrackerItemPage(Tracker tracker, TrackerItemId trackerItemId) {
		return visitTrackerItemPage(tracker.id(), trackerItemId);
	}
	
	public TrackerItemPage visitTrackerItemPage(TrackerId trackerId, TrackerItemId trackerItemId) {
		return new TrackerItemPage(codebeamerPage, trackerId, trackerItemId).visit();
	}

	public TrackerItemPage visitTrackerItemPage(Tracker tracker, TrackerItemId trackerItemId, Baseline baseline) {
		return visitTrackerItemPage(tracker.id(), trackerItemId, baseline);
	}
	
	public TrackerItemPage visitTrackerItemPage(TrackerId trackerId, TrackerItemId trackerItemId, Baseline baseline) {
		return new TrackerItemPage(codebeamerPage, trackerId, trackerItemId, baseline).visit();
	}

	public TrackerItemCreatePage visitTrackerItemCreatePage(Tracker tracker) {
		return visitTrackerItemCreatePage(tracker.id());
	}
	
	public TrackerItemCreatePage visitTrackerItemCreatePage(TrackerId trackerId) {
		return new TrackerItemCreatePage(codebeamerPage, trackerId).visit();
	}

	public TrackerItemEditPage visitTrackerItemEditPage(TrackerItem trackerItem) {
		return visitTrackerItemEditPage(trackerItem);
	}
	
	public TrackerItemEditPage visitTrackerItemEditPage(Tracker tracker, TrackerItemId trackerItemId) {
		return visitTrackerItemEditPage(tracker.id(), trackerItemId);
	}
	
	public TrackerItemEditPage visitTrackerItemEditPage(TrackerId trackerId, TrackerItemId trackerItemId) {
		return new TrackerItemEditPage(codebeamerPage, trackerId, trackerItemId).visit();
	}

	public TrackerConfigurationPage visitTrackerConfigPage(Tracker tracker) {
		return new TrackerConfigurationPage(codebeamerPage, tracker).visit();
	}

	public TrackerItemImportPage visitTrackerItemImportPage(Tracker tracker) {
		return new TrackerItemImportPage(codebeamerPage, tracker).visit();
	}

	public ReleasePage visitTrackerItemReleasePage(TrackerItemId trackerItemId) {
		return new ReleasePage(codebeamerPage, trackerItemId).visit();
	}

	public ReleasePage visitTrackerItemReleasePage(TrackerItemId trackerItemId, Baseline baseline) {
		return new ReleasePage(codebeamerPage, trackerItemId, baseline).visit();
	}

	public TrackerItemPage visitTrackerItemReleasePageTraceabilityTab(TrackerId trackerId, TrackerItemId trackerItemId, Baseline baseline) {
		return new TrackerItemPage(codebeamerPage, trackerId, trackerItemId, baseline).visitTraceabilityTab();
	}

	public TestRunnerPage visitTestRunnerPage(TrackerId trackerId, TrackerItemId trackerItemId) {
		return new TestRunnerPage(codebeamerPage, trackerId, trackerItemId).visit();
	}

	public ReleaseTrackerPage visitReleasesPage(Tracker tracker) {
		return new ReleaseTrackerPage(codebeamerPage, tracker).visit();
	}

	public ReleaseTrackerPage visitReleasesPage(Tracker tracker, Baseline baseline) {
		return new ReleaseTrackerPage(codebeamerPage, tracker, baseline).visit();
	}

	public ReleasePlannerPage visitReleasePlannerPage(TrackerId trackerId, TrackerItemId trackerItemId) {
		return new ReleasePlannerPage(codebeamerPage, trackerId, trackerItemId).visit();
	}

	public ReleaseKanbanBoardPage visitReleaseKanbanBoardPage(TrackerId trackerId, TrackerItemId trackerItemId) {
		return new ReleaseKanbanBoardPage(codebeamerPage, trackerId, trackerItemId).visit();
	}

	public UserChangePasswordPage visitChangePasswordPage() {
		return new UserChangePasswordPage(codebeamerPage).visit();
	}

	public BaselineLandingPage visitBaselineLandingPage(Project project) {
		return new BaselineLandingPage(codebeamerPage, project).visit();
	}

	public UserAccountPage visitUserAccountPage(User user) {
		return new UserAccountPage(codebeamerPage, user).visit();
	}

	public MyUserAccountPage visitMyUserAccountPage() {
		return new MyUserAccountPage(codebeamerPage, user).visit();
	}

	public ReviewTemplatePage visitReviewTemplatePage() {
		return new ReviewTemplatePage(codebeamerPage).visit();
	}

	public NewReportPage visitNewReportPage() {
		return new NewReportPage(codebeamerPage).visit();
	}

	public FindReportsPage visitFindReportsPage() {
		return new FindReportsPage(codebeamerPage).visit();
	}

	public ReviewItemPage visitReviewItemPage(Review review) {
		return new ReviewItemPage(codebeamerPage, review).visit();
	}

	public AllReviewPage visitAllReviewPage() {
		return new AllReviewPage(codebeamerPage).visit();
	}

	public ReviewAdminPage visitReviewAdminPage() {
		return new ReviewAdminPage(codebeamerPage).visit();
	}

	public ViewReportPage visitReportPage(Report report) {
		return new ViewReportPage(codebeamerPage, report).visit();
	}

	public EditReportPage visitEditReportPage(Report report) {
		return new EditReportPage(codebeamerPage, report).visit();
	}
  
	public UserAccountEditPage visitUserAccountEditPage() {
		return new UserAccountEditPage(codebeamerPage, user).visit();
	}

	public ProfilePhotoPage visitProfilePhotoPage() {
		return new ProfilePhotoPage(codebeamerPage, user).visit();
	}

	public NewTraceabilityReportPage visitNewTraceabilityReportPage() {
		return new NewTraceabilityReportPage(codebeamerPage).visit();
	}

	public ReviewStatisticsPage visitReviewStatisticsPage(Review review) {
		return new ReviewStatisticsPage(codebeamerPage, review).visit();
	}

	public SystemAdminDashboardPage visitSystemAdminDashboardPage() {
		return new SystemAdminDashboardPage(codebeamerPage).visit();
	}

	public SystemAdminUserGroupsPage visitSystemAdminUserGroupsPage() {
		return new SystemAdminUserGroupsPage(codebeamerPage).visit();
	}

	public SystemAdminCreateNewGroupPage visitSystemAdminCreateNewGroupPage() {
		return new SystemAdminCreateNewGroupPage(codebeamerPage).visit();
	}

	public CreateReviewPage visitCreateReviewPage() {
		return new CreateReviewPage(codebeamerPage).visit();
	}

	public MergeRequestsPage visitMergeRequestsPage() {
		return new MergeRequestsPage(codebeamerPage).visit();
	}

	public ProjectAdminPage visitProjectAdminPage(Project project) {
		return new ProjectAdminPage(codebeamerPage, project).visit();
	}

	public CreateReviewFromReportsPage visitReportCreateReviewPage(Report report) {
		return new CreateReviewFromReportsPage(codebeamerPage, report).visit();
	}
}
