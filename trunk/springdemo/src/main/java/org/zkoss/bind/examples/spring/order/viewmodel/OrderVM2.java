/* OrderVM.java

	Purpose:
		
	Description:
		
	History:

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
 */
package org.zkoss.bind.examples.spring.order.viewmodel;

import java.util.List;

import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.examples.spring.order.domain.Order;
import org.zkoss.bind.examples.spring.order.domain.OrderService;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

/**
 * @author Hawk
 * 
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class OrderVM2 {

	//the order list
	List<Order> orders;
	
	@WireVariable
	OrderService orderService;
	
	//the selected order
	Order selected;
	
	String confirmMessage;
	
	@Init
	public void init(@ContextParam(ContextType.BINDER) Binder binder){
		Selectors.wireVariables(binder.getView(), this, Selectors.newVariableResolvers(this.getClass(), null));
	}
	
	public List<Order> getOrders() {
		if (orders == null) {
			//init the list
			orders = orderService.list();
		}
		return orders;
	}

	public Order getSelected() {
		return selected;
	}

	@NotifyChange("selected")
	public void setSelected(Order selected) {
		this.selected = selected;
	}

	//action command
	@NotifyChange({"selected","orders"})
	@Command
	public void newOrder(){
		Order order = new Order();
		getOrders().add(order);
		selected = order;//select the new one
	}
	
	@NotifyChange("selected")
	@Command
	public void saveOrder(){
		orderService.save(selected);
	}
	
	@NotifyChange({"selected","orders"})
	@Command
	public void deleteOrder(){
		orderService.delete(selected);//delete selected
		orders = orderService.list();//refresh
		selected = null; //clean the selected
	}


	public String getConfirmMessage(){
		return confirmMessage;
	}
	
	@NotifyChange("confirmMessage")
	@Command
	public void confirmDelete(){
		//set the message to show to user
		confirmMessage = "Do you want to delete "+selected.getId()+" ?";
	}
	
	
	@NotifyChange("confirmMessage")
	@Command
	public void cancelDelete(){
		//clear the message
		confirmMessage = null; 
	}
		
}
