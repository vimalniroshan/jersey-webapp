package com.vml.jersey.db;

import java.util.List;

public interface IDao<E> {

    <T> E find(T id);

    List<E> finalAll();

    void create(E entity);

    void create(List<E> entities);

    void update(E entity);

    void update(List<E> entities);

    <T> void delete(T id);
}
