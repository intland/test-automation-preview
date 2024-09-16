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

package com.intland.codebeamer.integration.api.service.reviewhub;

import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.service.AbstractApiService;
import com.intland.codebeamer.integration.api.service.project.ProjectId;
import com.intland.codebeamer.integration.api.service.reviewhub.model.Review;
import com.intland.codebeamer.integration.api.service.reviewhub.model.ReviewId;
import com.intland.codebeamer.integration.api.service.reviewhub.model.ReviewTemplate;
import com.intland.codebeamer.integration.api.service.reviewhub.model.ReviewTemplateId;
import com.intland.codebeamer.integration.api.service.role.RoleApiService;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.api.service.usergroup.UserGroupApiService;
import com.intland.codebeamer.integration.configuration.ApplicationConfiguration;
import com.intland.codebeamer.integration.util.HttpStatus;
import com.intland.swagger.client.internal.ApiException;
import com.intland.swagger.client.internal.ApiResponse;
import com.intland.swagger.client.internal.api.ReviewsApi;
import com.intland.swagger.client.model.IdNamed;
import com.intland.swagger.client.model.Identifier;
import com.intland.swagger.client.model.ReviewTypeRequest;

public class ReviewHubApiService extends AbstractApiService {

	private static final Logger logger = LogManager.getLogger(ReviewHubApiService.class);

	private ReviewsApi reviewsApi;

	private UserApiService userService;

	private UserGroupApiService userGroupApiService;

	private RoleApiService roleApiService;

	public ReviewHubApiService(ApplicationConfiguration applicationConfiguration) {
		this(applicationConfiguration, applicationConfiguration.getApiUser());
	}

	public ReviewHubApiService(ApplicationConfiguration applicationConfiguration, String username) {
		this(applicationConfiguration, new ApiUser(username, UserApiService.DEFAULT_PASSWORD));
	}

	public ReviewHubApiService(ApplicationConfiguration applicationConfiguration, String username, String password) {
		this(applicationConfiguration, new ApiUser(username, password));
	}

	public ReviewHubApiService(ApplicationConfiguration applicationConfiguration, ApiUser apiUser) {
		super(applicationConfiguration, apiUser);
		this.reviewsApi = new ReviewsApi(getUserApiClient());

		this.userService = dataManagerService.getUserApiService(apiUser);
		this.userGroupApiService = dataManagerService.getUserGroupApiService(apiUser);
		this.roleApiService = dataManagerService.getRoleApiService(apiUser);
	}

	public ReviewTemplate createReviewTemplate(ProjectId project, String templateName,
			Function<CreateReviewTemplateBuilder, CreateReviewTemplateBuilder> builder) {
		com.intland.swagger.client.model.ReviewTemplate reviewTemplate = builder.apply(
				new CreateReviewTemplateBuilder(project, templateName, this.userService, this.userGroupApiService,
						this.roleApiService)).build();
		try {
			IdNamed reviewTemplateResult = reviewsApi.createReviewTemplate(reviewTemplate);
			return new ReviewTemplate(reviewTemplateResult.getName(), new ReviewTemplateId(reviewTemplateResult.getId()));
		} catch (ApiException e) {
			throw new RuntimeException(e);
		}
	}

	public Review createReview(String reviewName, Function<ReviewInformationBuilder, ReviewInformationBuilder> builder) {
		ReviewTypeRequest reviewTypeRequest = builder.apply(
				new ReviewInformationBuilder(new ReviewTypeRequest().reviewName(reviewName), this.userService, this.userGroupApiService,
						this.roleApiService)).build();
		try {
			Identifier reviewIdObject = reviewsApi.createReview(reviewTypeRequest);
			if (reviewIdObject.getId() == null) {
				throw new IllegalArgumentException("ReviewId is null");
			}
			return new Review(new ReviewId(reviewIdObject.getId()), reviewName);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public boolean deleteReviewTemplate(ReviewTemplate reviewTemplate) {
		try {
			ApiResponse<Void> apiResponse = reviewsApi.deleteReviewTemplateWithHttpInfo(reviewTemplate.id().id());
			return apiResponse.getStatusCode() == HttpStatus.NO_CONTENT.getCode();
		} catch (ApiException e) {
			if (e.getCode() == HttpStatus.NOT_FOUND.getCode()) {
				return false;
			}
			throw new IllegalStateException(e.getMessage(), e);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public boolean cancelReview(Review review) {
		try {
			ApiResponse<Void> apiResponse = reviewsApi.cancelReviewWithHttpInfo(review.id().id());
			return apiResponse.getStatusCode() == HttpStatus.NO_CONTENT.getCode();
		} catch (ApiException e) {
			if (e.getCode() == HttpStatus.BAD_REQUEST.getCode()) {
				logger.debug("Review cancel operation failed: status is already cancelled.", e);
				return false;
			}
			throw new IllegalStateException(e.getMessage(), e);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
}
