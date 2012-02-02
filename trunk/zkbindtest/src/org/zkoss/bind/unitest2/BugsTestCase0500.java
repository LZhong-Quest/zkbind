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
public class BugsTestCase0500 extends TestCaseBase{

	
	@Test
	public void b00603(){
		navigate(getTestCaseUrl("/bind/issue/B00603.zul"));
		Widget outerbox = findWidget("$outsidebox");
		List<Widget> outeritems = outerbox.getChildren();//include header
		outeritems.remove(0);//don't care header
		
		Assert.assertEquals(3, outeritems.size());
		String[] itemLabel = new String[]{"A","B","C"};
		String[] optionLabel = new String[]{"A","B"};
		for(int i=0;i<3;i++){
			Widget outeritem = outeritems.get(i);
			String outerl = itemLabel[i];
			Widget cell = outeritem.getFirstChild();
			Assert.assertEquals(outerl, cell.getLabel());
			Widget innerbox = outeritem.findWidget("@listbox");
			Assert.assertNotNull(innerbox);
			List<Widget> inneritems = innerbox.findWidgets("@listitem");
			Assert.assertEquals(2, inneritems.size());
			for(int j=0;j<2;j++){
				Widget inneritem = inneritems.get(j);
				String innerl = optionLabel[j];
				cell = inneritem.getFirstChild();
				Assert.assertEquals(outerl+" "+innerl, cell.getLabel());
				cell = cell.getNextSibling();
				Assert.assertEquals(outerl+" "+innerl+innerl, cell.getLabel());
			}
		}
	}
	
	@Test
	public void b00604(){
		navigate(getTestCaseUrl("/bind/issue/B00604.zul"));
		Widget inc1 = findWidget("$inc1");
		Widget inc2 = findWidget("$inc2");
		Widget listbox1 = inc1.findWidget("@listbox");
		Widget listbox2 = inc2.findWidget("@listbox");
		
		String[] itemLabel1 = new String[]{"A","B","C"};
		String[] itemLabel2 = new String[]{"X","Y","Z"};
		
		Assert.assertNotNull(listbox1);
		Assert.assertNull(listbox2);
		Widget listbox = listbox1;
		for(int i=0;i<2;i++){
			
			List<Widget> items = listbox.findWidgets("@listitem");
			Assert.assertEquals(3, items.size());
			
			for(int j=0;j<items.size();j++){
				Widget item = items.get(j);
				Widget cell1 = item.getFirstChild();
				Widget cell2 = cell1.getNextSibling();
				Assert.assertEquals(itemLabel1[j], cell1.getLabel());
				Assert.assertEquals(itemLabel2[j], cell2.getLabel());
			}
			
			findWidget("$btn1").click();
			listbox = findWidget("$inc2").findWidget("@listbox");
		}
	}
	
