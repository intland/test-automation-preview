package com.intland.codebeamer.integration.api;

public interface DataFactory {

	Integer createProject(String projectName);

	Integer createTracker(Integer projectId, String projectName);

}
