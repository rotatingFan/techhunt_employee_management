package com.techhunt.EEMagmt.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.techhunt.EEMagmt.model.Employee;
import com.techhunt.EEMagmt.model.JSONResponse;
import com.techhunt.EEMagmt.service.EmployeeService;


@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeRestController {
	
	@Autowired
	EmployeeService employeeService;
	
	@RequestMapping(value="/getEmployeeList", method=RequestMethod.GET)
	public @ResponseBody JSONResponse getEmployeeList() {
		JSONResponse resp = new JSONResponse();
		List<Employee> employeeList = employeeService.getEmployeeList();
		resp.setStatus(JSONResponse.SUCCESS);
		resp.setData(employeeList);
		return resp;
	}
	
	@RequestMapping(value="/updateEmployee", method=RequestMethod.POST)
	public @ResponseBody JSONResponse updateEmployee(@RequestBody final Employee employee) {
		JSONResponse resp = new JSONResponse();
		resp = employeeService.updateEmployee(employee);
		return resp;
	}
	
	@RequestMapping(value="/addEmployee", method=RequestMethod.POST)
	public @ResponseBody JSONResponse addEmployee(@RequestBody final Employee employee) {
		JSONResponse resp = new JSONResponse();
		resp = employeeService.addEmployee(employee);
		return resp;
	}
}
