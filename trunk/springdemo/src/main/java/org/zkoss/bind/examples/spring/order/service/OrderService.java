/* SearchService.java

	Purpose:
		
	Description:
		
	History:
		2011/10/25 Created by Dennis Chen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
 */
package org.zkoss.bind.examples.spring.order.service;

import java.util.List;

import org.zkoss.bind.examples.spring.order.domain.Order;

/**
 * @author dennis
 *
 */
public interface OrderService {
	public List<Order> list();
	public void save(Order order);
	public void delete(Order order);
}
