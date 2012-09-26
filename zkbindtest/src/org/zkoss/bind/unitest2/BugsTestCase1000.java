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
public class BugsTestCase1000 extends TestCaseBase{

	@Test
	public void b01005BeanValidatorPath(){
		navigate(getTestCaseUrl("/bind/issue/B01005BeanValidatorPath.zul"));
		Widget t1 = findWidget("$t1");
		Widget t2 = findWidget("$t2");
		Widget l1 = findWidget("$l1");
		
		Widget msg1 = findWidget("$msg1");
		Widget msg2 = findWidget("$msg2");
		
		Widget update = findWidget("$update");
		Widget msg = findWidget("$msg");
		
		Assert.assertEquals("A", t1.getValue());
		t1.replace("Aa").tab();
		Assert.assertEquals("min length is 3", msg1.getValue());
		Assert.assertEquals("A", l1.getValue());

		t1.replace("Aab").tab();
		Assert.assertEquals("", msg1.getValue());
		Assert.assertEquals("Aab", l1.getValue());
		
		
		Assert.assertEquals("A", t2.getValue());
		t2.replace("Ab").tab();
		Assert.assertEquals("min length is 3", msg2.getValue());
		Assert.assertEquals("Aab", l1.getValue());

		t2.replace("Abc").tab();
		Assert.assertEquals("", msg2.getValue());
		Assert.assertEquals("Aab", l1.getValue());
		
		update.click();
		Assert.assertEquals("Abc", t1.getValue());
		Assert.assertEquals("Abc", l1.getValue());
		
		Assert.assertEquals("update value1:Abc", msg.getValue());
		
	}
	
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
	
	
	@Test
	public void b01066IncorrectFormValue() {
		navigate(getTestCaseUrl("/bind/issue/B01066IncorrectFormValue.zul"));
		
		Widget tb1 = findWidget("$tb1");
		Widget save = findWidget("$save");
		
		
		Widget lb1 = findWidget("$lb1");
		Widget lb2 = findWidget("$lb2");
		
		Assert.assertEquals("A", lb1.getValue());
		Assert.assertEquals("A", lb2.getValue());
		
		tb1.replace("Abc").tab();
		
		Assert.assertEquals("A", lb1.getValue());
		Assert.assertEquals("A", lb2.getValue());
		
		save.click();
		
		Assert.assertEquals("Abc", lb1.getValue());
		Assert.assertEquals("Abc", lb2.getValue());
	}	
	
	
	@Test
	public void b01062NullIntValue(){
		navigate(getTestCaseUrl("/bind/issue/B01062NullIntValue.zul"));
		Widget lb11 = findWidget("$lb11");
		Widget lb12 = findWidget("$lb12");
		Widget lb21 = findWidget("$lb21");
		Widget lb22 = findWidget("$lb22");
		
		Widget msg1 = findWidget("$msg1");
		Widget msg2 = findWidget("$msg2");
		
		Widget save = findWidget("$save");
		
		Assert.assertEquals("", lb11.getValue());
		Assert.assertEquals("0", lb12.getValue());
		Assert.assertEquals("", lb21.getValue());
		Assert.assertEquals("0", lb22.getValue());
		
		save.click();
		
		Assert.assertEquals("", lb11.getValue());
		Assert.assertEquals("0", lb12.getValue());
		Assert.assertEquals("", lb21.getValue());
		Assert.assertEquals("0", lb22.getValue());
		
		Assert.assertEquals("value1 is null, value2 is 0", msg1.getValue());
		Assert.assertEquals("value1 is null, value2 is 0", msg2.getValue());
		
	}
	
