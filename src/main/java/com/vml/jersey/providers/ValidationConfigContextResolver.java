package com.vml.jersey.providers;

import org.glassfish.jersey.server.validation.ValidationConfig;
import org.glassfish.jersey.server.validation.internal.InjectingConstraintValidatorFactory;
import org.hibernate.validator.parameternameprovider.ParanamerParameterNameProvider;

import javax.validation.ParameterNameProvider;
import javax.validation.Validation;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Provider
public class ValidationConfigContextResolver implements ContextResolver<ValidationConfig> {

    @Context
    private ResourceContext resourceContext;

    @Override
    public ValidationConfig getContext(final Class <?> type) {
        return new ValidationConfig()
                .constraintValidatorFactory(resourceContext.getResource(InjectingConstraintValidatorFactory.class))
                .parameterNameProvider(new ParameterNameProvider() {

                    private ParameterNameProvider nameProvider = Validation
                            .byDefaultProvider().configure().getDefaultParameterNameProvider();

                    @Override
                    public List<String> getParameterNames(final Constructor<?> constructor) {
                        return nameProvider.getParameterNames(constructor);
                    }

                    @Override
                    public List <String> getParameterNames(final Method method) {
                        Class<?>[] params = method.getParameterTypes();
                        if (params.length > 0) {
                            return Arrays.stream(params)
                                    .map(c -> c.getSimpleName().toLowerCase())
                                    .collect(Collectors.toList());
                        } else {
                            return nameProvider.getParameterNames(method);
                        }
                    }
                });

    }
}
