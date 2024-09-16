package com.intland.codebeamer.integration.test.testdata;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.service.applicationconfiguration.ApplicationConfigurationRestApiService;
import com.intland.codebeamer.integration.api.service.association.AssociationApiService;
import com.intland.codebeamer.integration.api.service.baseline.BaselineApiService;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.projectcategory.ProjectCategoryApiService;
import com.intland.codebeamer.integration.api.service.reports.ReportsApiService;
import com.intland.codebeamer.integration.api.service.reports.TraceabilityApiService;
import com.intland.codebeamer.integration.api.service.reviewhub.ReviewHubApiService;
import com.intland.codebeamer.integration.api.service.role.RoleApiService;
import com.intland.codebeamer.integration.api.service.sharedfield.SharedFieldApiService;
import com.intland.codebeamer.integration.api.service.testmanagement.testrun.TestRunApiService;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.tracker.TrackerFieldApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.api.service.usergroup.UserGroupApiService;
import com.intland.codebeamer.integration.api.service.utility.UtilityApiService;
import com.intland.codebeamer.integration.api.service.workingset.WorkingSetApiService;
import com.intland.codebeamer.integration.configuration.ApplicationConfiguration;

public class DataManagerService {

	private ApplicationConfiguration applicationConfiguration;

	public DataManagerService(ApplicationConfiguration applicationConfiguration) {
		super();
		this.applicationConfiguration = applicationConfiguration;
	}

	/**
	 * Creates a UserApiService which authenticates API calls using the user from the configuration file.
	 *
	 * @return UserApiService object
	 */
	public UserApiService getUserApiServiceWithConfigUser() {
		return new UserApiService(this.applicationConfiguration);
	}

	public UserApiService getUserApiService(User user) {
		return new UserApiService(this.applicationConfiguration, user.getName());
	}

	public UserApiService getUserApiService(ApiUser apiUser) {
		return new UserApiService(this.applicationConfiguration, apiUser.username(), apiUser.password());
	}
	
	public UserApiService getUserApiService(User user, String password) {
		return new UserApiService(this.applicationConfiguration, user.getName(), password);
	}

	public UserGroupApiService getUserGroupApiService(User user) {
		return new UserGroupApiService(this.applicationConfiguration, user.getName());
	}

	public UserGroupApiService getUserGroupApiService(ApiUser apiUser) {
		return new UserGroupApiService(this.applicationConfiguration, apiUser.username(), apiUser.password());
	}
	
	public UserGroupApiService getUserGroupApiService(User user, String password) {
		return new UserGroupApiService(this.applicationConfiguration, user.getName(), password);
	}

	public AssociationApiService getAssociationApiService(User user) {
		return new AssociationApiService(this.applicationConfiguration, user.getName());
	}

	public TrackerItemApiService getTrackerItemApiService(User user) {
		return new TrackerItemApiService(this.applicationConfiguration, user.getName());
	}

	public TrackerItemApiService getTrackerItemApiService(ApiUser apiUser) {
		return new TrackerItemApiService(this.applicationConfiguration, apiUser.username(), apiUser.password());
	}

	public TrackerItemApiService getTrackerItemApiService(User user, String password) {
		return new TrackerItemApiService(this.applicationConfiguration, user.getName(), password);
	}

	public ProjectApiService getProjectApiService(User user) {
		return new ProjectApiService(this.applicationConfiguration, user.getName());
	}
	
	public ProjectApiService getProjectApiService(ApiUser apiUser) {
		return new ProjectApiService(this.applicationConfiguration, apiUser.username(), apiUser.password());
	}

	public ProjectApiService getProjectApiService(User user, String password) {
		return new ProjectApiService(this.applicationConfiguration, user.getName(), password);
	}

	public RoleApiService getRoleApiService(User user) {
		return new RoleApiService(this.applicationConfiguration, user.getName());
	}

	public RoleApiService getRoleApiService(ApiUser apiUser) {
		return new RoleApiService(this.applicationConfiguration, apiUser.username(), apiUser.password());
	}
	
