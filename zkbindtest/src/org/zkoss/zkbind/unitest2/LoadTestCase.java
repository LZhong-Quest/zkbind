package org.zkoss.zkbind.unitest2;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.zkoss.zktc.core.junit.TestCaseBase;
import org.zkoss.zktc.core.widget.SelectWidget;
import org.zkoss.zktc.core.widget.Widget;
import org.zkoss.zktc.core.widget.WidgetDriver;

public class LoadTestCase extends TestCaseBase{

	
	@Test
	public void testLoad(){
		WidgetDriver driver = getDriver();
		driver.navigate(getTestCaseUrl("/test2/zkbind-load.zul"));
		
		Assert.assertEquals("First1",findWidget("$l1").getAttribute("value"));
		Assert.assertEquals("Last1",findWidget("$l2").getAttribute("value"));
		Assert.assertEquals("First1 Last1",findWidget("$l3").getAttribute("value"));
		Assert.assertEquals("87 Zhengzhou Road #11F-2 Taipei",findWidget("$l4").getAttribute("value"));
		
		Assert.assertEquals("First1",findWidget("$l5").getAttribute("value"));
		Assert.assertEquals("Last1",findWidget("$l6").getAttribute("value"));
		Assert.assertEquals("First1 Last1",findWidget("$l7").getAttribute("value"));
		Assert.assertEquals("87 Zhengzhou Road #11F-2 Taipei",findWidget("$l8").getAttribute("value"));
		
		findWidget("$btn1").click();
		
		Assert.assertEquals("Dennis",findWidget("$l1").getAttribute("value"));
		Assert.assertEquals("Last1",findWidget("$l2").getAttribute("value"));
		Assert.assertEquals("Dennis Last1",findWidget("$l3").getAttribute("value"));
		Assert.assertEquals("87 Zhengzhou Road #11F-2 Taipei",findWidget("$l4").getAttribute("value"));
		
		Assert.assertEquals("Dennis",findWidget("$l5").getAttribute("value"));
		Assert.assertEquals("Last1",findWidget("$l6").getAttribute("value"));
		Assert.assertEquals("Dennis Last1",findWidget("$l7").getAttribute("value"));
		Assert.assertEquals("87 Zhengzhou Road #11F-2 Taipei",findWidget("$l8").getAttribute("value"));
		
		findWidget("$btn2").click();
		
		Assert.assertEquals("Dennis",findWidget("$l1").getAttribute("value"));
		Assert.assertEquals("Chen",findWidget("$l2").getAttribute("value"));
		Assert.assertEquals("Dennis Chen",findWidget("$l3").getAttribute("value"));
		Assert.assertEquals("87 Zhengzhou Road #11F-2 Taipei",findWidget("$l4").getAttribute("value"));
		
		Assert.assertEquals("Dennis",findWidget("$l5").getAttribute("value"));
		Assert.assertEquals("Chen",findWidget("$l6").getAttribute("value"));
		Assert.assertEquals("Dennis Chen",findWidget("$l7").getAttribute("value"));
		Assert.assertEquals("87 Zhengzhou Road #11F-2 Taipei",findWidget("$l8").getAttribute("value"));
		
		findWidget("$btn3").click();
		
		Assert.assertEquals("Alex",findWidget("$l1").getAttribute("value"));
		Assert.assertEquals("Chen",findWidget("$l2").getAttribute("value"));
		Assert.assertEquals("Alex Chen",findWidget("$l3").getAttribute("value"));
		Assert.assertEquals("87 Zhengzhou Road #11F-2 Taipei",findWidget("$l4").getAttribute("value"));
		
		Assert.assertEquals("Alex",findWidget("$l5").getAttribute("value"));
		Assert.assertEquals("Chen",findWidget("$l6").getAttribute("value"));
		Assert.assertEquals("Alex Chen",findWidget("$l7").getAttribute("value"));
		Assert.assertEquals("87 Zhengzhou Road #11F-2 Taipei",findWidget("$l8").getAttribute("value"));
		
		findWidget("$btn4").click();
		
		Assert.assertEquals("Alex",findWidget("$l1").getAttribute("value"));
		Assert.assertEquals("Wang",findWidget("$l2").getAttribute("value"));
		Assert.assertEquals("Alex Wang",findWidget("$l3").getAttribute("value"));
		Assert.assertEquals("87 Zhengzhou Road #11F-2 Taipei",findWidget("$l4").getAttribute("value"));
		
		Assert.assertEquals("Alex",findWidget("$l5").getAttribute("value"));
		Assert.assertEquals("Wang",findWidget("$l6").getAttribute("value"));
		Assert.assertEquals("Alex Wang",findWidget("$l7").getAttribute("value"));
		Assert.assertEquals("87 Zhengzhou Road #11F-2 Taipei",findWidget("$l8").getAttribute("value"));
		
		
		findWidget("$btn5").click();
		
		Assert.assertEquals("Alex",findWidget("$l1").getAttribute("value"));
		Assert.assertEquals("Chen",findWidget("$l2").getAttribute("value"));
		Assert.assertEquals("Alex Chen",findWidget("$l3").getAttribute("value"));
		Assert.assertEquals("87 Zhengzhou Road #11F-2 Taipei",findWidget("$l4").getAttribute("value"));
		
		Assert.assertEquals("Alex",findWidget("$l5").getAttribute("value"));
		Assert.assertEquals("Chen",findWidget("$l6").getAttribute("value"));
		Assert.assertEquals("Alex Chen",findWidget("$l7").getAttribute("value"));
		Assert.assertEquals("87 Zhengzhou Road #11F-2 Taipei",findWidget("$l8").getAttribute("value"));
		

		findWidget("$btn6").click();
		
		Assert.assertEquals("Alex",findWidget("$l1").getAttribute("value"));
		Assert.assertEquals("Wang",findWidget("$l2").getAttribute("value"));
		Assert.assertEquals("Alex Wang",findWidget("$l3").getAttribute("value"));
		Assert.assertEquals("87 Zhengzhou Road #11F-2 Taipei",findWidget("$l4").getAttribute("value"));
		
		Assert.assertEquals("Alex",findWidget("$l5").getAttribute("value"));
		Assert.assertEquals("Wang",findWidget("$l6").getAttribute("value"));
		Assert.assertEquals("Alex Wang",findWidget("$l7").getAttribute("value"));
		Assert.assertEquals("87 Zhengzhou Road #11F-2 Taipei",findWidget("$l8").getAttribute("value"));
		

		findWidget("$btn7").click();
		
		Assert.assertEquals("Ian",findWidget("$l1").getAttribute("value"));
		Assert.assertEquals("Tasi",findWidget("$l2").getAttribute("value"));
		Assert.assertEquals("Ian Tasi",findWidget("$l3").getAttribute("value"));
		Assert.assertEquals("87 Zhengzhou Road #11F-2 Taipei",findWidget("$l4").getAttribute("value"));
		
		Assert.assertEquals("Alex",findWidget("$l5").getAttribute("value"));
		Assert.assertEquals("Wang",findWidget("$l6").getAttribute("value"));
		Assert.assertEquals("Alex Wang",findWidget("$l7").getAttribute("value"));
		Assert.assertEquals("87 Zhengzhou Road #11F-2 Taipei",findWidget("$l8").getAttribute("value"));
		
		findWidget("$btn8").click();
		
		Assert.assertEquals("Ian",findWidget("$l1").getAttribute("value"));
		Assert.assertEquals("Tasi",findWidget("$l2").getAttribute("value"));
		Assert.assertEquals("Ian Tasi",findWidget("$l3").getAttribute("value"));
		Assert.assertEquals("87 Zhengzhou Road #11F-2 Taipei",findWidget("$l4").getAttribute("value"));
		
		Assert.assertEquals("Jumper",findWidget("$l5").getAttribute("value"));
		Assert.assertEquals("Chen",findWidget("$l6").getAttribute("value"));
		Assert.assertEquals("Jumper Chen",findWidget("$l7").getAttribute("value"));
		Assert.assertEquals("87 Zhengzhou Road #11F-2 Taipei",findWidget("$l8").getAttribute("value"));
	}
	
	
	@Test
	public void testIndirect1(){
		WidgetDriver driver = getDriver();
		driver.navigate(getTestCaseUrl("/test2/zkbind-load-indirect.zul"));
		
		Assert.assertEquals("First1",findWidget("$l1").getAttribute("value"));
		Assert.assertEquals("Last1",findWidget("$l2").getAttribute("value"));
		Assert.assertEquals("First1 Last1",findWidget("$l3").getAttribute("value"));
		Assert.assertEquals("First1",findWidget("$l4").getAttribute("value"));
		Assert.assertEquals(0L,findWidget("$select").getAttribute("selectedIndex"));
		
		
		findWidget("$l1").clear().keys("AAA");
		findWidget("$btn1").focus(0,1,2000);
		Assert.assertEquals("Last1",findWidget("$l2").getAttribute("value"));
		Assert.assertEquals("AAA Last1",findWidget("$l3").getAttribute("value"));
		Assert.assertEquals("AAA",findWidget("$l4").getAttribute("value"));
		
		
		
		findWidget("$btn2").click();
		
		Assert.assertEquals("AAA",findWidget("$l1").getAttribute("value"));
		Assert.assertEquals("Last1",findWidget("$l2").getAttribute("value"));
		Assert.assertEquals("AAA Last1",findWidget("$l3").getAttribute("value"));
		Assert.assertEquals("Last1",findWidget("$l4").getAttribute("value"));
		Assert.assertEquals(1L,findWidget("$select").getAttribute("selectedIndex"));
		
		findWidget("$l1").clear().keys("BBB");
		findWidget("$btn1").focus(0,1,2000);
		Assert.assertEquals("Last1",findWidget("$l2").getAttribute("value"));
		Assert.assertEquals("BBB Last1",findWidget("$l3").getAttribute("value"));
		Assert.assertEquals("Last1",findWidget("$l4").getAttribute("value"));
		
		
		findWidget("$btn3").click();
		
		Assert.assertEquals("BBB",findWidget("$l1").getAttribute("value"));
		Assert.assertEquals("Last1",findWidget("$l2").getAttribute("value"));
		Assert.assertEquals("BBB Last1",findWidget("$l3").getAttribute("value"));
		Assert.assertEquals("BBB Last1",findWidget("$l4").getAttribute("value"));
		Assert.assertEquals(2L,findWidget("$select").getAttribute("selectedIndex"));
		
		findWidget("$btn1").click();
		
		Assert.assertEquals("BBB",findWidget("$l1").getAttribute("value"));
		Assert.assertEquals("Last1",findWidget("$l2").getAttribute("value"));
		Assert.assertEquals("BBB Last1",findWidget("$l3").getAttribute("value"));
		Assert.assertEquals("BBB",findWidget("$l4").getAttribute("value"));
		Assert.assertEquals(0L,findWidget("$select").getAttribute("selectedIndex"));
		
		
	}
	
