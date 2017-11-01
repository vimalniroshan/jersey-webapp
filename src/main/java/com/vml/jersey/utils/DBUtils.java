package com.vml.jersey.utils;

import com.vml.jersey.db.AbstractDao;
import com.vml.jersey.db.IDao;

import javax.persistence.EntityManager;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class DBUtils {

    public static <T> T doInTransaction(final EntityManager entityManager, final Class<T> clazz, final Function<IDao<T>, T> function) {
        return doInTransaction(entityManager, () -> function.apply(new AbstractDao<T>(entityManager, clazz) {}));
    }

    public static <T> T doInTransaction(final EntityManager entityManager, final Function<EntityManager, T> function) {
        return doInTransaction(entityManager, () -> function.apply(entityManager));
    }

    public static <T> void doInTransaction(final EntityManager entityManager, final Class<T> clazz, final Consumer<IDao<T>> consumer) {
        doInTransaction(entityManager, () -> consumer.accept(new AbstractDao<T>(entityManager, clazz) {}));
    }

    public static <T> void doInTransaction(final EntityManager entityManager, final Consumer<EntityManager> consumer) {
        doInTransaction(entityManager, () -> consumer.accept(entityManager));
    }

    @FunctionalInterface
    private interface Command {
        void execute();
    }

    private static <A> void doInTransaction(final EntityManager entityManager, final Command command) {
        try{
            entityManager.getTransaction().begin();
            command.execute();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    private static <T, A> T doInTransaction(final EntityManager entityManager, final Supplier<T> supplier) {
        try{
            entityManager.getTransaction().begin();
            T t = supplier.get();
            entityManager.getTransaction().commit();
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            throw e;
        }
    }
}
