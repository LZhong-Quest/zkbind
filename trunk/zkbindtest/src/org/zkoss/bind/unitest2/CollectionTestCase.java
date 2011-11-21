package org.zkoss.bind.unitest2;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.zkoss.zktc.core.junit.TestCaseBase;
import org.zkoss.zktc.core.widget.Widget;

public class CollectionTestCase  extends TestCaseBase{
	@Test
	public void indexListbox1(){
		navigate(getTestCaseUrl("/zbind/basic/collection-index-listbox.zul"));
		
		Widget outerbox = findWidget("$outerbox");
		List<Widget> outeritems = outerbox.getChildren();//include header
		outeritems.remove(0);//don't care header
		
		String[] itemLabel = new String[]{"A","B","C", "D"};
		Assert.assertEquals(itemLabel.length, outeritems.size());
		
		for(int i=0;i<itemLabel.length;i++){
			Widget outeritem = outeritems.get(i);
			String outerl = itemLabel[i];
			
			Widget cell = outeritem.getFirstChild();
			Assert.assertEquals(""+i, cell.getLabel());// verify the index
			cell = cell.getNextSibling();
			Assert.assertEquals(outerl, cell.getLabel());//verify the label
			
			Widget innerbox = outeritem.findWidget("@listbox");
			Assert.assertNotNull(innerbox);
			List<Widget> inneritems = innerbox.findWidgets("@listitem");
			Assert.assertEquals(2, inneritems.size());
			for(int j=0;j<2;j++){
				Widget inneritem = inneritems.get(j);
				cell = inneritem.getFirstChild();
				Assert.assertEquals(""+j, cell.getLabel());
				cell = cell.getNextSibling();
				Assert.assertEquals(""+i, cell.getLabel());
				
				String innerl = itemLabel[i]+" "+j;
				cell = cell.getNextSibling();
				Assert.assertEquals(innerl, cell.getLabel());
			}
			
			cell = outeritem.getLastChild();
			Widget btn = cell.findWidget("@button");//index button
			Widget msg = findWidget("$msg");
			btn.click();
			
			Assert.assertEquals("item index "+i, msg.getValue());
		}
	}
	
