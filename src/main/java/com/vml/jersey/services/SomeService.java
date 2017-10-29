package com.vml.jersey.services;


import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import javax.persistence.EntityManager;

@Service
public class SomeService {

    @Inject
    private EntityManager entityManager;

    public SomeService() {
        System.out.println("Some Service Initialized");
    }

    public void doSomething() {
        System.out.println("Did some thing");
    }

}

