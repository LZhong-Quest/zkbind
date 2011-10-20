package org.zkoss.zkbind.unitest2;

import org.junit.Assert;
import org.junit.Test;
import org.zkoss.zktc.core.junit.TestCaseBase;
import org.zkoss.zktc.core.widget.WidgetDriver;
/**
 * 
 * @author dennis
 *
 */
public class MiscTestCase extends TestCaseBase{

	@Test
	public void testArgs(){
		WidgetDriver driver = getDriver();
		driver.navigate(getTestCaseUrl("/test2/args.zul"));
		
		Assert.assertEquals("A-Arg1",findWidget("$l1").getValue());
		Assert.assertEquals("B-myarg1",findWidget("$l2").getValue());
		Assert.assertEquals("A-Arg1",findWidget("$t1").getValue());
		Assert.assertEquals("B-myarg1",findWidget("$t2").getValue());
		
		findWidget("$t1").clear().keys("X").tab();
		Assert.assertEquals("X-Arg2-Arg1",findWidget("$l1").getValue());
		Assert.assertEquals("B-myarg1",findWidget("$l2").getValue());
		Assert.assertEquals("X-Arg2-Arg1",findWidget("$t1").getValue());
		Assert.assertEquals("B-myarg1",findWidget("$t2").getValue());
		
		findWidget("$t2").clear().keys("Y").tab();
		Assert.assertEquals("X-Arg2-Arg1",findWidget("$l1").getValue());
		Assert.assertEquals("Y-myarg2-myarg1",findWidget("$l2").getValue());
		Assert.assertEquals("X-Arg2-Arg1",findWidget("$t1").getValue());
		Assert.assertEquals("Y-myarg2-myarg1",findWidget("$t2").getValue());
		
		findWidget("$btn1").click();
		Assert.assertEquals("X-Arg2Dennis-Arg1",findWidget("$l1").getValue());
		Assert.assertEquals("Y-myarg2Chen-myarg1",findWidget("$l2").getValue());
		Assert.assertEquals("X-Arg2Dennis-Arg1",findWidget("$t1").getValue());
		Assert.assertEquals("Y-myarg2Chen-myarg1",findWidget("$t2").getValue());
		
		findWidget("$t3").clear().keys("ABC").tab();
		Assert.assertEquals("value have to equals V1",findWidget("$l3").getValue());
		findWidget("$t3").clear().keys("V1").tab();
		Assert.assertEquals("",findWidget("$l3").getValue());
		Assert.assertEquals("V1-Arg1",findWidget("$l1").getValue());
		
		findWidget("$t4").clear().keys("ABC").tab();
		Assert.assertEquals("",findWidget("$l4").getValue());
		findWidget("$btn2").click();
		Assert.assertEquals("value have to equals V2",findWidget("$l4").getValue());
		Assert.assertEquals("V1",findWidget("$t3").getValue());
		
		findWidget("$t4").clear().keys("V2").tab();
		findWidget("$btn2").click();
		Assert.assertEquals("execute cmd2",findWidget("$l4").getValue());
		Assert.assertEquals("V2-Arg1",findWidget("$l1").getValue());
		Assert.assertEquals("V2",findWidget("$t3").getValue());
		
		
	}
	
	
}
