package com.vml.jersey;

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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProjectResourceTest {

    private static HttpServer server;
    private static WebTarget target;

    private static List<Project> projects;

    @BeforeClass
    public static void setUp() throws Exception {
        // start the server
        server = Main.startServer();

        // create the client
        Client c = ClientBuilder.newClient()
                .property(LoggingFeature.LOGGING_FEATURE_VERBOSITY_CLIENT, LoggingFeature.Verbosity.PAYLOAD_ANY);;

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);

        projects = createInitialTestData();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        server.shutdownNow();
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void addProject() {
        Project project = new Project();
        project.setName("grizzly");

        Project p = target.path("project/add").request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(project, MediaType.APPLICATION_JSON)).readEntity(Project.class);

        assertNotNull(p.getId());
        assertEquals(p.getName(), project.getName());
    }

    @Test
    public void getProject(){
        Project project = target.path("project/" + projects.get(0).getId())
                .request(MediaType.APPLICATION_JSON)
                .get(Project.class);

        assertNotNull(project.getId());
        assertEquals(project.getName(), projects.get(0).getName());
    }

    public static List<Project> createInitialTestData() {

        Project p = new Project();
        p.setName("jesery");

        p = target.path("project/add").request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(p, MediaType.APPLICATION_JSON)).readEntity(Project.class);


        return Arrays.asList(p);
    }
}
