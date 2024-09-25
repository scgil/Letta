package es.uvigo.esei.letta.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import es.uvigo.esei.letta.entities.EventUnitTest;
import es.uvigo.esei.letta.entities.PartakerUnitTest;
import es.uvigo.esei.letta.entities.UserUnitTest;

@SuiteClasses({
	EventUnitTest.class,
	PartakerUnitTest.class,
	UserUnitTest.class
})
@RunWith(Suite.class)
public class UnitTestSuite {
}
