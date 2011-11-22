package org.zkoss.bind.unitest2;

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
		navigate(getTestCaseUrl("/zbind/issue/B00603.zul"));
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
		navigate(getTestCaseUrl("/zbind/issue/B00604.zul"));
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
		navigate(getTestCaseUrl("/zbind/issue/B00605.zul"));
		
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
		navigate(getTestCaseUrl("/zbind/issue/B00619.zul"));
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
	
	
}
