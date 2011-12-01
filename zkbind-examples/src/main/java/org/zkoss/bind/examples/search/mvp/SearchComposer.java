/* SearchVM.java

	Purpose:
		
	Description:
		
	History:
		2011/10/25 Created by Dennis Chen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.bind.examples.search.mvp;

import java.text.DecimalFormat;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Binder;
import org.zkoss.bind.Converter;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.examples.search.FakeSearchService;
import org.zkoss.bind.examples.search.Item;
import org.zkoss.bind.examples.search.SearchService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;
/**
 * An implementation in MVP pattern with zkbind
 * @author Hawk
 */
@SuppressWarnings("serial")
public class SearchComposer extends SelectorComposer<Component>{

	//the search condition
	private String filter = "*";
	
	//the search result
	private ListModelList<Item> items;

	private Converter totalPriceConverter;
	//the selected item
	private Item selected;
	private Binder binder;
	//UI component
	@Wire("window")
	private Window window;

	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		comp.setAttribute("presenter", this);
		
	}
	@Override
	public void doFinally() throws Exception {
		super.doFinally();

		binder = (Binder)window.getAttribute("binder");

	}
	
	protected SearchService getSearchService(){
		return new FakeSearchService();
	}
	
	public String getFilter() {
		return filter;
	}
	
	@NotifyChange
	public void setFilter(String filter) {
		this.filter = filter;
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
		items.addAll(getSearchService().search(filter));
		setSelected(null);
		if (binder != null){
			binder.notifyChange(this, "items");
			binder.notifyChange(this, "selected");
		}
	}
	

	public Item getSelected() {
		return selected;
	}

	@NotifyChange
	public void setSelected(Item selected) {
		this.selected = selected;
	}
	

	public Converter getTotalPriceConverter(){
		if(totalPriceConverter!=null){
			return totalPriceConverter;
		}
		return totalPriceConverter = new Converter(){
			public Object coerceToBean(Object val, Component component,
					BindContext ctx) {
				return null;//never called in this example
			}

			public Object coerceToUi(Object val, Component component,
					BindContext ctx) {
				if(val==null) return null;
				String str = new DecimalFormat("$ ###,###,###,##0.00").format((Double)val);
				return str;
			}
			
		};
	}
}
