/* ZKBindLoad1Composer.java

	Purpose:
		
	Description:
		
	History:
		Aug 2, 2011 1:01:07 PM, Created by henri

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.zkoss.zkbind.NotifyChange;
import org.zkoss.zkbind.GenericBindComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.ListModelList;
import test.Person.Address;

/**
 * @author henri
 *
 */
public class ZKBindLoad1Composer extends GenericBindComposer {
	private Person _selected;
	private List<Person> _persons;
	
	public ZKBindLoad1Composer() {
		_persons = new ArrayList<Person>();
		for(int j = 0; j < 4; ++j) {
			_persons.add(new Person("First"+j , "Last"+j));
		}
		_selected = _persons.get(1); //2nd person
		Address addr = new Address("台北市鄭州路87號", "103");
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
	@NotifyChange({"p1", "persons"})
	public void removeP1() {
		_selected = null;
		_persons.remove(1);
	}
	
	public void myCommand() {
		System.out.println("Execute 'myCommand'");
	}
}
