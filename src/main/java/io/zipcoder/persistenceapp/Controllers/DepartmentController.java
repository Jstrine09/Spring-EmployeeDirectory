package io.zipcoder.persistenceapp.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.zipcoder.persistenceapp.Service.DepartmentService;
import io.zipcoder.persistenceapp.models.Department;
import io.zipcoder.persistenceapp.models.Employee;

@RestController
@RequestMapping("/API/departments")
public class DepartmentController {
    
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        return new ResponseEntity<>(departmentService.createDepartment(department), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/manager/{managerId}")
    public ResponseEntity<Department> updateDepartmentManager(@PathVariable Long id, @PathVariable Long managerId) {
        return ResponseEntity.ok(departmentService.updateDepartmentManager(id, managerId));
    }

    @PutMapping("/{id}/name")
    public ResponseEntity<Department> changeDepartmentName(@PathVariable Long id, @RequestBody String newName) {
        return ResponseEntity.ok(departmentService.changeDepartmentName(id, newName));
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity<List<Employee>> getAllEmployeesByDepartment(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getAllEmployeesByDepartment(id));
    }

    @PutMapping("/merge/{sourceId}/{targetId}")
    public ResponseEntity<Department> mergeDepartments(@PathVariable Long sourceId, @PathVariable Long targetId) {
        return ResponseEntity.ok(departmentService.mergeDepartments(sourceId, targetId));
    }
}