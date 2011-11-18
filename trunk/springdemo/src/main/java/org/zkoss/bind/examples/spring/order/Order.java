/* Item.java

	Purpose:
		
	Description:
		
	History:
		2011/10/25 Created by Dennis Chen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
 */
package org.zkoss.bind.examples.spring.order;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.zkoss.bind.DependsOn;
import org.zkoss.bind.NotifyChange;

/**
 * Order is a keyword in SQL syntax, it can't be a table name.
 * @author Hawk
 */
@Entity
@Table(name="ORDERS")
public class Order {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	Integer id;
	String description;
	Date creationDate;
	Date shippingDate;
	double price;
	int quantity;
	


	public Integer getId() {
		return id;
	}

	@NotifyChange
	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}
	@NotifyChange
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getShippingDate() {
		return shippingDate;
	}
	@NotifyChange
	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}

	public String getDescription() {
		return description;
	}

	@NotifyChange
	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	@NotifyChange
	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	@NotifyChange
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@DependsOn( { "price", "quantity" })
	public double getTotalPrice() {
		return price * quantity;
	}

}
