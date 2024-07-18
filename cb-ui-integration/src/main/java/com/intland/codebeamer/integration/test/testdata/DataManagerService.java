package com.intland.codebeamer.integration.test.testdata;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.service.baseline.BaselineApiService;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.projectcategory.ProjectCategoryApiService;
import com.intland.codebeamer.integration.api.service.role.RoleApiService;
import com.intland.codebeamer.integration.api.service.tracker.TrackerApiService;
import com.intland.codebeamer.integration.api.service.tracker.TrackerFieldApiService;
import com.intland.codebeamer.integration.api.service.trackeritem.TrackerItemApiService;
import com.intland.codebeamer.integration.api.service.user.User;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.api.service.usergroup.UserGroupApiService;
import com.intland.codebeamer.integration.configuration.ApplicationConfiguration;

public class DataManagerService {

	private ApplicationConfiguration applicationConfiguration;

	public DataManagerService(ApplicationConfiguration applicationConfiguration) {
		super();
		this.applicationConfiguration = applicationConfiguration;
	}

	public UserApiService getUserApiService() {
		return new UserApiService(this.applicationConfiguration);
	}

	public UserApiService getUserApiService(User activeUser) {
		return new UserApiService(this.applicationConfiguration, activeUser.getName());
	}

	public UserApiService getUserApiService(ApiUser activeUser) {
		return new UserApiService(this.applicationConfiguration, activeUser.username(), activeUser.password());
	}
	
	public UserApiService getUserApiService(User activeUser, String password) {
		return new UserApiService(this.applicationConfiguration, activeUser.getName(), password);
	}
	
	public UserGroupApiService getUserGroupApiService() {
		return new UserGroupApiService(this.applicationConfiguration);
	}

	public UserGroupApiService getUserGroupApiService(User activeUser) {
		return new UserGroupApiService(this.applicationConfiguration, activeUser.getName());
	}

	public UserGroupApiService getUserGroupApiService(ApiUser activeUser) {
		return new UserGroupApiService(this.applicationConfiguration, activeUser.username(), activeUser.password());
	}
	
	public UserGroupApiService getUserGroupApiService(User activeUser, String password) {
		return new UserGroupApiService(this.applicationConfiguration, activeUser.getName(), password);
	}

	public TrackerItemApiService getTrackerItemApiService() {
		return new TrackerItemApiService(this.applicationConfiguration);
	}

	public TrackerItemApiService getTrackerItemApiService(User activeUser) {
		return new TrackerItemApiService(this.applicationConfiguration, activeUser.getName());
	}

	public TrackerItemApiService getTrackerItemApiService(ApiUser activeUser) {
		return new TrackerItemApiService(this.applicationConfiguration, activeUser.username(), activeUser.password());
	}

	public TrackerItemApiService getTrackerItemApiService(User activeUser, String password) {
		return new TrackerItemApiService(this.applicationConfiguration, activeUser.getName(), password);
	}

	public ProjectApiService getProjectApiService() {
		return new ProjectApiService(this.applicationConfiguration);
	}

	public ProjectApiService getProjectApiService(User activeUser) {
		return new ProjectApiService(this.applicationConfiguration, activeUser.getName());
	}
	
	public ProjectApiService getProjectApiService(ApiUser activeUser) {
		return new ProjectApiService(this.applicationConfiguration, activeUser.username(), activeUser.password());
	}

	public ProjectApiService getProjectApiService(User activeUser, String password) {
		return new ProjectApiService(this.applicationConfiguration, activeUser.getName(), password);
	}

	public RoleApiService getRoleApiService() {
		return new RoleApiService(this.applicationConfiguration);
	}

	public RoleApiService getRoleApiService(User activeUser) {
		return new RoleApiService(this.applicationConfiguration, activeUser.getName());
	}

	public RoleApiService getRoleApiService(ApiUser activeUser) {
		return new RoleApiService(this.applicationConfiguration, activeUser.username(), activeUser.password());
	}
	
	public RoleApiService getRoleApiService(User activeUser, String password) {
		return new RoleApiService(this.applicationConfiguration, activeUser.getName(), password);
	}

	public ProjectCategoryApiService getProjectCategoryApiService() {
		return new ProjectCategoryApiService(this.applicationConfiguration);
	}

	public ProjectCategoryApiService getProjectCategoryApiService(User activeUser) {
		return new ProjectCategoryApiService(this.applicationConfiguration, activeUser.getName());
	}

	public ProjectCategoryApiService getProjectCategoryApiService(User activeUser, String password) {
		return new ProjectCategoryApiService(this.applicationConfiguration, activeUser.getName(), password);
	}

	public TrackerApiService getTrackerApiService() {
		return new TrackerApiService(this.applicationConfiguration);
	}

	public TrackerApiService getTrackerApiService(User activeUser) {
		return new TrackerApiService(this.applicationConfiguration, activeUser.getName());
	}

	public TrackerApiService getTrackerApiService(User activeUser, String password) {
		return new TrackerApiService(this.applicationConfiguration, activeUser.getName(), password);
	}
	
	public TrackerFieldApiService getTrackerFieldApiService() {
		return new TrackerFieldApiService(this.applicationConfiguration);
	}

	public TrackerFieldApiService getTrackerFieldApiService(ApiUser activeUser) {
		return new TrackerFieldApiService(this.applicationConfiguration, activeUser.username(), activeUser.password());
	}

	public BaselineApiService getBaselineApiService() {
		return new BaselineApiService(this.applicationConfiguration);
	}

	public BaselineApiService getBaselineApiService(User activeUser) {
		return new BaselineApiService(this.applicationConfiguration, activeUser.getName());
	}
}