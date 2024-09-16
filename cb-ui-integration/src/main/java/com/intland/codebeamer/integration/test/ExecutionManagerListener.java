package com.intland.codebeamer.integration.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IExecutionListener;

public class ExecutionManagerListener implements IExecutionListener {

	private static final Logger logger = LogManager.getLogger();

	@Override
	public void onExecutionStart() {
		logger.info("Start executor");
		if (!ProjectCreatorExecutorService.INSTANCE.isRunning()) {
			throw new IllegalStateException("Executor is not running");
		}
	}

	@Override
	public void onExecutionFinish() {
		logger.info("Stop executor");
		ProjectCreatorExecutorService.INSTANCE.shutdownNow();
	}

}