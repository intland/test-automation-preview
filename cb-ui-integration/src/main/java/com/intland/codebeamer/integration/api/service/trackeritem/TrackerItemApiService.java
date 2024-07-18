package com.intland.codebeamer.integration.api.service.trackeritem;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.intland.codebeamer.integration.api.ApiUser;
import com.intland.codebeamer.integration.api.service.AbstractApiService;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.role.RoleApiService;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.api.service.tracker.TrackerFieldApiService;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.integration.configuration.ApplicationConfiguration;
import com.intland.swagger.client.internal.ApiException;
import com.intland.swagger.client.internal.api.TrackerItemApi;
import com.intland.swagger.client.model.TrackerItem;
import com.intland.swagger.client.model.TrackerItemSearchRequest;
import com.intland.swagger.client.model.TrackerItemSearchResult;

public class TrackerItemApiService extends AbstractApiService {

	private static final Logger logger = LogManager.getLogger();
	
	private TrackerItemApi trackerItemApi;

	private ProjectApiService projectApiService;

	private UserApiService userApiService;

	private RoleApiService roleApiService;

	private TrackerFieldApiService trackerFieldApiService;

	public TrackerItemApiService(ApplicationConfiguration applicationConfiguration) {
		this(applicationConfiguration, applicationConfiguration.getApiUser());
	}
	
	public TrackerItemApiService(ApplicationConfiguration applicationConfiguration, String username) {
		this(applicationConfiguration, new ApiUser(username, UserApiService.DEFAULT_PASSWORD));
	}
	
	public TrackerItemApiService(ApplicationConfiguration applicationConfiguration, String username, String password) {
		this(applicationConfiguration, new ApiUser(username, password));
	}
	
	public TrackerItemApiService(ApplicationConfiguration applicationConfiguration, ApiUser apiUser) {
		super(applicationConfiguration, apiUser);
		this.trackerItemApi = new TrackerItemApi(getUserApiClient());
		
		this.trackerFieldApiService = dataManagerService.getTrackerFieldApiService(apiUser);
		this.projectApiService = dataManagerService.getProjectApiService(apiUser);
		this.userApiService = dataManagerService.getUserApiService(apiUser);
		this.roleApiService = dataManagerService.getRoleApiService(apiUser);
	}

	public TrackerItemId createTrackerItem(Project project, String trackerName, Function<CreateTrackerItemBuilder, CreateTrackerItemBuilder> builder) {
		return createTrackerItem(projectApiService.findTrackerByName(project, trackerName), builder);
	}
	
	public TrackerItemId createTrackerItem(TrackerId trackerId, Function<CreateTrackerItemBuilder, CreateTrackerItemBuilder> builder) {
		return builder.apply(new CreateTrackerItemBuilder(trackerId, this.trackerFieldApiService, this.trackerItemApi, this.projectApiService, this.userApiService, this.roleApiService, this)).create();
	}

	public void updateTrackerItem(TrackerItemId trackerItemId, Function<UpdateTrackerItemBuilder, UpdateTrackerItemBuilder> builder) {
		try {
			TrackerItem trackerItem = trackerItemApi.getTrackerItem(trackerItemId.id(), null, null);
			builder.apply(new UpdateTrackerItemBuilder(new TrackerId(trackerItem.getTracker().getId()), trackerItem,
					this.trackerFieldApiService, this.trackerItemApi, this.projectApiService, this.userApiService, this.roleApiService, this)).update();
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public void deleteTrackerItemByName(Tracker tracker, String trackerItemName) {
		deleteTrackerItemByName(tracker.id(), trackerItemName);
	}

	public void deleteTrackerItemByName(TrackerId trackerId, String trackerItemName) {
		TrackerItemId trackerItem = findTrackerItemByName(trackerId, trackerItemName);
		deleteTrackerItem(trackerItem);
	}

	public void deleteTrackerItem(TrackerItemId trackerItemId) {
		deleteTrackerItem(trackerItemId.id());
	}

	public void deleteTrackerItem(Integer trackerItemId) {
		try {
			trackerItemApi.deleteTrackerItem(trackerItemId);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public TrackerItemId findTrackerItemByName(Tracker tracker, String trackerItemName) {
		return findTrackerItemByName(tracker.id(), trackerItemName);
	}
	
	public TrackerItemId findTrackerItemByName(TrackerId trackerId, String trackerItemName) {
		List<TrackerItemId> trackerItemsByNames = findTrackerItemByNames(trackerId, trackerItemName);
		if (CollectionUtils.size(trackerItemsByNames) > 1) {
			throw new IllegalStateException("More than one tracker item is found by name: '%s'".formatted(trackerItemName));
		}
		
		return trackerItemsByNames.getFirst();
	}
	
	public List<TrackerItemId> findTrackerItemByNames(TrackerId trackerId, String... trackerItemName) {
		Objects.requireNonNull(trackerItemName, "TrackerItemName cannot be null");
		
		try {
			String trackerItemNames = Arrays.stream(trackerItemName)
					.map(name -> "summary = '%s'".formatted(name)) // TODO Escape name
					.collect(Collectors.joining(" OR ")); 

			String queryString = "tracker.id IN (%s) AND (%s)".formatted(trackerId.id(), trackerItemNames);
			logger.debug("Query string: {}", queryString);
			
			TrackerItemSearchResult result = this.trackerItemApi.findTrackerItemsByCbQL(new TrackerItemSearchRequest().queryString(queryString));
			
			List<TrackerItem> items = result.getItems();
			if (CollectionUtils.isEmpty(items)) {
				throw new IllegalStateException("Tracker item is not found by name: '%s'".formatted(trackerItemNames));
			}

			return items.stream()
					.sorted((t1, t2) -> indexOf(t1, trackerItemName).compareTo(indexOf(t2, trackerItemName))) // keep the original order
					.map(trackerItem -> new TrackerItemId(trackerItem.getId()))
					.toList();
		} catch (ApiException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
		
	}

	private Integer indexOf(TrackerItem trackerItem, String... trackerItemName) {
		return Integer.valueOf(ArrayUtils.indexOf(trackerItemName, trackerItem.getName()));
	}

}
