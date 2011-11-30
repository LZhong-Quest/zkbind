package org.zkoss.bind.unitest2;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.zkoss.zktc.core.junit.TestCaseBase;
import org.zkoss.zktc.core.widget.WidgetDriver;
/**
 * test case for features from number 500-999
 * @author dennis
 *
 */
public class FeaturesTestCase0500 extends TestCaseBase{

	
	
	
	@Test
	public void f00638(){
		navigate(getTestCaseUrl("/bind/issue/F00638.zul"));
		
		Assert.assertEquals("A",findWidget("$l11").getValue());
		Assert.assertEquals("B",findWidget("$l12").getValue());
		Assert.assertEquals("A",findWidget("$l21").getValue());
		Assert.assertEquals("B",findWidget("$l22").getValue());
		Assert.assertEquals("C",findWidget("$l31").getValue());
		Assert.assertEquals("D",findWidget("$t31").getValue());
		
		findWidget("$t11").clear().keys("X").tab();
		findWidget("$t12").clear().keys("Y").tab();
		
		Assert.assertEquals("X",findWidget("$l11").getValue());
		Assert.assertEquals("Y",findWidget("$l12").getValue());
		Assert.assertEquals("X",findWidget("$l21").getValue());
		Assert.assertEquals("Y",findWidget("$l22").getValue());
		Assert.assertEquals("C",findWidget("$l31").getValue());
		Assert.assertEquals("D",findWidget("$t31").getValue());
	
		findWidget("$btn1").click();
		Assert.assertEquals("X",findWidget("$l11").getValue());
		Assert.assertEquals("Y",findWidget("$l12").getValue());
		Assert.assertEquals("X",findWidget("$l21").getValue());
		Assert.assertEquals("Y",findWidget("$l22").getValue());
		Assert.assertEquals("E",findWidget("$l31").getValue());
		Assert.assertEquals("F",findWidget("$t31").getValue());
	}
	
}
