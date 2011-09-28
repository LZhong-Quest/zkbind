package issues;

import org.zkoss.zkbind.GenericBindComposer;
import org.zkoss.zkbind.NotifyChange;

public class PromptCmdLoadComposer  extends GenericBindComposer {
	private String value1;

	public PromptCmdLoadComposer() {
		value1 = "A";
	}

	public String getValue1() {
		return value1;
	}

	@NotifyChange
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	
	public void cmd1(){
		
	}
}
