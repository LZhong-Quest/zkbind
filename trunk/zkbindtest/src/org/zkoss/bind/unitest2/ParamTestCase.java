package org.zkoss.bind.unitest2;

import org.junit.Assert;
import org.junit.Test;
import org.zkoss.zktc.core.junit.TestCaseBase;
/**
 * 
 * @author dennis
 *
 */
public class ParamTestCase extends TestCaseBase{

	@Test
	public void testHttpParam(){
		navigate(getTestCaseUrl("/bind/basic/httpparam.zul?param1=foo"));//param is not available in ztltest
		
		
		findWidget("$cmd1").click();
		
		Assert.assertNotNull(findWidget("$l11").getValue());
		Assert.assertNotNull(findWidget("$l12").getValue());
		Assert.assertNotNull(findWidget("$l13").getValue());
		
		Assert.assertFalse("".equals(findWidget("$l11").getValue().toString().trim()));
		Assert.assertFalse("".equals(findWidget("$l12").getValue().toString().trim()));
		Assert.assertFalse("".equals(findWidget("$l13").getValue().toString().trim()));
		
	}
	
	@Test
	public void testExecutionParam(){
		navigate(getTestCaseUrl("/bind/basic/executionparam.zul"));
		findWidget("$btn1").click();
		
		Assert.assertEquals("foo",findWidget("$w1 $l11").getValue());
		Assert.assertEquals("bar",findWidget("$w1 $l12").getValue());
		
		findWidget("$w1 $cmd1").click();
		Assert.assertEquals("",findWidget("$w1 $l11").getValue());
		Assert.assertEquals("",findWidget("$w1 $l12").getValue());
		
		findWidget("$btn2").click();
		
		Assert.assertEquals("abc",findWidget("$w2 $l11").getValue());
		Assert.assertEquals("goo",findWidget("$w2 $l12").getValue());
		
		findWidget("$w2 $cmd1").click();
		Assert.assertEquals("",findWidget("$w2 $l11").getValue());
		Assert.assertEquals("",findWidget("$w2 $l12").getValue());
	}
}
