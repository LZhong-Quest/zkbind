package org.zkoss.bind.unitest2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses( { LoadTestCase.class, FormTestCase.class,MiscTestCase.class,AllFeaturesTests.class})
public class AllTests {

}
