/* LoadBinding.java

	Purpose:
		
	Description:
		
	History:
		Aug 26, 2011 3:46:32 PM, Created by henrichen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.zkbind.sys;

import org.zkoss.zkbind.BindContext;

/**
 * Binding for loading.
 * @author henrichen
 *
 */
public interface LoadBinding extends Binding {
	/**
	 * Load data into the source attribute from the target property.
	 * @param ctx the binding runtime context 
	 */
	public void load(BindContext ctx);
}