	@Test
	public void testIndirect2(){
		WidgetDriver driver = getDriver();
		driver.navigate(getTestCaseUrl("/test2/zkbind-load-indirect.zul"));
		
		Assert.assertEquals("First1",findWidget("$l1").getAttribute("value"));
		Assert.assertEquals("Last1",findWidget("$l2").getAttribute("value"));
		Assert.assertEquals("First1 Last1",findWidget("$l3").getAttribute("value"));
		Assert.assertEquals("First1",findWidget("$l4").getAttribute("value"));
		Assert.assertEquals(0L,findWidget("$select").getAttribute("selectedIndex"));
		
		
		findWidget("$l1").clear().keys("AAA");
		findWidget("$btn1").focus(0,1,2000);
		Assert.assertEquals("Last1",findWidget("$l2").getAttribute("value"));
		Assert.assertEquals("AAA Last1",findWidget("$l3").getAttribute("value"));
		Assert.assertEquals("AAA",findWidget("$l4").getAttribute("value"));
		
		
		
		((SelectWidget)findWidget("$select")).select(1);
		
		Assert.assertEquals("AAA",findWidget("$l1").getAttribute("value"));
		Assert.assertEquals("Last1",findWidget("$l2").getAttribute("value"));
		Assert.assertEquals("AAA Last1",findWidget("$l3").getAttribute("value"));
		Assert.assertEquals("Last1",findWidget("$l4").getAttribute("value"));
		Assert.assertEquals(1L,findWidget("$select").getAttribute("selectedIndex"));
		
		findWidget("$l1").clear().keys("BBB");
		findWidget("$btn1").focus(0,1,2000);
		Assert.assertEquals("Last1",findWidget("$l2").getAttribute("value"));
		Assert.assertEquals("BBB Last1",findWidget("$l3").getAttribute("value"));
		Assert.assertEquals("Last1",findWidget("$l4").getAttribute("value"));
		
		
		((SelectWidget)findWidget("$select")).select(2);
		
		Assert.assertEquals("BBB",findWidget("$l1").getAttribute("value"));
		Assert.assertEquals("Last1",findWidget("$l2").getAttribute("value"));
		Assert.assertEquals("BBB Last1",findWidget("$l3").getAttribute("value"));
		Assert.assertEquals("BBB Last1",findWidget("$l4").getAttribute("value"));
		Assert.assertEquals(2L,findWidget("$select").getAttribute("selectedIndex"));
		
		((SelectWidget)findWidget("$select")).select(0);
		
		Assert.assertEquals("BBB",findWidget("$l1").getAttribute("value"));
		Assert.assertEquals("Last1",findWidget("$l2").getAttribute("value"));
		Assert.assertEquals("BBB Last1",findWidget("$l3").getAttribute("value"));
		Assert.assertEquals("BBB",findWidget("$l4").getAttribute("value"));
		Assert.assertEquals(0L,findWidget("$select").getAttribute("selectedIndex"));
		
		
	}
	
}
