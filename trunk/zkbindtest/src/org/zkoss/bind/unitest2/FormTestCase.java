package org.zkoss.bind.unitest2;

import org.junit.Assert;
import org.junit.Test;
import org.zkoss.zktc.core.junit.TestCaseBase;
import org.zkoss.zktc.core.widget.WidgetDriver;

public class FormTestCase extends TestCaseBase {

	@Test
	public void testLoad() {
		navigate(getTestCaseUrl("/zbind/basic/load-form.zul"));

		Assert.assertEquals("First1", findWidget("$l1").getAttribute("value"));
		Assert.assertEquals("Last1", findWidget("$l2").getAttribute("value"));
		Assert.assertEquals("First1 Last1",
				findWidget("$l3").getAttribute("value"));

		Assert.assertEquals("First1", findWidget("$l5").getAttribute("value"));
		Assert.assertEquals("Last1", findWidget("$l6").getAttribute("value"));
		Assert.assertEquals("First1 Last1",
				findWidget("$l7").getAttribute("value"));

		Assert.assertEquals("", findWidget("$l9").getAttribute("value"));
		Assert.assertEquals("", findWidget("$la").getAttribute("value"));
		Assert.assertEquals("", findWidget("$lb").getAttribute("value"));

		findWidget("$l1").clear().keys("XXX");
		findWidget("$btn1").focus();

		Assert.assertEquals("XXX", findWidget("$l1").getAttribute("value"));
		Assert.assertEquals("Last1", findWidget("$l2").getAttribute("value"));
		Assert.assertEquals("XXX Last1", findWidget("$l3")
				.getAttribute("value"));

		// spec change, p1.first change will not effect p1 -> fx
		Assert.assertEquals("First1", findWidget("$l5").getAttribute("value"));
		Assert.assertEquals("Last1", findWidget("$l6").getAttribute("value"));
		Assert.assertEquals("First1 Last1",
				findWidget("$l7").getAttribute("value"));

		Assert.assertEquals("", findWidget("$l9").getAttribute("value"));
		Assert.assertEquals("", findWidget("$la").getAttribute("value"));
		Assert.assertEquals("", findWidget("$lb").getAttribute("value"));

		findWidget("$l5").clear().keys("YYY");
		findWidget("$btn1").focus();

		Assert.assertEquals("XXX", findWidget("$l1").getAttribute("value"));
		Assert.assertEquals("Last1", findWidget("$l2").getAttribute("value"));
		Assert.assertEquals("XXX Last1", findWidget("$l3")
				.getAttribute("value"));

		Assert.assertEquals("YYY", findWidget("$l5").getAttribute("value"));
		Assert.assertEquals("Last1", findWidget("$l6").getAttribute("value"));
		Assert.assertEquals("First1 Last1",
				findWidget("$l7").getAttribute("value"));

		Assert.assertEquals("", findWidget("$l9").getAttribute("value"));
		Assert.assertEquals("", findWidget("$la").getAttribute("value"));
		Assert.assertEquals("", findWidget("$lb").getAttribute("value"));

		findWidget("$btn1").click();

		Assert.assertEquals("YYY", findWidget("$l1").getAttribute("value"));
		Assert.assertEquals("Last1", findWidget("$l2").getAttribute("value"));
		Assert.assertEquals("YYY Last1", findWidget("$l3")
				.getAttribute("value"));

		Assert.assertEquals("YYY", findWidget("$l5").getAttribute("value"));
		Assert.assertEquals("Last1", findWidget("$l6").getAttribute("value"));
		Assert.assertEquals("YYY Last1", findWidget("$l7")
				.getAttribute("value"));

		Assert.assertEquals("YYY", findWidget("$l9").getAttribute("value"));
		Assert.assertEquals("Last1", findWidget("$la").getAttribute("value"));
		Assert.assertEquals("YYY Last1", findWidget("$lb")
				.getAttribute("value"));

	}

