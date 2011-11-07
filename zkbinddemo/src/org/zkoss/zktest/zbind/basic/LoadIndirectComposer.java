/* ZKBindLoad1Composer.java

	Purpose:
		
	Description:
		
	History:
		Aug 2, 2011 1:01:07 PM, Created by henrichen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.zktest.zbind.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindComposer;
import org.zkoss.bind.NotifyChange;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.ListModelList;

import test.Person;
import test.Person.Address;

/**
 * @author Dennis Chen
 *
 */
public class LoadIndirectComposer extends BindComposer {
	private Person _selected;
	private List<Person> _persons;
	private String currField = "firstName";
	
	private Person _p2;
	
	public LoadIndirectComposer() {
		_persons = new ArrayList<Person>();
		for(int j = 0; j < 4; ++j) {
			_persons.add(new Person("First"+j , "Last"+j));
		}
		_selected = _persons.get(1); //2nd person
		Address addr = new Address("87 Zhengzhou Road #11F-2 Taipei", "103");
		_selected.setAddress(addr);
	}
	
	public Person getSelected() {
		return _selected;
	}
	@NotifyChange
	public void setSelected(Person p) {
		_selected = p;
	}
	
	public List<Person> getPersons() {
		return _persons;
	}
	
	public Person getP1() {
		return getSelected();
	}
	
	int i=0;

	public String getCurrField() {
		return currField;
	}

	@NotifyChange({"currField"})
	public void setCurrField(String currField) {
		this.currField = currField;
	}

	@NotifyChange({"currField"})
	public void toFirstName() {
		currField = "firstName";
	}
	
	@NotifyChange({"currField"})
	public void toLastName() {
		currField = "lastName";
	}
	
	@NotifyChange({"currField"})
	public void toFullName() {
		currField = "fullName";
	}
	
	
	public Person getP2(){
		return _p2;
	}
	
	@NotifyChange({"p1","p2"})
	public void saveForm(){
		_p2 = new Person(_selected.getFirstName(),_selected.getLastName());
	}
}
