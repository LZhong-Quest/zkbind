package org.zkoss.zktest.zbind.issue;

import org.zkoss.bind.NotifyChange;

public class B00619 {

	String selectedTab = "tabB";

	public String getSelectedTab() {
		return selectedTab;
	}

	@NotifyChange
	public void setSelectedTab(String selectedTab) {
		this.selectedTab = selectedTab;
	}
	
	
}
