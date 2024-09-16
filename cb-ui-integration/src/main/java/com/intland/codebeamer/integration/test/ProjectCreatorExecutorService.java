package com.intland.codebeamer.integration.test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum ProjectCreatorExecutorService {

	INSTANCE;

	private static final Logger logger = LogManager.getLogger();

	private final ExecutorService executor;

	ProjectCreatorExecutorService() {
		this.executor = Executors.newFixedThreadPool(1);
	}

	public <T> Future<T> submit(Callable<T> task) {
		logger.info("Task is scheduled on the {} service", this.executor);
		return executor.submit(task);
	}
	
	 boolean isRunning() {
		return !executor.isShutdown() && !executor.isTerminated();
	}

	void shutdownNow() {
		try {
			executor.shutdown();

			if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
				logger.error("Tasks did not terminate in time");
				executor.shutdownNow();
			}
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
			Thread.currentThread().interrupt();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

}