	@Test
	public void indexListbox2(){
		navigate(getTestCaseUrl("/zbind/basic/collection-index-listbox.zul"));
		
		Widget outerbox = findWidget("$outerbox");
		List<Widget> outeritems = outerbox.getChildren();//include header
		outeritems.remove(0);//don't care header
		
		//=================================delete 2rd row
		Widget outeritem = outeritems.get(1);
		outeritem.findWidgets("@button").get(1).click();//click the delete button on 2nd row
		
		outerbox = findWidget("$outerbox");
		outeritems = outerbox.getChildren();//include header
		outeritems.remove(0);//don't care header
		String[] itemLabel = new String[]{"A","C", "D"};
		Assert.assertEquals(itemLabel.length, outeritems.size());
		
		for(int i=0;i<itemLabel.length;i++){
			outeritem = outeritems.get(i);
			String outerl = itemLabel[i];
			
			Widget cell = outeritem.getFirstChild();
			Assert.assertEquals(""+i, cell.getLabel());// verify the index
			cell = cell.getNextSibling();
			Assert.assertEquals(outerl, cell.getLabel());//verify the label
			
			Widget innerbox = outeritem.findWidget("@listbox");
			Assert.assertNotNull(innerbox);
			List<Widget> inneritems = innerbox.findWidgets("@listitem");
			Assert.assertEquals(2, inneritems.size());
			for(int j=0;j<2;j++){
				Widget inneritem = inneritems.get(j);
				cell = inneritem.getFirstChild();
				Assert.assertEquals(""+j, cell.getLabel());
				cell = cell.getNextSibling();
				Assert.assertEquals(""+i, cell.getLabel());
				
				String innerl = itemLabel[i]+" "+j;
				cell = cell.getNextSibling();
				Assert.assertEquals(innerl, cell.getLabel());
			}
			
			cell = outeritem.getLastChild();
			Widget btn = cell.findWidget("@button");//index button
			Widget msg = findWidget("$msg");
			btn.click();
			
			Assert.assertEquals("item index "+i, msg.getValue());
		}
		
		//===============================add after row
		outeritem = outeritems.get(1);
		outeritem.findWidgets("@button").get(2).click();//click the add after button on 2nd row
		
		outerbox = findWidget("$outerbox");
		outeritems = outerbox.getChildren();//include header
		outeritems.remove(0);//don't care header
		itemLabel = new String[]{"A","C","C1","D"};
		Assert.assertEquals(itemLabel.length, outeritems.size());
		
		for(int i=0;i<itemLabel.length;i++){
			outeritem = outeritems.get(i);
			String outerl = itemLabel[i];
			
			Widget cell = outeritem.getFirstChild();
			Assert.assertEquals(""+i, cell.getLabel());// verify the index
			cell = cell.getNextSibling();
			Assert.assertEquals(outerl, cell.getLabel());//verify the label
			
			Widget innerbox = outeritem.findWidget("@listbox");
			Assert.assertNotNull(innerbox);
			List<Widget> inneritems = innerbox.findWidgets("@listitem");
			Assert.assertEquals(2, inneritems.size());
			for(int j=0;j<2;j++){
				Widget inneritem = inneritems.get(j);
				cell = inneritem.getFirstChild();
				Assert.assertEquals(""+j, cell.getLabel());
				cell = cell.getNextSibling();
				Assert.assertEquals(""+i, cell.getLabel());
				
				String innerl = itemLabel[i]+" "+j;
				cell = cell.getNextSibling();
				Assert.assertEquals(innerl, cell.getLabel());
			}
			
			cell = outeritem.getLastChild();
			Widget btn = cell.findWidget("@button");//index button
			Widget msg = findWidget("$msg");
			btn.click();
			
			Assert.assertEquals("item index "+i, msg.getValue());
		}
		
		//===============================add add before row
		outeritem = outeritems.get(2);
		outeritem.findWidgets("@button").get(3).click();//click the add before button on 2nd row
		
		outerbox = findWidget("$outerbox");
		outeritems = outerbox.getChildren();//include header
		outeritems.remove(0);//don't care header
		itemLabel = new String[]{"A","C","C12","C1","D"};
		Assert.assertEquals(itemLabel.length, outeritems.size());
		
		for(int i=0;i<itemLabel.length;i++){
			outeritem = outeritems.get(i);
			String outerl = itemLabel[i];
			
			Widget cell = outeritem.getFirstChild();
			Assert.assertEquals(""+i, cell.getLabel());// verify the index
			cell = cell.getNextSibling();
			Assert.assertEquals(outerl, cell.getLabel());//verify the label
			
			Widget innerbox = outeritem.findWidget("@listbox");
			Assert.assertNotNull(innerbox);
			List<Widget> inneritems = innerbox.findWidgets("@listitem");
			Assert.assertEquals(2, inneritems.size());
			for(int j=0;j<2;j++){
				Widget inneritem = inneritems.get(j);
				cell = inneritem.getFirstChild();
				Assert.assertEquals(""+j, cell.getLabel());
				cell = cell.getNextSibling();
				Assert.assertEquals(""+i, cell.getLabel());
				
				String innerl = itemLabel[i]+" "+j;
				cell = cell.getNextSibling();
				Assert.assertEquals(innerl, cell.getLabel());
			}
			
			cell = outeritem.getLastChild();
			Widget btn = cell.findWidget("@button");//index button
			Widget msg = findWidget("$msg");
			btn.click();
			
			Assert.assertEquals("item index "+i, msg.getValue());
		}
	}
	
	
	@Test
	public void indexGrid1(){
		navigate(getTestCaseUrl("/zbind/basic/collection-index-grid.zul"));
		
		Widget outerbox = findWidget("$outergrid");
		List<Widget> outerrows = outerbox.findWidget("@rows").getChildren();
		
		String[] itemLabel = new String[]{"A","B","C", "D"};
		Assert.assertEquals(itemLabel.length, outerrows.size());
		
		for(int i=0;i<itemLabel.length;i++){
			Widget outerrow = outerrows.get(i);
			String outerl = itemLabel[i];
			
			Widget rowkid = outerrow.getFirstChild();
			Assert.assertEquals(""+i, rowkid.getValue());// verify the index  on label
			rowkid = rowkid.getNextSibling();
			Assert.assertEquals(outerl, rowkid.getValue());//verify the label on label
			
			Widget innergrid = rowkid.getNextSibling();
			Assert.assertNotNull(innergrid);
			List<Widget> innerrows = innergrid.findWidgets("@row");
			Assert.assertEquals(2, innerrows.size());
			for(int j=0;j<2;j++){
				Widget innerrow = innerrows.get(j);
				rowkid = innerrow.getFirstChild();
				Assert.assertEquals(""+j, rowkid.getValue());
				rowkid = rowkid.getNextSibling();
				Assert.assertEquals(""+i, rowkid.getValue());
				
				String innerl = itemLabel[i]+" "+j;
				rowkid = rowkid.getNextSibling();
				Assert.assertEquals(innerl, rowkid.getValue());
			}
			
			rowkid = outerrow.getLastChild();
			Widget btn = rowkid.findWidget("@button");//index button
			Widget msg = findWidget("$msg");
			btn.click();
			
			Assert.assertEquals("item index "+i, msg.getValue());
		}
	}
	
