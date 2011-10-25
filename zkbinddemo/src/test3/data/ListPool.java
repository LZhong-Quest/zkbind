package test3.data;

import org.zkoss.zul.ListModelList;

public class ListPool {

	static public String [] fruits ={"Apple", "Orange", "Strawberry","Bananna", "Watermalon"};
	
	static public ListModelList getFruitList(){
		return new ListModelList<String>(fruits);
	}
	
}
