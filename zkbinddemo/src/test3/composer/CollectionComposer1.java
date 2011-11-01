package test3.composer;

import static java.lang.System.out;

import java.util.List;

import org.zkoss.bind.BindComposer;
import org.zkoss.bind.Converter;
import org.zkoss.bind.DependsOn;
import org.zkoss.bind.NotifyChange;
import org.zkoss.bind.Validator;

import test3.converter.NotConverter;
import test3.data.ListPool;
import test3.validator.MaxLengthValidator;
import test3.validator.NonNegativeValidator;


public class CollectionComposer1{
	private boolean isSingle = true;
	private List<String> selectedList;
	private String selectedOne = ListPool.getListNameList().get(0);
	

	public boolean isSingle() {
		return isSingle;
	}
	
	@NotifyChange
	public void setSingle(boolean isSingle) {
		this.isSingle = isSingle;
		out.println(isSingle);
	}
	
	public String getSelectedOne() {
		return selectedOne;
	}

	@NotifyChange
	public void setSelectedOne(String selectedOne) {
		this.selectedOne = selectedOne;
	}

	@DependsOn("selectedOne")
	public List<String> getSelectedList() {
		if (selectedOne.equals("Fruit")){
			selectedList = getFruitList();
		}else{
			selectedList = getCarMarkList();
		}
		return selectedList;
	}


	public List<String> getCarMarkList(){
		return ListPool.getCarMarkList();
	}
	
	public List<String> getFruitList(){
		return ListPool.getFruitList();
	}
	
	public List<String> getListNameList(){
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
