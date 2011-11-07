/* WizardViewModel.java

	Purpose:
		
	Description:
		
	History:
		Sep 27, 2011 11:55:24 AM, Created by henrichen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package test3.composer;

import org.zkoss.bind.BindComposer;
import org.zkoss.zktest.zbind.viewmodel.collection.ListPool;
import org.zkoss.zul.ListModelList;



/**
 * View model for examples/wizard.zul
 * @author Hawk
 */
public class WizardPageViewModel extends BindComposer {

	private String selectedFruit;
	
	public String getComposerName(){
		return this.toString();
	}
	
	public ListModelList<String> getFruitList(){
		return ListPool.getFruitList();
	}
	
	public void setSeletedFruit(String fruit){
		selectedFruit = fruit;
	}

	public String getSelectedFruit() {
		return selectedFruit;
	}
	
	
}
