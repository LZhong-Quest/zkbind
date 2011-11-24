/* SearchVM.java

	Purpose:
		
	Description:
		
	History:
		2011/10/25 Created by Dennis Chen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.bind.examples.search.mvp;

import org.zkoss.bind.examples.search.FakeSearchService;
import org.zkoss.bind.examples.search.Item;
import org.zkoss.bind.examples.search.SearchService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.GenericAnnotatedComposer;
import org.zkoss.zk.ui.select.Listen;
import org.zkoss.zk.ui.select.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
/**
 * An implementation in MVP pattern
 * @author Hawk
 */
@SuppressWarnings("serial")
public class SearchComposer extends GenericAnnotatedComposer<Component>{

	//the search result
	ListModelList<Item> items;
	
	//the selected item
	Item selected;
	//UI component
	@Wire
	Textbox filterBox;
	@Wire("button")
	Button searchButton;
	@Wire("#quantityLabel")
	Label quantityLabel;
	@Wire("#detailBox")
	Groupbox detailBox;
	
	
	protected SearchService getSearchService(){
		return new FakeSearchService();
	}
		
	public ListModel<Item> getItems() {
		if(items==null){
			doSearch();
		}
		return items;
	}

	@Listen("onClick = button")
	public void doSearch(){
		items = new ListModelList<Item>();
		items.addAll(getSearchService().search(filterBox.getValue()));
		setSelected(null);
	}
	

	public Item getSelected() {
		return selected;
	}

	public void setSelected(Item selected) {
		this.selected = selected;
		if (selected == null){
			detailBox.setVisible(false);
		}else{
			detailBox.setVisible(true);
			if (selected.getQuantity() <3 ){
				quantityLabel.setSclass("red");
			}else{
				quantityLabel.setSclass("");
			}
		}
	}
	
	
	@Listen("onChange = #filterBox")
	public void changeButtonStatus(){
		if (filterBox.getValue().length()==0){
			searchButton.setDisabled(true);
		}else{
			searchButton.setDisabled(false);
		}
	}
}
