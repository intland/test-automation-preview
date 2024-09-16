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
import com.intland.codebeamer.integration.classic.page.scmrepository.component.ScmRepositoryCreateActionBarComponent;
import com.intland.codebeamer.integration.classic.page.scmrepository.component.ScmRepositoryCreateContentComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:cbalog@ptc.com">Csongor Balog</a>
 */
@Page("ScmRepositoryCreatePage")
public class ScmRepositoryCreatePage extends AbstractCodebeamerPage<ScmRepositoryCreatePage> {

	private static final String SCM_PROJECT_SETTINGS_PATH = "scmProjectSettings.spr" ;

	private static final Pattern SCM_PROJECT_SETTINGS_PATTERN = Pattern.compile(".*/scmProjectSettings.spr.*");

	private final Project project;

	@Component("Scm Repository create action bar buttons")
	private final ScmRepositoryCreateActionBarComponent scmRepositoryCreateActionBarComponent;

	@Component("Scm Repository create content component")
	private final ScmRepositoryCreateContentComponent scmRepositoryCreateContentComponent;

	public ScmRepositoryCreatePage(CodebeamerPage codebeamerPage, Project project) {
		super(codebeamerPage);
		this.project = project;
		this.scmRepositoryCreateActionBarComponent = new ScmRepositoryCreateActionBarComponent(getCodebeamerPage());
		this.scmRepositoryCreateContentComponent = new ScmRepositoryCreateContentComponent(getCodebeamerPage());
	}

	@Action("Visit")
	public ScmRepositoryCreatePage visit() {
		navigate(String.format(SCM_PROJECT_SETTINGS_PATH, project.name()));
		return isActive();
	}

	@Override
	public ScmRepositoryCreatePage isActive() {
		assertUrl(SCM_PROJECT_SETTINGS_PATTERN, "Scm Repositories create page should be the active page");
		return this;
	}

	public ScmRepositoryCreateActionBarComponent getActionBarComponent() {
		return scmRepositoryCreateActionBarComponent;
	}

	public ScmRepositoryCreatePage actionBarComponent(Consumer<ScmRepositoryCreateActionBarComponent> formConsumer) {
		formConsumer.accept(scmRepositoryCreateActionBarComponent);
		return this;
	}

	public ScmRepositoryCreatePage contentComponent(Consumer<ScmRepositoryCreateContentComponent> formConsumer) {
		formConsumer.accept(scmRepositoryCreateContentComponent);
		return this;
	}

}
