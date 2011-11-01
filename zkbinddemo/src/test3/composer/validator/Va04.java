package test3.composer.validator;

import java.util.Map;

import org.zkoss.bind.Converter;
import org.zkoss.bind.NotifyChange;
import org.zkoss.bind.Validator;

import test3.converter.MaturityIndicator;
import test3.validator.UpperBoundValidator;


public class Va04{

	private Integer age = 0;
	private Integer positiveOne = 1;

	
	public Integer getPositiveOne() {
		return positiveOne;
	}

	public void setPositiveOne(Integer positiveOne) {
		this.positiveOne = positiveOne;
	}

	public Integer getAge() {
		return age;
	}

	@NotifyChange
	public void setAge(Integer age) {
		this.age = age;
	}
	

	// ------ validator ------------
	public Validator getUpperBoundValidator(){
		return new UpperBoundValidator();
	}
	//--------- converter ------------
	public Converter getMaturityIndicator(){
		return new MaturityIndicator();
	}
	
	
	// -----------command -----------------
	
	public void add(Map<String, Object> args){
		Long increment = (Long)args.get("increment");
		age += increment.intValue();
	}

}
