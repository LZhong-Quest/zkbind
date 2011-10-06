package org.zkoss.zkbind.unitest2;

import org.junit.Assert;
import org.junit.Test;
import org.zkoss.zktc.core.junit.TestCaseBase;
import org.zkoss.zktc.core.widget.WidgetDriver;
/**
 * test case for features from number 1-100
 * @author dennis
 *
 */
public class FeaturesTestCase0100 extends TestCaseBase{

	@Test
	public void f0010(){
		WidgetDriver driver = getDriver();
		driver.navigate(getTestCaseUrl("/test2/F0010.zul"));
		
		Assert.assertEquals("A-toUI-c0",findWidget("$l0").getValue());
		Assert.assertEquals("B-toUI-c1",findWidget("$l1").getValue());
		Assert.assertEquals("C-toUI-c2",findWidget("$l2").getValue());
		
		Assert.assertEquals("A-toUI-c0",findWidget("$t0").getValue());
		Assert.assertEquals("B-toUI-c1",findWidget("$t1").getValue());
		Assert.assertEquals("C-toUI-c2",findWidget("$t2").getValue());
		
		findWidget("$t0").clear().keys("I").tab();
		Assert.assertEquals("I-toBean-c0-toUI-c0",findWidget("$l0").getValue());
		Assert.assertEquals("B-toUI-c1",findWidget("$l1").getValue());
		Assert.assertEquals("C-toUI-c2",findWidget("$l2").getValue());
		
		Assert.assertEquals("I-toBean-c0-toUI-c0",findWidget("$t0").getValue());
		Assert.assertEquals("B-toUI-c1",findWidget("$t1").getValue());
		Assert.assertEquals("C-toUI-c2",findWidget("$t2").getValue());
		
		findWidget("$t1").clear().keys("J").tab();
		Assert.assertEquals("I-toBean-c0-toUI-c0",findWidget("$l0").getValue());
		Assert.assertEquals("J-toBean-c1-toUI-c1",findWidget("$l1").getValue());
		Assert.assertEquals("C-toUI-c2",findWidget("$l2").getValue());
		
		Assert.assertEquals("I-toBean-c0-toUI-c0",findWidget("$t0").getValue());
		Assert.assertEquals("J-toBean-c1-toUI-c1",findWidget("$t1").getValue());
		Assert.assertEquals("C-toUI-c2",findWidget("$t2").getValue());
		
		findWidget("$t2").clear().keys("K").tab();
		Assert.assertEquals("I-toBean-c0-toUI-c0",findWidget("$l0").getValue());
		Assert.assertEquals("J-toBean-c1-toUI-c1",findWidget("$l1").getValue());
		Assert.assertEquals("K-toBean-c2-toUI-c2",findWidget("$l2").getValue());
		
		Assert.assertEquals("I-toBean-c0-toUI-c0",findWidget("$t0").getValue());
		Assert.assertEquals("J-toBean-c1-toUI-c1",findWidget("$t1").getValue());
		Assert.assertEquals("K-toBean-c2-toUI-c2",findWidget("$t2").getValue());
		
		
		//test converter dependency
		findWidget("$t1").clear().keys("X").tab();
		Assert.assertEquals("I-toBean-c0-toUI-c0",findWidget("$l0").getValue());
		Assert.assertEquals("X-toBean-c1-toUI-c1",findWidget("$l1").getValue());//
		Assert.assertEquals("K-toBean-c2-toUI-c2",findWidget("$l2").getValue());
		
		Assert.assertEquals("I-toBean-c0-toUI-c0",findWidget("$t0").getValue());
		Assert.assertEquals("X-toBean-c1-toUI-c1",findWidget("$t1").getValue());//
		Assert.assertEquals("K-toBean-c2-toUI-c2",findWidget("$t2").getValue());
		
		findWidget("$btn1").click();
		Assert.assertEquals("I-toBean-c0-toUI-c0",findWidget("$l0").getValue());
		Assert.assertEquals("X-toBean-c1-toUI-c3",findWidget("$l1").getValue());//
		Assert.assertEquals("K-toBean-c2-toUI-c2",findWidget("$l2").getValue());
		
		Assert.assertEquals("I-toBean-c0-toUI-c0",findWidget("$t0").getValue());
		Assert.assertEquals("X-toBean-c1-toUI-c3",findWidget("$t1").getValue());//
		Assert.assertEquals("K-toBean-c2-toUI-c2",findWidget("$t2").getValue());
		
		findWidget("$t1").clear().keys("X").tab();
		Assert.assertEquals("I-toBean-c0-toUI-c0",findWidget("$l0").getValue());
		Assert.assertEquals("X-toBean-c3-toUI-c3",findWidget("$l1").getValue());//
		Assert.assertEquals("K-toBean-c2-toUI-c2",findWidget("$l2").getValue());
		
		Assert.assertEquals("I-toBean-c0-toUI-c0",findWidget("$t0").getValue());
		Assert.assertEquals("X-toBean-c3-toUI-c3",findWidget("$t1").getValue());//
		Assert.assertEquals("K-toBean-c2-toUI-c2",findWidget("$t2").getValue());
		
		findWidget("$btn2").click();
		Assert.assertEquals("I-toBean-c0-toUI-c0",findWidget("$l0").getValue());
		Assert.assertEquals("X-toBean-c3-toUI-c4",findWidget("$l1").getValue());//
		Assert.assertEquals("K-toBean-c2-toUI-c2",findWidget("$l2").getValue());
		
		Assert.assertEquals("I-toBean-c0-toUI-c0",findWidget("$t0").getValue());
		Assert.assertEquals("X-toBean-c3-toUI-c4",findWidget("$t1").getValue());//
		Assert.assertEquals("K-toBean-c2-toUI-c2",findWidget("$t2").getValue());
		
	}
	
	
	@Test
	public void f0013(){
		WidgetDriver driver = getDriver();
		driver.navigate(getTestCaseUrl("/test2/F0013.zul"));
		
		Assert.assertEquals("A",findWidget("$l1").getValue());
		Assert.assertEquals("B",findWidget("$l2").getValue());
		
		Assert.assertEquals("A",findWidget("$t1").getValue());
		Assert.assertEquals("B",findWidget("$t2").getValue());
		
		findWidget("$t1").clear().keys("Dennis");
		findWidget("$t2").clear().keys("Chen");
		
		findWidget("$btn1").click();
		
		Assert.assertEquals("Dennis-cmd1",findWidget("$l1").getValue());
		Assert.assertEquals("Chen-cmd1",findWidget("$l2").getValue());
		
		Assert.assertEquals("Dennis",findWidget("$t1").getValue());
		Assert.assertEquals("Chen",findWidget("$t2").getValue());
		
		
		findWidget("$t1").clear().keys("Alice");
		findWidget("$t2").clear().keys("Wu");
		
		findWidget("$btn2").click();
		
		Assert.assertEquals("Alice-cmd2",findWidget("$l1").getValue());
		Assert.assertEquals("Wu-cmd2",findWidget("$l2").getValue());
		
		Assert.assertEquals("Alice-cmd2",findWidget("$t1").getValue());
		Assert.assertEquals("Wu-cmd2",findWidget("$t2").getValue());
		
		findWidget("$t1").clear().keys("Jumper");
		findWidget("$t2").clear().keys("Tj");
		
		findWidget("$btn3").click();
		
		Assert.assertEquals("Jumper-cmd3",findWidget("$l1").getValue());
		Assert.assertEquals("Tj-cmd3",findWidget("$l2").getValue());
		
		Assert.assertEquals("Jumper-cmd3",findWidget("$t1").getValue());
		Assert.assertEquals("Tj-cmd3",findWidget("$t2").getValue());
	}
	
	
	
	
}
