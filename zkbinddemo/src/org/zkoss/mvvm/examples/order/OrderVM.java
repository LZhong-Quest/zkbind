/* OrderVM.java

	Purpose:
		
	Description:
		
	History:
		2011/10/31 Created by Dennis Chen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
 */
package org.zkoss.mvvm.examples.order;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.NotifyChange;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.zul.ListModelList;

/**
 * @author dennis
 * 
 */
public class OrderVM {

	ListModelList<Order> orders;
	Order selected;

	public ListModelList<Order> getOrders() {
		if (orders == null) {
			orders = new ListModelList<Order>(getService().list());
		}
		return orders;
	}

	public Order getSelected() {
		return selected;
	}

	@NotifyChange({"selected","itemMessages"})
	public void setSelected(Order selected) {
		this.selected = selected;
		itemMessages.clear();
	}

	public OrderService getService() {
		return FakeOrderService.getInstance();
	}
	
	@NotifyChange({"selected","orders","itemMessages"})
	public void newOrder(){
		Order order = new Order();
		getOrders().add(order);
		selected = order;
		itemMessages.clear();
	}
	
	@NotifyChange({"selected","itemMessages"})
	public void saveOrder(){
		getService().save(selected);
		itemMessages.clear();
	}
	
	
	@NotifyChange({"selected","orders","itemMessages"})
	public void deleteOrder(){
		getService().delete(selected);
		getOrders().remove(selected);
		selected = null;
		itemMessages.clear();
	}
	
	Map<String, String> itemMessages = new HashMap<String,String>();
	
	public Map<String,String> getItemMessages(){
		return itemMessages;
	}
	
	
	//validators for prompt
	public Validator getPriceValidator(){
		return new Validator(){
			public void validate(ValidationContext ctx) {
				Double price = (Double)ctx.getProperty().getValue();
				if(price==null || price<=0){
					ctx.setInvalid();
					itemMessages.put("price", "must large than 0");
				}else{
					itemMessages.remove("price");
				}
				//notify the 'price' message in messages was changed.
				ctx.getBindContext().getBinder().notifyChange(itemMessages, "price");
			}
		};
	}
	
	public Validator getQuantityValidator(){
		return new Validator(){
			public void validate(ValidationContext ctx) {
				Integer quantity = (Integer)ctx.getProperty().getValue();
				if(quantity==null || quantity<=0){
					ctx.setInvalid();
					itemMessages.put("quantity", "must large than 0");
				}else{
					itemMessages.remove("quantity");
				}
				//notify the 'price' message in messages was changed.
				ctx.getBindContext().getBinder().notifyChange(itemMessages, "quantity");
			}
		};
	}
}
