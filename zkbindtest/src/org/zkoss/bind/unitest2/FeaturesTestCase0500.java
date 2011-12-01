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
	
	
	@Test
	public void f00633(){
		navigate(getTestCaseUrl("/bind/issue/F00633.zul"));
		
		Assert.assertEquals("onCreate 1",findWidget("$l11").getValue());
		Assert.assertEquals("onCreate 2",findWidget("$l12").getValue());
		
		findWidget("$btn1").click();
		Assert.assertEquals("doCommand1",findWidget("$l11").getValue());
		
		findWidget("$btn2").click();
		Assert.assertEquals("doCommand2",findWidget("$l11").getValue());
		
		findWidget("$btn3").click();
		Assert.assertEquals("doCommand3 btn3 true",findWidget("$l11").getValue());
		
		findWidget("$btn4").click();
		Assert.assertEquals("doCommand4 null null null btn4 true",findWidget("$l11").getValue());
		
		findWidget("$btn5").click();
		Assert.assertEquals("doCommand5 99 true XYZ btn5 true",findWidget("$l11").getValue());
		
		findWidget("$btn6").click();
		Assert.assertEquals("doCommand6 9 true ABCD btn6 true",findWidget("$l11").getValue());
		
		findWidget("$btn7").click();
		Assert.assertEquals("doCommandX 9 true XYZ cmd7",findWidget("$l11").getValue());
		
		findWidget("$btn8").click();
		Assert.assertEquals("doCommandX 22 true ABCD cmd8",findWidget("$l11").getValue());
		
		findWidget("$btn9").click();
		Assert.assertEquals("doCommandX 9 false EFG cmd9",findWidget("$l11").getValue());
		
		findWidget("$btn10").click();
		Assert.assertEquals("object is btn10",findWidget("$l12").getValue());
		
		findWidget("$btn11").click();
		Assert.assertEquals("object is desktop",findWidget("$l12").getValue());
		
		findWidget("$btn12").click();
		Assert.assertEquals("object is h11",findWidget("$l12").getValue());
		
	}
}
