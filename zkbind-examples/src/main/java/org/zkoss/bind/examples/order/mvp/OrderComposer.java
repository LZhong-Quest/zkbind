/* OrderVM.java

	Purpose:
		
	Description:
		
	History:
		2011/10/31 Created by Dennis Chen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
 */
package org.zkoss.bind.examples.order.mvp;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.examples.order.FakeOrderService;
import org.zkoss.bind.examples.order.Order;
import org.zkoss.bind.examples.order.OrderService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;


/**
 * An implementation in MVP pattern with zkbind expression.
 * @author Hawk
 * 
 */
@SuppressWarnings("serial")
public class OrderComposer extends SelectorComposer<Component>{
	//the order list
	ListModelList<Order> orders;
	
	//the selected order
	Order selected;
	//UI components
	@Wire("#saveButton")
	Button saveButton;
	@Wire("#deleteButton")
	Button deleteButton;
	@Wire("#editBox")
	Groupbox editBox;
	@Wire("#confirmDialog")
	Window confirmDialog;
	@Wire("#messageLabel")
	Label messageLabel;

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

	public void setSelected(Order selected) {
		this.selected = selected;
		if (selected == null){
			saveButton.setDisabled(true);
			deleteButton.setDisabled(true);
			editBox.setVisible(false);
		}else{
			saveButton.setDisabled(false);
			deleteButton.setDisabled(false);
			editBox.setVisible(true);
		}
		validationMessages.clear();//clear when another order selected
	}

	//action command
	
	@Listen("onClick = button[label='New']")
	public void newOrder(){
		Order order = new Order();
		getOrders().add(order);
		selected = order;//select the new one
		validationMessages.clear();//clear message
	}
	
	@Listen("onClick = button[label='Save']")
	public void saveOrder(){
		getService().save(selected);
		validationMessages.clear();//clear message
	}
	
	
	@Listen("onClick = #confirmDeleteButton")
	public void deleteOrder(){
		getService().delete(selected);//delete selected
		getOrders().remove(selected);
		selected = null; //clean the selected
		confirmDialog.setVisible(false);
	}
	
	//validation messages
	Map<String, String> validationMessages = new HashMap<String,String>();
	
	public Map<String,String> getValidationMessages(){
		return validationMessages;
	}
	

	public OrderService getService() {
		return FakeOrderService.getInstance();
	}
	
	//validators for prompt
	public Validator getPriceValidator(){
		return new Validator(){
			public void validate(ValidationContext ctx) {
				Double price = (Double)ctx.getProperty().getValue();
				if(price==null || price<=0){
					ctx.setInvalid(); // mark invalid
					validationMessages.put("price", "must large than 0");
				}else{
					validationMessages.remove("price");
				}
				//notify messages was changed.
				ctx.getBindContext().getBinder().notifyChange(validationMessages, "price");
			}
		};
	}
	
	public Validator getQuantityValidator(){
		return new Validator(){
			public void validate(ValidationContext ctx) {
				Integer quantity = (Integer)ctx.getProperty().getValue();
				if(quantity==null || quantity<=0){
					ctx.setInvalid();// mark invalid
					validationMessages.put("quantity", "must large than 0");
				}else{
					validationMessages.remove("quantity");
				}
				//notify messages was changed.
				ctx.getBindContext().getBinder().notifyChange(validationMessages, "quantity");
			}
		};
	}
	//validators for command
	public Validator getCreationDateValidator(){
		return new Validator(){
			public void validate(ValidationContext ctx) {
				Date creation = (Date)ctx.getProperty().getValue();
				if(creation==null){
					ctx.setInvalid();// mark invalid
					validationMessages.put("creationDate", "must not null");
				}else{
					validationMessages.remove("creationDate");
				}
				//notify messages was changed.
				ctx.getBindContext().getBinder().notifyChange(validationMessages, "creationDate");
			}
		};
	}
	public Validator getShippingDateValidator(){
		return new Validator(){
			public void validate(ValidationContext ctx) {
				Date shipping = (Date)ctx.getProperty().getValue();//the main property
				Date creation = (Date)ctx.getProperties("creationDate")[0].getValue();//the collected
				//do mixed validation, shipping date have to large than creation more than 3 days.
				if(!CaldnearUtil.isDayAfter(creation,shipping,3)){
					ctx.setInvalid();
					validationMessages.put("shippingDate", "must large than creation date at least 3 days");
				}else{
					validationMessages.remove("shippingDate");
				}
				//notify the 'price' message in messages was changed.
				ctx.getBindContext().getBinder().notifyChange(validationMessages, "shippingDate");
			}

		};
	}
	
	static class CaldnearUtil{
		static public boolean isDayAfter(Date date, Date laterDay , int day) {
			if(date==null) return false;
			if(laterDay==null) return false;
			
			Calendar cal = Calendar.getInstance();
			Calendar lc = Calendar.getInstance();
			
			cal.setTime(date);
			lc.setTime(laterDay);
			
			int cy = cal.get(Calendar.YEAR);
			int ly = lc.get(Calendar.YEAR);
			
			int cd = cal.get(Calendar.DAY_OF_YEAR);
			int ld = lc.get(Calendar.DAY_OF_YEAR);
			
			return (ly*365+ld)-(cy*365+cd) >= day; 
		}
	}
	
	//message for confirming the deletion.
//	String deleteMessage;
	
//	public String getDeleteMessage(){
//		return deleteMessage;
//	}
	
//	@Override
//	@NotifyChange({"selected","orders","validationMessages","deleteMessage"})
//	public void deleteOrder(){
//		
//	}
	
	@Listen("onClick = #deleteButton")
	public void confirmDelete(){
		if (getSelected().getId()==null){
			deleteOrder();
		}else{
			//set the message to show to user
			messageLabel.setValue("Do you want to delete "+selected.getId()+" ?");
			confirmDialog.setVisible(true);
		}
	}
	
	@Listen("onClick = [label='Cancel']")
	public void cancelDelete(){
		confirmDialog.setVisible(false);
	}
	
}
