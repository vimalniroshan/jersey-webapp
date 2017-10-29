package com.vml.jersey.providers;

import org.glassfish.hk2.api.Factory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class EntityManagerProvider implements Factory<EntityManager> {

    private static final Logger logger = Logger.getLogger(EntityManager.class.getName());

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @Inject
    public EntityManagerProvider(@Value("db.url") final String dbUrl,
                                 @Value("db.driver.class") final String dbDriverClass,
                                 @Value("db.username") final String dbUsername,
                                 @Value("db.password") final String dbPassword,
                                 @Value("db.dialect") final String dbDialect,
                                 @Value("persistance.unit.name") final String persistanceUnitName) {

        Properties dbProperties = new Properties();
        dbProperties.setProperty("hibernate.connection.driver_class", dbDriverClass);
        dbProperties.setProperty("hibernate.connection.url", dbUrl);
        dbProperties.setProperty("hibernate.connection.username", dbUsername);
        dbProperties.setProperty("hibernate.connection.password", dbPassword);
        dbProperties.setProperty("hibernate.dialect", dbDialect);

        entityManagerFactory = Persistence.createEntityManagerFactory(persistanceUnitName, dbProperties);
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public EntityManager provide() {
        return entityManager;
    }

    @Override
    public void dispose(final EntityManager instance) {
        try {
            entityManager.close();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unable to close entityManager", e);
        }

        try{
            entityManagerFactory.close();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unable to close entityManagerFactory", e);
        }
    }
}
