/* HelloViewModel.java

	Purpose:
		
	Description:
		
	History:
		Sep 20, 2011 3:52:10 PM, Created by henrichen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.mvvm.examples.hello;

import org.zkoss.zkbind.BindContext;
import org.zkoss.zkbind.GenericBindComposer;
import org.zkoss.zkbind.NotifyChange;

/**
 * Hello World example of ZK Bind.
 * @author henrichen
 *
 */
public class HelloViewModel2 extends GenericBindComposer {
	private String message;
	
	public String getMessage() {
		return this.message;
	}
	
	@NotifyChange("message")
	public void showHello() {
		this.message = "Hello World!";
	}
	
	@NotifyChange("message")
	public void hideHello(BindContext ctx) {
		this.message = null;
		ctx.getTriggerEvent().stopPropagation();
	}
}
