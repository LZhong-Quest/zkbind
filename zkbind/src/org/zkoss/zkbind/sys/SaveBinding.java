/* SaveBinding.java

	Purpose:
		
	Description:
		
	History:
		Aug 26, 2011 3:45:02 PM, Created by henri

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.zkbind.sys;

import java.util.Set;

import org.zkoss.zkbind.BindContext;
import org.zkoss.zkbind.Property;

/**
 * Binding for saving.
 * @author henri
 *
 */
public interface SaveBinding extends Binding {
	/**
	 * Save data from the source attribute into the target property.
	 * @param ctx the binding runtime context
	 */
	public void save(BindContext ctx);
	
	/**
	 * Returns {@Property}s to be validated.
	 * @param ctx the binding runtime context
	 * @return {@Property}s to be validated.
	 */
	public Set<Property> getValidates(BindContext ctx);
	
	/**
	 * Returns whether to do validation.
	 * @return whether to do validation.
	 */
	public boolean isValidate();
}
