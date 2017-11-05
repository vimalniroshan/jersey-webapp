package com.vml.jersey;

import com.vml.jersey.models.Employee;
import com.vml.jersey.models.Project;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.logging.LoggingFeature;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EmployeeResourceTest {

    private static HttpServer server;
    private static WebTarget target;

    private static List<Employee> employees;

    @BeforeClass
    public static void setUp() throws Exception {
        // start the server
        server = Main.startServer();


        // create the client
        Client c = ClientBuilder
                .newClient()
                .property(LoggingFeature.LOGGING_FEATURE_VERBOSITY_CLIENT, LoggingFeature.Verbosity.PAYLOAD_ANY);

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);

        employees = createInitialTestData();
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
        e.setName("Nirosh");
        e.setDob(new Date());
        e.setEmail("nirosh@vimal.com");
        e.setGender(Employee.Gender.MALE);
        e.setProject(employees.get(0).getProject());

        Employee employee = target.path("employee/add").request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(e, MediaType.APPLICATION_JSON)).readEntity(Employee.class);

        assertNotNull(employee.getId());
        assertEquals(employee.getName(), "Nirosh");
        assertEquals(employee.getDob(), e.getDob());
        assertEquals(employee.getEmail(), e.getEmail());
        assertEquals(employee.getProject().getId(), e.getProject().getId());
        assertEquals(employee.getProject().getName(), e.getProject().getName());
    }

    @Test
    public void getEmployee(){
        Employee employee = target.path("employee/" + employees.get(0).getId())
                .request(MediaType.APPLICATION_JSON)
                .get(Employee.class);

        assertEquals(employee.getId(), employees.get(0).getId());
        assertEquals(employee.getName(), employees.get(0).getName());
        assertEquals(employee.getDob(), employees.get(0).getDob());
        assertEquals(employee.getEmail(), employees.get(0).getEmail());
        assertEquals(employee.getProject().getId(), employees.get(0).getProject().getId());
        assertEquals(employee.getProject().getName(), employees.get(0).getProject().getName());
    }

    public static List<Employee> createInitialTestData() {

        Project p = new Project();
        p.setName("jesery");

        p = target.path("project/add").request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(p, MediaType.APPLICATION_JSON)).readEntity(Project.class);

        Employee e = new Employee();
        e.setName("Vimal");
        e.setDob(new Date());
        e.setEmail("vimal@vimal.com");
        e.setGender(Employee.Gender.MALE);
        e.setProject(p);

        e = target.path("employee/add")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(e, MediaType.APPLICATION_JSON))
                .readEntity(Employee.class);

        return Arrays.asList(e);
    }
}
