package com.vml.jersey;

import com.vml.jersey.providers.EntityManagerProvider;
import com.vml.jersey.providers.Value;
import com.vml.jersey.providers.ValueInjectionResolver;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.BindingBuilderFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.ws.rs.ApplicationPath;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationPath("rest")
public class Application extends ResourceConfig {

    private static final Logger log = Logger.getLogger(Application.class.getName());

    private static final String SYSTEM_PROPERTIES_FILE = System.getProperty("system.properties.file", "system.properties");

    static {
        try {
            System.getProperties().load(Application.class.getClassLoader().getResourceAsStream(SYSTEM_PROPERTIES_FILE));
        } catch (Exception e) {
            log.log(Level.WARNING, "Unable to load system properties file with name '%s'", SYSTEM_PROPERTIES_FILE);
        }
    }

    public Application() {
        packages("com.vml.jersey");

        register(new org.glassfish.hk2.utilities.binding.AbstractBinder() {
            @Override
            protected void configure() {
                bindFactory(EntityManagerProvider.class, Singleton.class)
                        .to(EntityManager.class)
                        .in(Singleton.class);

                bind(ValueInjectionResolver.class)
                        .to(new TypeLiteral<InjectionResolver<Value>>(){})
                        .in(Singleton.class);
            }
        });

    }
}
