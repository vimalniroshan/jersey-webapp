package com.vml.jersey.resources;

import com.vml.jersey.db.IDao;
import com.vml.jersey.models.Project;

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

@Path("project")
public class ProjectResource {

    @Inject
    private EntityManager entityManager;


    @Path("add")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(@NotNull @Valid final Project project) {
        Project result = doInTransaction(entityManager, Project.class, (IDao<Project> dao) -> {
            dao.create(project);
            return project;
        });

        return Response.ok(result).build();
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") final long id) {

        Project result = doInTransaction(entityManager, Project.class, (IDao<Project> dao) -> dao.find(id));

        return result != null ? Response.ok(result).build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}
