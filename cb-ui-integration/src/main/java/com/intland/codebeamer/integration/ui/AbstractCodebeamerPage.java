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

package com.intland.codebeamer.integration.ui;

import java.net.URI;
import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import com.intland.codebeamer.integration.CodebeamerPage;

public abstract class AbstractCodebeamerPage<P extends AbstractCodebeamerPage> {
    
	protected static final String SLASH = "/";
	
	private final CodebeamerPage codebeamerPage;

	public AbstractCodebeamerPage(CodebeamerPage codebeamerPage) {
		this.codebeamerPage = codebeamerPage;
	}

	public abstract P isActive();
	
	public P screenshot() {
		return screenshot(p -> StringUtils.EMPTY);	
	}
	
	public P screenshot(Function<P, String> message) {
		URI screenshot = this.codebeamerPage.getCodebeamerScreenshot().screenshot();
		return log(page -> {
			String logMessage = message.apply(page);
			if (StringUtils.isBlank(logMessage)) {
				return screenshot.toString();
			}
			
			return logMessage + " - " + screenshot.toString();
		});	
	}
	
	public P assertPage(Consumer<P> assertion) {
		assertion.accept(getMySelf());
		return getMySelf();
	}
	
	public P log(Function<P, String> message) {
		return log(Level.INFO, message);
	}
	
	public P logDebug(Function<P, String> message) {
		return log(Level.DEBUG, message);
	}
	
	public P sleep(long seconds) {
		try {
			Thread.sleep(Duration.ofSeconds(seconds));
			return getMySelf();
		} catch (InterruptedException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
	
	protected void navigate(String url) {
		getCodebeamerPage().navigate(url);
	}
	
	protected void assertUrl(String expectedPagePath, String message) {
		getCodebeamerPage().getAssertions().assertUrl(expectedPagePath, message);
	}
	
	protected CodebeamerPage getCodebeamerPage() {
		return codebeamerPage;
	}

	public void pause() {
		getCodebeamerPage().pause();
	}
	
	private P log(Level level, Function<P, String> message) {
		LogManager.getLogger(this).log(level, message.apply(getMySelf()));
		return getMySelf();
	}

	private P getMySelf() {
		return (P) this;
	}
	
}
