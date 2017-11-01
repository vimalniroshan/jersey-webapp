package com.vml.jersey.resources;

import com.vml.jersey.db.IDao;
import com.vml.jersey.models.Employee;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.vml.jersey.utils.DBUtils.doInTransaction;


@Path("employee")
public class EmployeeResource {

    @Inject
    private EntityManager entityManager;

    @PUT
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(@NotNull @Valid final Employee employee) {
        Employee result = doInTransaction(entityManager, Employee.class, (IDao<Employee> dao) -> {
            dao.create(employee);
            return employee;
        });

        return Response.ok(result).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") Long id) {
        Employee result = doInTransaction(entityManager, Employee.class, (IDao<Employee> dao) -> dao.find(id));

        return Response.ok(result).build();
    }

}
