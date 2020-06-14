package com.techhunt.EEMagmt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.cj.util.StringUtils;
import com.techhunt.EEMagmt.DAO.EmployeeDAO;
import com.techhunt.EEMagmt.model.Employee;
import com.techhunt.EEMagmt.model.JSONResponse;
import com.techhunt.EEMagmt.validate.EmployeeValidation;

@Service
public class EmployeeService {


	@Autowired
	private EmployeeDAO employeeDao;
	
	@Autowired
	private EmployeeValidation employeeValidation;
	
	public List<Employee> getEmployeeList(){
		return employeeDao.getEmployeeList();
	}
	
	public JSONResponse updateEmployee(Employee employee) {
		JSONResponse resp = new JSONResponse();
		if(StringUtils.isNullOrEmpty(employee.getId())) {
			resp.setStatus(JSONResponse.FAIL);
			resp.setErrorMessage("No ID found.");
			return resp;
		}
		List<String> errorList = employeeValidation.validateUpdateEmployee(employee, employeeDao.getEmployeeById(employee.getId()));
		if(errorList.size()!=0) {
			resp.setStatus(JSONResponse.FAIL);
			resp.setErrorMessage(String.join("|", errorList));
			return resp;
		}
		//update employee
		int rowUpdated = employeeDao.updateEmployee(employee);
		if(rowUpdated==0) {
			resp.setStatus(JSONResponse.FAIL);
			resp.setErrorMessage("A more updated version of record found. Please refresh and try again.");
			return resp;
		}
		resp.setStatus(JSONResponse.SUCCESS);
		return resp;
	}
	
	public JSONResponse addEmployee(Employee employee) {
		//TODO
		return null;
	}
	
}
