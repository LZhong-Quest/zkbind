package test3.composer.validator;

import static java.lang.System.out;

import java.util.Date;
import java.util.Map;

import org.zkoss.bind.Converter;
import org.zkoss.bind.NotifyChange;
import org.zkoss.bind.Validator;

import test3.converter.*;
import test3.validator.*;


public class ValidatorComposer{

	private Integer age = 0;
	private Integer negativeOne = -1;
	private String keyword;
	private Integer maxLength =3;
	private Integer limit = 18;
	private boolean isAdult = false;
	private Date startDate;
	private Date endDate;
	
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

	public Date getStartDate() {
		return startDate;
	}

	@NotifyChange
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	@NotifyChange
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	public Validator getDurationValidator(){
		return new DurationValidator();
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
	
	public void ok(){
		
	}
}