	@Test
	public void testIndirect() {
		navigate(getTestCaseUrl("/zbind/basic/load-form-indirect.zul"));

		Assert.assertEquals("First1", findWidget("$l1").getAttribute("value"));
		Assert.assertEquals("Last1", findWidget("$l2").getAttribute("value"));
		Assert.assertEquals("First1 Last1",
				findWidget("$l3").getAttribute("value"));
		Assert.assertEquals("First1", findWidget("$l4").getAttribute("value"));
		Assert.assertEquals("First1", findWidget("$l5").getAttribute("value"));

		Assert.assertEquals("", findWidget("$l6").getAttribute("value"));
		Assert.assertEquals("", findWidget("$l7").getAttribute("value"));
		Assert.assertEquals("", findWidget("$l8").getAttribute("value"));

		findWidget("$l1").clear().keys("XXX");
		findWidget("$btn1").focus();

		Assert.assertEquals("XXX", findWidget("$l1").getAttribute("value"));
		Assert.assertEquals("Last1", findWidget("$l2").getAttribute("value"));
		Assert.assertEquals("First1 Last1",
				findWidget("$l3").getAttribute("value"));
		Assert.assertEquals("First1", findWidget("$l4").getAttribute("value"));
		Assert.assertEquals("First1", findWidget("$l5").getAttribute("value"));

		Assert.assertEquals("", findWidget("$l6").getAttribute("value"));
		Assert.assertEquals("", findWidget("$l7").getAttribute("value"));
		Assert.assertEquals("", findWidget("$l8").getAttribute("value"));

		findWidget("$btn2").click();
		Assert.assertEquals("First1", findWidget("$l4").getAttribute("value"));
		Assert.assertEquals("Last1", findWidget("$l5").getAttribute("value"));
		Assert.assertEquals("", findWidget("$l8").getAttribute("value"));

		findWidget("$btn3").click();
		Assert.assertEquals("First1", findWidget("$l4").getAttribute("value"));
		Assert.assertEquals("First1 Last1",
				findWidget("$l5").getAttribute("value"));
		Assert.assertEquals("", findWidget("$l8").getAttribute("value"));

		findWidget("$btn4").click();
		Assert.assertEquals("XXX", findWidget("$l1").getAttribute("value"));
		Assert.assertEquals("Last1", findWidget("$l2").getAttribute("value"));
		Assert.assertEquals("XXX Last1", findWidget("$l3")
				.getAttribute("value"));
		Assert.assertEquals("XXX Last1", findWidget("$l4")
				.getAttribute("value"));
		Assert.assertEquals("XXX Last1", findWidget("$l5")
				.getAttribute("value"));

		Assert.assertEquals("XXX", findWidget("$l6").getAttribute("value"));
		Assert.assertEquals("Last1", findWidget("$l7").getAttribute("value"));
		Assert.assertEquals("XXX Last1", findWidget("$l8")
				.getAttribute("value"));

		findWidget("$l1").clear().keys("YYY");
		findWidget("$btn1").focus();

		findWidget("$btn1").click();
		Assert.assertEquals("XXX Last1", findWidget("$l4")
				.getAttribute("value"));
		Assert.assertEquals("XXX", findWidget("$l5").getAttribute("value"));
		Assert.assertEquals("XXX Last1", findWidget("$l8")
				.getAttribute("value"));

		findWidget("$btn4").click();
		Assert.assertEquals("YYY", findWidget("$l1").getAttribute("value"));
		Assert.assertEquals("Last1", findWidget("$l2").getAttribute("value"));
		Assert.assertEquals("YYY Last1", findWidget("$l3")
				.getAttribute("value"));
		Assert.assertEquals("YYY", findWidget("$l4").getAttribute("value"));
		Assert.assertEquals("YYY", findWidget("$l5").getAttribute("value"));

		Assert.assertEquals("YYY", findWidget("$l6").getAttribute("value"));
		Assert.assertEquals("Last1", findWidget("$l7").getAttribute("value"));
		Assert.assertEquals("YYY Last1", findWidget("$l8")
				.getAttribute("value"));
	}

