package com.vml.jersey.providers;

import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.InjectionResolver;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ValueInjectionResolver implements InjectionResolver<Value> {

    @Override
    public Object resolve(final Injectee injectee, final ServiceHandle <?> root) {
        AnnotatedElement elm = injectee.getParent();

        Value v;
        if( elm instanceof Constructor) {
            Constructor c = (Constructor) elm;
            v = c.getParameters()[injectee.getPosition()].getAnnotation(Value.class);
        } else if (elm instanceof Method) {
            Method m = (Method) elm;
            v = m.getParameters()[injectee.getPosition()].getAnnotation(Value.class);
        } else {
            v = elm.getAnnotation(Value.class);
        }

        return System.getProperty(v.value());
    }

    @Override
    public boolean isConstructorParameterIndicator() {
        return true;
    }

    @Override
    public boolean isMethodParameterIndicator() {
        return true;
    }
}
