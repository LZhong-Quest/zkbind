package org.zkoss.bind.examples.order.richlet;


import java.util.HashMap;

import org.zkoss.bind.Binder;
import org.zkoss.bind.examples.order.Order;
import org.zkoss.bind.examples.order.OrderVM3;
import org.zkoss.bind.impl.BinderImpl;
import org.zkoss.bind.sys.BinderCtrl;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.GenericRichlet;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Style;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Window.Mode;


public class OrderRichlet extends GenericRichlet{


	public void service(Page page) {

		//initialize UI
		Window window = new Window("Order Management", "normal", false);
		window.setWidth("600px");
		window.setPage(page);

		//initialize binder, use DefaultBinder
		Binder binder = new BinderImpl(); //FIXME users shall not use BinderImpl directly
		binder.init(window, new OrderVM4()); //FIXME init() method doesn't belong to Binder 
		window.setAttribute("vm", binder.getViewModel());


		Vbox vbox = new Vbox();
		vbox.setHflex("true");
		window.appendChild(vbox);

		vbox.appendChild(buildOrderListbox(binder));
		vbox.appendChild(buildToolbar(binder,vbox));
		buildFormArea(binder, vbox);
		buildConfirmDialog(binder, window);


		Groupbox initTestBox = new Groupbox();
		initTestBox.setParent(vbox);
		//test init binding
		((OrderVM3)binder.getViewModel()).getValidationMessages().put("init", "init");

		Label label = new Label("Init with 'init'");
		Textbox textbox = new Textbox();
		initTestBox.appendChild(label);
		initTestBox.appendChild(textbox);

		binder.addPropertyInitBinding(textbox, "value", "vm.validationMessages['init']", null, null, null);
		// Must load value to components once, call it after all "add property binding" statements
		
		Groupbox formTestBox = new Groupbox();
		formTestBox.setParent(vbox);
		binder.addFormInitBinding(formTestBox, "fx", "vm.myForm", null);
		Label formLabel = new Label("Form init with 'init'");
		Textbox formTextbox = new Textbox();
		formTestBox.appendChild(formLabel);
		formTestBox.appendChild(formTextbox);

		binder.addPropertyInitBinding(formTextbox, "value", "fx.init", null, null, null);
		
		//loadComponent(component, true)
		((BinderImpl)binder).loadComponent(window,true); 
	}

	private Listbox buildOrderListbox(Binder binder){
		Listbox listbox = new Listbox();
		listbox.setHeight("200px");
		listbox.setHflex("true");
		Listhead head = new Listhead();
		listbox.appendChild(head);
		head.appendChild(new Listheader("Id"));
		head.appendChild(new Listheader("Quantity"));
		head.appendChild(new Listheader("Price"));
		head.appendChild(new Listheader("Creation Date"));
		head.appendChild(new Listheader("Shipping Date"));

		//FIXME can't use template
//		listbox.setTemplate(name, template)
		return listbox;
	}

	private Toolbar buildToolbar(Binder binder, Vbox vbox ){
		Button newButton = new Button("New");
		Button saveButton = new Button("Save");
		Button deleteButton = new Button("Delete");

		Toolbar toolbar = new Toolbar();
		toolbar.appendChild(newButton);
		toolbar.appendChild(saveButton);
		toolbar.appendChild(deleteButton);
		
		binder.addCommandBinding(newButton, Events.ON_CLICK, "'newOrder'", null);
		binder.addCommandBinding(saveButton, Events.ON_CLICK, "'saveOrder'", null);
		binder.addPropertyLoadBindings(saveButton, "disabled", "empty vm.selected", null, null, null, null, null);
//		binder.addCommandBinding(deleteButton, Events.ON_CLICK, "empty vm.selected.id?'deleteOrder':'confirmDelete'", null);
//		binder.addPropertyLoadBindings(deleteButton, "disabled", "empty vm.selected", null, null, null, null, null);
		
		vbox.appendChild(toolbar); //FIXME set before adding binding

		return toolbar;
	}

