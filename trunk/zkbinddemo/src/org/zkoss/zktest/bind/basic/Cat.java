package org.zkoss.zktest.bind.basic;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zk.ui.util.ComposerExt;

public class Cat implements Composer<Component>,ComposerExt<Component>{

	public ComponentInfo doBeforeCompose(Page page, Component parent, ComponentInfo compInfo) throws Exception {
		return compInfo;
	}

	public void doBeforeComposeChildren(Component comp) throws Exception {
		comp.setAttribute("ctrl", this);
	}

	public boolean doCatch(Throwable ex) throws Exception {

		return false;
	}

	public void doFinally() throws Exception {
	}

	public void doAfterCompose(Component comp) throws Exception {
	}
	
	public String foo(){
		return "foo";
	}
	
	public String bar(String bar){
		return bar;
	}
	
	public String cat(String a, String b){
		return a+":"+b;
	}
	
	static public class VM {
		public String foo(){
			return "foo";
		}
		
		public String bar(String bar){
			return bar;
		}
		
		public String cat(String a, String b){
			return a+":"+b;
		}
	}

}
