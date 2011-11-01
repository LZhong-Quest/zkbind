package test3.composer.validator;

import org.zkoss.bind.NotifyChange;
import org.zkoss.bind.Validator;

import test3.validator.MaxLengthValidator;


public class Va08{

	private String keyword;
	private Integer maxLength =3;
	
	public String getKeyword() {
		return keyword;
	}
	
	@NotifyChange
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	

	public Integer getMaxLength() {
		return maxLength;
	}
	
	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}
	

	// ------ validator ------------
	public Validator getMaxLengthValidator(){
		return new MaxLengthValidator();
	}

}
