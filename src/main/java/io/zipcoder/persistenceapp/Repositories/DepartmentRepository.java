package io.zipcoder.persistenceapp.Repositories;

import org.springframework.data.repository.CrudRepository;

import io.zipcoder.persistenceapp.models.Department;

public interface DepartmentRepository extends CrudRepository<Department, Long> {
    
}
