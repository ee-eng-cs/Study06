package kp.repository;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.kp.ws.Company;
import com.kp.ws.Department;
import com.kp.ws.Employee;
import com.kp.ws.Title;

/**
 * The repository with the company data.
 * 
 */
@Component
public class CompanyRepository {

	private final Company company = new Company();

	/**
	 * Initializes data.
	 * 
	 */
	@PostConstruct
	public void initData() {

		int id = 1;
		for (int i = 1; i <= 2; i++) {
			final Department department = new Department();
			department.setId(i);
			department.setName(String.format("N-a-m-e-%02d", i));
			for (int j = 0; j <= 2; j++, id++) {
				final Employee employee = new Employee();
				employee.setId(id);
				employee.setFirstName(String.format("F-N-a-m-e-%02d", id));
				employee.setLastName(String.format("L-N-a-m-e-%02d", id));
				employee.setTitle(Title.values()[j]);
				department.getEmployees().add(employee);
			}
			company.getDepartments().add(department);
		}
	}

	/**
	 * Finds company.
	 * 
	 * @return the company
	 */
	public Company findCompany() {
		return company;
	}

	/**
	 * Finds departments.
	 * 
	 * @param departmentName the department name
	 * @return the list of departments
	 */
	public List<Department> findDepartments(String departmentName) {

		Assert.notNull(departmentName, "The department name must not be null");
		final List<Department> departmentList = company.getDepartments().stream()/*-*/
				.filter(dep -> departmentName.equals(dep.getName()))/*-*/
				.collect(Collectors.toList());
		return departmentList;
	}

	/**
	 * Finds employees.
	 * 
	 * @param departmentName   the department name
	 * @param employeeLastName the employee last name
	 * @return the list of employees
	 */
	public List<Employee> findEmployees(String departmentName, String employeeLastName) {

		Assert.notNull(departmentName, "The department name must not be null");
		Assert.notNull(employeeLastName, "The employee last name must not be null");
		final List<Employee> employeeList = company.getDepartments().stream()
				.filter(dep -> departmentName.equals(dep.getName()))/*-*/
				.map(Department::getEmployees)/*-*/
				.flatMap(List::stream)/*-*/
				.filter(emp -> employeeLastName.equals(emp.getLastName()))/*-*/
				.collect(Collectors.toList());
		return employeeList;
	}
}