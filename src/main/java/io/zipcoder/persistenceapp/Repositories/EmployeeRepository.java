package io.zipcoder.persistenceapp.Repositories;

import org.springframework.data.repository.CrudRepository;

import io.zipcoder.persistenceapp.models.Employee;

public interface  EmployeeRepository extends CrudRepository<Employee, Long> {
    
}
