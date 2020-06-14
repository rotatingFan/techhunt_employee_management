package com.techhunt.EEMagmt.validate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mysql.cj.util.StringUtils;
import com.techhunt.EEMagmt.model.Employee;

@Component
public class EmployeeValidation {

	public List<String> validateUpdateEmployee(Employee employee, Employee oldEmployee){
		List<String> errorList = new ArrayList<String>();
		
		//validate if id exist
		if(oldEmployee == null) {
			errorList.add("Unable to find employee. Please try again.");
			return errorList;
		}
		//validate if theres any change to current input
		if(oldEmployee.getName().equals(employee.getName()) &&
			oldEmployee.getLogin().equals(employee.getLogin()) &&
			oldEmployee.getSalary() == employee.getSalary()) {
			errorList.add("You did not make changes.");
			return errorList;
		}
		
		List<String> mandatoryErrors = validateMandatoryFields(employee);
		errorList.addAll(mandatoryErrors);
		
		//validate salary
		String salaryError = validateSalary(employee);
		if(salaryError != null) {
			errorList.add(salaryError);
		}
		return errorList;
	}
	
	public List<String> validateMandatoryFields(Employee employee){
		List<String> errorList = new ArrayList<String>();
		//validate mandatory inputs
		if(StringUtils.isNullOrEmpty(employee.getId())) {
			errorList.add("ID is mandatory. Please input an ID.");
		}
		if(StringUtils.isNullOrEmpty(employee.getLogin())) {
			errorList.add("Login name is mandatory. Please input a login name.");
		}
		if(StringUtils.isNullOrEmpty(employee.getName())){
			errorList.add("name is empty. Please input a name.");
		}
		if(employee.getSalary() == 0.00) {
			errorList.add("salary is empty. Please input a salary.");
		}
		
		return errorList;
	}
	
	public String validateSalary(Employee employee) {
		if(employee.getSalary() <= 0.00) {
			return "Salary cannot be empty or negetive.";
		}
		return null;
	}
}
