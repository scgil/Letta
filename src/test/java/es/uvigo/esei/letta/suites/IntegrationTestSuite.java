package es.uvigo.esei.letta.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import es.uvigo.esei.letta.rest.EventsResourceTest;

@SuiteClasses({ 
	EventsResourceTest.class
})
@RunWith(Suite.class)
public class IntegrationTestSuite {
}