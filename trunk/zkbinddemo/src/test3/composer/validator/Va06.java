package test3.composer.validator;

import static java.lang.System.out;

import org.zkoss.bind.Converter;
import org.zkoss.bind.NotifyChange;
import org.zkoss.bind.Validator;

import test3.converter.AdultConverter;
import test3.validator.BooleanValidator;


public class Va06{

	private Integer age = 0;
	private Integer negativeOne = -1;
	private boolean isAdult = false;
	
	public Integer getAge() {
		return age;
	}

	@NotifyChange
	public void setAge(Integer age) {
		this.age = age;
	}
	
	public Integer getNegativeOne() {
		return negativeOne;
	}
	
	public void setNegativeOne(Integer negativeOne) {
		this.negativeOne = negativeOne;
	}
	
	
	public boolean isAdult() {
		return isAdult;
	}

	@NotifyChange
	public void setAdult(boolean isAdult) {
		this.isAdult = isAdult;
	}

	

	// ------ validator ------------
	public Validator getBooleanValidator(){
		return new BooleanValidator();
	}	
	//--------- converter ------------
	
	public Converter getAdultConverter(){
		return new AdultConverter();
	}
	
	// -----------command -----------------
	public void checkAdult(){
		out.println("is Adult: "+isAdult);
	}	
	
}
