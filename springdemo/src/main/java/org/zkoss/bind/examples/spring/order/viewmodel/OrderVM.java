/* OrderVM.java

	Purpose:
		
	Description:
		
	History:

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
 */
package org.zkoss.bind.examples.spring.order.viewmodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.examples.spring.order.domain.Order;
import org.zkoss.bind.examples.spring.order.domain.OrderService;
import org.zkoss.bind.examples.spring.order.util.Messages;
import org.zkoss.zul.ListModelList;

/**
 * @author Hawk
 * 
 */
@Component("orderVm")
@Scope("prototype")
public class OrderVM {

	//the order list
	ListModelList<Order> orders;
	
	@Autowired
	OrderService orderService;
	//the selected order
	Order selected;
	
	//validation messages
	@Autowired
	Messages messages;
	
	public ListModelList<Order> getOrders() {
		if (orders == null) {
			//init the list
			orders = new ListModelList<Order>(orderService.list());
		}
		return orders;
	}

	public Order getSelected() {
		return selected;
	}

	@NotifyChange({"selected","messages"})
	public void setSelected(Order selected) {
		this.selected = selected;
		messages.clear();//clear when another order selected
	}

	//action command
	@NotifyChange({"selected","orders","messages"})
	@Command
	public void newOrder(){
		Order order = new Order();
		getOrders().add(order);
		selected = order;//select the new one
		messages.clear();//clear message
	}
	
	@NotifyChange({"selected","messages"})
	@Command
	public void saveOrder(){
		orderService.save(selected);
		messages.clear();//clear message
	}
	
	@NotifyChange({"selected","orders","messages"})
	@Command
	public void deleteOrder(){
		orderService.delete(selected);//delete selected
		getOrders().remove(selected);
		selected = null; //clean the selected
		messages.clear();//clear message
	}


	public Messages getMessages(){
		return messages;
	}
	@NotifyChange("messages")
	@Command
	public void confirmDelete(){
		//set the message to show to user
		messages.put("delete", "Do you want to delete "+selected.getId()+" ?");
	}
	
	
	@NotifyChange("messages")
	@Command
	public void cancelDelete(){
		//clear the message
		messages.remove("delete");
	}
		
}
