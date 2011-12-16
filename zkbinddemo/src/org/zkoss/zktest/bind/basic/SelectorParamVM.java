package org.zkoss.zktest.bind.basic;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.SelectorParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;

public class SelectorParamVM {

	@Init
	public void init(@SelectorParam("#l14") Label l4,
			@SelectorParam(value = "label", index = -1) List<Label> labels) {
		for (int i = 0; i < labels.size(); i++) {
			labels.get(i).setValue("Init " + i);
		}
		l4.setValue(l4.getValue() + ":4");
	}

	@NotifyChange("*")
	@Command
	public void cmd1(@SelectorParam(value = "label", index = -1) List<Label> labels,
			@SelectorParam("#l13") Label l3) {
		for (int i = 0; i < labels.size(); i++) {
			labels.get(i).setValue("Command " + i);
		}
		l3.setValue(l3.getValue() + ":3");
	}

	@NotifyChange("*")
	@Command
	public void cmd2(@SelectorParam(value = "label", local = true, index=-1) List<Label> labels,
			@SelectorParam("button[label='cmd2']") Button btn) {
		btn.setLabel("size " + labels.size());
	}

}
