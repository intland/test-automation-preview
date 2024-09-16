package com.intland.codebeamer.integration.classic.page.user;

import java.util.function.Consumer;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.user.component.actionbar.dashboard.UserDashboardActionbarComponent;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("UserMyWikiPage")
public class UserMyWikiPage extends AbstractCodebeamerPage<UserMyWikiPage> {

	private static final String USER_WIKI_PAGE_PATH = "user";

	@Component("Action bar")
	private final UserDashboardActionbarComponent dashboardActionbarComponent;
	
	public UserMyWikiPage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
		this.dashboardActionbarComponent = new UserDashboardActionbarComponent(getCodebeamerPage());
	}

	@Action("Visit")
	public UserMyWikiPage visit() {
		navigate(USER_WIKI_PAGE_PATH);
		return isActive();
	}

	@Override
	public UserMyWikiPage isActive() {
		assertUrl(USER_WIKI_PAGE_PATH, "User wiki page should be the active page");
		return this;
	}

	public UserDashboardActionbarComponent getUserDashboardActionbarComponent() {
		return dashboardActionbarComponent;
	}

	@Override
	public UserMyWikiPage assertPage(Consumer<UserMyWikiPage> assertion) {
		assertion.accept(this);
		return this;
	}

}
