package test3.composer;

import static java.lang.System.out;

import org.zkoss.bind.Converter;
import org.zkoss.bind.NotifyChange;
import org.zkoss.bind.Validator;
import org.zkoss.zul.ListModelList;

import test3.converter.NotConverter;
import test3.data.ListPool;
import test3.validator.MaxLengthValidator;
import test3.validator.NonNegativeValidator;


public class CollectionComposer{
	private boolean isSingle = false;
	

	public boolean isSingle() {
		return isSingle;
	}
	
	@NotifyChange
	public void setSingle(boolean isSingle) {
		this.isSingle = isSingle;
		out.println(isSingle);
	}
	
	public ListModelList getListNameList(){
		return ListPool.getListNameList();
	}
	// ------ validator ------------
	public Validator getNonNegative(){
		return new NonNegativeValidator();
	}
	public Validator getMaxLengthValidator(){
		return new MaxLengthValidator();
	}
	
	//--------- converter ------------
	public Converter getNotConverter(){
		return new NotConverter();
	}

	
	// -----------command -----------------
	
}
