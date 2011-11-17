/* OrderVM.java

	Purpose:
		
	Description:
		
	History:
		2011/10/31 Created by Dennis Chen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
 */
package org.zkoss.bind.examples.spring.order;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.bind.NotifyChange;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.examples.spring.validator.CreationDateValidator;
import org.zkoss.bind.examples.spring.validator.MessagePool;
import org.zkoss.bind.examples.spring.validator.PriceValidator;
import org.zkoss.bind.examples.spring.validator.QuantityValidator;
import org.zkoss.bind.examples.spring.validator.ShippingDateValidator;
import org.zkoss.zul.ListModelList;

/**
 * @author dennis
 * 
 */
@Component("orderVm")
@Scope("session")
public class OrderVM {

	//the order list
	ListModelList<Order> orders;
	
	//the selected order
	Order selected;
	
	//validation messages
	@Autowired
	MessagePool validationMessages;
	
	//validator
	@Autowired
	QuantityValidator quantityValidator;
	@Autowired
	ShippingDateValidator shippingDateValidator;
	@Autowired
	CreationDateValidator creationDateValidator;
	@Autowired
	PriceValidator priceValidator;

	public ListModelList<Order> getOrders() {
		if (orders == null) {
			//init the list
			orders = new ListModelList<Order>(getService().list());
		}
		return orders;
	}

	public Order getSelected() {
		return selected;
	}

	@NotifyChange({"selected","validationMessages"})
	public void setSelected(Order selected) {
		this.selected = selected;
		validationMessages.clear();//clear when another order selected
	}

	//action command
	
	@NotifyChange({"selected","orders","validationMessages"})
	public void newOrder(){
		Order order = new Order();
		getOrders().add(order);
		selected = order;//select the new one
		validationMessages.clear();//clear message
	}
	
	@NotifyChange({"selected","validationMessages"})
	public void saveOrder(){
		getService().save(selected);
		validationMessages.clear();//clear message
	}
	
	
//	@NotifyChange({"selected","orders","validationMessages"})
//	public void deleteOrder(){
//		getService().delete(selected);//delete selected
//		getOrders().remove(selected);
//		selected = null; //clean the selected
//		validationMessages.clear();//clear message
//	}

	@NotifyChange({"selected","orders","validationMessages","deleteMessage"})
	public void deleteOrder(){
		getService().delete(selected);//delete selected
		getOrders().remove(selected);
		selected = null; //clean the selected
		validationMessages.clear();//clear message
		deleteMessage = null;
	}

	
	public MessagePool getValidationMessages(){
		return validationMessages;
	}
	

	public OrderService getService() {
		return FakeOrderService.getInstance();
	}
	
	//validators for command
	public Validator getPriceValidator(){
		return priceValidator;
	}

	public Validator getQuantityValidator(){
		return quantityValidator;
	}
	public Validator getCreationDateValidator(){
		return creationDateValidator;
	}
	public Validator getShippingDateValidator(){
		return shippingDateValidator;
	}
	
	
	
	//message for confirming the deletion.
	String deleteMessage;
	
	public String getDeleteMessage(){
		return deleteMessage;
	}
	

	
	@NotifyChange("deleteMessage")
	public void confirmDelete(){
		//set the message to show to user
		deleteMessage = "Do you want to delete "+selected.getId()+" ?";
	}
	
	
	@NotifyChange("deleteMessage")
	public void cancelDelete(){
		//clear the message
		deleteMessage = null;
	}
		
}