	@Test
	public void b01085NPEReferenceBinding(){
		navigate(getTestCaseUrl("/bind/issue/B01085NPEReferenceBinding.zul"));
		
		Widget listbox1 = findWidget("$listbox1");
		Widget listbox2 = findWidget("$listbox2");
		Widget listbox3 = findWidget("$listbox3");
		Widget listbox4 = findWidget("$listbox4");
		Widget listbox5 = findWidget("$listbox5");
		Widget listbox6 = findWidget("$listbox6");
		
		Widget lb1 = findWidget("$lb1");
		Widget lb2 = findWidget("$lb2");
		
		Assert.assertEquals(-1L,ListboxUtil.getSelectedIndex(listbox1));
		Assert.assertEquals(-1L,ListboxUtil.getSelectedIndex(listbox2));
		Assert.assertEquals(-1L,ListboxUtil.getSelectedIndex(listbox3));
		Assert.assertEquals("", lb1.getValue());
		
		Assert.assertEquals(1L,ListboxUtil.getSelectedIndex(listbox4));
		Assert.assertEquals(1L,ListboxUtil.getSelectedIndex(listbox5));
		Assert.assertEquals(1L,ListboxUtil.getSelectedIndex(listbox6));
		Assert.assertEquals("1", lb2.getValue());
		
		
		listbox1.findWidgets("@listitem").get(0).click();
		Assert.assertEquals(0L,ListboxUtil.getSelectedIndex(listbox2));
		Assert.assertEquals(0L,ListboxUtil.getSelectedIndex(listbox3));
		Assert.assertEquals("0", lb1.getValue());
		
		listbox2.findWidgets("@listitem").get(1).click();
		Assert.assertEquals(1L,ListboxUtil.getSelectedIndex(listbox1));
		Assert.assertEquals(1L,ListboxUtil.getSelectedIndex(listbox3));
		Assert.assertEquals("1", lb1.getValue());
		
		
		listbox3.findWidgets("@listitem").get(2).click();
		Assert.assertEquals(2L,ListboxUtil.getSelectedIndex(listbox1));
		Assert.assertEquals(2L,ListboxUtil.getSelectedIndex(listbox2));
		Assert.assertEquals("2", lb1.getValue());
		
		
		listbox4.findWidgets("@listitem").get(0).click();
		Assert.assertEquals(1L,ListboxUtil.getSelectedIndex(listbox5));
		Assert.assertEquals(1L,ListboxUtil.getSelectedIndex(listbox6));
		Assert.assertEquals("1", lb2.getValue());
		Assert.assertEquals(1,findWidgets(".z-window-modal").size());
		findWidget(".z-window-modal @button").click();
		
		listbox5.findWidgets("@listitem").get(0).click();
		Assert.assertEquals(0L,ListboxUtil.getSelectedIndex(listbox4));
		Assert.assertEquals(1L,ListboxUtil.getSelectedIndex(listbox6));
		Assert.assertEquals("1", lb2.getValue());
		Assert.assertEquals(1,findWidgets(".z-window-modal").size());
		findWidget(".z-window-modal @button").click();
		
		listbox6.findWidgets("@listitem").get(0).click();
		Assert.assertEquals(0L,ListboxUtil.getSelectedIndex(listbox4));
		Assert.assertEquals(0L,ListboxUtil.getSelectedIndex(listbox5));
		Assert.assertEquals("1", lb2.getValue());
		Assert.assertEquals(1,findWidgets(".z-window-modal").size());
		findWidget(".z-window-modal @button").click();
		
	}
	
