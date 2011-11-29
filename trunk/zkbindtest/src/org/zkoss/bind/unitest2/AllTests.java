package org.zkoss.bind.unitest2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses( { LoadTestCase.class, FormTestCase.class,LoadSaveTestCase.class,CollectionTestCase.class,MiscTestCase.class,
	ComponentTestCase.class,AllFeaturesTests.class,AllBugsTests.class})
public class AllTests {

}
