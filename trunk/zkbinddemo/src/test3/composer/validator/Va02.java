package test3.composer.validator;

import static java.lang.System.out;

import java.util.Date;
import java.util.Map;

import org.zkoss.bind.Converter;
import org.zkoss.bind.NotifyChange;
import org.zkoss.bind.Validator;

import test3.converter.*;
import test3.validator.*;


public class Va02{

	private Integer age = 0;
	private Integer negativeOne = -1;
	
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
	


	// ------ validator ------------
	public Validator getNonNegative(){
		return new NonNegativeValidator();
	}
	//--------- converter ------------
	public Converter getMaturityIndicator(){
		return new MaturityIndicator();
	}
	
	// -----------command -----------------

	public void add10(){
		age += 10;
	}
	
}
