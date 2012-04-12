package org.zkoss.bind.unitest2;

import org.junit.Assert;
import org.junit.Test;
import org.zkoss.zktc.core.junit.TestCaseBase;
import org.zkoss.zktc.core.widget.Widget;
/**
 * test case for features from number 500-999
 * @author dennis
 *
 */
public class FeaturesTestCase1000 extends TestCaseBase{

	
	
	
	@Test
	public void f01032BindContextEvent(){
		navigate(getTestCaseUrl("/bind/issue/F01032BindContextEvent.zul"));
		
		Widget msg = findWidget("$msg");
		Widget tb = findWidget("$tb");
		Widget btn = findWidget("$btn");
		
		tb.replace("a").tab();
		Assert.assertEquals("evt1:onChange,evt2:onChange, cmd:cmd",msg.getValue());
		
		btn.click();
		Assert.assertEquals("evt1:onClick,evt2:onClick, cmd:cmd",msg.getValue());
	}
	
	@Test
	public void f01033InitClass(){
		navigate(getTestCaseUrl("/bind/issue/F01033InitClass.zul"));
		
		Widget l11 = findWidget("$l11");
		Widget l12 = findWidget("$l12");
		Widget l21 = findWidget("$l21");
		Widget l22 = findWidget("$l22");

		Assert.assertEquals("",l11.getValue());
		Assert.assertEquals("Chen",l12.getValue());
		Assert.assertEquals("Ian",l21.getValue());
		Assert.assertEquals("Tasi",l22.getValue());
	}
}
