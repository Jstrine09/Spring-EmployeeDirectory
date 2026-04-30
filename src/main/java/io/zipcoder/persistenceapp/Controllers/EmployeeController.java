package io.zipcoder.persistenceapp.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.zipcoder.persistenceapp.Service.EmployeeService;
import io.zipcoder.persistenceapp.models.Employee;

@RestController
@RequestMapping("/API/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.createEmployee(employee), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
    }

    @PutMapping("/{id}/manager/{managerId}")
    public ResponseEntity<Employee> updateEmployeeManager(@PathVariable Long id, @PathVariable Long managerId) {
        return ResponseEntity.ok(employeeService.updateEmployeeManager(id, managerId));
    }

    @GetMapping("/manager/{managerId}")
    public ResponseEntity<List<Employee>> getEmployeesByManager(@PathVariable Long managerId) {
        return ResponseEntity.ok(employeeService.getEmployeesByManager(managerId));
    }

    @GetMapping("/no-manager")
    public ResponseEntity<List<Employee>> getEmployeesWithNoManager() {
        return ResponseEntity.ok(employeeService.getEmployeesWithNoManager());
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@PathVariable Long departmentId) {
        return ResponseEntity.ok(employeeService.getEmployeesByDepartment(departmentId));
    }

    @GetMapping("/{id}/hierarchy")
    public ResponseEntity<List<Employee>> getEmployeeHierarchy(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeHierarchy(id));
    }

    @GetMapping("/manager/{managerId}/all-reports")
    public ResponseEntity<List<Employee>> getAllIndirectReports(@PathVariable Long managerId) {
        return ResponseEntity.ok(employeeService.getAllIndirectReports(managerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/department/{departmentId}")
    public ResponseEntity<Void> removeEmployeesByDepartment(@PathVariable Long departmentId) {
        employeeService.removeEmployeesByDepartment(departmentId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/manager/{managerId}/all")
    public ResponseEntity<Void> removeAllUnderManager(@PathVariable Long managerId) {
        employeeService.removeAllUnderManager(managerId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/manager/{managerId}/direct")
    public ResponseEntity<Void> removeDirectReports(@PathVariable Long managerId) {
        employeeService.removeDirectReports(managerId);
        return ResponseEntity.noContent().build();
    }
}