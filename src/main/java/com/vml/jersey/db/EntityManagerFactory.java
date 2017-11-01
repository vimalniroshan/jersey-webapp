package com.vml.jersey.db;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.PerThread;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Service
public class EntityManagerFactory implements Factory<EntityManager> {

    private static final Logger logger = Logger.getLogger(EntityManagerFactory.class.getName());

    @Inject
    private javax.persistence.EntityManagerFactory entityManagerFactory;

    @Override
    @PerThread
    public EntityManager provide() {
        return entityManagerFactory.createEntityManager();
    }

    @Override
    public void dispose(final EntityManager instance) {
        try {
            instance.close();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unable to close entityManager", e);
        }
    }
}
