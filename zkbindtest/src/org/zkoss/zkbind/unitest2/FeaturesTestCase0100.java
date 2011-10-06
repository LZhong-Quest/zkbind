package org.zkoss.zkbind.unitest2;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.zkoss.zktc.core.junit.TestCaseBase;
import org.zkoss.zktc.core.widget.SelectWidget;
import org.zkoss.zktc.core.widget.Widget;
import org.zkoss.zktc.core.widget.WidgetDriver;
/**
 * test case for features from number 1-100
 * @author dennis
 *
 */
public class FeaturesTestCase0100 extends TestCaseBase{

	
	@Test
	public void f0013(){
		WidgetDriver driver = getDriver();
		driver.navigate(getTestCaseUrl("/test2/F0013.zul"));
		
		Assert.assertEquals("A",findWidget("$l1").getValue());
		Assert.assertEquals("B",findWidget("$l2").getValue());
		
		Assert.assertEquals("A",findWidget("$t1").getValue());
		Assert.assertEquals("B",findWidget("$t2").getValue());
		
		findWidget("$t1").clear(0,0).keys("Dennis",0,0);
		findWidget("$t2").clear(0,0).keys("Chen",0,0);
		
		findWidget("$btn1").click();
		
		Assert.assertEquals("Dennis-cmd1",findWidget("$l1").getValue());
		Assert.assertEquals("Chen-cmd1",findWidget("$l2").getValue());
		
		Assert.assertEquals("Dennis",findWidget("$t1").getValue());
		Assert.assertEquals("Chen",findWidget("$t2").getValue());
		
		
		findWidget("$t1").clear(0,0).keys("Alice");
		findWidget("$t2").clear(0,0).keys("Wu");
		
		findWidget("$btn2").click();
		
		Assert.assertEquals("Alice-cmd2",findWidget("$l1").getValue());
		Assert.assertEquals("Wu-cmd2",findWidget("$l2").getValue());
		
		Assert.assertEquals("Alice-cmd2",findWidget("$t1").getValue());
		Assert.assertEquals("Wu-cmd2",findWidget("$t2").getValue());
		
		findWidget("$t1").clear(0,0).keys("Jumper");
		findWidget("$t2").clear(0,0).keys("Tj");
		
		findWidget("$btn3").click();
		
		Assert.assertEquals("Jumper-cmd3",findWidget("$l1").getValue());
		Assert.assertEquals("Tj-cmd3",findWidget("$l2").getValue());
		
		Assert.assertEquals("Jumper-cmd3",findWidget("$t1").getValue());
		Assert.assertEquals("Tj-cmd3",findWidget("$t2").getValue());
	}
	
	
	
	
}
