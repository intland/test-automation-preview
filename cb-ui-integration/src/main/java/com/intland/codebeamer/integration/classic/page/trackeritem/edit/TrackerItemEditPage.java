package com.intland.codebeamer.integration.classic.page.trackeritem.edit;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.classic.page.trackeritem.TrackerItemPage;
import com.intland.codebeamer.integration.sitemap.annotation.Action;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

@Page("TrackerItemEditPage")
public class TrackerItemEditPage extends AbstractCodebeamerPage<TrackerItemEditPage> {

	public TrackerItemEditPage(CodebeamerPage codebeamerPage) {
		super(codebeamerPage);
	}

	@Action("Save")
	public TrackerItemPage save() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TrackerItemEditPage isActive() {
		// TODO Auto-generated method stub
		return null;
	}

}
