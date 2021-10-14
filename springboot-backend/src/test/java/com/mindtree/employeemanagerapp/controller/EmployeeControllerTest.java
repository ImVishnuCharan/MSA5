package com.mindtree.employeemanagerapp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mindtree.employeemanagerapp.exception.ResourceNotFoundException;
import com.mindtree.employeemanagerapp.model.Employee;
import com.mindtree.employeemanagerapp.service.EmployeeService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

	@InjectMocks
	private EmployeeController employeeController;
	
	@Mock
	private EmployeeService employeeService;
	
	@Test
	void testGetAllEmployees() {
		
		List<Employee> mockEmployees = new ArrayList<>();
		Employee employee = new Employee("John", "Smith", "jsmith@gmail.com");
		mockEmployees.add(employee);
		
		when(employeeService.getAllEmployees()).thenReturn(mockEmployees);
		
		ResponseEntity expected = new ResponseEntity (mockEmployees, HttpStatus.ACCEPTED);
		
		ResponseEntity actual = employeeController.getAllEmployees();
				
		assertThat(expected).isEqualTo(actual);
	}
	
	@Test
	void testGetAllEmployeesWithEmptyList() {
		
		List<Employee> mockEmployees = new ArrayList<>();
		
		when(employeeService.getAllEmployees()).thenReturn(mockEmployees);
		
		ResponseEntity expected = new ResponseEntity (mockEmployees, HttpStatus.ACCEPTED);
		
		ResponseEntity actual = employeeController.getAllEmployees();
		
		assertThat(expected).isEqualTo(actual);
	}
	
	@Test
	void testGetEmployeeById() {
		
		Long id = 1L;
		
		Employee employee = new Employee("John", "Smith", "jsmith@gmail.com");
		
		employee.setId(1L);
		
		when(employeeService.getEmployeeById(id)).thenReturn(employee);
		
		ResponseEntity expected = new ResponseEntity(employee, HttpStatus.ACCEPTED);
		 
		ResponseEntity<?> actual = employeeController.getEmployeeById(id); 
		
		assertThat(expected).isEqualTo(actual);
	}
	
	@Test
	void testGetEmployeeByIdWithResourceNotFoundException() {
		
		Long id = 10L;
		
		when(employeeService.getEmployeeById(id)).thenThrow(ResourceNotFoundException.class);
		
		assertThrows(ResourceNotFoundException.class, () -> {
			employeeController.getEmployeeById(id);
		});
	}
	
	@Test
	void testCreateEmployee() {
		
		Employee employee = new Employee("John", "Smith", "jsmith@gmail.com");
		
		when(employeeService.createEmployee(employee)).thenReturn(employee);
		
		ResponseEntity expected = new ResponseEntity (employee, HttpStatus.CREATED);
		
		ResponseEntity actual = employeeController.createEmployee(employee);
		
		assertThat(expected).isEqualTo(actual);
	}
	
	@Test
	void testUpdateEmployee() {
		
		Employee newEmployee = new Employee("John", "Smith", "jsmith@gmail.com");
		
		long id = 2L;
		
		when(employeeService.updateEmployeeByIdWithNewEmployee(id, newEmployee)).thenReturn(newEmployee);
		
		ResponseEntity expected = new ResponseEntity (newEmployee, HttpStatus.ACCEPTED);
		
		ResponseEntity actual = employeeController.updateEmployee(id, newEmployee);
		
		assertThat(expected).isEqualTo(actual);
	}
	
	@Test
	void testUpdateEmployeeWithResourceNotFoundException() {
		
		Employee newEmployee = new Employee("John", "Smith", "jsmith@gmail.com");
		
		Long id = 20L;
		
		when(employeeService.updateEmployeeByIdWithNewEmployee(id, newEmployee)).thenThrow(ResourceNotFoundException.class);
		
		assertThrows(ResourceNotFoundException.class, () -> {
			employeeController.updateEmployee(id, newEmployee);	
		});
		
	}
	
	@Test
	void testDeleteEmployee() {
		
		Long id = 1L;
		
		when(employeeService.deleteEmployee(id)).thenReturn(true);
		
		ResponseEntity expected = new ResponseEntity(true , HttpStatus.GONE);
		
		ResponseEntity actual = employeeController.deleteEmployee(id);
		
		assertThat(expected).isEqualTo(actual);
	}
	
	@Test
	void testDeleteEmployeeWithResouceNotFoundException() {
		
		Long id = 30L;
		
		when(employeeService.deleteEmployee(id)).thenThrow(ResourceNotFoundException.class);
		
		assertThrows(ResourceNotFoundException.class, () -> {
			employeeController.deleteEmployee(id);
		});
	}

}