	@Test
	public void b01088FormUpdate(){
		navigate(getTestCaseUrl("/bind/issue/B01088FormUpdate.zul"));
		
		Widget lb11 = findWidget("$lb11");
		Widget lb12 = findWidget("$lb12");
		
		Widget tb11 = findWidget("$tb11");
		Widget tb12 = findWidget("$tb12");
		
		Widget lb21 = findWidget("$lb21");
		Widget lb22 = findWidget("$lb22");
		
		Widget reload = findWidget("$reload");
		Widget save = findWidget("$save");
		
		Assert.assertEquals("Dennis", lb11.getValue());
		Assert.assertEquals("Chen", lb12.getValue());
		
		Assert.assertEquals("Dennis", tb11.getValue());
		Assert.assertEquals("Chen", tb12.getValue());
		
		Assert.assertEquals("Dennis Chen", lb21.getValue());
		Assert.assertEquals("false", lb22.getValue());
		
		
		tb11.replace("Alex").tab();
		Assert.assertEquals("Dennis Chen", lb21.getValue());
		Assert.assertEquals("true", lb22.getValue());
		
		reload.click();
		Assert.assertEquals("Dennis", tb11.getValue());
		Assert.assertEquals("Chen", tb12.getValue());
		
		Assert.assertEquals("Dennis Chen", lb21.getValue());
		Assert.assertEquals("false", lb22.getValue());
		
		tb11.replace("Alex").tab();
		tb12.replace("Wang").tab();
		Assert.assertEquals("Dennis Chen", lb21.getValue());
		Assert.assertEquals("true", lb22.getValue());
		
		save.click();
		Assert.assertEquals("Alex", lb11.getValue());
		Assert.assertEquals("Wang", lb12.getValue());
		
		Assert.assertEquals("Alex", tb11.getValue());
		Assert.assertEquals("Wang", tb12.getValue());
		
		Assert.assertEquals("Alex Wang", lb21.getValue());
		Assert.assertEquals("false", lb22.getValue());
	}
	
	@Test
	public void b01139LoadInit(){
		navigate(getTestCaseUrl("/bind/issue/B01139LoadInit.zul"));
		Widget liChk = findWidget("$liChk");
		Widget changeNameBtn = findWidget("$changeNameBtn");
		Widget nameTexb = findWidget("$nameTexb");
		Widget nameLbl = findWidget("$nameLbl");
		
		
		nameTexb.replace("XYZ");
		changeNameBtn.click();
		Assert.assertEquals("XYZ",nameLbl.getValue());
		
		nameTexb.replace("XXX");
		liChk.click();
		changeNameBtn.click();
		Assert.assertEquals("XYZ",nameLbl.getValue());
		
		nameTexb.replace("XXX");
		liChk.click();
		changeNameBtn.click();
		Assert.assertEquals("XXX",nameLbl.getValue());
	}
	
	@Test
	public void b01165InnerViewMdel(){
		navigate(getTestCaseUrl("/bind/issue/B01165NestedBinder.zul"));
		Widget outerPidLb = findWidget("$outerPidLb");
		Widget outerPDescLb = findWidget("$outerPDescLb");
		Widget pidLb = findWidget("$pidLb");
		Widget pDescLb = findWidget("$pDescLb");
		Widget vmsSelIdLb = findWidget("$vmsSelIdLb");
		Widget vmsSelDescLb = findWidget("$vmsSelDescLb");
		
		Assert.assertEquals("b3",outerPidLb.getValue());
		Assert.assertEquals("this is b3",outerPDescLb.getValue());
		Assert.assertEquals("b3",pidLb.getValue());
		Assert.assertEquals("this is b3",pDescLb.getValue());
		Assert.assertEquals("b3",vmsSelIdLb.getValue());
		Assert.assertEquals("this is b3",vmsSelDescLb.getValue());
	}
	
	@Test
	public void b01164RenderedImage(){
		navigate(getTestCaseUrl("/bind/issue/B01164ImageContentType.zul"));
		Widget img = findWidget("$img");
		Assert.assertNotNull(img);
	}
	
