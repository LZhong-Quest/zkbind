package org.zkoss.bind.unitest2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
	
	@Test
	public void f00743_1(){
		navigate(getTestCaseUrl("/bind/issue/F00743_1.zul"));
		Widget outerbox = findWidget("$outerbox");
		Widget selected = findWidget("$selected");
		Widget range = findWidget("$range");

		Widget clean1 = findWidget("$clean1");
		Widget clean2 = findWidget("$clean2");
		Widget select = findWidget("$select");
		Widget reload = findWidget("$reload");
		Widget select0 = findWidget("$select0");
		Widget showselect = findWidget("$showselect");
		
		
		outerbox.findWidgets("@listitem").get(0).click();
		outerbox.findWidgets("@listitem").get(2).click();
		Assert.assertEquals("[A, C]", selected.getValue());
		showselect.click();
		Assert.assertEquals("[0, 2]", range.getValue());
		
		clean1.click();
		Assert.assertEquals("", selected.getValue());
		Assert.assertArrayEquals(new long[0], ListboxUtil.getSelectedIndexs(outerbox));
		showselect.click();
		Assert.assertEquals("[]", range.getValue());
		
		
		outerbox.findWidgets("@listitem").get(2).click();
		outerbox.findWidgets("@listitem").get(4).click();
		Assert.assertEquals("[C, E]", selected.getValue());
		showselect.click();
		Assert.assertEquals("[2, 4]", range.getValue());
		
		clean2.click();
		Assert.assertEquals("[]", selected.getValue());
//		Assert.assertArrayEquals(new long[0], ListboxUtil.getSelectedIndexs(outerbox));
		showselect.click();
		Assert.assertEquals("[]", range.getValue());
		
		select.click();
//		Assert.assertArrayEquals(new long[]{1L,3L}, ListboxUtil.getSelectedIndexs(outerbox));
		Assert.assertEquals("[B, D]", selected.getValue());
		showselect.click();
		Assert.assertEquals("[1, 3]", range.getValue());
		
		
		select0.click();
//		Assert.assertArrayEquals(new long[]{0L,1L}, ListboxUtil.getSelectedIndexs(outerbox));
		Assert.assertEquals("[B, D]", selected.getValue());
		showselect.click();
		Assert.assertEquals("[0, 1]", range.getValue());
		
		
		reload.click();
//		Assert.assertArrayEquals(new long[]{1L,3L}, ListboxUtil.getSelectedIndexs(outerbox));
		Assert.assertEquals("[B, D]", selected.getValue());
		showselect.click();
		Assert.assertEquals("[1, 3]", range.getValue());
		
	}
	
	
	@Test
	public void f00743_2(){
		navigate(getTestCaseUrl("/bind/issue/F00743_2.zul"));
		Widget outerbox = findWidget("$outerbox");
		Widget range = findWidget("$range");

		Widget clean = findWidget("$clean");
		Widget select = findWidget("$select");
		Widget reload = findWidget("$reload");
		Widget select0 = findWidget("$select0");
		Widget showselect = findWidget("$showselect");
		
		
		outerbox.findWidgets("@listitem").get(0).click();
		outerbox.findWidgets("@listitem").get(2).click();
		showselect.click();
		Assert.assertEquals("[0, 2]", range.getValue());
		
		clean.click();
		Assert.assertArrayEquals(new long[0], ListboxUtil.getSelectedIndexs(outerbox));
		showselect.click();
		Assert.assertEquals("[]", range.getValue());
		
		
		outerbox.findWidgets("@listitem").get(2).click();
		outerbox.findWidgets("@listitem").get(4).click();
		showselect.click();
		Assert.assertEquals("[2, 4]", range.getValue());
		
		select.click();
//		Assert.assertArrayEquals(new long[]{1L,3L}, ListboxUtil.getSelectedIndexs(outerbox));
		showselect.click();
		Assert.assertEquals("[1, 3]", range.getValue());
		
		
		select0.click();
//		Assert.assertArrayEquals(new long[]{0L,1L}, ListboxUtil.getSelectedIndexs(outerbox));
		showselect.click();
		Assert.assertEquals("[0, 1]", range.getValue());
		
		
		reload.click();
//		Assert.assertArrayEquals(new long[]{0L,1L}, ListboxUtil.getSelectedIndexs(outerbox));
		showselect.click();
		Assert.assertEquals("[0, 1]", range.getValue());
		
	}
	
	
	@Test
	public void f00769(){
		navigate(getTestCaseUrl("/bind/issue/F00769.zul"));
		
		Widget msg = findWidget("$msg");
		Widget selected = findWidget("$selected");
		Widget clean1 = findWidget("$clean1");
		Widget clean2 = findWidget("$clean2");
		Widget select = findWidget("$select");
		Widget reload = findWidget("$reload");
		Widget select0 = findWidget("$select0");
		Widget select1 = findWidget("$select1");
		Widget showselect = findWidget("$showselect");
		
		findWidget("$A-0-1").getFirstChild().click();//treeitem->treerow
		Assert.assertEquals("[A-0-1]", selected.getValue());
		showselect.click();
		Assert.assertEquals("[[0, 1]]", msg.getValue());
		
		findWidget("$A-1-0-1").getFirstChild().click();//treeitem->treerow
		Assert.assertEquals("[A-0-1, A-1-0-1]", selected.getValue());
		showselect.click();
		Assert.assertEquals("[[0, 1], [1, 0, 1]]", msg.getValue());
		
		clean1.click();
		Assert.assertEquals("", selected.getValue());
		showselect.click();
		Assert.assertEquals("no selection", msg.getValue());
		
		select.click();
		Assert.assertEquals("[A-0-1, A-1-1-1]", selected.getValue());
		showselect.click();
		Assert.assertEquals("[[0, 1], [1, 1, 1]]", msg.getValue());
		
		findWidget("$A-1-0-1").getFirstChild().click();//treeitem->treerow
		Assert.assertEquals("[A-0-1, A-1-0-1, A-1-1-1]", selected.getValue());
		showselect.click();
		Assert.assertEquals("[[0, 1], [1, 0, 1], [1, 1, 1]]", msg.getValue());
		
		clean2.click();
		Assert.assertEquals("[]", selected.getValue());
		showselect.click();
		Assert.assertEquals("no selection", msg.getValue());
		
		select.click();
		select0.click();
		showselect.click();
		Assert.assertEquals("[[0, 0], [0, 1], [1, 1, 1]]", msg.getValue());
		
		reload.click();
		Assert.assertEquals("[A-0-1, A-1-1-1]", selected.getValue());
		Assert.assertEquals("reloaded [A-0-1, A-1-1-1]", msg.getValue());
		showselect.click();
		Assert.assertEquals("[[0, 1], [1, 1, 1]]", msg.getValue());
		
		select1.click();
		showselect.click();
		Assert.assertEquals("[[0, 0, 1], [0, 1], [1, 1, 1]]", msg.getValue());
		
		reload.click();
		Assert.assertEquals("[A-0-1, A-1-1-1]", selected.getValue());
		Assert.assertEquals("reloaded [A-0-1, A-1-1-1]", msg.getValue());
		showselect.click();
		Assert.assertEquals("[[0, 1], [1, 1, 1]]", msg.getValue());
	}
	
	
	@Test
	public void f00771_1(){
		navigate(getTestCaseUrl("/bind/issue/F00771.zul"));
		
		Widget val1 = findWidget("$val1");
		Widget val2 = findWidget("$val2");
		Widget val3 = findWidget("$val3");
		
		Widget t11 = findWidget("$t11");
		Widget l11 = findWidget("$l11");
		Widget l12 = findWidget("$l12");
		
		Widget t21 = findWidget("$t21");
		Widget l21 = findWidget("$l21");
		Widget l22 = findWidget("$l22");
		
		Widget t31 = findWidget("$t31");
		Widget l31 = findWidget("$l31");
		Widget l32 = findWidget("$l32");
		
		Widget reload1 = findWidget("$reload1");
		Widget reload2 = findWidget("$reload2");
		
		Assert.assertEquals("", val1.getValue());
		Assert.assertEquals("", val2.getValue());
		Assert.assertEquals("", val3.getValue());
		
		t11.replace("ab").tab();
		Assert.assertEquals("", val1.getValue());
		Assert.assertEquals("", val2.getValue());
		Assert.assertEquals("", val3.getValue());
		Assert.assertEquals("value1 must equalsIgnoreCase to abc", l11.getValue());
		Assert.assertEquals("value1 must equalsIgnoreCase to abc - by key", l12.getValue());
		Assert.assertEquals("", l21.getValue());
		Assert.assertEquals("value1 must equalsIgnoreCase to abc - by key", l22.getValue());
		Assert.assertEquals("", l31.getValue());
		Assert.assertEquals("", l32.getValue());
		
		t21.replace("de").tab();
		Assert.assertEquals("", val1.getValue());
		Assert.assertEquals("", val2.getValue());
		Assert.assertEquals("", val3.getValue());
		Assert.assertEquals("value1 must equalsIgnoreCase to abc", l11.getValue());
		Assert.assertEquals("value1 must equalsIgnoreCase to abc - by key", l12.getValue());
		Assert.assertEquals("value2 must equalsIgnoreCase to def", l21.getValue());
		Assert.assertEquals("value1 must equalsIgnoreCase to abc - by key", l22.getValue());
		Assert.assertEquals("", l31.getValue());
		Assert.assertEquals("", l32.getValue());
		
		t31.replace("xy").tab();
		Assert.assertEquals("", val1.getValue());
		Assert.assertEquals("", val2.getValue());
		Assert.assertEquals("", val3.getValue());
		Assert.assertEquals("value1 must equalsIgnoreCase to abc", l11.getValue());
		Assert.assertEquals("value1 must equalsIgnoreCase to abc - by key", l12.getValue());
		Assert.assertEquals("value2 must equalsIgnoreCase to def", l21.getValue());
		Assert.assertEquals("value1 must equalsIgnoreCase to abc - by key", l22.getValue());
		Assert.assertEquals("value3 must equalsIgnoreCase to xyz", l31.getValue());
		Assert.assertEquals("value3 must equalsIgnoreCase to xyz - by key", l32.getValue());
		
		
		t11.replace("abc").tab();
		Assert.assertEquals("abc", val1.getValue());
		Assert.assertEquals("", val2.getValue());
		Assert.assertEquals("", val3.getValue());
		Assert.assertEquals("", l11.getValue());
		Assert.assertEquals("value2 must equalsIgnoreCase to def - by key", l12.getValue());
		Assert.assertEquals("value2 must equalsIgnoreCase to def", l21.getValue());
		Assert.assertEquals("value2 must equalsIgnoreCase to def - by key", l22.getValue());
		Assert.assertEquals("value3 must equalsIgnoreCase to xyz", l31.getValue());
		Assert.assertEquals("value3 must equalsIgnoreCase to xyz - by key", l32.getValue());
		
		t21.replace("def").tab();
		Assert.assertEquals("abc", val1.getValue());
		Assert.assertEquals("def", val2.getValue());
		Assert.assertEquals("", val3.getValue());
		Assert.assertEquals("", l11.getValue());
		Assert.assertEquals("value3 must equalsIgnoreCase to xyz - by key", l12.getValue());
		Assert.assertEquals("", l21.getValue());
		Assert.assertEquals("value3 must equalsIgnoreCase to xyz - by key", l22.getValue());
		Assert.assertEquals("value3 must equalsIgnoreCase to xyz", l31.getValue());
		Assert.assertEquals("value3 must equalsIgnoreCase to xyz - by key", l32.getValue());
		
		t31.replace("xyz").tab();
		Assert.assertEquals("abc", val1.getValue());
		Assert.assertEquals("def", val2.getValue());
		Assert.assertEquals("xyz", val3.getValue());
		Assert.assertEquals("", l11.getValue());
		Assert.assertEquals("", l12.getValue());
		Assert.assertEquals("", l21.getValue());
		Assert.assertEquals("", l22.getValue());
		Assert.assertEquals("", l31.getValue());
		Assert.assertEquals("", l32.getValue());
		
		
		t11.replace("ab").tab();
		t21.replace("de").tab();
		t31.replace("xy").tab();
		Assert.assertEquals("abc", val1.getValue());
		Assert.assertEquals("def", val2.getValue());
		Assert.assertEquals("xyz", val3.getValue());
		Assert.assertEquals("value1 must equalsIgnoreCase to abc", l11.getValue());
		Assert.assertEquals("value1 must equalsIgnoreCase to abc - by key", l12.getValue());
		Assert.assertEquals("value2 must equalsIgnoreCase to def", l21.getValue());
		Assert.assertEquals("value1 must equalsIgnoreCase to abc - by key", l22.getValue());
		Assert.assertEquals("value3 must equalsIgnoreCase to xyz", l31.getValue());
		Assert.assertEquals("value3 must equalsIgnoreCase to xyz - by key", l32.getValue());
		
		reload1.click();
		Assert.assertEquals("abc", t11.getValue());
		Assert.assertEquals("de", t21.getValue());
		Assert.assertEquals("xy", t31.getValue());
		Assert.assertEquals("", l11.getValue());
		Assert.assertEquals("value2 must equalsIgnoreCase to def - by key", l12.getValue());
		Assert.assertEquals("value2 must equalsIgnoreCase to def", l21.getValue());
		Assert.assertEquals("value2 must equalsIgnoreCase to def - by key", l22.getValue());
		Assert.assertEquals("value3 must equalsIgnoreCase to xyz", l31.getValue());
		Assert.assertEquals("value3 must equalsIgnoreCase to xyz - by key", l32.getValue());
		
		reload2.click();
		Assert.assertEquals("abc", t11.getValue());
		Assert.assertEquals("def", t21.getValue());
		Assert.assertEquals("xy", t31.getValue());
		Assert.assertEquals("", l11.getValue());
		Assert.assertEquals("value3 must equalsIgnoreCase to xyz - by key", l12.getValue());
		Assert.assertEquals("", l21.getValue());
		Assert.assertEquals("value3 must equalsIgnoreCase to xyz - by key", l22.getValue());
		Assert.assertEquals("value3 must equalsIgnoreCase to xyz", l31.getValue());
		Assert.assertEquals("value3 must equalsIgnoreCase to xyz - by key", l32.getValue());
		
	}
	@Test
	public void f00771_2(){
		navigate(getTestCaseUrl("/bind/issue/F00771.zul"));
		Widget val1 = findWidget("$val1");
		Widget val2 = findWidget("$val2");
		Widget val3 = findWidget("$val3");
		
		Widget t11 = findWidget("$t11");
		Widget l11 = findWidget("$l11");
		Widget l12 = findWidget("$l12");
		
		Widget t21 = findWidget("$t21");
		Widget l21 = findWidget("$l21");
		Widget l22 = findWidget("$l22");
		
		Widget t31 = findWidget("$t31");
		Widget l31 = findWidget("$l31");
		Widget l32 = findWidget("$l32");
		
		
		t11.replace("ab").tab();
		t21.replace("de").tab();
		t31.replace("xy").tab();
		Assert.assertEquals("", val1.getValue());
		Assert.assertEquals("", val2.getValue());
		Assert.assertEquals("", val3.getValue());
		Assert.assertEquals("value1 must equalsIgnoreCase to abc", l11.getValue());
		Assert.assertEquals("value1 must equalsIgnoreCase to abc - by key", l12.getValue());
		Assert.assertEquals("value2 must equalsIgnoreCase to def", l21.getValue());
		Assert.assertEquals("value1 must equalsIgnoreCase to abc - by key", l22.getValue());
		Assert.assertEquals("value3 must equalsIgnoreCase to xyz", l31.getValue());
		Assert.assertEquals("value3 must equalsIgnoreCase to xyz - by key", l32.getValue());
		
		
		
		Widget t41 = findWidget("$t41");
		Widget t42 = findWidget("$t42");
		Widget t43 = findWidget("$t43");
		
		Widget l41 = findWidget("$l41");
		Widget l42 = findWidget("$l42");
		Widget l43 = findWidget("$l43");
		
		Widget submit = findWidget("$submit");
		
		t41.replace("ab");
		t42.replace("de");
		t43.replace("xy");
		submit.click();
		Assert.assertEquals("", val1.getValue());
		Assert.assertEquals("", val2.getValue());
		Assert.assertEquals("", val3.getValue());
		Assert.assertEquals("value1 must equalsIgnoreCase to abc - by key", l41.getValue());
		Assert.assertEquals("value2 must equalsIgnoreCase to def - by key", l42.getValue());
		Assert.assertEquals("value3 must equalsIgnoreCase to xyz - by key", l43.getValue());
		
		t41.replace("ABC");
		submit.click();
		Assert.assertEquals("", val1.getValue());
		Assert.assertEquals("", val2.getValue());
		Assert.assertEquals("", val3.getValue());
		Assert.assertEquals("", l41.getValue());
		Assert.assertEquals("value2 must equalsIgnoreCase to def - by key", l42.getValue());
		Assert.assertEquals("value3 must equalsIgnoreCase to xyz - by key", l43.getValue());
		
		
		t42.replace("DEF");
		submit.click();
		Assert.assertEquals("", val1.getValue());
		Assert.assertEquals("", val2.getValue());
		Assert.assertEquals("", val3.getValue());
		Assert.assertEquals("", l41.getValue());
		Assert.assertEquals("", l42.getValue());
		Assert.assertEquals("value3 must equalsIgnoreCase to xyz - by key", l43.getValue());
		
		t43.replace("XYZ");
		submit.click();
		Assert.assertEquals("ABC", val1.getValue());
		Assert.assertEquals("DEF", val2.getValue());
		Assert.assertEquals("XYZ", val3.getValue());
		Assert.assertEquals("", l41.getValue());
		Assert.assertEquals("", l42.getValue());
		Assert.assertEquals("", l43.getValue());
		
		Assert.assertEquals("", l11.getValue());
		Assert.assertEquals("", l12.getValue());
		Assert.assertEquals("", l21.getValue());
		Assert.assertEquals("", l22.getValue());
		Assert.assertEquals("", l31.getValue());
		Assert.assertEquals("", l32.getValue());
	}
	
	@Test
	public void f00772_inter_comm(){
		navigate(getTestCaseUrl("/bind/issue/F00772-inter-comm.zul"));
		Widget t11 = findWidget("$t11");
		Widget l21 = findWidget("$l21");
		Widget l31 = findWidget("$l31");
		Widget l41 = findWidget("$l41");
		
		Widget postx = findWidget("$postx");
		Widget posty = findWidget("$posty");
		Widget postz = findWidget("$postz");
		Widget postmy = findWidget("$postmy");
		
		Widget globalx = findWidget("$globalx");
		Widget globaly = findWidget("$globaly");
		Widget globalz = findWidget("$globalz");
		
		Assert.assertEquals("", t11.getValue());
		Assert.assertEquals("", l21.getValue());
		Assert.assertEquals("", l31.getValue());
		Assert.assertEquals("", l41.getValue());
		
		postx.click();
		Assert.assertEquals("postX-X1", l21.getValue());
		Assert.assertEquals("postX-X2", l31.getValue());
		Assert.assertEquals("", l41.getValue());
		
		posty.click();
		Assert.assertEquals("postX-X1", l21.getValue());
		Assert.assertEquals("postY-X2", l31.getValue());
		Assert.assertEquals("", l41.getValue());
		
		postz.click();
		Assert.assertEquals("postE-X1", l21.getValue());
		Assert.assertEquals("postZ-X3", l31.getValue());
		Assert.assertEquals("", l41.getValue());
		
		postmy.click();
		Assert.assertEquals("postE-X1", l21.getValue());
		Assert.assertEquals("postZ-X3", l31.getValue());
		Assert.assertEquals("postMy-XMy", l41.getValue());
		
		t11.replace("A");
		globalx.click();
		Assert.assertEquals("A-local-X1", l21.getValue());
		Assert.assertEquals("A-local-X2", l31.getValue());
		Assert.assertEquals("postMy-XMy", l41.getValue());
		
		globaly.click();
		Assert.assertEquals("A-local-X1", l21.getValue());
		Assert.assertEquals("A-local-X1-X2", l31.getValue());
		Assert.assertEquals("postMy-XMy", l41.getValue());
		
		globalz.click();
		Assert.assertEquals("postE-X1", l21.getValue());
		Assert.assertEquals("A-local-X1-X2-X3", l31.getValue());
		Assert.assertEquals("postMy-XMy", l41.getValue());
	}
}
