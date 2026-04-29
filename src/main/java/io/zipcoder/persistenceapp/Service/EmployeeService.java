package io.zipcoder.persistenceapp.Service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.zipcoder.persistenceapp.Repositories.DepartmentRepository;
import io.zipcoder.persistenceapp.Repositories.EmployeeRepository;
import io.zipcoder.persistenceapp.models.Department;
import io.zipcoder.persistenceapp.models.Employee;


@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository repository;

    @Autowired
private DepartmentRepository departmentRepository;

    public Employee createEmployee(Employee employee) {
        return repository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Employee updateEmployee(Long id, Employee employee) {
        Employee existingEmployee = repository.findById(id).orElse(null);
        if (existingEmployee != null) {
            existingEmployee.setFirstName(employee.getFirstName());
            existingEmployee.setLastName(employee.getLastName());
            existingEmployee.setTitle(employee.getTitle());
            existingEmployee.setPhoneNumber(employee.getPhoneNumber());
            existingEmployee.setEmail(employee.getEmail());
            existingEmployee.setHireDate(employee.getHireDate());
            existingEmployee.setManager(employee.getManager());
            existingEmployee.setDepartment(employee.getDepartment());
            return repository.save(existingEmployee);
        }
        return null;
    }

    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }

    public List<Employee> getEmployeesByManager(Long managerId) {
    Employee manager = repository.findById(managerId).orElse(null);
    return repository.findByManager(manager);
    }

    public List<Employee> getEmployeesWithNoManager() {
    return repository.findByManagerIsNull();
    }

    public List<Employee> getEmployeesByDepartment(Long departmentId) {
    Department department = departmentRepository.findById(departmentId).orElse(null);
    return repository.findByDepartment(department);
    }
    
    public List<Employee> getEmployeeHierarchy(Long employeeId) {
        List<Employee> hierarchy = new ArrayList<>();
        Employee employee = repository.findById(employeeId).orElse(null);
        while (employee != null && employee.getManager() != null) {
            hierarchy.add(employee.getManager());
            employee = employee.getManager();
        }
    return hierarchy;
    }

    public Employee updateEmployeeManager(Long employeeId, Long managerId) {
    Employee employee = repository.findById(employeeId).orElse(null);
    Employee manager = repository.findById(managerId).orElse(null);
    if (employee != null && manager != null) {
        employee.setManager(manager);
        return repository.save(employee);
    }
    return null;
    }

    public void removeEmployeesByDepartment(Long departmentId) {
    List<Employee> employees = getEmployeesByDepartment(departmentId);
    for (Employee employee : employees) {
        repository.deleteById(employee.getId());
        }
    }

    public void removeAllUnderManager(Long managerId) {
    List<Employee> directReports = getEmployeesByManager(managerId);
    for (Employee employee : directReports) {
        removeAllUnderManager(employee.getId()); // recursion!
        repository.deleteById(employee.getId());
        }
    }

    public void removeDirectReports(Long managerId) {
    Employee manager = repository.findById(managerId).orElse(null);
    List<Employee> directReports = getEmployeesByManager(managerId);
    for (Employee directReport : directReports) {
        List<Employee> indirectReports = getEmployeesByManager(directReport.getId());
        for (Employee indirectReport : indirectReports) {
            indirectReport.setManager(manager);
            repository.save(indirectReport);
            }
        repository.deleteById(directReport.getId());
        }
    }

    public List<Employee> getAllIndirectReports(Long managerId) {
        List<Employee> allReports = new ArrayList<>();
        List<Employee> directReports = getEmployeesByManager(managerId);
        for (Employee employee : directReports) {
        allReports.add(employee);
        allReports.addAll(getAllIndirectReports(employee.getId())); // recursion!
        }
        return allReports;
    }
}
