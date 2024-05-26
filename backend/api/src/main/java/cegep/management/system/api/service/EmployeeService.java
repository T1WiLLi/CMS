package cegep.management.system.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cegep.management.system.api.error.ResourceNotFoundException;
import cegep.management.system.api.model.Employee;
import cegep.management.system.api.repo.EmployeeRepository;
import jakarta.transaction.Transactional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee updateEmployee(Long employeeId, Employee updatedEmployee) {
        return employeeRepository.findById(employeeId).map(employee -> {
            employee.setPerson(updatedEmployee.getPerson());
            employee.setSeniority(updatedEmployee.getSeniority());
            employee.setType(updatedEmployee.getType());
            return employeeRepository.save(employee);
        }).orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }

    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}
