/* SaveBinding.java

	Purpose:
		
	Description:
		
	History:
		Aug 26, 2011 3:45:02 PM, Created by henrichen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.zkbind.sys;

import java.util.Map;
import java.util.Set;

import org.zkoss.zkbind.BindContext;
import org.zkoss.zkbind.Property;
import org.zkoss.zkbind.Validator;

/**
 * Binding for saving.
 * @author henrichen
 *
 */
public interface SaveBinding extends Binding {
	/**
	 * Save data from the source attribute into the target property.
	 * @param ctx the binding runtime context
	 */
	public void save(BindContext ctx);
	
	/**
	 * Returns {@Property} to be validated.
	 * @param ctx the binding runtime context
	 * @return {@Property} to be validated.
	 */
	public Property getValidate(BindContext ctx);
//	public Set<Property> getValidates(BindContext ctx);
	
	/**
	 * Returns whether to do validation. which means, if true, than getValidator should not return null
	 * @return whether to do validation.
	 */
	public boolean hasValidator();
	
	/**
	 * return {@Validator} to do validation
	 * @return the validator if existed
	 */
	public Validator getValidator();
	
	/**
	 * Returns an argument <tags, object> pairs map for validator. 
	 * @return an argument <tags, object> pairs map for validator.
	 */
	public Map<String, Object> getValidatorArgs();	
}
