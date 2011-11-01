package test3.composer.validator;

import static java.lang.System.out;

import org.zkoss.bind.Converter;
import org.zkoss.bind.DependsOn;
import org.zkoss.bind.NotifyChange;
import org.zkoss.bind.Validator;

import test3.converter.MaturityIndicator;
import test3.validator.NonNegativeValidator;


public class Va11{

	private Integer age = 0;
	private Integer negativeOne = -1;
	private boolean isLessThanThirteen = true;
	private boolean isLessThanEighteen = true;
	private boolean isOverEighteen = false;
	
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	public Integer getNegativeOne() {
		return negativeOne;
	}
	
	public void setNegativeOne(Integer negativeOne) {
		this.negativeOne = negativeOne;
	}
	
	@DependsOn("age")
	public boolean isLessThanThirteen() {
		isLessThanThirteen = age < 13;
		return isLessThanThirteen;
	}

	public void setLessThanThirteen(boolean isLessThanThirteen) {
		this.isLessThanThirteen = isLessThanThirteen;
	}

	@DependsOn("age")
	public boolean isLessThanEighteen() {
		isLessThanEighteen = age < 18;
		return isLessThanEighteen;
	}

	public void setLessThanEighteen(boolean isLessThanEighteen) {
		this.isLessThanEighteen = isLessThanEighteen;
	}

	@DependsOn("age")
	public boolean isOverEighteen() {
		isOverEighteen = age >= 18;
		return isOverEighteen;
	}

	public void setOverEighteen(boolean isOverEighteen) {
		this.isOverEighteen = isOverEighteen;
	}

	// ------ validator ------------
	public Validator getNonNegative(){
		return new NonNegativeValidator();
	}
	
	//--------- converter ------------
	public Converter getMaturityIndicator(){
		return new MaturityIndicator();
	}

	// -----------command -----------------
	public void classify(){
//		if (age < 13){
//			isLessThanThirteen = true;
//			isLessThanEighteen = true;
//			isOverEighteen = false;
//		}else if (age < 18){
//			isLessThanThirteen = false;
//			isLessThanEighteen = true;
//			isOverEighteen = false;
//		}else{
//			isLessThanThirteen = false;
//			isLessThanEighteen = false;
//			isOverEighteen = true;
//		}
	}
}
