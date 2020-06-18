package com.techhunt.EEMagmt;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.techhunt.EEMagmt.model.Employee;
import com.techhunt.EEMagmt.validate.EmployeeValidation;

@SpringBootTest
class EeMagmtApplicationTests {
	@Autowired
	private EmployeeValidation employeeValidation;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	public void updateEENoLogin() {
		Employee employee = new Employee();
		employee.setId("e1234");
		employee.setName("testName");
		employee.setLogin(null);
		employee.setSalary(1000.00);
		
		Employee oldEmployee = new Employee();
		employee.setId("e1234");
		oldEmployee.setName("testName");
		oldEmployee.setLogin("testLogin");
		oldEmployee.setSalary(1000.00);
		
		List<String> errorList = employeeValidation.validateUpdateEmployee(employee, oldEmployee);
		assertFalse(errorList.size()==0);
	}
	
	@Test
	public void updateEENoPastRecord() {
		Employee employee = new Employee();
		employee.setName("testName");
		employee.setLogin("testLogin");
		employee.setSalary(1000.00);
		List<String> errorList = employeeValidation.validateUpdateEmployee(employee, null);
		assertFalse(errorList.size()==0);
	}
	
	@Test
	public void updateEENegetiveSalary() {
		Employee employee = new Employee();
		employee.setId("e1234");
		employee.setName("testName");
		employee.setLogin("testLogin");
		employee.setSalary(-1000.00);
		
		Employee oldEmployee = new Employee();
		employee.setId("e1234");
		oldEmployee.setName("testName");
		oldEmployee.setLogin("testLogin");
		oldEmployee.setSalary(1000.00);
		
		List<String> errorList = employeeValidation.validateUpdateEmployee(employee, oldEmployee);
		assertFalse(errorList.size()==0);
	}
	
	@Test
	public void updateEEZeroSalary() {
		Employee employee = new Employee();
		employee.setId("e1234");
		employee.setName("testName");
		employee.setLogin("testLogin");
		employee.setSalary(Double.MIN_VALUE);
		
		Employee oldEmployee = new Employee();
		employee.setId("e1234");
		oldEmployee.setName("testName");
		oldEmployee.setLogin("testLogin");
		oldEmployee.setSalary(1000.00);
		
		List<String> errorList = employeeValidation.validateUpdateEmployee(employee, oldEmployee);
		assertFalse(errorList.size()==0);
	}
	
	@Test
	public void updateEENoId() {
		Employee employee = new Employee();
		employee.setName("testName");
		employee.setLogin("testLogin");
		employee.setSalary(1000.00);
		
		Employee oldEmployee = new Employee();
		employee.setId("e1234");
		oldEmployee.setName("testName");
		oldEmployee.setLogin("testLogin");
		oldEmployee.setSalary(1000.00);
		
		List<String> errorList = employeeValidation.validateUpdateEmployee(employee, oldEmployee);
		assertFalse(errorList.size()==0);
	}
	
	@Test
	public void addEENoName() {
		Employee employee = new Employee();
		employee.setLogin("testLogin");
		employee.setSalary(1000.00);
		
		List<Employee> sameLoginEmployee = null;
		
		List<String> errorList = employeeValidation.validateAddEmployee(employee,sameLoginEmployee);
		assertFalse(errorList.size()==0);
	}
	
	@Test
	public void addEENoLogin() {
		Employee employee = new Employee();
		employee.setName("testName");
		employee.setSalary(1000.00);
		
		List<Employee> sameLoginEmployee = null;
		
		List<String> errorList = employeeValidation.validateAddEmployee(employee,sameLoginEmployee);
		assertFalse(errorList.size()==0);
	}
	
	@Test
	public void addEENoSalary() {
		Employee employee = new Employee();
		employee.setName("testName");
		employee.setLogin("testLogin");
		
		List<Employee> sameLoginEmployee = null;
		
		List<String> errorList = employeeValidation.validateAddEmployee(employee,sameLoginEmployee);
		assertFalse(errorList.size()==0);
	}
	
	@Test
	public void addEEZeroSalary() {
		Employee employee = new Employee();
		employee.setName("testName");
		employee.setLogin("testLogin");
		employee.setSalary(Double.MIN_VALUE);
		
		List<Employee> sameLoginEmployee = null;
		
		List<String> errorList = employeeValidation.validateAddEmployee(employee,sameLoginEmployee);
		assertFalse(errorList.size()==0);
	}
	

	@Test
	public void addEENegetiveSalary() {
		Employee employee = new Employee();
		employee.setName("testName");
		employee.setLogin("testLogin");
		employee.setSalary(-1);
		
		List<Employee> sameLoginEmployee = null;
		
		List<String> errorList = employeeValidation.validateAddEmployee(employee,sameLoginEmployee);
		assertFalse(errorList.size()==0);
	}
	
}
