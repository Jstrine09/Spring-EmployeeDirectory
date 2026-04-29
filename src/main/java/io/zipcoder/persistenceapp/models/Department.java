package io.zipcoder.persistenceapp.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dpt_num;

    private String name;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;
    
    public Department() {
    }

    public Department(String name, Employee manager) {
        this.name = name;
        this.manager = manager;
    }

    public Long getDpt_num() {
        return dpt_num;
    }

    public void setDpt_num(Long dpt_num) {
        this.dpt_num = dpt_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    
}