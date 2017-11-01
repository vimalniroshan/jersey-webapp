package com.vml.jersey.db;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractDao<E> implements IDao<E> {

    protected EntityManager entityManager;
    private Class<E> clazz;

    public AbstractDao(final EntityManager entityManager, final Class<E> clazz) {
        this.entityManager = entityManager;
        this.clazz = clazz;
    }

    @Override
    public <T> E find(final T id) {
        return entityManager.find(clazz, id);
    }

    @Override
    public List<E> finalAll() {
        return entityManager.createQuery(String.format("from %s", clazz.getName()), clazz).getResultList();
    }

    @Override
    public void create(final E entity) {
        entityManager.persist(entity);
    }

    @Override
    public void create(final List <E> entities) {
        entities.forEach(this::create);
    }

    @Override
    public void update(final E entity) {
        entityManager.merge(entity);
    }

    @Override
    public void update(final List <E> entities) {
        entities.forEach(this::update);
    }

    @Override
    public <T> void delete(final T id) {
        entityManager.remove(find(id));
    }
}
