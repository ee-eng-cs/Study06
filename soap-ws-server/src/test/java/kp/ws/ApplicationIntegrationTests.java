package kp.ws;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ClassUtils;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.kp.ws.GetCompanyRequest;
import com.kp.ws.GetCompanyResponse;
import com.kp.ws.GetDepartmentsRequest;
import com.kp.ws.GetDepartmentsResponse;
import com.kp.ws.GetEmployeesRequest;
import com.kp.ws.GetEmployeesResponse;

import kp.Constants;

/**
 * The integration tests.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationIntegrationTests {

	private final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

	private final WebServiceTemplate webServiceTemplate = new WebServiceTemplate(marshaller);

	@LocalServerPort
	private int port = 0;

	private String REQUEST_URI;

	/**
	 * Initializes marshaller.
	 * 
	 * @throws Exception the exception
	 */
	@Before
	public void init() throws Exception {

		marshaller.setPackagesToScan(ClassUtils.getPackageName(GetCompanyRequest.class));
		marshaller.afterPropertiesSet();
		REQUEST_URI = String.format("http://localhost:%s/ws", port);
	}

	/**
	 * Should get the company.
	 * 
	 */
	@Test
	public void shouldGetCompany() {

		// GIVEN
		final GetCompanyRequest request = new GetCompanyRequest();
		// WHEN
		final GetCompanyResponse response = (GetCompanyResponse) webServiceTemplate.marshalSendAndReceive(REQUEST_URI,
				request);
		// THEN
		assertThat(response).isNotNull();
		assertThat(response.getCompany()).isNotNull();
		assertEquals(response.getCompany().getDepartments().size(), 2);
	}

	/**
	 * Should get the departments.
	 * 
	 */
	@Test
	public void shouldGetDepartments() {

		// GIVEN
		final GetDepartmentsRequest request = new GetDepartmentsRequest();
		request.setDepartmentName(Constants.DEPARTMENT_NAME);
		// WHEN
		final GetDepartmentsResponse response = (GetDepartmentsResponse) webServiceTemplate
				.marshalSendAndReceive(REQUEST_URI, request);
		// THEN
		assertThat(response).isNotNull();
		assertThat(response.getDepartments()).isNotNull();
		assertEquals(response.getDepartments().size(), 1);
		assertEquals(response.getDepartments().get(0).getName(), Constants.DEPARTMENT_NAME);
	}

	/**
	 * Should get the employees.
	 * 
	 */
	@Test
	public void shouldGetEmployees() {

		// GIVEN
		final GetEmployeesRequest request = new GetEmployeesRequest();
		request.setDepartmentName(Constants.DEPARTMENT_NAME);
		request.setEmployeeLastName(Constants.EMPLOYEE_LAST_NAME);
		// WHEN
		final GetEmployeesResponse response = (GetEmployeesResponse) webServiceTemplate
				.marshalSendAndReceive(REQUEST_URI, request);
		// THEN
		assertThat(response).isNotNull();
		assertThat(response.getEmployees()).isNotNull();
		assertEquals(response.getEmployees().size(), 1);
		assertEquals(response.getEmployees().get(0).getLastName(), Constants.EMPLOYEE_LAST_NAME);
	}
}