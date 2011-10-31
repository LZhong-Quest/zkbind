/* OrderVM.java

	Purpose:
		
	Description:
		
	History:
		2011/10/31 Created by Dennis Chen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
 */
package org.zkoss.mvvm.examples.order;

import org.zkoss.bind.NotifyChange;


/**
 * @author dennis
 * 
 */
public class OrderVM3 extends OrderVM2{

	String deleteMessage;
	
	
	public String getDeleteMessage(){
		return deleteMessage;
	}
	
	
	@NotifyChange({"selected","orders","itemMessages","deleteMessage"})
	public void deleteOrder(){
		getService().delete(selected);
		getOrders().remove(selected);
		selected = null;
		itemMessages.clear();
		deleteMessage = null;
	}
	
	@NotifyChange("deleteMessage")
	public void confirmDelete(){
		deleteMessage = "Do you want to delete "+selected.getId()+" ?";
	}
	
	@NotifyChange("deleteMessage")
	public void cancelDelete(){
		deleteMessage = null;
	}
	
}
