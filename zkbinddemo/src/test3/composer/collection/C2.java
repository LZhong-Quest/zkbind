package test3.composer.collection;

import static java.lang.System.out;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.NotifyChange;
import org.zkoss.zul.ListModelList;

import test3.data.ListPool;


public class C2{
	private List<String> fruitList ;

	public C2(){
		fruitList = new LinkedList<String>();
		fruitList.addAll(ListPool.getFruitList());

	}
	

	public List<String> getFruitList(){
		return new ListModelList(fruitList);
	}

	
	//FIXME can NOT remove last 2 items
	// -----------command -----------------
	@NotifyChange("fruitList")
	public void delete(Map<String, Object> args){
		Number index = (Number)args.get("index");
		out.println(index);
		fruitList.remove(index.intValue());
		out.println("size:"+fruitList.size());
	}
}
