package org.zkoss.zktest.zbind.basic;

import org.zkoss.bind.NotifyChange;

/**
 * a mix example of mvp to mvvm.
 * 
 * 
 * @author dennis
 * 
 */
public class MVP2MVVMViewModel {

	private Boolean mWriteProtected;

	private String mTextA;

	public MVP2MVVMViewModel() {
		mWriteProtected = true;
		mTextA = "Start text";
	}

	public Boolean getWriteProtected() {
		return mWriteProtected;
	}

	@NotifyChange("writeProtected")
	public void toggleWriteProtected() {
		mWriteProtected = !mWriteProtected;
	}

	public String getTextA() {
		return mTextA;
	}

	@NotifyChange
	public void setTextA(String textA) {
		mTextA = textA;
	}

}
