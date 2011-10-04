/* ConfirmViewModel.java

	Purpose:
		
	Description:
		
	History:
		Sep 21, 2011 11:54:49 AM, Created by DennisChen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
 */

package org.zkoss.mvvm.examples.confirm;

import java.util.Date;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkbind.Form;
import org.zkoss.zkbind.GenericBindComposer;
import org.zkoss.zkbind.NotifyChange;
import org.zkoss.zkbind.Property;
import org.zkoss.zul.ListModelList;

/**
 * Demo how to use validation to do the confirmation.
 * @author dennis
 */
public class ConfirmViewModel extends GenericBindComposer {
	private ListModelList cityList;
	private City selected;
	private City tobeSelected = null;
	
	private DialogBean dialog; 
	private String message;

	public ConfirmViewModel() {
		//init the fake data
		cityList = new ListModelList();
		cityList.add(new City("New York",30));
		cityList.add(new City("Taipei",40));
		cityList.add(new City("Beijing",50));
		cityList.add(new City("Tokyo",60));
		
		//a bean to help me show dialog
		dialog = new DialogBean();
	}
	
	//TODO, we should not store this, remove this after the postCommand api ready
	Component self;
	//TOOD, we should not store grid, to get From, remoe this after the form binding init ready.
	Component grid;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		self = comp;
		grid = self.getFellow("grid");
	}
	
	public Form getForm(){
		return (Form)grid.getAttribute("fx");
	}
	//

	public ListModelList getCityList() {
		return cityList;
	}

	public void setCityList(ListModelList cityList) {
		this.cityList = cityList;
	}

	public City getSelected() {
		return selected;
	}

	@NotifyChange
	public void setSelected(City selected) {
		this.selected = selected;
		tobeSelected = null;
	}

	public String getMessage(){
		return message;
	}
	
	public void setMessage(String message){
		this.message = message;
		//notify by java code, @NotifyChange doesn't work when internally
		getBinder().notifyChange(this, "message",null,null);
	}

	public DialogBean getDialog() {
		return dialog;
	}
	
	void setDialog(boolean visible,City selected){
		dialog.setVisible(visible);
		dialog.setCity(selected);
		//notify by java code, @NotifyChange doesn't work when internally
		getBinder().notifyChange(this, "dialog",null,null);
	}

	
	public boolean validate(String cmd, Set<Property> properties, org.zkoss.zkbind.BindContext ctx){
		for(Property p:properties){
			if(this==p.getBase() && "selected".equals(p.getProperty())){
				if(selected!=null && checkFormDirty()){
					//form is dirty, we keep the object as a to-be-selected obj;
					tobeSelected = (City)p.getValue();
					//show dialog
					setDialog(true,selected);
					return false;
				}
				return true;
			}else if(p.getBase() instanceof City){
				
				if("population".equals(p.getProperty())){
					if(((Integer)p.getValue())<=10){
						//validation fail
						if(tobeSelected==null){
							//no tobeSelected, it was from update command;
							setMessage("validation fail");
						}else{
							setMessage("validation and select back to "+selected);
							tobeSelected = null;
							//Don't use notify, we are already in command lifecycle 
//							getBinder().notifyCommand("confirmCancel", null);
							
							//TODO a way to postCommand
							final EventListener listener = new EventListener(){
								public void onEvent(Event event)
										throws Exception {
									//trigger the confirmCancel command
									getBinder().notifyCommand("confirmCancel", null);
									self.removeEventListener("onPostCommand", this);
								}
							};
							self.addEventListener("onPostCommand", listener);
							Events.postEvent("onPostCommand", self, null);
						}
						return false;
					}
				}
			}
		}
		return true;
	}
	
	
	private boolean checkFormDirty() {
		Form f = getForm();
		return f.isDirty();
	}

	//commands
	
	@NotifyChange({"selected"})
	public void update(){
		//update selected to database
		setMessage("update "+selected.getName() +"["+new Date()+"]");
	}
	
	
	public void confirmUpdate(){
		selected = tobeSelected;
		setDialog(false,null);
		setMessage("confirmUpdate "+selected +"["+new Date()+"]");
	}
	
	@NotifyChange({"selected"})
	public void confirmSelectOnly(){
		//change selection to next
		selected = tobeSelected;
		setDialog(false,null);
		setMessage("confirmSelectOnly "+selected +"["+new Date()+"]");
	}
	
	public void confirmCancel(){
		//nothing, clean dialog
		setDialog(false,null);
		setMessage("confirmCancel "+selected +"["+new Date()+"]");
	}

	public static class City {
		String name;
		int population;

		public City(String name,int population) {
			this.name = name;
			this.population = population;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getPopulation() {
			return population;
		}

		public void setPopulation(int population) {
			this.population = population;
		}
		
		public String toString(){
			return new StringBuilder().append("[").append(name).append(",").append(population).append("]").toString();
		}
		
	}

}
