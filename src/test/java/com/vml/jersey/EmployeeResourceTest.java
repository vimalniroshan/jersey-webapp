package com.vml.jersey;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.vml.jersey.models.Employee;
import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EmployeeResourceTest {

    private static HttpServer server;
    private static WebTarget target;

    @BeforeClass
    public static void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        server.shutdownNow();
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void addEmployee() {
        Employee e = new Employee();
        e.setName("Vimal");
        e.setDob(new Date());
        e.setEmail("vimal@vimal.com");

        Employee employee = target.path("employee/add").request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(e, MediaType.APPLICATION_JSON)).readEntity(Employee.class);

        assertEquals(employee.getName(), "Vimal");
        assertEquals(employee.getDob(), e.getDob());
        assertNotNull(employee.getId());
    }

    @Test
    public void getEmployee(){
        Employee e = new Employee();
        e.setName("Nirosh");
        e.setDob(new Date());
        e.setEmail("nirosh@vimal.com");

        target.path("employee/add").request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(e, MediaType.APPLICATION_JSON)).readEntity(Employee.class);

        Employee employee = target.path("employee/" + e.getId()).request(MediaType.APPLICATION_JSON)
                .get(Employee.class);

        assertEquals(e.getId(), employee.getId());
        assertEquals(e.getName(), employee.getName());
        assertEquals(e.getDob(), employee.getDob());
        assertEquals(e.getEmail(), employee.getEmail());
    }
}
