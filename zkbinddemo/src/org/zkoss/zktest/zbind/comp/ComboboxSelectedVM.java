package org.zkoss.zktest.zbind.comp;

import org.zkoss.bind.NotifyChange;

public class ComboboxSelectedVM {

	String selected = "itemB";

	public String getSelected() {
		return selected;
	}

	@NotifyChange
	public void setSelected(String selected) {
		this.selected = selected;
	}

}