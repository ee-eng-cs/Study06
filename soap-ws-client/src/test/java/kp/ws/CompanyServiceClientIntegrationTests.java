package kp.ws;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ws.test.client.MockWebServiceServer;
import org.springframework.ws.test.client.RequestMatcher;
import org.springframework.ws.test.client.RequestMatchers;
import org.springframework.ws.test.client.ResponseCreator;
import org.springframework.ws.test.client.ResponseCreators;

import com.kp.ws.Company;
import com.kp.ws.Department;
import com.kp.ws.Employee;

import kp.Constants;

/**
 * The client-side integration testing.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CompanyServiceClientIntegrationTests {

	@Autowired
	private CompanyServiceClient companyServiceClient;

	private MockWebServiceServer mockServer;

	/**
	 * Initializes mock web service server.
	 * 
	 * @throws Exception the exception
	 */
	@Before
	public void initServer() throws Exception {
		mockServer = MockWebServiceServer.createServer(companyServiceClient);
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
		final RequestMatcher requestMatcher = RequestMatchers.payload(requestPayload);
		final Source responsePayload = new StreamSource(new ClassPathResource("getCompanyResponse.xml").getFile());
		final ResponseCreator responseCreator = ResponseCreators.withPayload(responsePayload);
		// WHEN
		mockServer.expect(requestMatcher).andRespond(responseCreator);
		final Company company = companyServiceClient.getCompany();
		// THEN
		assertThat(company).isNotNull();
		assertEquals(company.getDepartments().size(), 2);
		// verifying that the expected message was actually received
		mockServer.verify();
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
		final RequestMatcher requestMatcher = RequestMatchers.payload(requestPayload);
		final Source responsePayload = new StreamSource(new ClassPathResource("getDepartmentsResponse.xml").getFile());
		final ResponseCreator responseCreator = ResponseCreators.withPayload(responsePayload);
		// WHEN
		mockServer.expect(requestMatcher).andRespond(responseCreator);
		final List<Department> departmentsList = companyServiceClient.getDepartments(Constants.DEPARTMENT_NAME);
		// THEN
		assertThat(departmentsList).isNotNull();
		assertEquals(departmentsList.size(), 1);
		assertEquals(departmentsList.get(0).getName(), Constants.DEPARTMENT_NAME);
		// verifying that the expected message was actually received
		mockServer.verify();
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
		final RequestMatcher requestMatcher = RequestMatchers.payload(requestPayload);
		final Source responsePayload = new StreamSource(new ClassPathResource("getEmployeesResponse.xml").getFile());
		final ResponseCreator responseCreator = ResponseCreators.withPayload(responsePayload);
		// WHEN
		mockServer.expect(requestMatcher).andRespond(responseCreator);
		final List<Employee> employeesList = companyServiceClient.getEmployees(Constants.DEPARTMENT_NAME,
				Constants.EMPLOYEE_LAST_NAME);
		// THEN
		assertThat(employeesList).isNotNull();
		assertEquals(employeesList.size(), 1);
		assertEquals(employeesList.get(0).getLastName(), Constants.EMPLOYEE_LAST_NAME);
		// verifying that the expected message was actually received
		mockServer.verify();
	}
}