/* ListboxModelVM.java

	Purpose:
		
	Description:
		
	History:
		Created by Dennis

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
 */

package org.zkoss.zktest.bind.issue;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.ListModelList;

/**
 * @author Dennis Chen
 * 
 */
public class F00743_2 {
	private String message1;

	ListModelList<Item> items;


	public F00743_2() {
		items = new ListModelList<Item>();
		items.setMultiple(true);
		items.add(new Item("A"));
		items.add(new Item("B"));
		items.add(new Item("C"));
		items.add(new Item("D"));
		items.add(new Item("E"));
	}

	public List<Item> getItems() {
		return items;
	}

	public String getMessage1() {
		return message1;
	}

	static public class Item {
		String name;
		
		List<String> options = new ArrayList<String>();

		public Item(String name) {
			this.name = name;
			options.add(name+" 0");
			options.add(name+" 1");
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<String> getOptions() {
			return options;
		}
		public String toString(){
			return name;
		}
	}

	@Command @NotifyChange({"items","message1"}) 
	public void reload() {
		java.util.List selection = new java.util.ArrayList();
		for(int i = items.getMinSelectionIndex(); i<=items.getMaxSelectionIndex();i++){
			if(items.isSelectedIndex(i)){
				selection.add(i);
			}
		}
		message1 = "reloaded "+selection;
	}
	@Command @NotifyChange({"message1"}) 
	public void select() {
		message1 = "select";
		items.clearSelection();
		items.addSelectionInterval(1,1);
		items.addSelectionInterval(3,3);
	}
	@Command @NotifyChange({"message1"}) 
	public void clean() {
		message1 = "clean1";
		items.clearSelection();
	}

}
