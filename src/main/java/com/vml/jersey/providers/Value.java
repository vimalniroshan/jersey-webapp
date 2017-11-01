package com.vml.jersey.providers;

import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.ServiceHandle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface Value {
    String value() default "";


    class ValueInjectionResolver implements InjectionResolver<Value> {

        @Override
        public Object resolve(final Injectee injectee, final ServiceHandle<?> root) {
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
}
