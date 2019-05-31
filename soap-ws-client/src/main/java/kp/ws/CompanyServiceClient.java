package kp.ws;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.kp.ws.Company;
import com.kp.ws.Department;
import com.kp.ws.Employee;
import com.kp.ws.GetCompanyRequest;
import com.kp.ws.GetCompanyResponse;
import com.kp.ws.GetDepartmentsRequest;
import com.kp.ws.GetDepartmentsResponse;
import com.kp.ws.GetEmployeesRequest;
import com.kp.ws.GetEmployeesResponse;

import kp.Constants;

/**
 * The company service client.
 *
 */
public class CompanyServiceClient extends WebServiceGatewaySupport {
	private static final Logger logger = LoggerFactory.getLogger(CompanyServiceClient.class);

	/**
	 * Gets the company with all departments and employees.
	 * 
	 * @return the company
	 */
	public Company getCompany() {

		final GetCompanyRequest request = new GetCompanyRequest();

		final SoapActionCallback requestCallback = new SoapActionCallback(
				Constants.TARGET_NAMESPACE.concat("/GetCompanyRequest"));

		final GetCompanyResponse response = (GetCompanyResponse) getWebServiceTemplate()/*-*/
				.marshalSendAndReceive(Constants.ENDPOINT_ADDRESS, request, requestCallback);
		final Company company = response.getCompany();
		logger.info("getCompany():");
		return company;
	}

	/**
	 * Gets the departments with given department name.
	 * 
	 * @param departmentName the department name
	 * @return the list of departments
	 */
	public List<Department> getDepartments(String departmentName) {

		final GetDepartmentsRequest request = new GetDepartmentsRequest();
		request.setDepartmentName(departmentName);
		final SoapActionCallback requestCallback = new SoapActionCallback(
				Constants.TARGET_NAMESPACE.concat("/GetDepartmentsRequest"));

		final GetDepartmentsResponse response = (GetDepartmentsResponse) getWebServiceTemplate()/*-*/
				.marshalSendAndReceive(Constants.ENDPOINT_ADDRESS, request, requestCallback);
		final List<Department> departmentsList = response.getDepartments();
		logger.info(String.format("getDepartments(): department name[%s], departments list size[%d]", departmentName,
				departmentsList.size()));
		return departmentsList;
	}

	/**
	 * Gets the employees with given department name and employee last name.
	 * 
	 * @param departmentName   the department name
	 * @param employeeLastName the employee last name
	 * @return the list of employees
	 */
	public List<Employee> getEmployees(String departmentName, String employeeLastName) {

		final GetEmployeesRequest request = new GetEmployeesRequest();
		request.setDepartmentName(departmentName);
		request.setEmployeeLastName(employeeLastName);
		final SoapActionCallback requestCallback = new SoapActionCallback(
				Constants.TARGET_NAMESPACE.concat("/GetEmployeesRequest"));

		final GetEmployeesResponse response = (GetEmployeesResponse) getWebServiceTemplate()/*-*/
				.marshalSendAndReceive(Constants.ENDPOINT_ADDRESS, request, requestCallback);
		final List<Employee> employeesList = response.getEmployees();
		logger.info(
				String.format("getEmployees(): department name[%s], employee last name[%s], employees list size[%d]",
						departmentName, employeeLastName, employeesList.size()));
		return employeesList;
	}
}