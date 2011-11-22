/* CollectionIndexComposer.java

	Purpose:
		
	Description:
		
	History:
		Created by Dennis

Copyright (C) 2011 Potix Corporation. All Rights Reserved.
 */

package org.zkoss.zktest.zbind.basic;

import java.util.Map;

import org.zkoss.bind.BindComposer;
import org.zkoss.bind.NotifyChange;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.TreeNode;

/**
 * @author Dennis Chen
 * 
 */
public class CollectionIndexTreeComposer extends BindComposer {
	private String message1;

	TreeModel<TreeNode<String>> model;

	public CollectionIndexTreeComposer() {

		MyTreeNode root = new MyTreeNode("Root",
				new MyTreeNode[] {});

		String[] labs = new String[]{"A","B","C"};
		
		for (int i = 0; i < 3; i++) {
			MyTreeNode ni = new MyTreeNode(labs[i] + i,
					new MyTreeNode[] {});
			for (int j = 0; j < 3; j++) {
				MyTreeNode nj = new MyTreeNode(ni.getData()
						+ "-" + j, new MyTreeNode[] {});
				for (int k = 0; k < 2; k++) {
					MyTreeNode nk = new MyTreeNode(
							nj.getData() + "-" + k);
					nj.add(nk);
				}
				ni.add(nj);
			}
			root.add(ni);
		}

		model = new DefaultTreeModel<String>(root);

	}

	public TreeModel<TreeNode<String>> getModel() {
		return model;
	}

	@NotifyChange({ "message1" })
	public void showIndex(Map args) {
		Number index = (Number) args.get("index");
		int i = index.intValue();
		message1 = "item index " + i;
	}

	@NotifyChange({ "model", "message1" })
	public void delete(Map args) {
		MyTreeNode node = (MyTreeNode) args.get("node");
		MyTreeNode parent = (MyTreeNode)node.getParent();
		int i = parent.getIndex(node);
		parent.remove(i);
		message1 = "remove item index " + i;
	}

	@NotifyChange({ "model", "message1" })
	public void addAfter(Map args) {
		MyTreeNode node = (MyTreeNode) args.get("node");
		MyTreeNode parent = (MyTreeNode)node.getParent();
		int i = parent.getIndex(node);
		parent.insert(new MyTreeNode(node.getData()), i + 1);
		message1 = "addAfter item index " + i;
	}

	@NotifyChange({ "model", "message1" })
	public void addBefore(Map args) {
		TreeNode<String> node = (TreeNode<String>) args.get("node");
		TreeNode<String> parent = node.getParent();
		int i = parent.getIndex(node);
		parent.insert(new MyTreeNode(node.getData()), i);
		message1 = "addBefore item index " + (i + 1);
	}

	public String getMessage1() {
		return message1;
	}

	static public class MyTreeNode extends DefaultTreeNode<String> {

		boolean open;

		public MyTreeNode(String data, MyTreeNode[] children) {
			super(data,children);
		}
		public MyTreeNode(String data) {
			super(data);
		}

		public boolean isOpen() {
			return open;
		}

		public void setOpen(boolean open) {
			this.open = open;
		}

	}
}
