/* MutualDependentViewModel.java

	Purpose:
		
	Description:
		
	History:
		Sep 23, 2011 4:41:41 PM, Created by henrichen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package test;

import org.zkoss.zkbind.DependsOn;
import org.zkoss.zkbind.GenericBindComposer;
import org.zkoss.zkbind.NotifyChange;

/**
 * test circular DependsOn case.
 * @author henrichen
 *
 */
public class MutualDependentViewModel extends GenericBindComposer {
	private String a;
	private String b;
	
	@DependsOn("b")
	public String getA() {
		return a;
	}
	@NotifyChange
	public void setA(String a) {
		this.a = a;
	}
	
	@DependsOn("a")
	public String getB() {
		return b;
	}
	@NotifyChange
	public void setB(String b) {
		this.b = b;
	}
}
