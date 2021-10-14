package com.mindtree.employeemanagerapp.service.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.mindtree.employeemanagerapp.exception.ResourceNotFoundException;
import com.mindtree.employeemanagerapp.model.Employee;
import com.mindtree.employeemanagerapp.repository.EmployeeRepository;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
	
	@Mock
	private EmployeeRepository employeeRepository;
	
	@InjectMocks
	private EmployeeServiceImpl employeeServiceImpl;
	
	@Test
	@DirtiesContext
	void testCreateEmployee() {
		
		Employee employee = new Employee("John", "Smith", "jsmith@gmail.com");
		
		when(employeeRepository.save(employee)).thenReturn(employee);
		
		Employee createdEmployee = employeeServiceImpl.createEmployee(employee);
		
		assertThat(createdEmployee.getId()).isGreaterThanOrEqualTo(0L);
		
		assertThat(createdEmployee).isEqualTo(employee);
	}

	@Test
	void testGetAllEmployees() {
		
		List<Employee> mockEmployees = new ArrayList<>();
		Employee employee = new Employee("John", "Smith", "jsmith@gmail.com");
		mockEmployees.add(employee);
		
		when(employeeRepository.findAll()).thenReturn(mockEmployees);
		
		List<Employee> employees = employeeServiceImpl.getAllEmployees();
		
		assertThat(employees).isEqualTo(mockEmployees);
		
	}
	
	@Test
	void testGetAllEmployeesWithEmptyList() {
		
		List<Employee> mockEmployees = new ArrayList<>();
		
		when(employeeRepository.findAll()).thenReturn(mockEmployees);
		
		List<Employee> employees = employeeServiceImpl.getAllEmployees();
		
		assertThat(employees.size()).isEqualTo(0);
		
	}
	
	@Test
	void testGetAllEmployeesWithNullPointerException() {
				
		when(employeeRepository.findAll()).thenReturn(null);
		
		assertThrows(NullPointerException.class, () -> {
			employeeServiceImpl.getAllEmployees().size();
		});
		
	}
	
	@Test
	void testGetEmployeeById() {
		
		long id = 1L;
		
		Optional<Employee> employee = Optional.of(new Employee("John", "Smith", "jsmith@gmail.com"));
		
		employee.get().setId(id);
		
		when(employeeRepository.findById(id)).thenReturn(employee);
		
		Employee employeeofId1 = employeeServiceImpl.getEmployeeById(id);
		
		assertEquals(employee.get(), employeeofId1);
	}
	
	@Test
	void testGetEmployeeByIdWithResourceNotFoundException() {
		
		long id = 10L;
		
		Optional<Employee> employee = Optional.empty();
		
		when(employeeRepository.findById(id)).thenReturn(employee);
		
		assertThrows(ResourceNotFoundException.class, () -> {
			employeeServiceImpl.getEmployeeById(id);
		});
		
	}
	
	@Test
	void testUpdateEmployeeByIdWithNewEmployee() {
		
		Long id = 2L;
		
		Optional<Employee> employeeToBeUpdated = Optional.of(new Employee("John", "Doe", "johnd@gmail.com"));
		employeeToBeUpdated.get().setId(id);
		
		Employee newEmployee = new Employee("Jane", "Smith", "janes@gmail.com");
		newEmployee.setId(id);
		
		when(employeeRepository.findById(id)).thenReturn(employeeToBeUpdated);
		lenient().when(employeeRepository.save(newEmployee)).thenReturn(newEmployee);
		
		Employee updatedEmployee = employeeServiceImpl.updateEmployeeByIdWithNewEmployee(id, newEmployee);
		
		assertThat(newEmployee).isEqualTo(updatedEmployee);
		
	}
	
	@Test
	void testUpdateEmployeeByIdWithNewEmployeeWithResourceNotFoundException() {
		
		Long id = 20L;
		
		Optional<Employee> employeeToBeUpdated = Optional.empty();
		
		Employee newEmployee = new Employee("Jane", "Smith", "janes@gmail.com");
		
		when(employeeRepository.findById(id)).thenReturn(employeeToBeUpdated);
		
		assertThrows(ResourceNotFoundException.class, () -> {
			employeeServiceImpl.updateEmployeeByIdWithNewEmployee(id, newEmployee);
		});
		
	}
	
	@Test
	void testDeleteEmployee() {
		
		Long id = 3L;
		
		Optional<Employee> employeeToBeDeleted = Optional.of(new Employee("John", "Doe", "johnd@gmail.com"));
		
		when(employeeRepository.findById(id)).thenReturn(employeeToBeDeleted);
		
		assertTrue(employeeServiceImpl.deleteEmployee(id));
		
	}

	@Test
	void testDeleteEmployeeWithResourceNotFoundException() {
		
		Long id = 30L;
		
		Optional<Employee> employeeToBeDeleted = Optional.empty();
		
		when(employeeRepository.findById(id)).thenReturn(employeeToBeDeleted);
		
		assertThrows(ResourceNotFoundException.class, () ->{
			employeeServiceImpl.deleteEmployee(id);
		});
		
	}

}
