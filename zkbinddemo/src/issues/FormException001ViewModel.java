/* ConfirmViewModel.java

	Purpose:
		
	Description:
		
	History:
		Sep 21, 2011 11:54:49 AM, Created by DennisChen

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
 */

package issues;

import java.util.Set;

import org.zkoss.zkbind.BindComposer;
import org.zkoss.zkbind.NotifyChange;
import org.zkoss.zkbind.Property;

/**
 * Demo how to use validation to do the confirmation.
 */
public class FormException001ViewModel extends BindComposer {
	private City selected;

	public FormException001ViewModel() {
		selected = new City("New York",30);
	}

	public City getSelected() {
		return selected;
	}

	
	public boolean validate(String cmd, Set<Property> properties, org.zkoss.zkbind.BindContext ctx){
		return true;
	}


	//command
	
	@NotifyChange({"selected"})
	public void update(){

	}

	public static class City {
		String name;
		int population;

		public City(String name,int population) {
			this.name = name;
			this.population = population;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getPopulation() {
			return population;
		}

		public void setPopulation(int population) {
			this.population = population;
		}
		
		public String toString(){
			return new StringBuilder().append("[").append(name).append(",").append(population).append("]").toString();
		}
		
	}

}