	@Test
	public void testValidation() {
		navigate(getTestCaseUrl("/zbind/basic/validation.zul"));

		Assert.assertEquals("0", findWidget("$l11").getValue());
		Assert.assertEquals("", findWidget("$l12").getValue());
		Assert.assertEquals("0", findWidget("$t21").getValue());
		Assert.assertEquals("", findWidget("$t22").getValue());
		Assert.assertEquals("0", findWidget("$t31").getValue());
		Assert.assertEquals("", findWidget("$t32").getValue());
		Assert.assertEquals("", findWidget("$msg1").getValue());
		Assert.assertEquals("", findWidget("$msg2").getValue());

		findWidget("$t31").clear().keys("1").tab();
		Assert.assertEquals("0", findWidget("$l11").getValue());
		Assert.assertEquals("", findWidget("$l12").getValue());
		Assert.assertEquals("0", findWidget("$t21").getValue());
		Assert.assertEquals("", findWidget("$t22").getValue());
		Assert.assertEquals("1", findWidget("$t31").getValue());
		Assert.assertEquals("", findWidget("$t32").getValue());
		Assert.assertEquals("", findWidget("$msg1").getValue());
		Assert.assertEquals("", findWidget("$msg2").getValue());

		findWidget("$t32").clear().keys("3").tab();
		Assert.assertEquals("0", findWidget("$l11").getValue());
		Assert.assertEquals("", findWidget("$l12").getValue());
		Assert.assertEquals("0", findWidget("$t21").getValue());
		Assert.assertEquals("", findWidget("$t22").getValue());
		Assert.assertEquals("1", findWidget("$t31").getValue());
		Assert.assertEquals("3", findWidget("$t32").getValue());
		Assert.assertEquals("", findWidget("$msg1").getValue());
		Assert.assertEquals("", findWidget("$msg2").getValue());

		findWidget("$btn1").click();
		Assert.assertEquals("0", findWidget("$l11").getValue());
		Assert.assertEquals("", findWidget("$l12").getValue());
		Assert.assertEquals("0", findWidget("$t21").getValue());
		Assert.assertEquals("", findWidget("$t22").getValue());
		Assert.assertEquals("1", findWidget("$t31").getValue());
		Assert.assertEquals("3", findWidget("$t32").getValue());
		Assert.assertEquals("value 1 have to large than 10",
				findWidget("$msg1").getValue());
		Assert.assertEquals("", findWidget("$msg2").getValue());

		findWidget("$t31").clear().keys("15").tab();
		Assert.assertEquals("0", findWidget("$l11").getValue());
		Assert.assertEquals("", findWidget("$l12").getValue());
		Assert.assertEquals("0", findWidget("$t21").getValue());
		Assert.assertEquals("", findWidget("$t22").getValue());
		Assert.assertEquals("15", findWidget("$t31").getValue());
		Assert.assertEquals("3", findWidget("$t32").getValue());
		Assert.assertEquals("value 1 have to large than 10",
				findWidget("$msg1").getValue());
		Assert.assertEquals("", findWidget("$msg2").getValue());

		findWidget("$btn1").click();
		Assert.assertEquals("0", findWidget("$l11").getValue());
		Assert.assertEquals("", findWidget("$l12").getValue());
		Assert.assertEquals("0", findWidget("$t21").getValue());
		Assert.assertEquals("", findWidget("$t22").getValue());
		Assert.assertEquals("15", findWidget("$t31").getValue());
		Assert.assertEquals("3", findWidget("$t32").getValue());
		Assert.assertEquals("", findWidget("$msg1").getValue());
		Assert.assertEquals("value 2 have to large than 20",
				findWidget("$msg2").getValue());

		findWidget("$t32").clear().keys("35").tab();
		Assert.assertEquals("0", findWidget("$l11").getValue());
		Assert.assertEquals("", findWidget("$l12").getValue());
		Assert.assertEquals("0", findWidget("$t21").getValue());
		Assert.assertEquals("", findWidget("$t22").getValue());
		Assert.assertEquals("15", findWidget("$t31").getValue());
		Assert.assertEquals("35", findWidget("$t32").getValue());
		Assert.assertEquals("", findWidget("$msg1").getValue());
		Assert.assertEquals("value 2 have to large than 20",
				findWidget("$msg2").getValue());

		findWidget("$btn1").click();
		Assert.assertEquals("15", findWidget("$l11").getValue());
		Assert.assertEquals("35", findWidget("$l12").getValue());
		Assert.assertEquals("15", findWidget("$t21").getValue());
		Assert.assertEquals("35", findWidget("$t22").getValue());
		Assert.assertEquals("15", findWidget("$t31").getValue());
		Assert.assertEquals("35", findWidget("$t32").getValue());
		Assert.assertEquals("", findWidget("$msg1").getValue());
		Assert.assertEquals("", findWidget("$msg2").getValue());
	}

}
