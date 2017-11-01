package com.vml.jersey.providers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class ObjectMapperProvider implements ContextResolver<ObjectMapper>{

    private static ObjectMapper objectMapper = createDefaultObjectMapper();

    @Override
    public ObjectMapper getContext(final Class <?> type) {
        return objectMapper;
    }

    private static ObjectMapper createDefaultObjectMapper() {
        return new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT);
    }
}
