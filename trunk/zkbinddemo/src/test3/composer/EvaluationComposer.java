package test3.composer;

import static java.lang.System.out;

import org.zkoss.bind.BindComposer;
import org.zkoss.bind.NotifyChange;

import test3.data.ListPool;



public class EvaluationComposer extends BindComposer{

	Boolean booleanFlag = true;
	
	public String getComposerName(){
		return this.getClass().toString();
	}
	
	public String[] getFruitList(){
		out.println(ListPool.getFruitList().toString());
		return ListPool.fruits;
	}

	public Boolean getBooleanFlag() {
		return booleanFlag;
	}
	
	/**
	 * TODO if we don't put '@NotifyChange' here, it won't notify other components that also bind to this property.  
	 * @param booleanFlag
	 */
	@NotifyChange
	public void setBooleanFlag(Boolean booleanFlag) {
		out.println("booleanFlag is "+booleanFlag);
		this.booleanFlag = booleanFlag;
	}
	

}
