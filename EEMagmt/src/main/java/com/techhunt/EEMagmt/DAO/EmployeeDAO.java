package com.techhunt.EEMagmt.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.techhunt.EEMagmt.model.Employee;

@Mapper
public interface  EmployeeDAO {
	List<Employee> getEmployeeList(@Param("minSalary")double minSalary, @Param("maxSalary") double maxSalary,@Param("offset") int offset, @Param("limit") int limit,@Param("sort") String sort, @Param("orderAsc")boolean orderAsc);
	List<Employee> getEmployeeListByLogin(@Param("login") String login);
	Employee getEmployeeById(@Param("id")String id);
	int updateEmployee(Employee employee);
	void deleteEmployeeById(@Param("id") String id);//TODO
	void addEmployee(Employee employee);
	void generateId(Employee employee);
	int getPaginationPage(@Param("minSalary")double minSalary, @Param("maxSalary") double maxSalary);
	
}