	private Groupbox buildFormArea(Binder binder, Component parent){
		Style messageStyle = new Style();
		messageStyle.setContent(".z-label.red{ color:red;}");
		
		Groupbox form = new Groupbox();
		form.setMold("3d");
		form.setHflex("true");
		form.setVisible(false); 
		form.setParent(parent);
		form.appendChild(messageStyle);
		
		binder.addPropertyLoadBindings(form, "visible", "not empty vm.selected", null, null, null, null, null);
		
		Grid grid = new Grid();
		grid.setHflex("true");
		grid.setParent(form);
		Columns columns = new Columns();
		Column labelCol = new Column();
		labelCol.setWidth("120px");
		columns.appendChild(labelCol);
		columns.appendChild(new Column());
		grid.appendChild(columns);

		Rows rows = new Rows();
		grid.appendChild(rows);
		Row idRow = new Row();
		idRow.appendChild(new Label("Id"));
		Label idLabel = new Label();
		idRow.appendChild(idLabel);
		rows.appendChild(idRow);
		
		binder.addPropertyLoadBindings(idLabel, "value", "vm.selected.id", null, null, null, null, null);

		Row descriptionRow = new Row();
		descriptionRow.appendChild(new Label("Description"));
		Textbox descriptionBox = new Textbox();
		descriptionRow.appendChild(descriptionBox);
		rows.appendChild(descriptionRow);
		//add binding
		String[] beforeCommand = {"saveOrder"};
		binder.addPropertyLoadBindings(descriptionBox, "value", "vm.selected.description", null, null, null, null, null);
		binder.addPropertySaveBindings(descriptionBox, "value", "vm.selected.description", beforeCommand, null, null, null, null, null, null);

		
		Row quantityRow = new Row();
		quantityRow.appendChild(new Label("Quantity"));
		Hlayout qLayout = new Hlayout();
		quantityRow.appendChild(qLayout);
		Intbox quantityIntbox = new Intbox();
		qLayout.appendChild(quantityIntbox);
		Label quantityMessage = new Label();
		quantityMessage.setSclass("red");
		qLayout.appendChild(quantityMessage); //validation message
		rows.appendChild(quantityRow);
		
		binder.addPropertyLoadBindings(quantityMessage, "value", "vm.validationMessages['quantity']", null, null, null, null, null);
		binder.addPropertySaveBindings(quantityIntbox, "value", "vm.selected.quantity", beforeCommand, null, null, null, null, "vm.quantityValidator", null);
		binder.addPropertyLoadBindings(quantityIntbox, "value", "vm.selected.quantity", null, null, null, null, null);		

		Row priceRow = new Row();
		priceRow.appendChild(new Label("Price"));
		Hlayout pLayout = new Hlayout();
		priceRow.appendChild(pLayout);
		Doublebox priceBox = new Doublebox();
		priceBox.setFormat("###,##0.00");
		pLayout.appendChild(priceBox);
		Label priceMessage = new Label();
		priceMessage.setSclass("red");
		pLayout.appendChild(priceMessage); //validation message
		rows.appendChild(priceRow);
		
		binder.addPropertyLoadBindings(priceMessage, "value", "vm.validationMessages['price']", null, null, null, null, null);
		binder.addPropertySaveBindings(priceBox, "value", "vm.selected.price", beforeCommand, null, null, null, null, "vm.priceValidator", null);
		binder.addPropertyLoadBindings(priceBox, "value", "vm.selected.price", null, null, null, null, null);	

		Row totalRow = new Row();
		totalRow.appendChild(new Label("Total Price"));
		Label totalPriceLabel = new Label();
		totalRow.appendChild(totalPriceLabel);
		HashMap<String, Object> totalPriceArg = new HashMap<String, Object>();
		totalPriceArg.put("format", "###,##0.00");
//		format='###,##0.00'
		binder.addPropertyLoadBindings(totalPriceLabel, "value", "vm.selected.totalPrice", null, null, null, "'formatedNumber'", totalPriceArg);
		
		Row creationRow = new Row();
		creationRow.appendChild(new Label("Creation Date"));
		Hlayout cLayout = new Hlayout();
		creationRow.appendChild(cLayout);
		Datebox creationDatebox = new Datebox();
		cLayout.appendChild(creationDatebox);
		Label creationMessage = new Label();
		creationMessage.setSclass("red");
		cLayout.appendChild(creationMessage);//validation message
		rows.appendChild(creationRow);
		
		binder.addPropertyLoadBindings(creationMessage, "value", "vm.validationMessages['creationDate']", null, null, null, null, null);
		binder.addPropertySaveBindings(creationDatebox, "value", "vm.selected.creationDate", beforeCommand, null, null, null, null, "vm.creationDateValidator", null);
		binder.addPropertyLoadBindings(creationDatebox, "value", "vm.selected.creationDate", null, null, null, null, null);	

		Row shippingRow = new Row();
		shippingRow.appendChild(new Label("Shipping Date"));
		Hlayout sLayout = new Hlayout();
		shippingRow.appendChild(sLayout);
		Datebox shippingDatebox = new Datebox();
		sLayout.appendChild(shippingDatebox);
		Label shippingMessage = new Label();
		shippingMessage.setSclass("red");
		sLayout.appendChild(shippingMessage);//validation message
		rows.appendChild(shippingRow);
		
		binder.addPropertyLoadBindings(shippingMessage, "value", "vm.validationMessages['shippingDate']", null, null, null, null, null);
		binder.addPropertySaveBindings(shippingDatebox, "value", "vm.selected.shippingDate", beforeCommand, null, null, null, null, "vm.shippingDateValidator", null);
		binder.addPropertyLoadBindings(shippingDatebox, "value", "vm.selected.shippingDate", null, null, null, null, null);

		return form;
	}

	private Window buildConfirmDialog(Binder binder, Component parent) {
		Window confirmDialog = new Window("Confirm", "normal",false);
		confirmDialog.setParent(parent);
		confirmDialog.setMode(Mode.MODAL);
		confirmDialog.setVisible(false);
		confirmDialog.setWidth("300px");
		
		binder.addPropertyLoadBindings(confirmDialog, "visible", "not empty vm.deleteMessage", null, null, null, null, null);
		
		Vbox vbox =  new Vbox();
		confirmDialog.appendChild(vbox);
		vbox.setHflex("true");
		Hlayout messageArea = new Hlayout();
		messageArea.setHeight("50px");
		vbox.appendChild(messageArea);
		messageArea.appendChild(new Image("~./zul/img/msgbox/question-btn.png"));
		Label message = new Label();
		messageArea.appendChild(message);
		
		binder.addPropertyLoadBindings(message, "value", "vm.deleteMessage", null, null, null, null, null);

		Hbox hbox = new Hbox();
		vbox.appendChild(hbox);
		hbox.setPack("center");
		hbox.setHflex("true");
		Button deleteButton = new Button("Delete");
		Button cancelButton = new Button("Cancel");
		hbox.appendChild(deleteButton);
		hbox.appendChild(cancelButton);

		binder.addCommandBinding(deleteButton, Events.ON_CLICK, "'deleteOrder'", null);
		binder.addCommandBinding(cancelButton, Events.ON_CLICK, "'cancelDelete'", null);
		
		return confirmDialog;
	}
}