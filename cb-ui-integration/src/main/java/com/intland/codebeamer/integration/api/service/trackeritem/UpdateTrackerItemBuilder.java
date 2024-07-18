package com.intland.codebeamer.integration.api.service.trackeritem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.intland.codebeamer.integration.api.service.project.ProjectApiService;
import com.intland.codebeamer.integration.api.service.role.RoleApiService;
import com.intland.codebeamer.integration.api.service.tracker.TrackerFieldApiService;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;
import com.intland.codebeamer.integration.api.service.user.UserApiService;
import com.intland.codebeamer.utils.SafeStopWatch;
import com.intland.swagger.client.internal.api.TrackerItemApi;
import com.intland.swagger.client.model.TrackerItem;

public class UpdateTrackerItemBuilder extends AbstractTrackerItemBuilder<UpdateTrackerItemBuilder> {

	private static final Logger logger = LogManager.getLogger();

	public UpdateTrackerItemBuilder(TrackerId trackerId, TrackerItem trackerItem, TrackerFieldApiService trackerFieldApiService,
			TrackerItemApi trackerItemApi, ProjectApiService projectApiService, UserApiService userService,
			RoleApiService roleApiService, TrackerItemApiService trackerItemApiService) {
		super(trackerId, trackerItem, trackerFieldApiService, trackerItemApi, projectApiService, userService, roleApiService,
				trackerItemApiService);
	}

	public void update() {
		try {
			SafeStopWatch.measure(() -> callUpdateTrackerItem(), (taskTimeMillis) -> logger
					.debug("Creating a new tracker item Took {}ms", Long.valueOf(taskTimeMillis)));
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	private void callUpdateTrackerItem() {
		try {
			this.trackerItemApi.updateTrackerItem(trackerItem.getId(), trackerItem, null, null);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

}