	@Test
	public void b01189FormNotifyChange1(){
		navigate(getTestCaseUrl("/bind/issue/B01189FormNotifyChange.zul"));
		Widget tb1 = findWidget("$tb1");
		Widget tb2 = findWidget("$tb2");
		Widget tb3 = findWidget("$tb3");
		Widget tb4 = findWidget("$tb4");
		
		Widget save1 = findWidget("$save1");
		Widget lb1 = findWidget("$lb1");
		
		Assert.assertEquals("", tb1.getValue());
		Assert.assertEquals("", tb2.getValue());
		Assert.assertEquals("", tb3.getValue());
		Assert.assertEquals("", tb4.getValue());
		Assert.assertEquals("", lb1.getValue());
		
		tb1.replace("A").tab();
		Assert.assertEquals("A", tb2.getValue());
		Assert.assertEquals("", tb3.getValue());
		Assert.assertEquals("A", tb4.getValue());
		save1.click();
		Assert.assertEquals("A", lb1.getValue());
		
		
		tb2.replace("B").tab();
		Assert.assertEquals("B", tb1.getValue());
		Assert.assertEquals("", tb3.getValue());
		Assert.assertEquals("B", tb4.getValue());
		save1.click();
		Assert.assertEquals("B", lb1.getValue());
		
		
		tb3.replace("C").tab();
		Assert.assertEquals("B", tb1.getValue());
		Assert.assertEquals("B", tb2.getValue());
		Assert.assertEquals("C", tb4.getValue());
		save1.click();
		Assert.assertEquals("C", lb1.getValue());
		
		tb4.replace("D").tab();
		Assert.assertEquals("B", tb1.getValue());
		Assert.assertEquals("B", tb2.getValue());
		Assert.assertEquals("C", tb3.getValue());
		save1.click();
		Assert.assertEquals("D", lb1.getValue());
		
		
		tb1.replace("E").tab();
		Assert.assertEquals("E", tb2.getValue());
		Assert.assertEquals("C", tb3.getValue());
		Assert.assertEquals("E", tb4.getValue());
		save1.click();
		Assert.assertEquals("E", lb1.getValue());
		
		
		tb2.replace("F").tab();
		Assert.assertEquals("F", tb1.getValue());
		Assert.assertEquals("C", tb3.getValue());
		Assert.assertEquals("F", tb4.getValue());
		save1.click();
		Assert.assertEquals("F", lb1.getValue());
		
		
		tb3.replace("G").tab();
		Assert.assertEquals("F", tb1.getValue());
		Assert.assertEquals("F", tb2.getValue());
		Assert.assertEquals("G", tb4.getValue());
		save1.click();
		Assert.assertEquals("G", lb1.getValue());
		
		tb4.replace("H").tab();
		Assert.assertEquals("F", tb1.getValue());
		Assert.assertEquals("F", tb2.getValue());
		Assert.assertEquals("G", tb3.getValue());
		save1.click();
		Assert.assertEquals("H", lb1.getValue());
	}
	
	@Test
	public void b01189FormNotifyChange2(){
		navigate(getTestCaseUrl("/bind/issue/B01189FormNotifyChange.zul"));
		
		Widget tb5 = findWidget("$tb5");
		Widget tb6 = findWidget("$tb6");
		Widget tb7 = findWidget("$tb7");
		Widget tb8 = findWidget("$tb8");
		
		Widget save2 = findWidget("$save2");
		Widget lb2 = findWidget("$lb2");
		Widget lb3 = findWidget("$lb3");
		
		Assert.assertEquals("", tb5.getValue());
		Assert.assertEquals("", tb6.getValue());
		Assert.assertEquals("", tb7.getValue());
		Assert.assertEquals("", tb8.getValue());
		Assert.assertEquals("", lb2.getValue());
		Assert.assertEquals("", lb3.getValue());
		
		tb5.replace("A").tab();
		Assert.assertEquals("A", tb6.getValue());
		Assert.assertEquals("", tb7.getValue());
		Assert.assertEquals("", tb8.getValue());
		
		tb6.replace("B").tab();
		Assert.assertEquals("B", tb5.getValue());
		Assert.assertEquals("", tb7.getValue());
		Assert.assertEquals("", tb8.getValue());
		
		tb7.replace("C").tab();
		Assert.assertEquals("B", tb5.getValue());
		Assert.assertEquals("B", tb6.getValue());
		Assert.assertEquals("C", tb8.getValue());
		
		tb8.replace("D").tab();
		Assert.assertEquals("B", tb5.getValue());
		Assert.assertEquals("B", tb6.getValue());
		Assert.assertEquals("D", tb7.getValue());
		
		save2.click();
		
		Assert.assertEquals("B", lb2.getValue());
		Assert.assertEquals("D", lb3.getValue());
	}
	
