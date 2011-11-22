/* OrderVM.java

	Purpose:
		
	Description:
		
	History:
		2011/10/31 Created by Dennis Chen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
 */
package org.zkoss.bind.examples.spring.order.viewmodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.bind.NotifyChange;
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
	Messages messagePool;
	
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

	@NotifyChange({"selected"})
	public void setSelected(Order selected) {
		this.selected = selected;
		messagePool.clear();//clear when another order selected
	}

	//action command
	
	@NotifyChange({"selected","orders"})
	public void newOrder(){
		Order order = new Order();
		getOrders().add(order);
		selected = order;//select the new one
		messagePool.clear();//clear message
	}
	
	@NotifyChange({"selected"})
	public void saveOrder(){
		System.out.println(">>>>vm>>>> "+this);
		System.out.println(">>>>service>>>> "+orderService);
		orderService.save(selected);
		messagePool.clear();//clear message
	}
	
	
//	@NotifyChange({"selected","orders","validationMessages"})
//	public void deleteOrder(){
//		orderService.delete(selected);//delete selected
//		getOrders().remove(selected);
//		selected = null; //clean the selected
//		validationMessages.clear();//clear message
//	}

	@NotifyChange({"selected","orders","deleteMessage"})
	public void deleteOrder(){
		orderService.delete(selected);//delete selected
		getOrders().remove(selected);
		selected = null; //clean the selected
		messagePool.clear();//clear message
//		deleteMessage = null;
	}


	//message for confirming the deletion.
//	String deleteMessage;
//	
//	public String getDeleteMessage(){
//		return deleteMessage;
//	}
//	

//	@NotifyChange
//	public void setMessagePool(MessagePool mp){
//		messagePool = mp;
//	}
	public Messages getMessagePool(){
		return messagePool;
	}
	@NotifyChange("messagePool")
	public void confirmDelete(){
		//set the message to show to user
//		deleteMessage = "Do you want to delete "+selected.getId()+" ?";
		messagePool.put("delete", "Do you want to delete "+selected.getId()+" ?");
	}
	
	
	@NotifyChange("messagePool")
	public void cancelDelete(){
		//clear the message
		messagePool.remove("delete");
//		deleteMessage = null;
	}
		
}
