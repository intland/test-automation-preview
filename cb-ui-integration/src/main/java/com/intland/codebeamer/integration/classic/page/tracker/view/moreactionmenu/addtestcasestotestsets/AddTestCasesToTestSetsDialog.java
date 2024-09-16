package com.intland.codebeamer.integration.classic.page.tracker.view.moreactionmenu.addtestcasestotestsets;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.api.service.tracker.Tracker;
import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerDialog;

public class AddTestCasesToTestSetsDialog extends AbstractCodebeamerDialog {

	@Component("Assign test cases to test sets form")
	private final AddTestCasesToTestSetsFormComponent addTestCasesToTestSetsFormComponent;

	public AddTestCasesToTestSetsDialog(CodebeamerPage codebeamerPage, Tracker tracker) {
		super(codebeamerPage, "iframe[src*='assignTestCasesToSets.spr'],iframe[src*='empty.jsp']");
		this.addTestCasesToTestSetsFormComponent = new AddTestCasesToTestSetsFormComponent(getCodebeamerPage(),
				getDialogLocator(), tracker);
	}

	public AddTestCasesToTestSetsFormComponent getAddTestCasesToTestSetsFormComponent() {
		return addTestCasesToTestSetsFormComponent;
	}
}