	@Test
	public void b00605(){
		navigate(getTestCaseUrl("/bind/issue/B00605.zul"));
		
		Assert.assertEquals("A", findWidget("$tb1").getValue());
		Assert.assertEquals("A", findWidget("$lb1").getValue());
		Assert.assertEquals("A", findWidget("$tb2").getValue());
		Assert.assertEquals("A", findWidget("$lb2").getValue());
		Assert.assertNull(findWidget("$tb3",0));//no need to wait it is not exist in init
		Assert.assertNull(findWidget("$lb3",0));//no need to wait it is not exist in init
		
		findWidget("$tb1").clear().keys("JJ").tab();
		Assert.assertEquals("JJ", findWidget("$tb1").getValue());
		Assert.assertEquals("JJ", findWidget("$lb1").getValue());
		Assert.assertEquals("A", findWidget("$tb2").getValue());
		Assert.assertEquals("A", findWidget("$lb2").getValue());

		findWidget("$tb2").clear().keys("KK").tab();
		Assert.assertEquals("JJ", findWidget("$tb1").getValue());
		Assert.assertEquals("JJ", findWidget("$lb1").getValue());
		Assert.assertEquals("KK", findWidget("$tb2").getValue());
		Assert.assertEquals("KK", findWidget("$lb2").getValue());
		
		findWidget("$btn1").click();
		
		Assert.assertEquals("A", findWidget("$tb3").getValue());
		Assert.assertEquals("A", findWidget("$lb3").getValue());
		
		findWidget("$tb3").clear().keys("LL").tab();
		Assert.assertEquals("JJ", findWidget("$tb1").getValue());
		Assert.assertEquals("JJ", findWidget("$lb1").getValue());
		Assert.assertEquals("KK", findWidget("$tb2").getValue());
		Assert.assertEquals("KK", findWidget("$lb2").getValue());
		Assert.assertEquals("LL", findWidget("$tb3").getValue());
		Assert.assertEquals("LL", findWidget("$lb3").getValue());
		
		//test again since inc2 is here
		findWidget("$tb1").clear().keys("X").tab();
		Assert.assertEquals("X", findWidget("$tb1").getValue());
		Assert.assertEquals("X", findWidget("$lb1").getValue());
		Assert.assertEquals("KK", findWidget("$tb2").getValue());
		Assert.assertEquals("KK", findWidget("$lb2").getValue());
		Assert.assertEquals("LL", findWidget("$tb3").getValue());
		Assert.assertEquals("LL", findWidget("$lb3").getValue());

		findWidget("$tb2").clear().keys("Y").tab();
		Assert.assertEquals("X", findWidget("$tb1").getValue());
		Assert.assertEquals("X", findWidget("$lb1").getValue());
		Assert.assertEquals("Y", findWidget("$tb2").getValue());
		Assert.assertEquals("Y", findWidget("$lb2").getValue());
		Assert.assertEquals("LL", findWidget("$tb3").getValue());
		Assert.assertEquals("LL", findWidget("$lb3").getValue());
		
		findWidget("$tb3").clear().keys("Z").tab();
		Assert.assertEquals("X", findWidget("$tb1").getValue());
		Assert.assertEquals("X", findWidget("$lb1").getValue());
		Assert.assertEquals("Y", findWidget("$tb2").getValue());
		Assert.assertEquals("Y", findWidget("$lb2").getValue());
		Assert.assertEquals("Z", findWidget("$tb3").getValue());
		Assert.assertEquals("Z", findWidget("$lb3").getValue());
	}
	
	@Test
	public void b00619(){
		navigate(getTestCaseUrl("/bind/issue/B00619.zul"));
		Assert.assertEquals(1L,findWidget("$listbox").getAttribute("selectedIndex"));
		Assert.assertEquals(1L,findWidget("$tabbox").getAttribute("selectedIndex"));
		assertFalseOrNull((Boolean)findWidget("$taba").getAttribute("selected"));
		Assert.assertTrue((Boolean)findWidget("$tabb").getAttribute("selected"));
		assertFalseOrNull((Boolean)findWidget("$tabc").getAttribute("selected"));
		
		
		findWidget("$itema").click();
		Assert.assertEquals(0L,findWidget("$listbox").getAttribute("selectedIndex"));
		Assert.assertEquals(0L,findWidget("$tabbox").getAttribute("selectedIndex"));
		Assert.assertTrue((Boolean)findWidget("$taba").getAttribute("selected"));
		assertFalseOrNull((Boolean)findWidget("$tabb").getAttribute("selected"));
		assertFalseOrNull((Boolean)findWidget("$tabc").getAttribute("selected"));
		
		
		findWidget("$tabc").click();
		Assert.assertEquals(2L,findWidget("$listbox").getAttribute("selectedIndex"));
		Assert.assertEquals(2L,findWidget("$tabbox").getAttribute("selectedIndex"));
		assertFalseOrNull((Boolean)findWidget("$taba").getAttribute("selected"));
		assertFalseOrNull((Boolean)findWidget("$tabb").getAttribute("selected"));
		Assert.assertTrue((Boolean)findWidget("$tabc").getAttribute("selected"));

	}
	