	@Test
	public void b01188MixingELWithRef1(){
		navigate(getTestCaseUrl("/bind/issue/B01188MixingELWithRef.zul"));
		
		Widget lb = findWidget("$lb");
		
		List<Widget> outerItems = lb.findWidgets("@listitem.outer");
		Assert.assertEquals(2, outerItems.size());
		Assert.assertEquals("Today",outerItems.get(0).findWidget("@listcell.outer @label").getValue());
		Assert.assertEquals("Tomorrow",outerItems.get(1).findWidget("@listcell.outer @label").getValue());
		
		
		List<Widget> innerItems = outerItems.get(0).findWidgets("@listbox @listitem.inner");
		Assert.assertEquals(2, innerItems.size());
		Assert.assertEquals("Item 1",innerItems.get(0).findWidget("@listcell.inner @label").getValue());
		Assert.assertEquals("Item 2",innerItems.get(1).findWidget("@listcell.inner @label").getValue());
		
		innerItems = outerItems.get(1).findWidgets("@listbox @listitem.inner");
		Assert.assertEquals(2, innerItems.size());
		Assert.assertEquals("Item 3",innerItems.get(0).findWidget("@listcell.inner @label").getValue());
		Assert.assertEquals("Item 4",innerItems.get(1).findWidget("@listcell.inner @label").getValue());
	}
	
	@Test
	public void b01188MixingELWithRef2(){
		navigate(getTestCaseUrl("/bind/issue/B01188MixingELWithRefAll.zul"));
		
		List<Widget> items = findWidget("$halyout1").findWidgets("@label");
		Assert.assertEquals("0.Item 1",items.get(0).getValue());
		Assert.assertEquals("1.Item 2",items.get(1).getValue());
	
		
		items = findWidget("$listbox1").findWidgets("@listitem");
		Assert.assertEquals("0.Item 1",items.get(0).findWidget("@listcell @label").getValue());
		Assert.assertEquals("1.Item 2",items.get(1).findWidget("@listcell @label").getValue());
		
		items = findWidget("$grid1").findWidgets("@row");
		Assert.assertEquals("0.Item 1",items.get(0).findWidget("@label").getValue());
		Assert.assertEquals("1.Item 2",items.get(1).findWidget("@label").getValue());
		
		findWidget("$combobox1").call("open");
		waitForTrip(1, 500);
		items = findWidget("$combobox1").findWidgets("@comboitem");
		Assert.assertEquals("0.Item 1",items.get(0).getLabel());
		Assert.assertEquals("1.Item 2",items.get(1).getLabel());
		
		items = findWidget("$radiogroup1").findWidgets("@radio");
		Assert.assertEquals("0.Item 1",items.get(0).getLabel());
		Assert.assertEquals("1.Item 2",items.get(1).getLabel());
		
		//cannot verify selectbox items yet
//		items = findWidget("$selectbox1").findWidgets("@label");
//		Assert.assertEquals("0.Item 1",items.get(0).getValue());
//		Assert.assertEquals("1.Item 2",items.get(1).getValue());
		
		
		items = findWidget("$tree1").findWidgets("@treecell");
		Assert.assertEquals("0.Item 1",items.get(0).getLabel());
		Assert.assertEquals("0.Item 1-1",items.get(1).getLabel());
		Assert.assertEquals("1.Item 1-2",items.get(2).getLabel());
		Assert.assertEquals("1.Item 2",items.get(3).getLabel());
	}
	
