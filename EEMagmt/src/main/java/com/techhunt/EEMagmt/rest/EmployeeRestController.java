package com.techhunt.EEMagmt.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.techhunt.EEMagmt.model.Employee;
import com.techhunt.EEMagmt.model.GetEmployeeResponse;
import com.techhunt.EEMagmt.model.JSONResponse;
import com.techhunt.EEMagmt.service.EmployeeService;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeRestController {
	
	@Autowired
	EmployeeService employeeService;
	
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public @ResponseBody GetEmployeeResponse getEmployeeList(@RequestParam("minSalary")double minSalary, @RequestParam("maxSalary") double maxSalary, @RequestParam("offset") int offset, @RequestParam("limit") int limit, @RequestParam("sort") String sort, HttpServletResponse response) {
		
		GetEmployeeResponse resp = new GetEmployeeResponse();
		
		boolean orderAsc = true;
		System.out.println(sort.substring(0,1));
		if(sort.substring(0,1).equals("-")) {
			orderAsc = false;
		}
		System.out.println(orderAsc);
		sort = sort.substring(1, sort.length());
		if(!sort.equals("id") && !sort.equals("name") && !sort.equals("login") && !sort.equals("salary")) {
			response.setStatus( HttpServletResponse.SC_BAD_REQUEST  );
			resp.setError("Invalid sort");
			return resp;
		}
		if(limit>30) {
			response.setStatus( HttpServletResponse.SC_BAD_REQUEST  );
			response.setStatus( HttpServletResponse.SC_BAD_REQUEST  );
			resp.setError("Over limit");
			return resp;
		}
		if(minSalary<0 || maxSalary<0) {
			response.setStatus( HttpServletResponse.SC_BAD_REQUEST  );
			resp.setError("No negetive salary allowed");
			return resp;
		}
		if(minSalary > maxSalary) {
			response.setStatus( HttpServletResponse.SC_BAD_REQUEST  );
			resp.setError("min salary more than max salary");
			return resp;
		}
		if(maxSalary>4000) {
			response.setStatus( HttpServletResponse.SC_BAD_REQUEST  );
			resp.setError("Over salary search limit");
			return resp;
		}
		
		
		resp = employeeService.getEmployeePagination(minSalary, maxSalary, offset, limit,sort,orderAsc);
		return resp;
	}
	
	@RequestMapping(value="/users/{id}", method=RequestMethod.PATCH)
	public @ResponseBody JSONResponse updateEmployee(@RequestBody final Employee employee, @PathVariable String id) {
		JSONResponse resp = new JSONResponse();
		if(employee.getId()!=null && employee.getId().equals(id) == false) {
			resp.setStatus(JSONResponse.FAIL);
			resp.setErrorMessage("You are not able to update id of employee.");
		}else {
			resp = employeeService.updateEmployee(employee,true);
		}
		return resp;
	}
	
	@RequestMapping(value="/users", method=RequestMethod.POST)
	public @ResponseBody JSONResponse addEmployee(@RequestBody final Employee employee){
		JSONResponse resp = new JSONResponse();
		resp = employeeService.addEmployee(employee,true);
		return resp;
	}
	
	@RequestMapping(value="/users/{id}", method=RequestMethod.DELETE)
	public @ResponseBody JSONResponse deleteEmployee(@PathVariable String id){
		JSONResponse resp = new JSONResponse();
		resp = employeeService.deleteEmployee(id,true);
		return resp;
	}
	
	@RequestMapping(value="/users/upload", method=RequestMethod.POST)
	public @ResponseBody JSONResponse uploadEmployee(@RequestBody final MultipartFile file) {
		JSONResponse resp = new JSONResponse();
		List<Employee> data = new ArrayList<Employee>();
		try {
			
			Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
			 CSVReader readercsv = new CSVReader(reader);
		     String [] nextLine;
		     int count = 0;
		     while ((nextLine = readercsv.readNext()) != null) {
		        // nextLine[] is an array of values from the line
		        System.out.println(nextLine[0] + nextLine[1] + "etc...");
		        if(count ==0) {
		        	System.out.println("SKIP");
			        count++;
		        	continue;
		        }
		        if(nextLine != null && (nextLine.length==1 || nextLine.length==4)) {
		        	if(StringUtils.isEmpty(nextLine[0])){
		        		System.out.println("EXTRACTING DATA FOR " + nextLine[0]);
		        		Employee employee = new Employee();
		        		employee.setId(StringUtils.isEmpty(nextLine[0])?null:nextLine[0]);
		        		employee.setName(nextLine[1]);
		        		employee.setLogin(nextLine[2]);
		        		employee.setSalary(Double.parseDouble(nextLine[3]));
		        		data.add(employee);
		        	}else if(!nextLine[0].substring(0,1).equals("#")){
		        		System.out.println(count);
		        		System.out.println("EXTRACTING DATA FOR " + nextLine[0]);
		        		Employee employee = new Employee();
		        		employee.setId(StringUtils.isEmpty(nextLine[0])?null:nextLine[0]);
		        		employee.setName(nextLine[1]);
		        		employee.setLogin(nextLine[2]);
		        		employee.setSalary(Double.parseDouble(nextLine[3]));
		        		data.add(employee);
		        	}
		        }
		        count++;
		     }
		     resp = employeeService.uploadEmployees(data);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return resp;
	}
}