	@Test
	public void b00632(){
		navigate(getTestCaseUrl("/bind/issue/B00632.zul"));
		
		Assert.assertEquals("XYZ", findWidget("$lb1").getValue());
		Assert.assertEquals("XYZ", findWidget("$lb2").getValue());
		Assert.assertEquals("XYZ", findWidget("$lb3").getValue());
		Assert.assertEquals("XYZ", findWidget("$lb4").getValue());
		
		Assert.assertEquals("A", findWidget("$l11").getValue());
		Assert.assertEquals("B", findWidget("$l12").getValue());
		
		findWidget("$t11").clear().keys("YY").tab();
		Assert.assertEquals("YY", findWidget("$l11").getValue());
		Assert.assertEquals("by-YY", findWidget("$l12").getValue());
	}
	
	@Test
	public void b00634(){
		navigate(getTestCaseUrl("/bind/issue/B00634.zul"));
		 
		Assert.assertEquals("A", findWidget("$l11").getValue());
		Assert.assertEquals("B", findWidget("$l12").getValue());
		
		findWidget("$t11").clear().keys("PP").tab();
		Assert.assertEquals("A", findWidget("$l11").getValue());
		Assert.assertEquals("B", findWidget("$l12").getValue());
		Assert.assertEquals("value 1 has to be XX or ZZ", findWidget("$msg1").getValue());
		Assert.assertEquals("value 2 has to be YY or ZZ", findWidget("$msg2").getValue());
		
		
		findWidget("$t11").clear().keys("XX").tab();
		Assert.assertEquals("A", findWidget("$l11").getValue());
		Assert.assertEquals("B", findWidget("$l12").getValue());
		Assert.assertEquals("", findWidget("$msg1").getValue());
		Assert.assertEquals("value 2 has to be YY or ZZ", findWidget("$msg2").getValue());
		
		findWidget("$t11").clear().keys("YY").tab();
		Assert.assertEquals("A", findWidget("$l11").getValue());
		Assert.assertEquals("B", findWidget("$l12").getValue());
		Assert.assertEquals("value 1 has to be XX or ZZ", findWidget("$msg1").getValue());
		Assert.assertEquals("", findWidget("$msg2").getValue());
		
		findWidget("$t11").clear().keys("ZZ").tab();
		Assert.assertEquals("ZZ", findWidget("$l11").getValue());
		Assert.assertEquals("ZZ", findWidget("$l12").getValue());
		Assert.assertEquals("", findWidget("$msg1").getValue());
		Assert.assertEquals("", findWidget("$msg2").getValue());
	}
	
	@Test
	public void b00657(){
		navigate(getTestCaseUrl("/bind/issue/B00657.zul"));
		 
		Assert.assertEquals(0L, findWidget("$listbox").getAttribute("selectedIndex"));
		Assert.assertEquals(0L, findWidget("$intbox").getValue());
		
		findWidget("$intbox").replace("1").tab();
		Assert.assertEquals(1L, findWidget("$listbox").getAttribute("selectedIndex"));
		Assert.assertEquals(1L, findWidget("$intbox").getValue());
		
		findWidget("$intbox").replace("2").tab();
		Assert.assertEquals(2L, findWidget("$listbox").getAttribute("selectedIndex"));
		Assert.assertEquals(2L, findWidget("$intbox").getValue());
	}
	
