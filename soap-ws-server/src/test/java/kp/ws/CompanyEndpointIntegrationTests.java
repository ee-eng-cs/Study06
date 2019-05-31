package kp.ws;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.ws.test.server.RequestCreator;
import org.springframework.ws.test.server.RequestCreators;
import org.springframework.ws.test.server.ResponseActions;
import org.springframework.ws.test.server.ResponseMatchers;
import org.springframework.ws.test.server.ResponseXPathExpectations;

import kp.Constants;

/**
 * The server-side integration testing.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CompanyEndpointIntegrationTests {

	private static final boolean VERBOSE = false;

	private MockWebServiceClient mockClient;

	@Autowired
	private ApplicationContext applicationContext;

	private Resource schemaResource = new ClassPathResource(Constants.SCHEMA_XSD_PATH);

	/**
	 * Initializes mock web service client.
	 * 
	 * @throws Exception the exception
	 */
	@Before
	public void initClient() throws Exception {
		mockClient = MockWebServiceClient.createClient(applicationContext);
	}

	/**
	 * Should get the company.
	 * 
	 * @throws IOException the exception
	 */
	@Test
	public void shouldGetCompany() throws IOException {

		// GIVEN
		final Source requestPayload = new StreamSource(new ClassPathResource("getCompanyRequest.xml").getFile());
		final RequestCreator requestCreator = RequestCreators.withPayload(requestPayload);
		final ResponseXPathExpectations expectedXpath = ResponseMatchers.xpath(
				"/ns:getCompanyResponse/ns:company/ns:departments[ns:name='N-a-m-e-01']/"
						+ "ns:employees[ns:lastName='L-N-a-m-e-01']",
				Collections.singletonMap("ns", "http://kp.com/ws"));
		final Source responsePayload = new StreamSource(new ClassPathResource("getCompanyResponse.xml").getFile());
		// WHEN
		final ResponseActions responseActions = mockClient.sendRequest(requestCreator);
		// THEN
		responseActions.andExpect(ResponseMatchers.noFault());
		responseActions.andExpect(expectedXpath.exists());
		responseActions.andExpect(ResponseMatchers.payload(responsePayload));
		responseActions.andExpect(ResponseMatchers.validPayload(schemaResource));
		printResponse(responseActions);
	}

	/**
	 * Should get the departments.
	 * 
	 * @throws IOException the exception
	 */
	@Test
	public void shouldGetDepartments() throws IOException {

		// GIVEN
		final Source requestPayload = new StreamSource(new ClassPathResource("getDepartmentsRequest.xml").getFile());
		final RequestCreator requestCreator = RequestCreators.withPayload(requestPayload);
		final Source responsePayload = new StreamSource(new ClassPathResource("getDepartmentsResponse.xml").getFile());
		// WHEN
		final ResponseActions responseActions = mockClient.sendRequest(requestCreator);
		// THEN
		responseActions.andExpect(ResponseMatchers.noFault());
		responseActions.andExpect(ResponseMatchers.payload(responsePayload));
		responseActions.andExpect(ResponseMatchers.validPayload(schemaResource));
		printResponse(responseActions);
	}

	/**
	 * Should get the employees.
	 * 
	 * @throws IOException the exception
	 */
	@Test
	public void shouldGetEmployees() throws IOException {

		// GIVEN
		final Source requestPayload = new StreamSource(new ClassPathResource("getEmployeesRequest.xml").getFile());
		final RequestCreator requestCreator = RequestCreators.withPayload(requestPayload);
		final Source responsePayload = new StreamSource(new ClassPathResource("getEmployeesResponse.xml").getFile());
		// WHEN
		final ResponseActions responseActions = mockClient.sendRequest(requestCreator);
		// THEN
		responseActions.andExpect(ResponseMatchers.noFault());
		responseActions.andExpect(ResponseMatchers.payload(responsePayload));
		responseActions.andExpect(ResponseMatchers.validPayload(schemaResource));
		printResponse(responseActions);
	}

	/**
	 * Prints web service response.
	 * 
	 * @param responseActions the actions
	 */
	private void printResponse(ResponseActions responseActions) {

		if (VERBOSE) {
			final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			responseActions.andExpect((request, response) -> response.writeTo(outputStream));
			final String content = new String(outputStream.toByteArray());
			System.err.println(content.replace("><", ">\n<"));
		}
	}
}