	@Test
	public void indexGrid2(){
		navigate(getTestCaseUrl("/zbind/basic/collection-index-grid.zul"));
		
		Widget outerbox = findWidget("$outergrid");
		List<Widget> outerrows = outerbox.findWidget("@rows").getChildren();
		
		//=================================delete 2rd row
		Widget outeritem = outerrows.get(1);
		outeritem.findWidgets("@button").get(1).click();//click the delete button on 2nd row
		
		outerbox = findWidget("$outergrid");
		outerrows = outerbox.findWidget("@rows").getChildren();
		String[] itemLabel = new String[]{"A" ,"C", "D"};
		Assert.assertEquals(itemLabel.length, outerrows.size());
		
		for(int i=0;i<itemLabel.length;i++){
			Widget outerrow = outerrows.get(i);
			String outerl = itemLabel[i];
			
			Widget rowkid = outerrow.getFirstChild();
			Assert.assertEquals(""+i, rowkid.getValue());// verify the index  on label
			rowkid = rowkid.getNextSibling();
			Assert.assertEquals(outerl, rowkid.getValue());//verify the label on label
			
			Widget innergrid = rowkid.getNextSibling();
			Assert.assertNotNull(innergrid);
			List<Widget> innerrows = innergrid.findWidgets("@row");
			Assert.assertEquals(2, innerrows.size());
			for(int j=0;j<2;j++){
				Widget innerrow = innerrows.get(j);
				rowkid = innerrow.getFirstChild();
				Assert.assertEquals(""+j, rowkid.getValue());
				rowkid = rowkid.getNextSibling();
				Assert.assertEquals(""+i, rowkid.getValue());
				
				String innerl = itemLabel[i]+" "+j;
				rowkid = rowkid.getNextSibling();
				Assert.assertEquals(innerl, rowkid.getValue());
			}
			
			rowkid = outerrow.getLastChild();
			Widget btn = rowkid.findWidget("@button");//index button
			Widget msg = findWidget("$msg");
			btn.click();
			
			Assert.assertEquals("item index "+i, msg.getValue());
		}
		
		//=================================add after row
		outeritem = outerrows.get(1);
		outeritem.findWidgets("@button").get(2).click();//add after 2nd row
		
		outerbox = findWidget("$outergrid");
		outerrows = outerbox.findWidget("@rows").getChildren();
		itemLabel = new String[]{"A" ,"C","C1", "D"};
		Assert.assertEquals(itemLabel.length, outerrows.size());
		
		for(int i=0;i<itemLabel.length;i++){
			Widget outerrow = outerrows.get(i);
			String outerl = itemLabel[i];
			
			Widget rowkid = outerrow.getFirstChild();
			Assert.assertEquals(""+i, rowkid.getValue());// verify the index  on label
			rowkid = rowkid.getNextSibling();
			Assert.assertEquals(outerl, rowkid.getValue());//verify the label on label
			
			Widget innergrid = rowkid.getNextSibling();
			Assert.assertNotNull(innergrid);
			List<Widget> innerrows = innergrid.findWidgets("@row");
			Assert.assertEquals(2, innerrows.size());
			for(int j=0;j<2;j++){
				Widget innerrow = innerrows.get(j);
				rowkid = innerrow.getFirstChild();
				Assert.assertEquals(""+j, rowkid.getValue());
				rowkid = rowkid.getNextSibling();
				Assert.assertEquals(""+i, rowkid.getValue());
				
				String innerl = itemLabel[i]+" "+j;
				rowkid = rowkid.getNextSibling();
				Assert.assertEquals(innerl, rowkid.getValue());
			}
			
			rowkid = outerrow.getLastChild();
			Widget btn = rowkid.findWidget("@button");//index button
			Widget msg = findWidget("$msg");
			btn.click();
			
			Assert.assertEquals("item index "+i, msg.getValue());
		}
		
		//=================================add after row
		outeritem = outerrows.get(2);
		outeritem.findWidgets("@button").get(3).click();//add after 2nd row
		
		outerbox = findWidget("$outergrid");
		outerrows = outerbox.findWidget("@rows").getChildren();
		itemLabel = new String[]{"A" ,"C","C12","C1", "D"};
		Assert.assertEquals(itemLabel.length, outerrows.size());
		
		for(int i=0;i<itemLabel.length;i++){
			Widget outerrow = outerrows.get(i);
			String outerl = itemLabel[i];
			
			Widget rowkid = outerrow.getFirstChild();
			Assert.assertEquals(""+i, rowkid.getValue());// verify the index  on label
			rowkid = rowkid.getNextSibling();
			Assert.assertEquals(outerl, rowkid.getValue());//verify the label on label
			
			Widget innergrid = rowkid.getNextSibling();
			Assert.assertNotNull(innergrid);
			List<Widget> innerrows = innergrid.findWidgets("@row");
			Assert.assertEquals(2, innerrows.size());
			for(int j=0;j<2;j++){
				Widget innerrow = innerrows.get(j);
				rowkid = innerrow.getFirstChild();
				Assert.assertEquals(""+j, rowkid.getValue());
				rowkid = rowkid.getNextSibling();
				Assert.assertEquals(""+i, rowkid.getValue());
				
				String innerl = itemLabel[i]+" "+j;
				rowkid = rowkid.getNextSibling();
				Assert.assertEquals(innerl, rowkid.getValue());
			}
			
			rowkid = outerrow.getLastChild();
			Widget btn = rowkid.findWidget("@button");//index button
			Widget msg = findWidget("$msg");
			btn.click();
			
			Assert.assertEquals("item index "+i, msg.getValue());
		}
	}
	
	
	@Test
	public void indexCombobox1(){
		navigate(getTestCaseUrl("/zbind/basic/collection-index-combobox.zul"));
		
		Widget outerbox = findWidget("$outergrid");
		List<Widget> outerrows = outerbox.findWidget("@rows").getChildren();
		String[] itemLabel = new String[]{"A","B","C", "D"};
		Assert.assertEquals(itemLabel.length, outerrows.size());
		
		for(int i=0;i<itemLabel.length;i++){
			Widget outerrow = outerrows.get(i);
			Widget combobox = outerrow.findWidget("@combobox");
			List<Widget> comboitems = combobox.findWidgets("@comboitem");
			Assert.assertEquals(2, comboitems.size());
			for(int j=0;j<2;j++){
				Widget comboitem = comboitems.get(j);
				Assert.assertEquals(itemLabel[i]+" "+j+"-"+j+"-"+i, comboitem.getLabel());
				Assert.assertEquals(itemLabel[i]+" "+j, comboitem.getAttribute("description"));
			}
			Widget btn = outerrow.findWidget("@button");//index button
			Widget msg = findWidget("$msg");
			btn.click();
			
			Assert.assertEquals("item index "+i, msg.getValue());
		}
	}
	