	@Test
	public void b00678(){
		navigate(getTestCaseUrl("/bind/issue/B00678.zul"));
		 
		Assert.assertEquals("Value A", findWidget("$l1").getValue());
		Assert.assertEquals("msg A", findWidget("$l2").getValue());
		
		findWidget("$btn1").click();
		Assert.assertEquals("Value B", findWidget("$l1").getValue());
		Assert.assertEquals("msg B", findWidget("$l2").getValue());
		
		findWidget("$btn2").click();
		Assert.assertEquals("Value C", findWidget("$l1").getValue());
		Assert.assertEquals("msg C", findWidget("$l2").getValue());
	}
	
	
	@Test
	public void b00682(){
		navigate(getTestCaseUrl("/bind/issue/B00682.zul"));
		
		Widget l1 = findWidget("$l1");
		Widget l2 = findWidget("$l2");
		Widget l3 = findWidget("$l3");
		Widget l4 = findWidget("$l4");
		Widget l5 = findWidget("$l5");
		Widget l6 = findWidget("$l6");
		
		Assert.assertNotNull(l1);
		Assert.assertNotNull(l2);
		Assert.assertNotNull(l3);
		Assert.assertNotNull(l4);
		Assert.assertNotNull(l5);
		Assert.assertNotNull(l6);
		
		Assert.assertEquals("", l1.getValue());
		Assert.assertEquals("", l2.getValue());
		Assert.assertEquals("-1.0", l3.getValue());
		Assert.assertEquals("-2.0", l4.getValue());
		Assert.assertEquals("-3", l5.getValue());
		Assert.assertEquals("-4", l6.getValue());
		
		Widget i11 = findWidget("$inp11");
		Widget i12 = findWidget("$inp12");
		Widget i13 = findWidget("$inp13");
		Widget i14 = findWidget("$inp14");
		Widget i15 = findWidget("$inp15");
		Widget i16 = findWidget("$inp16");
		Widget i17 = findWidget("$inp17");
		Widget i18 = findWidget("$inp18");
		Widget i19 = findWidget("$inp19");
		Widget i1a = findWidget("$inp1a");
		Widget i1b = findWidget("$inp1b");
		
		Widget i21 = findWidget("$inp21");
		Widget i22 = findWidget("$inp22");
		Widget i23 = findWidget("$inp23");
		Widget i24 = findWidget("$inp24");
		Widget i25 = findWidget("$inp25");
		Widget i26 = findWidget("$inp26");
		Widget i27 = findWidget("$inp27");
		Widget i28 = findWidget("$inp28");
		Widget i29 = findWidget("$inp29");
		Widget i2a = findWidget("$inp2a");
		Widget i2b = findWidget("$inp2b");
		
		Assert.assertNotNull(i11);
		Assert.assertNotNull(i12);
		Assert.assertNotNull(i13);
		Assert.assertNotNull(i14);
		Assert.assertNotNull(i15);
		Assert.assertNotNull(i16);
		Assert.assertNotNull(i17);
		Assert.assertNotNull(i18);
		Assert.assertNotNull(i19);
		Assert.assertNotNull(i1a);
		Assert.assertNotNull(i1b);
		
		
		Assert.assertNotNull(i21);
		Assert.assertNotNull(i22);
		Assert.assertNotNull(i23);
		Assert.assertNotNull(i24);
		Assert.assertNotNull(i25);
		Assert.assertNotNull(i26);
		Assert.assertNotNull(i27);
		Assert.assertNotNull(i28);
		Assert.assertNotNull(i29);
		Assert.assertNotNull(i2a);
		Assert.assertNotNull(i2b);
		
		
		Assert.assertEquals("", i11.getValue());
		Assert.assertEquals(null, i12.getValue());
		Assert.assertEquals(null, i13.getValue());
		Assert.assertEquals(null, i14.getValue());
		Assert.assertEquals(null, i15.getValue());
//		Assert.assertEquals(-1L, i16.getValue()); //widget bug
		Assert.assertEquals(-2L, i17.getValue());
		Assert.assertEquals(-2L, i18.getValue());
		Assert.assertEquals(-3L, i19.getValue());
		Assert.assertEquals(-3L, i1a.getValue());
//		Assert.assertEquals(-4L, i1b.getValue()); //widget bug
		
		
		i11.focus(0,1000);
		i11.tab(0,1000);
		i12.tab(0,1000);
		i13.tab(0,1000);
		i14.tab(0,1000);
		i15.tab(0,1000);
		i16.tab(0,1000);
		i17.tab(0,1000);
		i18.tab(0,1000);
		i19.tab(0,1000);
		i1a.tab(0,1000);
		i1b.tab(0,1000);
		
		i21.tab(0,1000);
		i22.tab(0,1000);
		i23.tab(0,1000);
		i24.tab(0,1000);
		i25.tab(0,1000);
		i26.tab(0,1000);
		i27.tab(0,1000);
		i28.tab(0,1000);
		i29.tab(0,1000);
		i2a.tab(0,1000);
		i2b.tab(0,1000);
		
		
		Assert.assertEquals("", l1.getValue());
		Assert.assertEquals("", l2.getValue());
		Assert.assertEquals("-1.0", l3.getValue());
		Assert.assertEquals("-2.0", l4.getValue());
		Assert.assertEquals("-3", l5.getValue());
		Assert.assertEquals("-4", l6.getValue());
		
	}
	
	
	@Test
	public void b00722(){
		navigate(getTestCaseUrl("/bind/issue/B00722.zul"));
		
		Widget l11 = findWidget("$l11");
		Widget t21 = findWidget("$t21");
		Widget m21 = findWidget("$m21");
		Widget cmd1 = findWidget("$cmd1");
		Widget cmd2 = findWidget("$cmd2");
		
		
		Assert.assertEquals("abc", l11.getValue());
		Assert.assertEquals("abc", t21.getValue());
		Assert.assertEquals("", m21.getValue());
		
		t21.replace("efg").tab();
		Assert.assertEquals("abc", l11.getValue());
		Assert.assertEquals("efg", t21.getValue());
		Assert.assertEquals("the value has to be 'abc' or 'ABC'", m21.getValue());
		
		cmd1.click();
		Assert.assertEquals("abc", l11.getValue());
		Assert.assertEquals("efg", t21.getValue());
		Assert.assertEquals("the value has to be 'abc' or 'ABC'", m21.getValue());
		
		t21.replace("ABC").tab();
		Assert.assertEquals("abc", l11.getValue());
		Assert.assertEquals("ABC", t21.getValue());
		Assert.assertEquals("", m21.getValue());
		
		cmd1.click();
		Assert.assertEquals("ABC:saved", l11.getValue());
		Assert.assertEquals("ABC", t21.getValue());
		Assert.assertEquals("", m21.getValue());
		
		t21.replace("kkk").tab();
		Assert.assertEquals("ABC:saved", l11.getValue());
		Assert.assertEquals("kkk", t21.getValue());
		Assert.assertEquals("the value has to be 'abc' or 'ABC'", m21.getValue());
		
		cmd2.click();
		Assert.assertEquals("ABC:saved", l11.getValue());
		Assert.assertEquals("ABC:saved", t21.getValue());
		Assert.assertEquals("", m21.getValue());
	}
	


