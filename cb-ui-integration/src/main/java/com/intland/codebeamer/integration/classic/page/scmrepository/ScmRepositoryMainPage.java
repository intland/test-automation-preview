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

package com.intland.codebeamer.integration.classic.page.scmrepository;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.project.Project;
import com.intland.codebeamer.integration.classic.page.scmrepository.component.ScmRepositoryMainActionBarComponent;
import com.intland.codebeamer.integration.classic.page.scmrepository.component.ScmRepositoryMainContentComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:cbalog@ptc.com">Csongor Balog</a>
 */
@Page("ScmRepositoryMainPage")
public class ScmRepositoryMainPage extends AbstractCodebeamerPage<ScmRepositoryMainPage> {

	private static final String SCM_PROJECT_REPOSITORIES_PATH = "project/%s/repositories" ;

	private static final Pattern SCM_PROJECT_REPOSITORIES_PATTERN = Pattern.compile(".*/project/.*/repositories.*");

	private final Project project;

	@Component("Scm Repository main action bar buttons")
	private final ScmRepositoryMainActionBarComponent scmRepositoryMainActionBarComponent;

	@Component("Scm Repository main content component")
	private final ScmRepositoryMainContentComponent scmRepositoryMainContentComponent;

	public ScmRepositoryMainPage(CodebeamerPage codebeamerPage, Project project) {
		super(codebeamerPage);
		this.project = project;
		this.scmRepositoryMainActionBarComponent = new ScmRepositoryMainActionBarComponent(getCodebeamerPage());
		this.scmRepositoryMainContentComponent = new ScmRepositoryMainContentComponent(getCodebeamerPage());
	}

	@Action("Visit")
	public ScmRepositoryMainPage visit() {
		navigate(String.format(SCM_PROJECT_REPOSITORIES_PATH, project.name()));
		return isActive();
	}

	@Override
	public ScmRepositoryMainPage isActive() {
		assertUrl(SCM_PROJECT_REPOSITORIES_PATTERN, "Scm Repositories main page should be the active page");
		return this;
	}

	public ScmRepositoryMainActionBarComponent getActionBarComponent() {
		return scmRepositoryMainActionBarComponent;
	}

	public ScmRepositoryMainPage actionBarComponent(Consumer<ScmRepositoryMainActionBarComponent> formConsumer) {
		formConsumer.accept(scmRepositoryMainActionBarComponent);
		return this;
	}

	public ScmRepositoryMainPage contentComponent(Consumer<ScmRepositoryMainContentComponent> formConsumer) {
		formConsumer.accept(scmRepositoryMainContentComponent);
		return this;
	}

}
