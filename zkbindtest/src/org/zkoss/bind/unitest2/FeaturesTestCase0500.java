package org.zkoss.bind.unitest2;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;
import org.zkoss.zktc.core.junit.TestCaseBase;
import org.zkoss.zktc.core.widget.Widget;
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
		Assert.assertEquals("Y2",findWidget("$l13").getValue());
		Assert.assertEquals("C",findWidget("$l31").getValue());
		Assert.assertEquals("D",findWidget("$t31").getValue());
		
		findWidget("$t11").clear().keys("X").tab();
		findWidget("$t12").clear().keys("Y").tab();
		
		Assert.assertEquals("X",findWidget("$l11").getValue());
		Assert.assertEquals("Y",findWidget("$l12").getValue());
		Assert.assertEquals("C",findWidget("$l31").getValue());
		Assert.assertEquals("D",findWidget("$t31").getValue());
	
		findWidget("$btn1").click();
		Assert.assertEquals("X",findWidget("$l11").getValue());
		Assert.assertEquals("Y",findWidget("$l12").getValue());
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
		Assert.assertEquals("doCommand4 3 false null btn4 true",findWidget("$l11").getValue());
		
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
	
	
	
	@Test
	public void f00687(){
		navigate(getTestCaseUrl("/bind/issue/F00687.zul"));
		
		Widget l11 = findWidget("$l11");
		Widget l12 = findWidget("$l12");
		Widget l13 = findWidget("$l13");
		Widget l14 = findWidget("$l14");
		Widget t11 = findWidget("$t11");
		Widget t12 = findWidget("$t12");
		Widget t13 = findWidget("$t13");
		Widget t14 = findWidget("$t14");
		
		Assert.assertEquals("A",l11.getValue());
		Assert.assertEquals("B",l12.getValue());
		Assert.assertEquals("C",l13.getValue());
		Assert.assertEquals("D",l14.getValue());
		
		t11.replace("Q").tab();
		Assert.assertEquals("Q",l11.getValue());
		Assert.assertEquals("B",l12.getValue());
		Assert.assertEquals("C",l13.getValue());
		Assert.assertEquals("D",l14.getValue());
		
		t12.replace("W").tab();
		Assert.assertEquals("Q",l11.getValue());
		Assert.assertEquals("B",l12.getValue());
		Assert.assertEquals("C",l13.getValue());
		Assert.assertEquals("D",l14.getValue());
		
		t13.replace("E").tab();
		Assert.assertEquals("Q",l11.getValue());
		Assert.assertEquals("W",l12.getValue());
		Assert.assertEquals("E",l13.getValue());
		Assert.assertEquals("D",l14.getValue());
		
		findWidget("$btn1").click();
		Assert.assertEquals("Q",l11.getValue());
		Assert.assertEquals("W",l12.getValue());
		Assert.assertEquals("E",l13.getValue());
		Assert.assertEquals("command 1",l14.getValue());
		Assert.assertEquals("command 1",t14.getValue());
		
	}
	
	
	@Test
	public void f00718(){
		navigate(getTestCaseUrl("/bind/issue/F00718.zul"));
		
		Widget tb1 = findWidget("$tb1");
		Widget tb2 = findWidget("$tb2");
		Widget msg2 = findWidget("$msg2");
		
		Widget tb3 = findWidget("$tb3");
		Widget msg3 = findWidget("$msg3");
		
		Widget db4 = findWidget("$db4");
		Widget msg4 = findWidget("$msg4");
		
		
		Widget reload = findWidget("$reload");
		
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		Calendar now = Calendar.getInstance();
		String today = format.format(now.getTime());
		now.add(Calendar.DATE, 1);
		String tomorrow = format.format(now.getTime());
		now.add(Calendar.DATE, -2);
		String yesterday = format.format(now.getTime());
		
		Assert.assertEquals("Dennis",tb1.getValue());
		Assert.assertEquals("Chen",tb2.getValue());
		Assert.assertEquals("",msg2.getValue());
		Assert.assertEquals("",tb3.getValue());
		Assert.assertEquals("",msg3.getValue());
		Assert.assertEquals(today,db4.getText());
		Assert.assertEquals("",msg4.getValue());
		
		tb1.replace("").tab();
		tb2.replace("").tab();
		tb3.replace("A").tab();
		db4.replace(tomorrow).tab();
		
		
		
		
		Assert.assertEquals("",tb1.getValue());
		Assert.assertEquals("",tb2.getValue());
		Assert.assertEquals("Last name can not be null",msg2.getValue());
		Assert.assertEquals("A",tb3.getValue());
		Assert.assertEquals("not a well-formed email address",msg3.getValue());
		Assert.assertEquals(tomorrow,db4.getText());
		Assert.assertEquals("Birth date must be in the past",msg4.getValue());
		
		reload.click();
		Assert.assertEquals("Dennis",tb1.getValue());
		Assert.assertEquals("Chen",tb2.getValue());
		Assert.assertEquals("",msg2.getValue());
		Assert.assertEquals("",tb3.getValue());
		Assert.assertEquals("",msg3.getValue());
		Assert.assertEquals(today,db4.getText());
		Assert.assertEquals("",msg4.getValue());
		
		tb3.replace("A@B.C").tab();
		Assert.assertEquals("A@B.C",tb3.getValue());
		Assert.assertEquals("email lenght must large than 8",msg3.getValue());
		
		tb3.replace("AA@BB.CC.DD").tab();
		Assert.assertEquals("AA@BB.CC.DD",tb3.getValue());
		Assert.assertEquals("",msg3.getValue());
		
		db4.replace(tomorrow).tab();
		Assert.assertEquals(tomorrow,db4.getText());
		Assert.assertEquals("Birth date must be in the past",msg4.getValue());
		
		db4.replace(yesterday).tab();
		Assert.assertEquals(yesterday,db4.getText());
		Assert.assertEquals("",msg4.getValue());
		
	}
}
