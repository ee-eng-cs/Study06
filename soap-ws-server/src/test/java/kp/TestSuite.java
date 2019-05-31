package kp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import kp.ws.ApplicationIntegrationTests;
import kp.ws.CompanyEndpointIntegrationTests;

/**
 * The test suite for the web services.
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ ApplicationIntegrationTests.class, CompanyEndpointIntegrationTests.class })
public class TestSuite {
}