	@Test
	public void B01196BinderUiLcConfusion(){
		navigate(getTestCaseUrl("/bind/issue/B01196BinderUiLcConfusion.zul"));
		List<Widget> listItems = findWidget("@listbox").findWidgets("@listitem");
		Assert.assertEquals(5, listItems.size());
	}
	
	@Test
	public void B01185CollectionBindingNPE(){
		navigate(getTestCaseUrl("/bind/issue/B01185CollectionBindingNPE.zul"));
		findWidget("$addPersonBtn").click();
		findWidget("$delPerson_0").click();
		findWidget("$addUrlBtn").click();
		findWidget("$delUrl_0").click();
		Widget widget = findWidget("$delUrl_0");
		Assert.assertEquals(true, widget==null);
	}
	
	@Test
	public void B01299RefNPE(){
		navigate(getTestCaseUrl("/bind/issue/B01299RefNPE.zul"));
		
		findWidget("$tba1").replace("AA").tab();
		Assert.assertEquals("AA",findWidget("$lba1").getValue());
		
		findWidget("$tba2").replace("BB").tab();
		Assert.assertEquals("BB",findWidget("$lba2").getValue());
		
		findWidget("$tba3").replace("CC").tab();
		Assert.assertEquals("CC",findWidget("$lba31").getValue());
		Assert.assertEquals("CC",findWidget("$lba32").getValue());
		Assert.assertEquals("CC",findWidget("$lba33").getValue());
		Assert.assertEquals("CC",findWidget("$lba34").getValue());
		
		//condition
		
		findWidget("$tbb1").replace("D").tab();
		findWidget("$btnb1").click();
		Assert.assertEquals("D",findWidget("$lbb1").getValue());
		
		findWidget("$tbb2").replace("E").tab();
		findWidget("$btnb2").click();
		Assert.assertEquals("E",findWidget("$lbb2").getValue());
		
		findWidget("$tbb3").replace("F").tab();
		findWidget("$btnb3").click();
		Assert.assertEquals("F",findWidget("$lbb3").getValue());
		
		findWidget("$tbb4").replace("G").tab();
		findWidget("$btnb4").click();
		Assert.assertEquals("G",findWidget("$lbb41").getValue());
		Assert.assertEquals("G",findWidget("$lbb42").getValue());
		Assert.assertEquals("G",findWidget("$lbb43").getValue());
		Assert.assertEquals("G",findWidget("$lbb44").getValue());
		
		findWidget("$tbb5").replace("H").tab();
		findWidget("$btnb5").click();
		Assert.assertEquals("H",findWidget("$lbb5").getValue());
		
		findWidget("$tbb6").replace("I").tab();
		findWidget("$btnb6").click();
		Assert.assertEquals("I",findWidget("$lbb61").getValue());
		Assert.assertEquals("I",findWidget("$lbb62").getValue());
		Assert.assertEquals("I",findWidget("$lbb63").getValue());
		Assert.assertEquals("I",findWidget("$lbb64").getValue());
		
	}
	
