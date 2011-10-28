package test3.composer;

import static java.lang.System.out;

import java.util.Map;

import org.zkoss.bind.Converter;
import org.zkoss.bind.NotifyChange;
import org.zkoss.bind.Validator;

import test3.converter.AdultConverter;
import test3.converter.AgeLimitIndicator;
import test3.converter.MaturityIndicator;
import test3.validator.MaxLengthValidator;
import test3.validator.NonNegativeValidator;


public class ConverterComposer{

	private Integer age = 0;
	private Integer negativeOne = -1;
	private String keyword;
	private Integer maxLength =3;
	private Integer limit = 18;
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
	
	public String getKeyword() {
		return keyword;
	}
	
	@NotifyChange
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public boolean isAdult() {
		return isAdult;
	}

	@NotifyChange
	public void setAdult(boolean isAdult) {
		this.isAdult = isAdult;
	}

	public Integer getMaxLength() {
		return maxLength;
	}
	
	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}
	
	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	// ------ validator ------------
	public Validator getNonNegative(){
		return new NonNegativeValidator();
	}
	public Validator getMaxLengthValidator(){
		return new MaxLengthValidator();
	}
	
	//--------- converter ------------
	public Converter getMaturityIndicator(){
		return new MaturityIndicator();
	}
	public Converter getAgeLimitIndicator(){
		return new AgeLimitIndicator();
	}
	
	public Converter getAdultConverter(){
		return new AdultConverter();
	}
	
	// -----------command -----------------
	public void submit(){
		out.println("current age is "+age);
	}
	public void submit5(){
		submit();
	}
	public void checkAdult(){
		out.println("is Adult: "+isAdult);
	}	
	
	public void add(Map<String, Object> args){
		Long increment = (Long)args.get("increment");
		age += increment.intValue();
	}

	public void add10(){
		age += 10;
	}
}
