/* ZKBindLoad1Composer.java

	Purpose:
		
	Description:
		
	History:
		Aug 2, 2011 1:01:07 PM, Created by henrichen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package test2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.zkoss.zkbind.NotifyChange;
import org.zkoss.zkbind.GenericBindComposer;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.ListModelList;

import test.Person;
import test.Person.Address;

/**
 * @author Dennis Chen
 *
 */
public class Test2ZKBindLoadComposer extends GenericBindComposer {
	private Person _selected;
	private List<Person> _persons;
	
	private Person _p2;
	
	public Test2ZKBindLoadComposer() {
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

	@NotifyChange({"p1","selected"})
	public void changeFirstName1() {
		_selected.setFirstName("Dennis");
	}
	
	public void changeLastName1() {
		_selected.setLastName("Chen");
		notifyChange(_selected, "lastName");
	}
	
	@NotifyChange({"p1","selected"})
	public void changeFirstName2() {
		_selected.setFirstName("Alex");
	}
	
	public void changeLastName2() {
		_selected.setLastName("Wang");
		notifyChange(_selected, "lastName");
	}
	
	@NotifyChange({"p1"})
	public void notifyP1() {
		_selected.setFirstName("Ian");
		_selected.setLastName("Tasi");
	}
	
	@NotifyChange({"selected"})
	public void notifySelected() {
		_selected.setFirstName("Jumper");
		_selected.setLastName("Chen");
	}
	
	public Person getP2(){
		return _p2;
	}
	
	@NotifyChange({"p1","p2"})
	public void saveForm(){
		_p2 = new Person(_selected.getFirstName(),_selected.getLastName());
	}
}
