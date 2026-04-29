package io.zipcoder.persistenceapp.Repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.zipcoder.persistenceapp.models.Department;
import io.zipcoder.persistenceapp.models.Employee;

public interface  EmployeeRepository extends CrudRepository<Employee, Long> {
    List<Employee> findByManager(Employee manager);
    List<Employee> findByDepartment(Department department);
    List<Employee> findByManagerIsNull();
}
