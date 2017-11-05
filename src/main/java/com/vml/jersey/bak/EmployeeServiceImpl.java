package com.vml.jersey.bak;


import com.vml.jersey.db.AbstractDao;
import com.vml.jersey.db.IDao;
import com.vml.jersey.models.Employee;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.UUID;

//@Service @ContractsProvided(EmployeeService.class)
public class EmployeeServiceImpl implements EmployeeService {

    @Inject
    private EntityManager entityManager;

    private IDao<Employee> employeeDao = new AbstractDao <Employee>(entityManager, Employee.class) {};

    public EmployeeServiceImpl() {
        System.out.println("Some Service Initialized");
    }

    public Employee doSomething() {
        //((AbstractDao)employeeDao).setEntityManager(entityManager);
        System.out.println("Did some thing");

        UUID id;
        //entityManager.getTransaction().begin();
        Employee e = new Employee();
        e.setName("Vimal");
        employeeDao.create(e);

        id = e.getId();
        //entityManager.getTransaction().commit();

        //entityManager.getTransaction().begin();
        Employee f = employeeDao.find(id);
        System.out.println("Found : " + f.getName());
        //entityManager.getTransaction().commit();


        return f;
    }

}

