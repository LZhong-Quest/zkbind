/* AbcViewModel.java

	Purpose:
		
	Description:
		
	History:
		Oct 4, 2011 2:06:57 PM, Created by henrichen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package test;

import org.zkoss.zkbind.DependsOn;
import org.zkoss.zkbind.GenericBindComposer;
import org.zkoss.zkbind.NotifyChange;

/**
 * @author henrichen
 *
 */
public class AbcViewModel extends GenericBindComposer{
	private String value;
	
	@NotifyChange
	public void setA(String a) {
		value = a;
	}
	public String getA() {
		return value;
	}
	
	@NotifyChange
	public void setB(String b) {
		value = b;
	}
	@DependsOn("a")
	public String getB() {
		return value;
	}
	
	@NotifyChange
	public void setC(String c) {
		value = c;
	}
	@DependsOn("b")
	public String getC() {
		return value;
	}
}
