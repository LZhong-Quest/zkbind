package org.zkoss.bind.unitest2;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.zkoss.zktc.core.junit.TestCaseBase;
import org.zkoss.zktc.core.widget.Widget;
/**
 * test case for bugs from number 500-999
 * @author dennis
 *
 */
public class BugsTestCase1000 extends TestCaseBase{

	
	@Test
	public void b01017NestedFormPath(){
		navigate(getTestCaseUrl("/bind/issue/B01017NestedFormPath.zul"));
		Widget l11 = findWidget("$l11");
		Widget l12 = findWidget("$l12");
		
		Widget l21 = findWidget("$l21");
		Widget l22 = findWidget("$l22");
		
		Widget l31 = findWidget("$l31");
		Widget l32 = findWidget("$l32");
		
		Widget t1 = findWidget("$t1");
		Widget t2 = findWidget("$t2");
		Widget t3 = findWidget("$t3");
		
		Widget msg = findWidget("$msg");
		
		Widget update = findWidget("$update");
		
		Assert.assertEquals("A", l11.getValue());
		Assert.assertEquals("B", l21.getValue());
		Assert.assertEquals("C", l31.getValue());
		
		Assert.assertEquals("A", t1.getValue());
		Assert.assertEquals("B", t2.getValue());
		Assert.assertEquals("C", t3.getValue());
		
		t1.replace("Aa").tab();
		Assert.assertEquals("A", l11.getValue());
		Assert.assertEquals("value is 'Aa'", l12.getValue());
		
		
		t2.replace("Bb").tab();
		Assert.assertEquals("B", l21.getValue());
		Assert.assertEquals("value is 'Bb'", l22.getValue());
		
		t3.replace("Cc").tab();
		Assert.assertEquals("C", l31.getValue());
		Assert.assertEquals("value is 'Cc'", l32.getValue());
		
		update.click();
		Assert.assertEquals("Aa", l11.getValue());
		Assert.assertEquals("Bb", l21.getValue());
		Assert.assertEquals("Cc", l31.getValue());
		
		Assert.assertEquals("update value1:Aa,value2:Bb,value3:Cc", msg.getValue());
		
		
	}
	
	
}