	public RoleApiService getRoleApiService(User user, String password) {
		return new RoleApiService(this.applicationConfiguration, user.getName(), password);
	}

	public ProjectCategoryApiService getProjectCategoryApiService() {
		return new ProjectCategoryApiService(this.applicationConfiguration);
	}

	public ProjectCategoryApiService getProjectCategoryApiService(User user) {
		return new ProjectCategoryApiService(this.applicationConfiguration, user.getName());
	}

	public ProjectCategoryApiService getProjectCategoryApiService(User user, String password) {
		return new ProjectCategoryApiService(this.applicationConfiguration, user.getName(), password);
	}

	public TrackerApiService getTrackerApiService(ApiUser apiUser) {
		return new TrackerApiService(this.applicationConfiguration, apiUser.username(), apiUser.password());
	}

	public TrackerApiService getTrackerApiService(User user) {
		return new TrackerApiService(this.applicationConfiguration, user.getName());
	}

	public TrackerApiService getTrackerApiService(User user, String password) {
		return new TrackerApiService(this.applicationConfiguration, user.getName(), password);
	}
	
	public TrackerFieldApiService getTrackerFieldApiService() {
		return new TrackerFieldApiService(this.applicationConfiguration);
	}

	public TrackerFieldApiService getTrackerFieldApiService(ApiUser apiUser) {
		return new TrackerFieldApiService(this.applicationConfiguration, apiUser.username(), apiUser.password());
	}

	public TrackerFieldApiService getTrackerFieldApiService(User user) {
		return new TrackerFieldApiService(this.applicationConfiguration, user.getName());
	}

	public BaselineApiService getBaselineApiService(User user) {
		return new BaselineApiService(this.applicationConfiguration, user.getName());
	}

	public ReviewHubApiService getReviewHubApiService(User user) {
		return new ReviewHubApiService(this.applicationConfiguration, user.getName());
	}

	public ReviewHubApiService getReviewHubApiService(ApiUser apiUser) {
		return new ReviewHubApiService(this.applicationConfiguration, apiUser.username(), apiUser.password());
	}

	public ReviewHubApiService getReviewHubApiService(User user, String password) {
		return new ReviewHubApiService(this.applicationConfiguration, user.getName(), password);
	}

	public TestRunApiService getTestRunApiService(User user) {
		return new TestRunApiService(this.applicationConfiguration, user.getName());
	}

	public ReportsApiService getReportsApiService(User user) {
		return new ReportsApiService(this.applicationConfiguration, user.getName());
	}

	public ApplicationConfigurationRestApiService getApplicationConfigurationRestApiService(User user) {
		return new ApplicationConfigurationRestApiService(this.applicationConfiguration, user.getName());
	}

	public SharedFieldApiService getSharedFieldApiService() {
		return new SharedFieldApiService(this.applicationConfiguration);
	}

	public SharedFieldApiService getSharedFieldApiService(User user) {
		return new SharedFieldApiService(this.applicationConfiguration, user.getName());
	}

	public SharedFieldApiService getSharedFieldApiService(User user, String password) {
		return new SharedFieldApiService(this.applicationConfiguration, user.getName(), password);
	}

	public WorkingSetApiService getWorkingSetApiService() {
		return new WorkingSetApiService(this.applicationConfiguration);
	}

	public WorkingSetApiService getWorkingSetApiService(User user) {
		return new WorkingSetApiService(this.applicationConfiguration, user.getName());
	}

	public WorkingSetApiService getWorkingSetApiService(User user, String password) {
		return new WorkingSetApiService(this.applicationConfiguration, user.getName(), password);
	}

	public UtilityApiService getUtilityApiService() {
		return new UtilityApiService(this.applicationConfiguration);
	}

	public UtilityApiService getUtilityApiService(User user) {
		return new UtilityApiService(this.applicationConfiguration, user.getName());
	}

	public UtilityApiService getUtilityApiService(User user, String password) {
		return new UtilityApiService(this.applicationConfiguration, user.getName(), password);
	}

	public TraceabilityApiService getTraceabilityApiService(User user) {
		return new TraceabilityApiService(this.applicationConfiguration, user.getName());
	}
}