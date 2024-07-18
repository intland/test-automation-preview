package com.intland.codebeamer.integration.api.service.projectcategory;

import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.service.AbstractApiService;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.configuration.ApplicationConfiguration;
import com.intland.swagger.client.internal.api.ProjectApi;
import com.intland.swagger.client.model.DetailedProjectCategory;
import com.intland.swagger.client.model.ProjectCategoryCreateRequest;

public class ProjectCategoryApiService extends AbstractApiService {
	
	private ProjectApi projectApi;

	public ProjectCategoryApiService(ApplicationConfiguration applicationConfiguration) {
		this(applicationConfiguration, applicationConfiguration.getApiUser());
	}
	
	public ProjectCategoryApiService(ApplicationConfiguration applicationConfiguration, String username) {
		this(applicationConfiguration, new ApiUser(username, UserApiService.DEFAULT_PASSWORD));
	}
	
	public ProjectCategoryApiService(ApplicationConfiguration applicationConfiguration, String username, String password) {
		this(applicationConfiguration, new ApiUser(username, password));
	}
	
	public ProjectCategoryApiService(ApplicationConfiguration applicationConfiguration, ApiUser apiUser) {
		super(applicationConfiguration, apiUser);

		this.projectApi = new ProjectApi(getUserApiClient());
	}

	public Optional<ProjectCategory> findCategoryByName(String categoryName) {
		try {
			return projectApi.getProjectCategories().stream()
					.filter(c -> c.getName().equals(categoryName))
					.map(category -> new ProjectCategory(new ProjectCategoryId(category.getId()), category.getName()))
					.findAny();
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
	
	public ProjectCategory createNewCategory() {
		return createNewCategory(randomAlphabetic());
	}
	
	public ProjectCategory createNewCategory(String categoryName) {
		try {
			DetailedProjectCategory category = projectApi.createNewCategory(new ProjectCategoryCreateRequest().categoryName(categoryName));
			return new ProjectCategory(new ProjectCategoryId(category.getId()), category.getName());
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public void deleteCategory(ProjectCategory category) {
		try {
			this.projectApi.deleteCategory(category.id().id());
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	private String randomAlphabetic() {
		return RandomStringUtils.randomAlphabetic(10).toLowerCase();
	}
	
}
