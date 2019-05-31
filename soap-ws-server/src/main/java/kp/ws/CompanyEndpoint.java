package kp.ws;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.kp.ws.Department;
import com.kp.ws.Employee;
import com.kp.ws.GetCompanyRequest;
import com.kp.ws.GetCompanyResponse;
import com.kp.ws.GetDepartmentsRequest;
import com.kp.ws.GetDepartmentsResponse;
import com.kp.ws.GetEmployeesRequest;
import com.kp.ws.GetEmployeesResponse;

import kp.Constants;
import kp.repository.CompanyRepository;

/**
 * The company web service endpoint.
 *
 */
@Endpoint
public class CompanyEndpoint {
	private static final Log logger = LogFactory.getLog(CompanyEndpoint.class);

	private CompanyRepository companyRepository;

	/**
	 * Constructor.
	 * 
	 * @param companyRepository the company repository
	 */
	@Autowired
	public CompanyEndpoint(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	/**
	 * Gets company.
	 * 
	 * @param request the company request
	 * @return the company response
	 */
	@PayloadRoot(namespace = Constants.TARGET_NAMESPACE, localPart = "getCompanyRequest")
	@ResponsePayload
	public GetCompanyResponse getCompany(@RequestPayload GetCompanyRequest request) {

		final GetCompanyResponse response = new GetCompanyResponse();
		response.setCompany(companyRepository.findCompany());
		logger.info("getCompany():");
		return response;
	}

	/**
	 * Gets departments.
	 * 
	 * @param request the departments request
	 * @return the departments response
	 */
	@PayloadRoot(namespace = Constants.TARGET_NAMESPACE, localPart = "getDepartmentsRequest")
	@ResponsePayload
	public GetDepartmentsResponse getDepartments(@RequestPayload GetDepartmentsRequest request) {

		final GetDepartmentsResponse response = new GetDepartmentsResponse();
		final List<Department> departmentList = companyRepository.findDepartments(request.getDepartmentName());
		response.getDepartments().addAll(departmentList);
		logger.info(String.format("getDepartments(): department name[%s]", request.getDepartmentName()));
		return response;
	}

	/**
	 * Gets employees.
	 * 
	 * @param request the employees request
	 * @return the employees response
	 */
	@PayloadRoot(namespace = Constants.TARGET_NAMESPACE, localPart = "getEmployeesRequest")
	@ResponsePayload
	public GetEmployeesResponse getEmployees(@RequestPayload GetEmployeesRequest request) {

		final GetEmployeesResponse response = new GetEmployeesResponse();
		final List<Employee> employeeList = companyRepository.findEmployees(request.getDepartmentName(),
				request.getEmployeeLastName());
		response.getEmployees().addAll(employeeList);
		logger.info(String.format("getEmployees(): department name[%s], employee last name[%s]",
				request.getDepartmentName(), request.getEmployeeLastName()));
		return response;
	}
}