	@Test
	public void B01194NestedVMInit(){
		navigate(getTestCaseUrl("/bind/issue/B01194NestedVMInit.zul"));
		
		Widget headerNameLb = findWidget("$headerNameLb");
		
		Widget vmsNameTxb = findWidget("$vmsNameTxb");
		Widget vmsDescTxb = findWidget("$vmsDescTxb");
		Widget vmInnerVmDescTxb = findWidget("$vmInnerVmDescTxb");
		Widget vmInnerVmDescLb = findWidget("$vmInnerVmDescLb");
		
		Widget outerNameLb = findWidget("$outerNameLb");
		Widget outerDescTxb = findWidget("$outerDescTxb");
		
		String text = null;//headerNameLb.getValue().toString();
//		Assert.assertTrue(text.length() > 0);
//		Assert.assertEquals(text, vmsNameTxb.getValue());
//		Assert.assertEquals(text, outerNameLb.getValue());
		
		text = vmsDescTxb.getValue().toString();
		Assert.assertTrue(text.length() > 0);
		Assert.assertEquals(text,vmInnerVmDescTxb.getValue());
		Assert.assertEquals(text,vmInnerVmDescLb.getValue());
		Assert.assertEquals(text,outerDescTxb.getValue());
		
		text = "Ian Tsai 1";
		vmsNameTxb.replace(text).tab();
		Assert.assertEquals(text,headerNameLb.getValue());
		Assert.assertEquals(text,outerNameLb.getValue());
		
		
		text = "AAA";
		vmsDescTxb.replace(text).tab();
		Assert.assertEquals(text,vmInnerVmDescTxb.getValue());
		Assert.assertEquals(text,vmInnerVmDescLb.getValue());
		Assert.assertEquals(text,outerDescTxb.getValue());
		
		
		text = "BBB";
		vmInnerVmDescTxb.replace(text).tab();
		Assert.assertEquals(text,vmsDescTxb.getValue());
		Assert.assertEquals(text,vmInnerVmDescLb.getValue());
		Assert.assertEquals(text,outerDescTxb.getValue());
	
		
		text = "CCC";
		outerDescTxb.replace(text).tab();
		Assert.assertEquals(text,vmsDescTxb.getValue());
		Assert.assertEquals(text,vmInnerVmDescLb.getValue());
		Assert.assertEquals(text,vmInnerVmDescTxb.getValue());
	}
	
	
	@Test
	public void B01268UnsupportChildExp(){
		navigate(getTestCaseUrl("/bind/issue/B01268UnsupportChildExp.zul"));
		Widget listbox = findWidget("@listbox");
		Assert.assertTrue(listbox!=null);//check if the page can show up.
	}
	
	
	@Test
	public void B01344DeletingEntry(){
		navigate(getTestCaseUrl("/bind/issue/B01344DeletingEntry.zul"));
		Widget btn1 = findWidget("$btn1");
		
		for(int i=10;i>=0;i--){
			Assert.assertEquals(""+i, findWidget("$lb1").getValue());
			Assert.assertEquals(i, findWidget("$listbox").findWidgets("@listitem").size());
			if(i>0){
				btn1.click();
			}
		}		
	}
	
	@Test
	public void B01347CheckboxCrash(){
		navigate(getTestCaseUrl("/bind/issue/B01347CheckboxCrash.zul"));
		Widget listbox = findWidget("@listbox");
		Assert.assertTrue(listbox!=null);//check if the page can show up.
	}
	
	@Test
	public void B01366RadioSelectedItem(){
		navigate(getTestCaseUrl("/bind/issue/B01366RadioSelectedItem.zul"));
		Widget rg1 = findWidget("$radiogroup1");
		Widget rg2 = findWidget("$radiogroup2");
		Widget lb1 = findWidget("$lb1");
		
		rg1.findWidgets("@radio").get(0).click();
		Assert.assertEquals(true,rg2.findWidgets("@radio").get(0).getAttribute("checked"));
		Assert.assertEquals(false,rg2.findWidgets("@radio").get(1).getAttribute("checked"));
		Assert.assertEquals(false,rg2.findWidgets("@radio").get(2).getAttribute("checked"));
		Assert.assertEquals("name 0",lb1.getValue());
		
		rg2.findWidgets("@radio").get(1).click();
		Assert.assertEquals(false,rg1.findWidgets("@radio").get(0).getAttribute("checked"));
		Assert.assertEquals(true,rg1.findWidgets("@radio").get(1).getAttribute("checked"));
		Assert.assertEquals(false,rg1.findWidgets("@radio").get(2).getAttribute("checked"));
		Assert.assertEquals("name 1",lb1.getValue());
	}
}




