	@Test
	public void b00762Listbox1(){
		navigate(getTestCaseUrl("/bind/issue/B00762Listbox1.zul"));
		Widget outerbox = findWidget("$outerbox");
		Widget selected = findWidget("$selected");
		Widget min = findWidget("$min");
//		Widget max = findWidget("$max");

		Widget clean = findWidget("$clean");
		Widget select = findWidget("$select");
		Widget showselect = findWidget("$showselect");
		
		
		outerbox.findWidgets("@listitem").get(0).click();
		Assert.assertEquals("A", selected.getValue());
		showselect.click();
		Assert.assertEquals("0", min.getValue());
//		Assert.assertEquals("0", max.getValue());
		
		outerbox.findWidgets("@listitem").get(2).click();
		Assert.assertEquals("C", selected.getValue());
		showselect.click();
		Assert.assertEquals("2", min.getValue());
//		Assert.assertEquals("2", max.getValue());
		
		clean.click();
		Assert.assertEquals(-1L, ListboxUtil.getSelectedIndex(outerbox));
		Assert.assertEquals("", selected.getValue());
		showselect.click();
		Assert.assertEquals("-1", min.getValue());
//		Assert.assertEquals("-1", max.getValue());
		
		select.click();
		Assert.assertEquals(1L, ListboxUtil.getSelectedIndex(outerbox));
		Assert.assertEquals("B", selected.getValue());
		showselect.click();
		Assert.assertEquals("1", min.getValue());
//		Assert.assertEquals("1", max.getValue());
		
		
	}
	
	
	@Test
	public void b00762Listbox2(){
		navigate(getTestCaseUrl("/bind/issue/B00762Listbox2.zul"));
		Widget outerbox = findWidget("$outerbox");
		Widget min = findWidget("$min");
//		Widget max = findWidget("$max");

		Widget clean = findWidget("$clean");
		Widget select = findWidget("$select");
		Widget showselect = findWidget("$showselect");
		
		
		outerbox.findWidgets("@listitem").get(0).click();
		showselect.click();
		Assert.assertEquals("0", min.getValue());
//		Assert.assertEquals("0", max.getValue());
		
		outerbox.findWidgets("@listitem").get(2).click();
		showselect.click();
		Assert.assertEquals("2", min.getValue());
//		Assert.assertEquals("2", max.getValue());
		
		clean.click();
		Assert.assertEquals(-1L, ListboxUtil.getSelectedIndex(outerbox));
		showselect.click();
		Assert.assertEquals("-1", min.getValue());
//		Assert.assertEquals("-1", max.getValue());
		
		select.click();
		Assert.assertEquals(1L, ListboxUtil.getSelectedIndex(outerbox));
		showselect.click();
		Assert.assertEquals("1", min.getValue());
//		Assert.assertEquals("1", max.getValue());
		
	}
	
	
	@Test
	public void b00762Combobox1(){
		navigate(getTestCaseUrl("/bind/issue/B00762Combobox1.zul"));
		Widget outerbox = findWidget("$outerbox");
		Widget selected = findWidget("$selected");
		Widget min = findWidget("$min");
//		Widget max = findWidget("$max");

		Widget clean = findWidget("$clean");
		Widget select = findWidget("$select");
		Widget reload = findWidget("$reload");
		Widget showselect = findWidget("$showselect");
		
		outerbox.call("open");
		waitForTrip(1, 500);
		outerbox.findWidgets("@comboitem").get(0).click();
		Assert.assertEquals("A", selected.getValue());
		showselect.click();
		Assert.assertEquals("0", min.getValue());
//		Assert.assertEquals("0", max.getValue());
		
		outerbox.call("open");
		waitForTrip(1, 500);
		outerbox.findWidgets("@comboitem").get(2).click();
		Assert.assertEquals("C", selected.getValue());
		showselect.click();
		Assert.assertEquals("2", min.getValue());
//		Assert.assertEquals("2", max.getValue());
		
		clean.click();
		Assert.assertEquals("", outerbox.getValue());
		Assert.assertEquals("", selected.getValue());
		showselect.click();
		Assert.assertEquals("-1", min.getValue());
//		Assert.assertEquals("-1", max.getValue());
		
		select.click();
		Assert.assertEquals("B", outerbox.getValue());
		Assert.assertEquals("B", selected.getValue());
		showselect.click();
		Assert.assertEquals("1", min.getValue());
//		Assert.assertEquals("1", max.getValue());
		
	}
	
