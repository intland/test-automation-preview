package com.intland.codebeamer.integration.api.service.baseline;

import com.intland.codebeamer.integration.api.service.project.ProjectId;
import com.intland.codebeamer.integration.api.service.tracker.TrackerId;

public record Baseline(BaselineId id, String name, ProjectId projectId, TrackerId tracker) {

}
