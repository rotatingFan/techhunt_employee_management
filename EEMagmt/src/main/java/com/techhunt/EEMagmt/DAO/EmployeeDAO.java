package com.techhunt.EEMagmt.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.techhunt.EEMagmt.model.Employee;

@Mapper
public interface  EmployeeDAO {
	List<Employee> getEmployeeList();
	Employee getEmployeeById(@Param("id")String id);//TODO
	int updateEmployee(Employee employee);//TODO
}
