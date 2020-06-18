package com.techhunt.EEMagmt.validate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.techhunt.EEMagmt.model.Employee;

@Component
public class EmployeeValidation {
	
	public List<String> validateUploadEmployee(Employee employee){
		List<String> errorList = new ArrayList<String>();
		//check all fields are present
		if(StringUtils.isEmpty(employee.getId())) {
			errorList.add("ID is mandatory. Please input an ID.");
		}
		List<String> mandatoryErrors = validateMandatoryFields(employee);
		if(mandatoryErrors.size()!= 0) {
			return mandatoryErrors;
		}
		if(!StringUtils.isEmpty(employee.getId())) {
			//check id format
			String pattern = "^e[0-9]{9}$";
			Pattern r = Pattern.compile(pattern);
			System.out.println("match id with regex: " + employee.getId());
			Matcher m = r.matcher(employee.getId());
			if(m.find()==false) {
				errorList.add("Wrong ID format. Please use format eXXXXXXXXX where X is from 0-9.");
			}
		}

		return errorList;
	}

	public List<String> validateUpdateEmployee(Employee employee, Employee oldEmployee){
		List<String> errorList = new ArrayList<String>();
		
		//validate if id exist
		if(oldEmployee == null) {
			errorList.add("Unable to find employee. Please try again.");
			return errorList;
		}
		
		if(StringUtils.isEmpty(employee.getId())) {
			errorList.add("ID is mandatory. Please input an ID.");
		}
		
		List<String> mandatoryErrors = validateMandatoryFields(employee);
		if(mandatoryErrors.size()!= 0) {
			return mandatoryErrors;
		}
		
		//validate if theres any change to current input
		if(oldEmployee.getName().equals(employee.getName()) &&
			oldEmployee.getLogin().equals(employee.getLogin()) &&
			oldEmployee.getSalary() == employee.getSalary()) {
			errorList.add("You did not make changes.");
			return errorList;
		}
		
		//validate salary
		String salaryError = validateSalary(employee);
		if(salaryError != null) {
			errorList.add(salaryError);
		}
		return errorList;
	}
	
	public List<String> validateAddEmployee(Employee employee, List<Employee> sameLoginEmployees){
		List<String> errorList = new ArrayList<String>();
		if(null != sameLoginEmployees && sameLoginEmployees.size()!=0) {
			errorList.add("Please input a unique Login ID.");
			return errorList;
		}
		
		List<String> mandatoryErrors = validateMandatoryFields(employee);
		errorList.addAll(mandatoryErrors);
		
		return errorList;
		
	}
	
	public List<String> validateMandatoryFields(Employee employee){
		List<String> errorList = new ArrayList<String>();
		//validate mandatory inputs
		if(StringUtils.isEmpty(employee.getLogin())) {
			System.out.println("NO LOGIN ");
			errorList.add("Login name is mandatory. Please input a login name.");
		}
		if(StringUtils.isEmpty(employee.getName())){
			errorList.add("name is empty. Please input a name.");
		}
		if(employee.getSalary() <= Double.MIN_VALUE) {
			errorList.add("salary is empty. Please input a salary.");
		}
		
		
		return errorList;
	}
	
	public String validateSalary(Employee employee) {
		if(employee.getSalary() <= Double.MIN_VALUE) {
			return "Salary cannot be empty or negetive.";
		}
		return null;
	}
}