	@Test
	public void b00762Combobox2(){
		navigate(getTestCaseUrl("/bind/issue/B00762Combobox2.zul"));
		Widget outerbox = findWidget("$outerbox");
		Widget min = findWidget("$min");
//		Widget max = findWidget("$max");

		Widget clean = findWidget("$clean");
		Widget select = findWidget("$select");
		Widget reload = findWidget("$reload");
		Widget showselect = findWidget("$showselect");
		
		outerbox.call("open");
		waitForTrip(1, 500);
		outerbox.findWidgets("@comboitem").get(0).click();
		showselect.click();
		Assert.assertEquals("0", min.getValue());
//		Assert.assertEquals("0", max.getValue());
		
		outerbox.call("open");
		waitForTrip(1, 500);
		outerbox.findWidgets("@comboitem").get(2).click();
		showselect.click();
		Assert.assertEquals("2", min.getValue());
//		Assert.assertEquals("2", max.getValue());
		
		clean.click();
		Assert.assertEquals("", outerbox.getValue());
		showselect.click();
		Assert.assertEquals("-1", min.getValue());
//		Assert.assertEquals("-1", max.getValue());
		
		select.click();
		Assert.assertEquals("B", outerbox.getValue());
		showselect.click();
		Assert.assertEquals("1", min.getValue());
//		Assert.assertEquals("1", max.getValue());
		
	}
	
	@Test
	public void b00775ListmodelSelection(){
		navigate(getTestCaseUrl("/bind/issue/B00775ListmodelSelection.zul"));
		Widget listbox = findWidget("$listbox");
		Widget header = findWidget("$header");
		Widget shrink = findWidget("$shrink");
		
		header.click();
		header.click();//twice
		
		listbox.findWidgets("@listitem").get(8).click();
		Assert.assertEquals(8L,ListboxUtil.getSelectedIndex(listbox));
		
		shrink.click();
		Assert.assertEquals(0L,ListboxUtil.getSelectedIndex(listbox));
	}
}
