package org.zkoss.zktest.zbind.comp;

import org.zkoss.bind.NotifyChange;

public class TabSelectedVM {

	String selectedTab1 = "tabB";
	String selectedTab2 = "tabC";
	public String getSelectedTab1() {
		return selectedTab1;
	}
	@NotifyChange
	public void setSelectedTab1(String selectedTab1) {
		this.selectedTab1 = selectedTab1;
	}
	public String getSelectedTab2() {
		return selectedTab2;
	}
	@NotifyChange
	public void setSelectedTab2(String selectedTab2) {
		this.selectedTab2 = selectedTab2;
	}

	
}
