package com.techhunt.EEMagmt.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techhunt.EEMagmt.DAO.EmployeeDAO;
import com.techhunt.EEMagmt.model.Employee;
import com.techhunt.EEMagmt.model.GetEmployeeResponse;
import com.techhunt.EEMagmt.model.JSONResponse;
import com.techhunt.EEMagmt.validate.EmployeeValidation;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeDAO employeeDao;
	
	@Autowired
	private EmployeeValidation employeeValidation;
	
	public GetEmployeeResponse getEmployeePagination (double minSalary, double maxSalary, int offset, int limit,String sort, boolean orderAsc){
		GetEmployeeResponse resp = new GetEmployeeResponse();
		List<Employee> employees = getEmployeeList(minSalary, maxSalary, limit*offset, limit,sort,orderAsc);
		resp.setResults(employees);
		resp.setMaxPage(employeeDao.getPaginationPage(minSalary, maxSalary));
		return resp;
	}
	
	public List<Employee> getEmployeeList(double minSalary, double maxSalary, int offset, int limit,String sort, boolean orderAsc){
		
		return employeeDao.getEmployeeList(minSalary, maxSalary, offset, limit,sort,orderAsc);
	}
	
	public String generateEmployeeId() {
		Employee employee = new Employee();
		employeeDao.generateId(employee);
		while(null != employeeDao.getEmployeeById(employee.getId())) {
			employeeDao.generateId(employee);
		}
		String id = "e"+StringUtils.leftPad(employee.getId(), 9, "0");
		return id;
	}
	
	public JSONResponse updateEmployee(Employee employee, boolean insertDB) {
		JSONResponse resp = new JSONResponse();
		
		Employee oldEmployee = null;
		if(!StringUtils.isEmpty(employee.getId())) {
			oldEmployee = employeeDao.getEmployeeById(employee.getId());
		}
		List<String> errorList = employeeValidation.validateUpdateEmployee(employee, oldEmployee);
		if(errorList.size()!=0) {
			resp.setStatus(JSONResponse.FAIL);
			resp.setErrorMessage(String.join("|", errorList));
			return resp;
		}
		//update employee
		if(insertDB == true) {
			int rowUpdated = employeeDao.updateEmployee(employee);
			if(rowUpdated==0) {
				resp.setStatus(JSONResponse.FAIL);
				resp.setErrorMessage("A more updated version of record found. Please refresh and try again.");
				return resp;
			}
		}
		
		resp.setStatus(JSONResponse.SUCCESS);
		return resp;
	}
	
	public JSONResponse addEmployee(Employee employee, boolean insertDB) {
		JSONResponse resp = new JSONResponse();
		
		List<Employee> sameLoginEmployees = null;
		if(StringUtils.isEmpty(employee.getName())) {
			sameLoginEmployees = employeeDao.getEmployeeListByLogin(employee.getName());
		}
		List<String> errorList = employeeValidation.validateAddEmployee(employee,sameLoginEmployees);
		if(errorList.size()!=0) {
			resp.setStatus(JSONResponse.FAIL);
			resp.setErrorMessage(String.join("|", errorList));
			return resp;
		}
		//generate id
		String id = generateEmployeeId();
		employee.setId(id);
		//add employee
		if(insertDB == true) {
			employeeDao.addEmployee(employee);
		}
		resp.setStatus(JSONResponse.SUCCESS);
		return resp;
	}
	
	public JSONResponse deleteEmployee(String id, boolean insertDB) {
		JSONResponse resp = new JSONResponse();
		if(StringUtils.isEmpty(id)) {
			resp.setStatus(JSONResponse.FAIL);
			resp.setErrorMessage("Invalid ID");
			return resp;
		}
		if(insertDB == true) {
			employeeDao.deleteEmployeeById(id);
		}
		resp.setStatus(JSONResponse.SUCCESS);
		
		return resp;
	}
	
	@Transactional
	public JSONResponse uploadEmployees(List<Employee>employeeList) {
		JSONResponse resp = new JSONResponse();

		List<Employee>faildata = new ArrayList<Employee>();
		
		boolean fail = false;
		for(Employee employee : employeeList) {
			List<String> errorList = employeeValidation.validateUploadEmployee(employee);
			if(errorList.size()!=0) {
				fail = true;
				employee.setErrorList(errorList);
				faildata.add(employee);
			}
		}
		if(fail == false) {
			//add to db
			for(Employee employee : employeeList) {
				//check if ID is avaliable
				Employee oldEmployee = employeeDao.getEmployeeById(employee.getId());
				if(null != oldEmployee) {
					//update
					if(oldEmployee.getLogin().equals(employee.getLogin()) == false) {
						//get new id for old employee
						String id = generateEmployeeId();
						oldEmployee.setId(id);
						//update current employee to his id
						employee.setVersion(oldEmployee.getVersion());
						employeeDao.updateEmployee(employee);
						//insert old employee with his new id
						employeeDao.addEmployee(oldEmployee);
					}else {
						//update current employee
						employee.setVersion(oldEmployee.getVersion());
						employeeDao.updateEmployee(employee);
					}
				}else {
					//insert
					String id = generateEmployeeId();
					employee.setId(id);
					employeeDao.addEmployee(employee);
				}
			}
			resp.setStatus(JSONResponse.SUCCESS);
		}else {

			resp.setStatus(JSONResponse.FAIL);
			resp.setData(faildata);
		}
		return resp;
	}
	
}
