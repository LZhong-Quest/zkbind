/* ZKBindLoad1Composer.java

	Purpose:
		
	Description:
		
	History:
		Aug 2, 2011 1:01:07 PM, Created by henri

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
*/

package test;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkbind.BindContext;
import org.zkoss.zkbind.Converter;
import org.zkoss.zkbind.DependsOn;
import org.zkoss.zkbind.Form;
import org.zkoss.zkbind.NotifyChange;
import org.zkoss.zkbind.GenericBindComposer;

/**
 * @author henri
 *
 */
public class ZKBindSave1Composer extends GenericBindComposer {
	private Person _p1;
	public ZKBindSave1Composer() {
		_p1 = new Person("Henri", "Chen");
		
		addConverter("fullName", new Converter() {
			//person -> fullName
			@DependsOn({"firstName", "lastName"})
			public Object coerceToUi(Object val, Component component, BindContext ctx) {
				if (val instanceof Form) {
					final Form bean = (Form) val;
					return "" + bean.getField("firstName")+ " " + bean.getField("lastName");
				} else {
					final Person person = (Person) val;
					return person.getFirstName() + " " + person.getLastName();
				}
			}
	
			public Object coerceToBean(Object val, Component component, BindContext ctx) {
				//Never call here
				return val;
			}
		});
		addConverter("firstName", new Converter() {
			public Object coerceToUi(Object val, Component component, BindContext ctx) {
				return val;
			}
	
			@NotifyChange("firstName")
			public Object coerceToBean(Object val, Component component, BindContext ctx) {
				return val;
			}
		});
		addConverter("lastName", new Converter() {
			public Object coerceToUi(Object val, Component component, BindContext ctx) {
				return val;
			}
	
			@NotifyChange("lastName")
			public Object coerceToBean(Object val, Component component, BindContext ctx) {
				return val;
			}
		});
	}
	
	public Person getP1() {
		return _p1;
	}
	
	@NotifyChange
	public void setP1(Person p) {
		_p1 = p;
	}
	
	public void myCommand(Map args) {
		System.out.println("myCommand executed:"+args);
	}
	
	private String _fieldName = "firstName";
	public void setFieldName(String fieldName) {
		_fieldName = fieldName;
	}
	
	public String getFieldName() {
		return _fieldName;
	}
	@NotifyChange("fieldName")
	public void changeFieldName(Map args) {
		setFieldName((String)args.get("fieldName"));
	}
}
