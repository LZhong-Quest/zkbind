package org.zkoss.bind.unitest2;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.zkoss.zktc.core.junit.TestCaseBase;
import org.zkoss.zktc.core.widget.Widget;
/**
 * test case for bugs from number 1-100
 * @author dennis
 *
 */
public class BugsTestCase0100 extends TestCaseBase{

	@Test
	public void b0020(){
		navigate(getTestCaseUrl("/test2/B0020.zul"));
		//test property init
		Assert.assertEquals(5,findWidgets("@button").size());
		Widget b = findWidget("@button");
		b.click();
		
		Assert.assertEquals(4,findWidgets("@button").size());
		b = findWidget("@button");
		b.click();
		
		Assert.assertEquals(3,findWidgets("@button").size());
		b = findWidget("@button");
		b.click();
		
		Assert.assertEquals(2,findWidgets("@button").size());
		b = findWidget("@button");
		b.click();
		
		Assert.assertEquals(1,findWidgets("@button").size());
		b = findWidget("@button");
		b.click();
		
		Assert.assertEquals(0,findWidgets("@button").size());
		b = findWidget("@button");
		Assert.assertNull(b);
		
	}
}
