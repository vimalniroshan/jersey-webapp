package com.vml.jersey.bak;

import org.glassfish.hk2.api.PerThread;
import org.glassfish.hk2.api.Proxiable;

import javax.inject.Scope;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Scope
@PerThread
@Proxiable
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Transaction {
}
