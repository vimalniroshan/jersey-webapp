package com.vml.jersey.resources;

import com.vml.jersey.model.Employee;
import com.vml.jersey.providers.Value;
import com.vml.jersey.services.SomeService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Properties;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
@Singleton
public class MyResource {

    @Inject
    private EntityManager entityManager;

    @Inject
    private SomeService someService;

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        someService.doSomething();

        Long id = null;

        entityManager.getTransaction().begin();
        Employee e = new Employee();
        e.setName("Vimal");
        entityManager.persist(e);
        id = e.getId();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        Employee f = entityManager.find(Employee.class, id);
        System.out.println("Found : " + f.getName());
        entityManager.getTransaction().commit();

        return "Got it!";
    }
}
