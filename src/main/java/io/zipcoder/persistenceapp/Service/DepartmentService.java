package io.zipcoder.persistenceapp.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.zipcoder.persistenceapp.Repositories.DepartmentRepository;
import io.zipcoder.persistenceapp.Repositories.EmployeeRepository;
import io.zipcoder.persistenceapp.models.Department;
import io.zipcoder.persistenceapp.models.Employee;

@Service
public class DepartmentService {
    
    @Autowired
    private DepartmentRepository repository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Department createDepartment(Department department) {
        return repository.save(department);
    }

    public Department updateDepartmentManager(Long departmentId, Long managerId) {
        Department department = repository.findById(departmentId).orElse(null);
        if (department != null) {
            Employee manager = employeeRepository.findById(managerId).orElse(null);
            department.setManager(manager);
            return repository.save(department);
        }
        return null;
    }

    public Department changeDepartmentName(Long departmentId, String newName) {
        Department department = repository.findById(departmentId).orElse(null);
        if (department != null) {
            department.setName(newName);
            return repository.save(department);
        }
        return null;
    }

    public List<Employee> getAllEmployeesByDepartment(Long departmentId) {
    Department department = repository.findById(departmentId).orElse(null);
    return employeeRepository.findByDepartment(department);
    }

    public Department mergeDepartments(Long sourceDepartmentId, Long targetDepartmentId) {
    Department sourceDepartment = repository.findById(sourceDepartmentId).orElse(null);
    Department targetDepartment = repository.findById(targetDepartmentId).orElse(null);
    if (sourceDepartment != null && targetDepartment != null) {
        Employee sourceManager = sourceDepartment.getManager();
        Employee targetManager = targetDepartment.getManager();
        if (sourceManager != null) {
            sourceManager.setManager(targetManager);
            employeeRepository.save(sourceManager);
        }
        List<Employee> employeesToMove = employeeRepository.findByDepartment(sourceDepartment);
        for (Employee employee : employeesToMove) {
            employee.setDepartment(targetDepartment);
            employeeRepository.save(employee);
        }
        repository.delete(sourceDepartment);
        return targetDepartment;
    }
    return null;
}
}