	@Test
	public void indexCombobox2(){
		navigate(getTestCaseUrl("/zbind/basic/collection-index-combobox.zul"));
		
		Widget outerbox = findWidget("$outergrid");
		List<Widget> outerrows = outerbox.findWidget("@rows").getChildren();
		
		//=================================delete 2rd row
		Widget outeritem = outerrows.get(1);
		outeritem.findWidgets("@button").get(1).click();//click the delete button on 2nd row
		
		outerbox = findWidget("$outergrid");
		outerrows = outerbox.findWidget("@rows").getChildren();
		String[] itemLabel = new String[]{"A","C", "D"};
		Assert.assertEquals(itemLabel.length, outerrows.size());
		
		for(int i=0;i<itemLabel.length;i++){
			Widget outerrow = outerrows.get(i);
			Widget combobox = outerrow.findWidget("@combobox");
			List<Widget> comboitems = combobox.findWidgets("@comboitem");
			Assert.assertEquals(2, comboitems.size());
			for(int j=0;j<2;j++){
				Widget comboitem = comboitems.get(j);
				Assert.assertEquals(itemLabel[i]+" "+j+"-"+j+"-"+i, comboitem.getLabel());
				Assert.assertEquals(itemLabel[i]+" "+j, comboitem.getAttribute("description"));
			}
			Widget btn = outerrow.findWidget("@button");//index button
			Widget msg = findWidget("$msg");
			btn.click();
			
			Assert.assertEquals("item index "+i, msg.getValue());
		}
		
		//=================================add after 2rd row
		outeritem = outerrows.get(1);
		outeritem.findWidgets("@button").get(2).click();//click the add after button on 2nd row
		
		outerbox = findWidget("$outergrid");
		outerrows = outerbox.findWidget("@rows").getChildren();
		itemLabel = new String[]{"A","C", "C1", "D"};
		Assert.assertEquals(itemLabel.length, outerrows.size());
		
		for(int i=0;i<itemLabel.length;i++){
			Widget outerrow = outerrows.get(i);
			Widget combobox = outerrow.findWidget("@combobox");
			List<Widget> comboitems = combobox.findWidgets("@comboitem");
			Assert.assertEquals(2, comboitems.size());
			for(int j=0;j<2;j++){
				Widget comboitem = comboitems.get(j);
				Assert.assertEquals(itemLabel[i]+" "+j+"-"+j+"-"+i, comboitem.getLabel());
				Assert.assertEquals(itemLabel[i]+" "+j, comboitem.getAttribute("description"));
			}
			Widget btn = outerrow.findWidget("@button");//index button
			Widget msg = findWidget("$msg");
			btn.click();
			
			Assert.assertEquals("item index "+i, msg.getValue());
		}
		
		//=================================add before 2rd row
		outeritem = outerrows.get(2);
		outeritem.findWidgets("@button").get(3).click();//click the add after button on 2nd row
		
		outerbox = findWidget("$outergrid");
		outerrows = outerbox.findWidget("@rows").getChildren();
		itemLabel = new String[]{"A","C", "C12", "C1", "D"};
		Assert.assertEquals(itemLabel.length, outerrows.size());
		
		for(int i=0;i<itemLabel.length;i++){
			Widget outerrow = outerrows.get(i);
			Widget combobox = outerrow.findWidget("@combobox");
			List<Widget> comboitems = combobox.findWidgets("@comboitem");
			Assert.assertEquals(2, comboitems.size());
			for(int j=0;j<2;j++){
				Widget comboitem = comboitems.get(j);
				Assert.assertEquals(itemLabel[i]+" "+j+"-"+j+"-"+i, comboitem.getLabel());
				Assert.assertEquals(itemLabel[i]+" "+j, comboitem.getAttribute("description"));
			}
			Widget btn = outerrow.findWidget("@button");//index button
			Widget msg = findWidget("$msg");
			btn.click();
			
			Assert.assertEquals("item index "+i, msg.getValue());
		}
		
	}
}
