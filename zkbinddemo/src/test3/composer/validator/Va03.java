package test3.composer.validator;

import java.util.Map;

import org.zkoss.bind.Converter;
import org.zkoss.bind.NotifyChange;
import org.zkoss.bind.Validator;

import test3.converter.MaturityIndicator;
import test3.validator.LowerBoundValidator;


public class Va03{

	private Integer age = 0;
	
	public Integer getAge() {
		return age;
	}

	@NotifyChange
	public void setAge(Integer age) {
		this.age = age;
	}



	// ------ validator ------------
	public Validator getLowerBoundValidator(){
		return new LowerBoundValidator();
	}
	//--------- converter ------------
	public Converter getMaturityIndicator(){
		return new MaturityIndicator();
	}
	
	// -----------command -----------------
	
	public void minus(Map<String, Object> args){
		Long decrement = (Long)args.get("decrement");
		age -= decrement.intValue();
	}

}
